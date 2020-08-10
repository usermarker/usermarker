package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.City;
import com.xiaoshu.entity.Teacher;
import com.xiaoshu.entity.TeacherExample;
import com.xiaoshu.entity.TeacherVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper extends BaseMapper<Teacher> {
    public List<TeacherVo> queryTeacher();
    
    public List<City> queryCityByPid(Integer pid);
}