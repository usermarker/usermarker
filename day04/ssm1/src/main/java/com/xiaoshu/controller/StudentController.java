package com.xiaoshu.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Major;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVO;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("student")
public class StudentController {
	static Logger logger = Logger.getLogger(StudentController.class);

	@Autowired
	private OperationService operationService;

	@Autowired
	StudentService ss;

	@Autowired
	JedisPool jedisPool;
	
	@RequestMapping("studentIndex")
	public String index(HttpServletRequest request, Integer menuid) throws Exception {
		// 查看当前menu菜单中的所有的按钮
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);

		Jedis jedis = jedisPool.getResource();
		
		String majors = jedis.get("majorList");
		
		if(majors==null){
			// 根据专业进行查询
			List<Major> majorList = ss.findMajor();
			request.setAttribute("majorList", majorList);
			
			// 直接存入redis中     fastJson 工具中的一个方法，可以将java中的数据类型，直接转变为stringjson的字符串
			String jsonString = JSON.toJSONString(majorList);
			jedis.set("majorList", jsonString);
			System.out.println("第一次获取，从mysql中获取");
			
		}else{
			// 将json字符串，转化成对应的实体类集合
			List<Major> majorList = JSON.parseArray(majors, Major.class);
			request.setAttribute("majorList", majorList);
			System.out.println("第N次获取，从redis中获取");
			
		}

		
		
		return "student";
	}

	@RequestMapping(value = "studentList", method = RequestMethod.POST)
	public void studentList(StudentVO student, HttpServletRequest request, HttpServletResponse response, String offset,
			String limit) throws Exception {
		try {
			// 分页所需的内容
			Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
			Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
			// 分页操作的查询所有
			PageInfo<Student> userList = ss.findStudentPage(student, pageNum, pageSize);

			// ajax请求的响应内容
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total", userList.getTotal());
			jsonObj.put("rows", userList.getList());
			WriterUtil.write(response, jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误", e);
			throw e;
		}
	}

	// 新增或修改
	@RequestMapping("reserveStudent")
	public void reserveUser(HttpServletRequest request, Student student, HttpServletResponse response) {
		Integer sid = student.getSid();
		JSONObject result = new JSONObject();
		try {
			if (sid != null) { // userId不为空 说明是修改
				ss.update(student);
				result.put("success", true);
			} else { // 添加
				// 验证账号是否存在！
				if (ss.existStudentWithsName(student.getsName()) == null) { // 没有重复可以添加
					ss.addStudent(student);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}

	@RequestMapping("deleteStudent")
	public void deleteStudent(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
			String[] ids = request.getParameter("ids").split(",");
			for (String id : ids) {
				ss.deleteStudent(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误", e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}

	@RequestMapping("inStudent")
	public void inStudent(MultipartFile file, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
			// 通过传入的file对象 来获取文件的流对象
			// workbook对象可以直接用来接收流对象
			HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
			// sheet
			HSSFSheet sheet = workbook.getSheetAt(0);

			// 得到最后一行的下标
			int lastRowNum = sheet.getLastRowNum();
			for (int i = 1; i < lastRowNum + 1; i++) {
				HSSFRow row = sheet.getRow(i);
				String sName = row.getCell(1).getStringCellValue();
				String sex = row.getCell(2).getStringCellValue();
				String hobby = row.getCell(3).getStringCellValue();
				// 生日需要将string类型转变为date类型
				String b = row.getCell(4).getStringCellValue();
				Date birthday = TimeUtil.ParseTime(b, "yyyy-MM-dd");
				
				String state = row.getCell(5).getStringCellValue();
				// 专业，不能直接给一个名字，需要转变为id
				String maname = row.getCell(6).getStringCellValue();
				Integer maid = ss.findMajorByName(maname);
				
				Student s = new Student(null, sName, sex, hobby, birthday, state, maid,null);
				
				// 将数据添加到数据库中
				ss.addStudent(s);
			
			}

			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误", e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}

	@RequestMapping("outStudent")
	public void outStudent(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
			// workbook
			HSSFWorkbook workbook = new HSSFWorkbook();
			// sheet
			HSSFSheet sheet = workbook.createSheet();
			// row 创建标题行
			String[] title = { "学生编号", "学生姓名", "学生性别", "学生爱好", "生日", "学习状态", "专业" };
			HSSFRow titleRow = sheet.createRow(0);
			for (int i = 0; i < title.length; i++) {
				titleRow.createCell(i).setCellValue(title[i]);
			}
			// 查询数据库中的所有数据
			List<Student> list = ss.findAll();
			// 遍历数据
			for (int i = 0; i < list.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				Student s = list.get(i);

				row.createCell(0).setCellValue(s.getSid());
				row.createCell(1).setCellValue(s.getsName());
				row.createCell(2).setCellValue(s.getSex());
				row.createCell(3).setCellValue(s.getHobby());

				Date b = s.getBirthday();
				String birthday = TimeUtil.formatTime(b, "yyyy-MM-dd");
				row.createCell(4).setCellValue(birthday);

				row.createCell(5).setCellValue(s.getState());
				// 专业不能直接给个id
				row.createCell(6).setCellValue(s.getMajor().getsName());

			}
			// 回显
			workbook.write(new File("f:/h1905c-ssm.xls"));
			workbook.close();

			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误", e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
}
