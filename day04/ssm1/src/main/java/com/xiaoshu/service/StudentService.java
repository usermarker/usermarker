package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.MajorMapper;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.entity.Major;
import com.xiaoshu.entity.MajorExample;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentExample;
import com.xiaoshu.entity.StudentExample.Criteria;
import com.xiaoshu.entity.User;

@Service
public class StudentService {

	@Autowired
	StudentMapper sm;
	
	@Autowired
	MajorMapper mm;

	public List<Major> findMajor() {
		return mm.selectAll();
	}

	public PageInfo<Student> findStudentPage(Student student, Integer pageNum, Integer pageSize) {
		// 先不搞多条件
		PageHelper.startPage(pageNum, pageSize);
		
		List<Student> userList = sm.findAll(student);
		PageInfo<Student> pageInfo = new PageInfo<Student>(userList);
		return pageInfo;
	}

	public Student existStudentWithsName(String sName) {
		// 连接数据，查询是否存在此姓名
		StudentExample sExample = new StudentExample();
		// 用于构建sql语句中where之后的条件的类
		Criteria criteria = sExample.createCriteria();
		// select * from student where sName = #{sName}
		criteria.andSNameEqualTo(sName);
		List<Student> list = sm.selectByExample(sExample);
		return list.isEmpty()?null:list.get(0);
	}

	public void addStudent(Student student) {
		sm.insert(student);
	}

	public void update(Student student) {
		sm.updateByPrimaryKey(student);
	}

	public void deleteStudent(int id) {
		sm.deleteByPrimaryKey(id);
	}

	public List<Student> findAll() {
		return sm.findAll(null);
	}

	public Integer findMajorByName(String maname) {
		MajorExample example = new MajorExample();
		// <where></where>
		com.xiaoshu.entity.MajorExample.Criteria criteria = example.createCriteria();
		
		criteria.andSNameEqualTo(maname);
		List<Major> list = mm.selectByExample(example);
		
		return list.get(0).getMaid();
	}
	
}
