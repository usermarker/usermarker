package com.xiaoshu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.dao.BankMapper;
import com.xiaoshu.entity.Bank;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.service.PersonService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("person")
public class PersonController extends LogController{
	static Logger logger = Logger.getLogger(PersonController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private BankMapper bankMapper;
	
	
	@RequestMapping("personIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<Bank> list = bankMapper.selectAll();
		request.setAttribute("list", list);
		return "person";
	}
	
	
	@RequestMapping(value="personList",method=RequestMethod.POST)
	public void userList(PersonVo personVo,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<PersonVo> info = personService.findpersonVoPage(personVo, pageNum, pageSize);
			
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
	@RequestMapping("reservePerson")
	public void reserveUser(HttpServletRequest request,PersonVo personVo,HttpServletResponse response){
		Integer userId = personVo.getpId();
		JSONObject result=new JSONObject();
		try {
			if (userId != null) {   // userId不为空 说明是修改
				personService.updateperson(personVo);
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
//				
			}else {   // 添加
				personService.addperson(personVo);
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
	
	
	@RequestMapping("deleteperson")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				personService.deleteperson(Integer.parseInt(id));
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
	
	@RequestMapping("echartsPerson")
	public void echartsPerson(PersonVo personVo, HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			List<PersonVo> list = personService.findpersonVo(personVo);
			result.put("list", list);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("展示用户信息错误",e);
			result.put("errorMsg", "对不起，展示失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
}
