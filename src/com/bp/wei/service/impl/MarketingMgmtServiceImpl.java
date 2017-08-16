package com.bp.wei.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bp.wei.dao.MarketinginfoDao;
import com.bp.wei.dao.MemberDao;
import com.bp.wei.dao.MemberinfoDao;
import com.bp.wei.dao.FollowerinfoDao;
import com.bp.wei.dao.MemberToFollowerDao;
import com.bp.wei.dao.QuestionnaireDao;
import com.bp.wei.model.Followerinfo;
import com.bp.wei.model.Marketinginfo;
import com.bp.wei.model.MarketinginfoWithBLOBs;
import com.bp.wei.model.Member;
import com.bp.wei.model.MemberToFollower;
import com.bp.wei.model.Memberinfo;
import com.bp.wei.model.MemberinfoWithBLOBs;
import com.bp.wei.model.Questionnaire;
import com.bp.wei.service.MarketingMgmtService;

@Service
public class MarketingMgmtServiceImpl implements MarketingMgmtService {
	
	public static Logger log = LoggerFactory.getLogger(MarketingMgmtService.class);
	
	
	@Resource
	private MemberinfoDao mbdao;
	
	@Resource
	private FollowerinfoDao fldao;
	
	@Resource
	private MemberToFollowerDao mtfdao;
	
	@Resource
	private QuestionnaireDao qDao;
	
	@Resource
	private MarketinginfoDao mkDao;
	
	
	//search
	@Override
	public Marketinginfo getMarketinglist() {
		Marketinginfo marketing = mkDao.selecAllMarketingList();
		System.out.println("@@@@@@@@@@@@@@marketing : " + marketing.getId());
		return marketing;
	}
	@Override
	public MarketinginfoWithBLOBs getMarketing(String id){
		MarketinginfoWithBLOBs marketing = mkDao.selectMarketingInfo(id);
		System.out.println("@@@@@@@@@@@@@@marketing : " + marketing.getId());
		return marketing;
	}
	@Override
	public Questionnaire getQuestionnaireById(String id) {
		if(id == null || id.length() <= 0){
			log.error("Invalid questionnaire id： " + id);
			return null;
		}
		Questionnaire questionnaire = qDao.selectByPrimaryKeyWithQA(id);
		if(questionnaire == null){
			log.error("Questionnaire with id :" + id + " does not exist.");
		}
		return questionnaire;
	}	

	
	

	
	
	
	
	
	
	
	
	////////////////for follower
	
	
	@Override
	public int setMember(Member member) {
		int result = dao.insertSelective(member);
		return result;
	}
	
	//myfollower
	public Followerinfo getFollowerlist(String id) {
		Followerinfo followerinfo = fldao.selectMyFollowerListByKey(id);
		return followerinfo;
	}
		
	////////////////for member
	//insert
	@Override
	public int insertMemberinfo(MemberinfoWithBLOBs memberinfowithblogs, String openid) {
		
		int result = mbdao.insert(memberinfowithblogs);
		//System.out.println("@@@@@@@@@@@@@@member id: " + memberinfowithblogs.getId());
		
		String followerID = fldao.selectByPrimaryOpenid(openid);
		
		MemberToFollower mbTofl = new MemberToFollower();
		mbTofl.setEc1MemberEc1Followerec1MemberIda(memberinfowithblogs.getId());
		mbTofl.setEc1MemberEc1Followerec1FollowerIdb(followerID);
		
		result = mtfdao.insert(mbTofl);
		
		return result;
	}
	//search
	@Override
	public MemberinfoWithBLOBs getMemberinfobyname(String name) {
		if(name.length() <= 0){
			log.error("Invalid member name: " + name);
			return null;
		}
		MemberinfoWithBLOBs memberinfo = mbdao.selectByMemberName(new String(name));
		return memberinfo;
	}
	//update
	public int updateMemberinfo(MemberinfoWithBLOBs memberinfowithblogs) {
		
		int result = mbdao.updateByPrimaryKeyWithBLOBs(memberinfowithblogs);
		
		return result;
	}
	

	///////////////////for test follower  
	public String getTestFollowerinfo(Followerinfo follow) {
		
		System.out.println("@@@@@@@@@@@@@@follower open id: " + follow.getName());
		if(follow.getName().length() <= 0){
			log.error("Invalid member name: " + follow.getName());
			return "null";
		}
		
		String FollowerID = fldao.selectByPrimaryOpenid(follow.getName());
		
		if(FollowerID != null && FollowerID.length() > 0){
			return FollowerID;
		} else {
			
			int result = fldao.insert(follow);
			if(result == 1){
				return follow.getId();
			} else {
				return "null";
			}
			
		}
	}
		

	//for examples
	@Resource
	private MemberDao dao;

	@Override
	public Member getMemberById(int memberId) {
		if(memberId <= 0){
			log.error("Invalid member id: " + memberId);
			return null;
		}
		Member member = dao.selectByPrimaryKey(new Integer(memberId));
		return member;
	}

}
