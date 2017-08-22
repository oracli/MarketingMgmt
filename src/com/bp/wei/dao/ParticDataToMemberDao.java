package com.bp.wei.dao;

import com.bp.wei.model.ParticDataToMember;

public interface ParticDataToMemberDao {
    int deleteByPrimaryKey(String id);

    int insert(ParticDataToMember record);

    int insertSelective(ParticDataToMember record);

    ParticDataToMember selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ParticDataToMember record);

    int updateByPrimaryKey(ParticDataToMember record);
}