package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.ClassesMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Classes;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class ClassesService {

	@Autowired
	ClassesMapper cm;

	public PageInfo<Classes> findclassesPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Classes> classesList = cm.selectAll();
		PageInfo<Classes> pageInfo = new PageInfo<Classes>(classesList);
		return pageInfo;
	}


}
