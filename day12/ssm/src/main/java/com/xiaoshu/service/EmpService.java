package com.xiaoshu.service;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.DeptMapper;
import com.xiaoshu.dao.EmpMapper;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.entity.Dept;
import com.xiaoshu.entity.Emp;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.Tj;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class EmpService {

	@Autowired
	private EmpMapper em;
	
	@Autowired
	private DeptMapper dm;
	
	@Autowired
	private RedisTemplate rt;
	
	@Autowired
	private JmsTemplate jt;
	
	

	public PageInfo<Emp> findEmpPage(int pageNum, int pageSize,Emp emp) {
		PageHelper.startPage(pageNum, pageSize);
		List<Emp> empList=(List<Emp>) rt.boundHashOps("myhc").get("emplist");
		if(CollectionUtils.isEmpty(empList)) {
			//此时说明redis中没有集合，此时集合应该从数据库中查询出来
			empList = em.queryAllEmp(emp);
			System.out.println("从数据库查询到的");
			rt.boundHashOps("myhc").put("emplist", empList);
		}else {
			System.out.println("从缓存里查询的");
		}
		
		PageInfo<Emp> pageInfo = new PageInfo<Emp>(empList);
		return pageInfo;
	}
	
	public List<Emp> export(){
		return em.queryAllEmp(null);
	}

	// 新增
	public void addEmp(Emp e) throws Exception {
		em.insert(e);
		fs(e.toString());
		rt.delete("myhc");
	};

	// 修改
	public void updateEmp(Emp e) throws Exception {
		em.updateByPrimaryKeySelective(e);
		rt.delete("myhc");
	};

	// 删除
	public void deleteEmp(Integer eid) throws Exception {
		em.deleteByPrimaryKey(eid);
		rt.delete("myhc");
	};
	
	//查询所有部门
	public List<Dept> queryDept(){
		return dm.selectAll();
	}
	
	public Dept getDid(String dname) {
		return dm.getDid(dname);
	}
	
	//添加部门的方法
	public void addDept(Dept d) {
		dm.insert(d);
	}
	//得到统计的方法
	public List<Tj> getTj(){
		return em.getTj();
	}
	
	//使用mq发送消息的方法
	public void fs(final String message) {
		jt.send("h1909dssm",new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createTextMessage(message);
			}
		});
	}
}
