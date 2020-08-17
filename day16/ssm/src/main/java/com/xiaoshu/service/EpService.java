package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.EpMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.EpVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class EpService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	EpMapper epMapper;


	// 新增
	public void addUser(EpVo t) throws Exception {
		epMapper.insert(t);
	};

	// 修改
	public void updateUser(EpVo t) throws Exception {
		epMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteUser(Integer id) throws Exception {
		epMapper.deleteByPrimaryKey(id);
	};


	// 通过用户名判断是否存在，（新增时不能重名）
	public User existUserWithUserName(String userName) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};


	public PageInfo<EpVo> findUserPage(EpVo epVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<EpVo> list = epMapper.findAll(epVo);
		PageInfo<EpVo> info = new PageInfo<>(list);
		return info;
	}

	public List<EpVo> findExport(EpVo epVo) {
		return epMapper.findAll(epVo);
	}

	public List<EpVo> findEcharts() {
		return epMapper.findEcharts();
	}


}
