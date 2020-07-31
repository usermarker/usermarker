package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.ContentMapper;
import com.xiaoshu.dao.ContentcategoryMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentVo;
import com.xiaoshu.entity.Contentcategory;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class ContentService {

	@Autowired
	ContentMapper contentMapper;

	
	public PageInfo<ContentVo> findContentVoPage(ContentVo contentVo, Integer pageNum, Integer pageSize,
			String ordername, String order) {
		PageHelper.startPage(pageNum, pageSize);
		ordername = StringUtil.isNotEmpty(ordername)?ordername:"userid";
		order = StringUtil.isNotEmpty(order)?order:"desc";
		
		List<ContentVo> userList = contentMapper.findContentVoPage(contentVo);
		PageInfo<ContentVo> pageInfo = new PageInfo<ContentVo>(userList);
		return pageInfo;
	}


	public void deleteUser(int id) {
		contentMapper.deleteByPrimaryKey(id);
	}

	@Autowired
	ContentcategoryMapper contentcategoryMapper;

	public List<Contentcategory> findAllconjj() {
		// TODO Auto-generated method stub
		return contentcategoryMapper.selectAll();
	}


	public void addContent(Content content) {
		content.setCreatetime(new Date());
		contentMapper.insert(content);
	}


	public Content findAllContentByName(String contenttitle) {
		// TODO Auto-generated method stub
		return contentMapper.findAllContentByName(contenttitle);
	}


	public Contentcategory findAll(String cname) {
		// TODO Auto-generated method stub
		return contentcategoryMapper.findAll(cname);
	}


	public List<ContentVo> findAllContent() {
		// TODO Auto-generated method stub
		return contentMapper.findContentVoPage(new ContentVo());
	}


}
