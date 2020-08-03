package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.ContentMapper;
import com.xiaoshu.dao.StyleMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentVo;
import com.xiaoshu.entity.Style;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class ContentService {

	@Autowired
	ContentMapper CcntentMapper;

	public PageInfo<ContentVo> findContentVoPage(ContentVo contentVo, Integer pageNum, Integer pageSize,
			String ordername, String order) {
		PageHelper.startPage(pageNum, pageSize);
		ordername = StringUtil.isNotEmpty(ordername)?ordername:"userid";
		order = StringUtil.isNotEmpty(order)?order:"desc";
		
		List<ContentVo> userList = CcntentMapper.findContentVoPage(contentVo);
		PageInfo<ContentVo> pageInfo = new PageInfo<ContentVo>(userList);
		return pageInfo;
	}

	public void deleteUser(int id) {
		CcntentMapper.deleteByPrimaryKey(id);
	}

	public void updateUser(Content content) {
		CcntentMapper.updateByPrimaryKey(content);
	}

	public void addUser(Content content) {
		content.setCreatetime(new Date());
		CcntentMapper.insert(content);
	}

	public Content findAllContentByname(String title) {
		// TODO Auto-generated method stub
		return CcntentMapper.findAllContentByname(title);
	}
	
	@Autowired
	StyleMapper styleMapper;
	
	public List<Style> findAllStyle() {
		// TODO Auto-generated method stub
		return styleMapper.selectAll();
	}


}
