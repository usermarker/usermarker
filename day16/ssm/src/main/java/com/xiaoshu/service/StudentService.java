package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.dao.TeacherMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.Teacher;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class StudentService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	StudentMapper studentMapper;
	
	@Autowired
	TeacherMapper teacherMapper;


	// 新增
	public void addStudent(StudentVo t) throws Exception {
		studentMapper.insert(t);
	};

	// 修改
	public void updateStudent(StudentVo t) throws Exception {
		studentMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteStudent(Integer id) throws Exception {
		studentMapper.deleteByPrimaryKey(id);
	};


	// 通过用户名判断是否存在，（新增时不能重名）
	public User existUserWithUserName(String userName) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};

	public PageInfo<StudentVo> findStudentPage(StudentVo studentVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<StudentVo> list = studentMapper.findAll();
		PageInfo<StudentVo> info = new PageInfo<>(list);
		return info;
	}

	public List<StudentVo> findStudent(StudentVo studentVo) {
		return studentMapper.findAll();
	}

	public List<StudentVo> findEcharts(StudentVo studentVo) {
		return studentMapper.findEcharts(studentVo);
	}

	public void findType(Teacher teacher) {
		teacherMapper.insert(teacher);
	}


}
