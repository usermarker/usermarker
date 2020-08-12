package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class StudentService {
	
	@Autowired
	private StudentMapper sm;
	
	

	public PageInfo<Student> findStudentPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<Student> studentList = sm.selectAll();
		PageInfo<Student> pageInfo = new PageInfo<Student>(studentList);
		return pageInfo;
	}
}
