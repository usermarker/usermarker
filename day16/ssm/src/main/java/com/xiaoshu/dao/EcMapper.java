package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Ec;
import com.xiaoshu.entity.EcExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EcMapper extends BaseMapper<Ec> {
    long countByExample(EcExample example);

    int deleteByExample(EcExample example);

    List<Ec> selectByExample(EcExample example);

    int updateByExampleSelective(@Param("record") Ec record, @Param("example") EcExample example);

    int updateByExample(@Param("record") Ec record, @Param("example") EcExample example);
}