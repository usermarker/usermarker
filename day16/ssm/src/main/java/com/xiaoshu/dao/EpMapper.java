package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Ep;
import com.xiaoshu.entity.EpExample;
import com.xiaoshu.entity.EpVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EpMapper extends BaseMapper<Ep> {
    List<EpVo> findEcharts();

	long countByExample(EpExample example);

    int deleteByExample(EpExample example);

    List<Ep> selectByExample(EpExample example);

    int updateByExampleSelective(@Param("record") Ep record, @Param("example") EpExample example);

    int updateByExample(@Param("record") Ep record, @Param("example") EpExample example);

    List<EpVo> findAll(EpVo epVo);
}