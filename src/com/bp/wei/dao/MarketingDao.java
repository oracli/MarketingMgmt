package com.bp.wei.dao;

import com.bp.wei.model.Marketing;

public interface MarketingDao {
    int deleteByPrimaryKey(String id);

    int insert(Marketing record);

    int insertSelective(Marketing record);

    Marketing selectByPrimaryKey(String id);
    
    Marketing selecAllMarketingList();

    int updateByPrimaryKeySelective(Marketing record);

    int updateByPrimaryKeyWithBLOBs(Marketing record);

    int updateByPrimaryKey(Marketing record);
}