package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.GoodsMapper;
import com.xiaoshu.dao.GoodstypeMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.GoodsExample;
import com.xiaoshu.entity.GoodsExample.Criteria;
import com.xiaoshu.entity.GoodsVo;
import com.xiaoshu.entity.Goodstype;
import com.xiaoshu.entity.User;

@Service
public class GoodsService {


	@Autowired
	private GoodsMapper goodsMapper;

	// 新增
	public void addGoods(Goods t) throws Exception {
		goodsMapper.insert(t);
	};

	// 修改
	public void updateGoods(Goods t) throws Exception {
		goodsMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
	public void deleteGoods(Integer id) throws Exception {
		goodsMapper.deleteByPrimaryKey(id);
	};


	// 通过用户名判断是否存在，（新增时不能重名）
	public Goods existUserWithUserName(String userName) throws Exception {
		//UserExample example = new UserExample();
		GoodsExample example = new GoodsExample();
		Criteria criteria = example.createCriteria();
		criteria.andCodeEqualTo(userName);
		List<Goods> userList = goodsMapper.selectByExample(example);
		return  userList.isEmpty()?null:userList.get(0);
	};


	public PageInfo<GoodsVo> findGoodsPage(GoodsVo goodsVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<GoodsVo> userList = goodsMapper.findPage(goodsVo);
		PageInfo<GoodsVo> pageInfo = new PageInfo<GoodsVo>(userList);
		return pageInfo;
	}

	@Autowired
	private GoodstypeMapper goodstypeMapper;
	public List<Goodstype> findGoodstypePage() {
		// TODO Auto-generated method stub
		return goodstypeMapper.selectAll();
	}
}
