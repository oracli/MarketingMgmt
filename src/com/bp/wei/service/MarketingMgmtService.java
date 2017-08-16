package com.bp.wei.service;


import com.bp.wei.model.Followerinfo;
import com.bp.wei.model.Marketinginfo;
import com.bp.wei.model.MarketinginfoWithBLOBs;
import com.bp.wei.model.Member;
import com.bp.wei.model.Memberinfo;
import com.bp.wei.model.MemberinfoWithBLOBs;
import com.bp.wei.model.Questionnaire;

public interface MarketingMgmtService {
	
	Marketinginfo getMarketinglist();
	
	MarketinginfoWithBLOBs getMarketing(String id);
	
	Questionnaire getQuestionnaireById(String id);
	
	
	
	///////////////////for follower
	//search myfollower
	Followerinfo getFollowerlist(String id);
	
	///////////////////for member
	//insert
	int insertMemberinfo(MemberinfoWithBLOBs memberinfowithblogs, String openid);
	//search
	MemberinfoWithBLOBs getMemberinfobyname(String name);
	//update
	int updateMemberinfo(MemberinfoWithBLOBs memberinfowithblogs);
	
	
    ///////////////////for test follower  
	String getTestFollowerinfo(Followerinfo follow);

	//for examples
	Member getMemberById(int memberId);
	int setMember(Member member);
	
}
