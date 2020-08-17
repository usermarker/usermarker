package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.PersonMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class PersonService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	PersonMapper personMapper;


	// 新增
	public void addperson(PersonVo t) throws Exception {
		personMapper.insert(t);
	};

	// 修改
	public void updateperson(PersonVo t) throws Exception {
		personMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteperson(Integer id) throws Exception {
		personMapper.deleteByPrimaryKey(id);
	};


	// 通过用户名判断是否存在，（新增时不能重名）
	public User existUserWithUserName(String userName) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};


	public PageInfo<PersonVo> findpersonVoPage(PersonVo personVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<PersonVo> list = personMapper.findAll(personVo);
		PageInfo<PersonVo> info = new PageInfo<>(list);
		return info;
	}

	public List<PersonVo> findpersonVo(PersonVo personVo) {
		return personMapper.findpersonVo(personVo);
	}


}
