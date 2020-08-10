package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.ClassesMapper;
import com.xiaoshu.dao.CompanyMapper;
import com.xiaoshu.dao.PersonMapper;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.dao.TeacherMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.City;
import com.xiaoshu.entity.Classes;
import com.xiaoshu.entity.Company;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.TeacherVo;
import com.xiaoshu.entity.Tj;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class TeacherService {

	@Autowired
	private TeacherMapper tm;
	
	

	public PageInfo<TeacherVo> findTeacherPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TeacherVo> teacherList =tm.queryTeacher();
		PageInfo<TeacherVo> pageInfo = new PageInfo<TeacherVo>(teacherList);
		return pageInfo;
	}
	
	public List<City> queryCityByPid(Integer pid){
		return tm.queryCityByPid(pid);
	}

}
