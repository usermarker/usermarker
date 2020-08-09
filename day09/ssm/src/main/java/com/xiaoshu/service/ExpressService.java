package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.ExpressCompanyMapper;
import com.xiaoshu.dao.ExpressPersonMapper;
import com.xiaoshu.entity.ExpressCompany;
import com.xiaoshu.entity.ExpressPerson;
import com.xiaoshu.entity.ExpressVo;

@Service
public class ExpressService {


	// 新增
	public void addExpress(ExpressPerson t) throws Exception {
		expressPersonMapper.insert(t);
	};

	// 修改
	public void updateExpress(ExpressPerson t) throws Exception {
		expressPersonMapper.updateByPrimaryKeySelective(t);
	};


	// 删除
	public void deleteExpress(Integer id) throws Exception {
		expressPersonMapper.deleteByPrimaryKey(id);
	};
	

	/*// 通过用户名判断是否存在，（新增时不能重名）
	public User existUserWithUserName(String userName) throws Exception {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		List<User> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	};*/

	
	@Autowired
	private ExpressPersonMapper expressPersonMapper;
	@Autowired
	private ExpressCompanyMapper expressCompanyMapper;
	public PageInfo<ExpressVo> findUserPage(ExpressVo expressPerson, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ExpressVo> list = expressPersonMapper.findPage(expressPerson);
		PageInfo<ExpressVo> pageInfo = new PageInfo<ExpressVo>(list);
		return pageInfo;
	}
	public List<ExpressCompany> findAllCompany() {
		return expressCompanyMapper.selectAll();
	}

	public List<ExpressVo> findPage(ExpressVo person) {
		return expressPersonMapper.findPage(person);
	}

	public int findCidByCname(String cname) {
		ExpressCompany company = new ExpressCompany();
		company.setExpressName(cname);
		ExpressCompany one = expressCompanyMapper.selectOne(company);
		return one.getId();
	}


}
