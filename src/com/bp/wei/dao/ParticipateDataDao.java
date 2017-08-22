package com.bp.wei.dao;

import com.bp.wei.model.ParticipateData;

public interface ParticipateDataDao {
    int deleteByPrimaryKey(String id);

    int insert(ParticipateData record);

    int insertSelective(ParticipateData record);

    ParticipateData selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ParticipateData record);

    int updateByPrimaryKeyWithBLOBs(ParticipateData record);

    int updateByPrimaryKey(ParticipateData record);
}