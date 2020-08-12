package com.xiaoshu.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Dept;
import com.xiaoshu.entity.Emp;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.Tj;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.EmpService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("emp")
public class EmpController {
	@Autowired
	private EmpService es;
	@Autowired
	private OperationService operationService;

	@RequestMapping("empIndex")
	public String index(HttpServletRequest request, Integer menuid) throws Exception {
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<Dept> deptList = es.queryDept();
		request.setAttribute("deptList", deptList);
		return "emp";
	}

	@RequestMapping(value = "empList", method = RequestMethod.POST)
	public void empList(Emp emp,HttpServletRequest request, HttpServletResponse response, String offset, String limit)
			throws Exception {
		try {

			Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
			Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
			PageInfo<Emp> empList = es.findEmpPage(pageNum, pageSize,emp);

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total", empList.getTotal());
			jsonObj.put("rows", empList.getList());
			WriterUtil.write(response, jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();

			throw e;
		}
	}

	// 新增或修改
	@RequestMapping("reserveEmp")
	public void reserveEmp(HttpServletRequest request, Emp emp, HttpServletResponse response,MultipartFile pic) {
		Integer eid = emp.getEid();
		JSONObject result = new JSONObject();
		try {
			if (eid != null) { // userId不为空 说明是修改
				if(pic!=null) {
					String fileName=pic.getOriginalFilename();
					if(fileName!=null&&!fileName.equals("")) {
						pic.transferTo(new File("f:/pic/"+fileName));
						emp.setImg(fileName);
					}else {
						
					}
				}
				
				es.updateEmp(emp);
				result.put("success", true);
			} else {
				String fileName=pic.getOriginalFilename();
				if(fileName!=null&&!fileName.equals("")) {
					pic.transferTo(new File("f:/pic/"+fileName));
					emp.setImg(fileName);
				}
				es.addEmp(emp);
				result.put("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();

			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}

	@RequestMapping("deleteEmp")
	public void delEmp(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
			String[] ids = request.getParameter("ids").split(",");
			for (String id : ids) {
				es.deleteEmp(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("exp")
	public void exportPoi(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sheet=wb.createSheet();
		String[] head= {"员工编号","员工名","性别","年龄","头像","生日","部门"};
		HSSFRow frow=sheet.createRow(0);
		for(int i=0;i<head.length;i++) {
			frow.createCell(i).setCellValue(head[i]);
		}
		List<Emp> emplist=es.export();
		for(int i=0;i<emplist.size();i++) {
			Emp e=emplist.get(i);
			HSSFRow row=sheet.createRow(i+1);
			row.createCell(0).setCellValue(e.getEid());
			row.createCell(1).setCellValue(e.getEname());
			row.createCell(2).setCellValue(e.getSex());
			row.createCell(3).setCellValue(e.getAge());
			row.createCell(4).setCellValue(e.getImg());
			row.createCell(5).setCellValue(TimeUtil.formatTime(e.getBirthday(), "yyyy-MM-dd"));
			row.createCell(6).setCellValue(e.getDept().getDname());
			
		}
//		File exc=new File("d:/h1909表.xls");
//		OutputStream os=new FileOutputStream(exc);
//		wb.write(os);
//		wb.close();
		
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("大数据P7H1909D.xls", "UTF-8"));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/octet-stream");

		//5.2通过io写出
		wb.write(response.getOutputStream());	
		wb.close();
	}
	
	
	@RequestMapping("importexcel")
	public void importExcel(HttpServletRequest request,HttpServletResponse response,MultipartFile excel) throws Exception {
		JSONObject result = new JSONObject();
		//1.通过上传的文件输入流得到工作簿
		HSSFWorkbook wb=new HSSFWorkbook(excel.getInputStream());
		//2.通过下标得到工作簿中的第一张表
		HSSFSheet sheet=wb.getSheetAt(0);
		//3.通过LastRowNum得到总行数，总行数就是你的循环次数
		int hs=sheet.getLastRowNum();
		//4.循环，循环从几开始取决于你是否有表头，因为我的代码有表头，所以我从1开始。
		for(int i=1;i<=hs;i++) {
			//5.循环得到行
			HSSFRow r=sheet.getRow(i);
			Emp e=new Emp();
			
			e.setEname(r.getCell(1).getStringCellValue());
			e.setSex(r.getCell(2).getStringCellValue());
			e.setAge((int)r.getCell(3).getNumericCellValue());
			e.setImg(r.getCell(4).getStringCellValue());
			e.setBirthday(r.getCell(5).getDateCellValue());
			String dname=r.getCell(6).getStringCellValue();
			Dept d=es.getDid(dname);
			if(d==null) {
				//没查着
				Dept d2=new Dept();
				d2.setDname(dname);
				es.addDept(d2);
				Dept d3=es.getDid(d2.getDname());
				e.setDid(d3.getDid());
			}else {
				//查到了
				e.setDid(d.getDid());
			}
			es.addEmp(e);
		}
		result.put("success", true);
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("tj")
	public void getTj(HttpServletRequest request,HttpServletResponse response) {
		List<Tj> tl=es.getTj();
		Object json=JSONObject.toJSON(tl);
		WriterUtil.write(response, json.toString());
	}

}
