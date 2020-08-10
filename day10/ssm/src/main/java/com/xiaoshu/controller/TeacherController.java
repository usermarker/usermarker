package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.City;
import com.xiaoshu.entity.Classes;
import com.xiaoshu.entity.Company;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.TeacherVo;
import com.xiaoshu.entity.Tj;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.ClassesService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.PersonService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.service.TeacherService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("teacher")
public class TeacherController extends LogController {
	static Logger logger = Logger.getLogger(TeacherController.class);

	@Autowired
	private TeacherService ts;
	@Autowired
	private OperationService operationService;

	@RequestMapping("teacherIndex")
	public String index(HttpServletRequest request, Integer menuid) throws Exception {

		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<City> shengl=ts.queryCityByPid(0);
		request.setAttribute("shengl", shengl);
		return "teacher";
	}
	
	@RequestMapping("ld")
	public void getld(HttpServletRequest request, HttpServletResponse response,Integer pid) {
		List<City> cl=ts.queryCityByPid(pid);
		Object json = JSONObject.toJSON(cl);
		WriterUtil.write(response, json.toString());
	}

	@RequestMapping(value = "teacherList", method = RequestMethod.POST)
	public void userList(HttpServletRequest request, HttpServletResponse response, String offset,
			String limit) throws Exception {
		try {
			Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
			Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
			PageInfo<TeacherVo> teacherList = ts.findTeacherPage(pageNum, pageSize);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total", teacherList.getTotal());
			jsonObj.put("rows", teacherList.getList());
			WriterUtil.write(response, jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误", e);
			throw e;
		}
	}


}
