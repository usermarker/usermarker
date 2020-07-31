package com.xiaoshu.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentVo;
import com.xiaoshu.entity.Contentcategory;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.ContentService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("content")
public class ContentController extends LogController{
	static Logger logger = Logger.getLogger(ContentController.class);

	@Autowired
	private	ContentService contentService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("contentIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<Contentcategory> list = contentService.findAllconjj();
		request.setAttribute("clist", list);
		return "content";
	}
	
	
	@RequestMapping(value="contentList",method=RequestMethod.POST)
	public void userList(ContentVo contentVo , HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<ContentVo> userList= contentService.findContentVoPage(contentVo,pageNum,pageSize,ordername,order);
			
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
	@RequestMapping("reserveUser")
	public void reserveUser(Content content , HttpServletRequest request,User user,HttpServletResponse response){
		Integer userId = content.getContentid();
		JSONObject result=new JSONObject();
		try {
			if (userId != null) {   // userId不为空 说明是修改
//				Content sdf = contentService.findAllContentByName();
//				if(sdf == null || sdf.getContentid() != null){  // 没有重复可以添加
//					contentService.updateContent(content);
//					result.put("success", true);
//				} else {
//					result.put("success", true);
//					result.put("errorMsg", "该用户名被使用");
//				}
//				
			}else {   // 添加
				Content sdf = contentService.findAllContentByName(content.getContenttitle());
				if(sdf == null){  // 没有重复可以添加
					contentService.addContent(content);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该广告标题被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("deleteUser")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				contentService.deleteUser(Integer.parseInt(id));
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
	
	@SuppressWarnings("resource")
	@RequestMapping("reserveimport")
	public String reserveimport(MultipartFile file) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 1; i <= lastRowNum; i++) {
				Content content = new Content();
				
				XSSFRow row = sheet.getRow(i);
				content.setContenttitle(row.getCell(1).toString());
				
				String cname = row.getCell(2).toString();
				Contentcategory cc = contentService.findAll(cname);
				content.setContentcategoryid(cc.getContentcategoryid());
				content.setPicpath(row.getCell(3).toString());
				content.setContenturl(row.getCell(4).toString());
				content.setPrice((int)row.getCell(5).getNumericCellValue());
				content.setStatus(row.getCell(6).toString().equals("启用")?1:2);
				content.setCreatetime(sdf.parse(row.getCell(7).toString()));
				
				contentService.addContent(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:contentIndex.htm?menuid=10";
	}
//	
//	@RequestMapping("exportUser")
//	public String exportUser() {
//		try {
//			List<ContentVo> list = contentService.findAllContent();
//			
//			new 
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "redirect:contentIndex.htm";
//	}
	
}
