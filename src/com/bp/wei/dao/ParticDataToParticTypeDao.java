package com.bp.wei.dao;

import com.bp.wei.model.ParticDataToParticType;

public interface ParticDataToParticTypeDao {
    int deleteByPrimaryKey(String id);

    int insert(ParticDataToParticType record);

    int insertSelective(ParticDataToParticType record);

    ParticDataToParticType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ParticDataToParticType record);

    int updateByPrimaryKey(ParticDataToParticType record);
    
    int insertParticDataToParticType(ParticDataToParticType record);
}