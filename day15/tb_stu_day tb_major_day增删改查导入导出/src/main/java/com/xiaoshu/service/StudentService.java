package com.xiaoshu.service;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.MajorMapper;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Major;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class StudentService {

	@Autowired
	StudentMapper sm;


	// 新增
	public void addStudent(Student t) throws Exception {
		sm.insert(t);
	};

	// 修改
	public void updateStudent(Student t) throws Exception {
		sm.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteStudent(Integer id) throws Exception {
		sm.deleteByPrimaryKey(id);
	};


	public PageInfo<StudentVo> findStudentPage(StudentVo student, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<StudentVo> studentList = sm.findPage(student);
		PageInfo<StudentVo> pageInfo = new PageInfo<StudentVo>(studentList);
		return pageInfo;
	}

	@Autowired
	private MajorMapper mm;
	public List<Major> findMajor() {
		// TODO Auto-generated method stub
		return mm.selectAll();
	}

	public List<StudentVo> findExcel(StudentVo student) {
		// TODO Auto-generated method stub
		return sm.findPage(student);
	}

	public List<StudentVo> findEcharts() {
		// TODO Auto-generated method stub
		return sm.findEcharts();
	}
	
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination queueTextDestination;
	public void addMajor(final Major major) {
		// TODO Auto-generated method stub
		mm.insert(major);
		//发送消息
		jmsTemplate.send(queueTextDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				String json = JSONObject.toJSONString(major.getMdId());
				return session.createTextMessage(json);
			}
		});
	}



}
