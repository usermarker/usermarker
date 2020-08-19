package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.xiaoshu.dao.ContentMapper;
import com.xiaoshu.dao.ContentcategoryMapper;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentExample;
import com.xiaoshu.entity.ContentExample.Criteria;
import com.xiaoshu.entity.ContentVo;
import com.xiaoshu.entity.Contentcategory;

@Service
public class ContentService {

	@Autowired
	private ContentMapper contentMapper;
	
	// 查询所有
	public List<Content> findContent(Content t) throws Exception {
		return contentMapper.select(t);
	};

	// 新增
	public void addContent(Content t) throws Exception {
		contentMapper.insert(t);
	};

	// 修改
	public void updateContent(Content t) throws Exception {
		contentMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteContent(Integer id) throws Exception {
		contentMapper.deleteByPrimaryKey(id);
	};


	// 通过用户名判断是否存在，（新增时不能重名）
	public Content existUserWithUserName(String userName) throws Exception {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andContenttitleEqualTo(userName);
		List<Content> userList = contentMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};


	public PageInfo<ContentVo> findContentPage(ContentVo contentVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<ContentVo> userList = contentMapper.findPage(contentVo);
		PageInfo<ContentVo> pageInfo = new PageInfo<ContentVo>(userList);
		return pageInfo;
	}

	@Autowired
	private ContentcategoryMapper contentcategoryMapper;
	public List<Contentcategory> findAllContentcategory() {
		// TODO Auto-generated method stub
		return contentcategoryMapper.selectAll();
	}

	public List<ContentVo> findEcharts() {
		// TODO Auto-generated method stub
		return contentMapper.findEcharts();
	}
}
