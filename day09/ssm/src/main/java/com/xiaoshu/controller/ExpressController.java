package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Attachment;
import com.xiaoshu.entity.ExpressCompany;
import com.xiaoshu.entity.ExpressPerson;
import com.xiaoshu.entity.ExpressVo;
import com.xiaoshu.entity.Log;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.ExpressService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("express")
public class ExpressController extends LogController{
	static Logger logger = Logger.getLogger(ExpressController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	@Autowired
	private ExpressService expressService;
	
	@RequestMapping("expressIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Role> roleList = roleService.findRole(new Role());
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		//查询所有公司
		List<ExpressCompany> clist = expressService.findAllCompany();
		request.setAttribute("clist", clist);
		request.setAttribute("operationList", operationList);
		request.setAttribute("roleList", roleList);
		return "express";
	}
	
	
	@RequestMapping(value="expressList",method=RequestMethod.POST)
	public void expressList(ExpressVo expressPerson,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<ExpressVo> userList= expressService.findUserPage(expressPerson,pageNum,pageSize);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",userList.getTotal() );
			jsonObj.put("rows", userList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveExpress")
	public void reserveUser(HttpServletRequest request,ExpressPerson person,HttpServletResponse response){
		Integer id = person.getId();
		JSONObject result=new JSONObject();
		try {
			if (id != null) {   // userId不为空 说明是修改
				person.setId(id);
				expressService.updateExpress(person);
				result.put("success", true);
			}else {   // 添加
				expressService.addExpress(person);
				result.put("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("deleteExpress")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				expressService.deleteExpress(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	//导入
	@RequestMapping("importExpress")
	public void importExpress(MultipartFile expressXls,HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			//获取webbook
			HSSFWorkbook wb = new HSSFWorkbook(expressXls.getInputStream());
			//获取sheet页
			HSSFSheet sheet = wb.getSheetAt(0);
			//获取最后一行行数
			int lastRowNum = sheet.getLastRowNum();
			for (int i = 1; i <= lastRowNum; i++) {
				//获取每一行的对象
				HSSFRow row = sheet.getRow(i);
				String expressName = row.getCell(0).toString();
				String sex = row.getCell(1).toString();
				if(sex.equals("女")){
					continue;
				}
				String expressTrait = row.getCell(2).toString();
				Date entryTime = row.getCell(3).getDateCellValue();
				Date createTime = row.getCell(4).getDateCellValue();
				String cname = row.getCell(5).toString();
				if(!cname.equals("京东快递")){
					continue;
				}
				int expressTypeId = expressService.findCidByCname(cname);
				//封装实体类
				ExpressPerson person = new ExpressPerson();
				person.setCreateTime(createTime);
				person.setEntryTime(entryTime);
				person.setExpressName(expressName);
				person.setExpressTrait(expressTrait);
				person.setExpressTypeId(expressTypeId);
				person.setSex(sex);
				//保存
				expressService.addExpress(person);
			}
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	/**
	 * 备份
	 */
	@RequestMapping("exportExpress")
	public void backup(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try {
			String time = TimeUtil.formatTime(new Date(), "yyyyMMddHHmmss");
		    String excelName = "手动备份"+time;
		    ExpressVo person = new ExpressVo();
			List<ExpressVo> list = expressService.findPage(person);
			String[] handers = {"员工编号","员工姓名","员工性别","员工特点","入职时间","创建时间","所属公司"};
			// 1导入硬盘
			ExportExcelToDisk(request,response,handers,list, excelName);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("", "对不起，备份失败");
		}
	}
	
	// 导出到硬盘
	@SuppressWarnings("resource")
	private void ExportExcelToDisk(HttpServletRequest request,HttpServletResponse response,
			String[] handers, List<ExpressVo> list, String excleName) throws Exception {
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook();//创建工作簿
			HSSFSheet sheet = wb.createSheet("操作记录备份");//第一个sheet
			HSSFRow rowFirst = sheet.createRow(0);//第一个sheet第一行为标题
			rowFirst.setHeight((short) 500);
			for (int i = 0; i < handers.length; i++) {
				sheet.setColumnWidth((short) i, (short) 4000);// 设置列宽
			}
			//写标题了
			for (int i = 0; i < handers.length; i++) {
			    //获取第一行的每一个单元格
			    HSSFCell cell = rowFirst.createCell(i);
			    //往单元格里面写入值
			    cell.setCellValue(handers[i]);
			}
			for (int i = 0;i < list.size(); i++) {
			    //获取list里面存在是数据集对象
			    ExpressVo vo = list.get(i);
			    //创建数据行
			    HSSFRow row = sheet.createRow(i+1);
			    //设置对应单元格的值
			    row.setHeight((short)400);   // 设置每行的高度
			    //"员工编号","员工姓名","员工性别","员工特点","入职时间","创建时间","所属公司"
			    row.createCell(0).setCellValue(i+1);
			    row.createCell(1).setCellValue(vo.getExpressName());
			    row.createCell(2).setCellValue(vo.getSex());
			    row.createCell(3).setCellValue(vo.getExpressTrait());
			    row.createCell(4).setCellValue(TimeUtil.formatTime(vo.getEntryTime(), "yyyy-MM-dd"));
			    row.createCell(5).setCellValue(TimeUtil.formatTime(vo.getCreateTime(), "yyyy-MM-dd"));
			    row.createCell(6).setCellValue(vo.getCname());
			}
			//写出文件（path为文件路径含文件名）
				response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("员工列表.xls", "UTF-8"));
				response.setHeader("Connection", "close");
				response.setHeader("Content-Type", "application/octet-stream");
		        wb.write(response.getOutputStream());
		        wb.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
	}
	
}
