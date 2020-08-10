package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Company;
import com.xiaoshu.entity.Person;
import com.xiaoshu.entity.PersonExample;
import com.xiaoshu.entity.Tj;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PersonMapper extends BaseMapper<Person> {
   public List<Person> queryPerson(Person p);
   
   public List<Company> queryEnableCompany();

   public void addPerson(Person p);
   
   public void updatePerson(Person p);
   
   public Company getCompanyByCname(String cname);
   
   public List<Tj> getTj();

}