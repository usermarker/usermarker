package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.dao.TeacherMapper;
import com.xiaoshu.entity.Attachment;
import com.xiaoshu.entity.Log;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.Teacher;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("student")
public class StudentController extends LogController{
	static Logger logger = Logger.getLogger(StudentController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeacherMapper teacherMapper;
	
	@RequestMapping("studentIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<Teacher> list = teacherMapper.selectAll();
		request.setAttribute("list", list);
		return "student";
	}
	
	
	@RequestMapping(value="studentList",method=RequestMethod.POST)
	public void studentList(StudentVo studentVo, HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<StudentVo> info = studentService.findStudentPage(studentVo, pageNum, pageSize);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",info.getTotal() );
			jsonObj.put("rows", info.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveStudent")
	public void reserveStudent(HttpServletRequest request,StudentVo studentVo,HttpServletResponse response){
		Integer id = studentVo.getId();
		JSONObject result=new JSONObject();
		try {
			if (id != null) {   // userId不为空 说明是修改
				studentService.updateStudent(studentVo);
				result.put("success", true);
//				User userName = userService.existUserWithUserName(user.getUsername());
//				if(userName != null && userName.getUserid().compareTo(userId)==0){
//					user.setUserid(userId);
//					userService.updateUser(user);
//					result.put("success", true);
//				}else{
//					result.put("success", true);
//					result.put("errorMsg", "该用户名被使用");
//				}
				
			}else {   // 添加
				studentVo.setEntrytime(new Date());
				studentService.addStudent(studentVo);
				result.put("success", true);
//				if(userService.existUserWithUserName(user.getUsername())==null){  // 没有重复可以添加
//					userService.addUser(user);
//					result.put("success", true);
//				} else {
//					result.put("success", true);
//					result.put("errorMsg", "该用户名被使用");
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	// 新增或修改
		@RequestMapping("reservetype")
		public void reservetype(HttpServletRequest request,Teacher teacher,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				studentService.findType(teacher);
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("保存用户信息错误",e);
				result.put("success", true);
				result.put("errorMsg", "对不起，操作失败");
			}
			WriterUtil.write(response, result.toString());
		}
	
	@RequestMapping("deleteStudent")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				studentService.deleteStudent(Integer.parseInt(id));;
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
	
	@RequestMapping("deleteEcharts")
	public void deleteEcharts(StudentVo studentVo,HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			List<StudentVo> list= studentService.findEcharts(studentVo);
			result.put("list", list);
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
	@RequestMapping("exportStudent")
	public void backup(HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		try {
			String time = TimeUtil.formatTime(new Date(), "yyyyMMddHHmmss");
		    String excelName = "手动备份"+time;
		    StudentVo studentVo = new StudentVo();
		    List<StudentVo> list = studentService.findStudent(studentVo);
			/*
			 * 用户编号 学生姓名 老师姓名 年龄 所在年级 入校时间
			 */
			String[] handers = {"用户编号","学生姓名","老师姓名","年龄","所在年级","入校时间"};
			// 1导入硬盘
			ExportExcelToDisk(request,handers,list, excelName);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("", "对不起，备份失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	
	// 导出到硬盘 bgh4gcZY&^
	@SuppressWarnings("resource")
	private void ExportExcelToDisk(HttpServletRequest request,
			String[] handers, List<StudentVo> list, String excleName) throws Exception {
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
			    StudentVo vo = list.get(i);
			    //创建数据行
			    HSSFRow row = sheet.createRow(i+1);
			    //设置对应单元格的值
			    row.setHeight((short)400);   // 设置每行的高度
			    //"序号","操作人","IP地址","操作时间","操作模块","操作类型","详情"
				/*
				 * 用户编号 学生姓名 老师姓名 年龄 所在年级 入校时间
				 */
			    row.createCell(0).setCellValue(vo.getCode());
			    row.createCell(1).setCellValue(vo.getName());
			    row.createCell(2).setCellValue(vo.getCname());
			    row.createCell(3).setCellValue(vo.getAge());
			    row.createCell(4).setCellValue(vo.getGrade());
			    row.createCell(5).setCellValue(TimeUtil.formatTime(vo.getEntrytime(), "yyyy-MM-dd"));
			}
			//写出文件（path为文件路径含文件名）
				OutputStream os;
				File file = new File("E:/img/"+excleName+".xls");
				
				if (!file.exists()){//若此目录不存在，则创建之  
					file.createNewFile();  
					logger.debug("创建文件夹路径为："+ file.getPath());  
	            } 
				os = new FileOutputStream(file);
				wb.write(os);
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
	}
}
