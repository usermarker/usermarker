package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Classes;
import com.xiaoshu.entity.ClassesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClassesMapper extends BaseMapper<Classes> {
    long countByExample(ClassesExample example);

    int deleteByExample(ClassesExample example);

    List<Classes> selectByExample(ClassesExample example);

    int updateByExampleSelective(@Param("record") Classes record, @Param("example") ClassesExample example);

    int updateByExample(@Param("record") Classes record, @Param("example") ClassesExample example);
}