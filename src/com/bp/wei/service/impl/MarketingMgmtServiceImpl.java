package com.bp.wei.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bp.wei.dao.InteracDataToInteracAnswerDao;
import com.bp.wei.dao.InteracDataToInteracDao;
import com.bp.wei.dao.InteracDataToInteracTypeDao;
import com.bp.wei.dao.InteracDataToMemberDao;
import com.bp.wei.dao.InteractionDataDao;
import com.bp.wei.dao.MarketinginfoDao;
import com.bp.wei.dao.MemberDao;
import com.bp.wei.dao.MemberToInteractionDao;
import com.bp.wei.dao.MemberinfoDao;
import com.bp.wei.dao.FollowerinfoDao;
import com.bp.wei.dao.MemberToFollowerDao;
import com.bp.wei.dao.QuestionnaireDao;
import com.bp.wei.model.Followerinfo;
import com.bp.wei.model.InteracDataToInterac;
import com.bp.wei.model.InteracDataToInteracAnswer;
import com.bp.wei.model.InteracDataToInteracType;
import com.bp.wei.model.InteracDataToMember;
import com.bp.wei.model.InteractionData;
import com.bp.wei.model.Marketinginfo;
import com.bp.wei.model.MarketinginfoWithBLOBs;
import com.bp.wei.model.Member;
import com.bp.wei.model.MemberToFollower;
import com.bp.wei.model.MemberToInteraction;
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
	
	//保存互动结果
	@Resource
	private InteractionDataDao idDao;
	
	@Resource
	private InteracDataToInteracAnswerDao idTarDao;
	
	@Resource
	private InteracDataToInteracTypeDao idTtyDao;
	
	@Resource
	private InteracDataToInteracDao idTitDao;
	
	@Resource
	private InteracDataToMemberDao idTmbDao;
	
	@Resource
	private MemberToInteractionDao mbTitDao;	
	
	//保存体验结果
	
	
	
	
	
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

	//save Interaction Data
	@Override
	public boolean setInteractionData(HttpServletRequest request) {
		
		String mkid = request.getParameter("mkname");
		String surveryId = request.getParameter("sid");
		String mbid = "2e48426f-a448-0b1c-2324-597f2ca95921";
		System.out.println("mkkkkkkkkkkkkkkkkk id: " + mkid);
		
		int i = 1;
		boolean hasnext = true;
		while(hasnext){
			String questionId = request.getParameter("qid_" + i);
			System.out.println("question id: " + questionId);
			if(questionId != null && questionId.length() > 0){
				String answer = request.getParameter(questionId);
				System.out.println("answer id:。。。。。。。。。。 " + answer);
				
				//insert Interaction Data
				InteractionData iddata = new InteractionData();
				iddata.setName("互动资料");
				
				int result = idDao.insertInteractionData(iddata);
				
				String interactionDataID = iddata.getId();
				System.out.println("interactionData id:。。。。。。。。。。 " + interactionDataID);
				
				//Interaction Data 关联 问卷答案
				InteracDataToInteracAnswer idTar = new InteracDataToInteracAnswer();
				idTar.setEc1InteractionDataEc1InteractionAskec1InteractionDataIdb(interactionDataID);
				idTar.setEc1InteractionDataEc1InteractionAskec1InteractionAskIda(answer);				
				
				result = idTarDao.insertInteractionDataToAnswer(idTar);
				
				//Interaction Data 关联 问卷问题
				InteracDataToInteracType idTty = new InteracDataToInteracType();
				idTty.setEc1Intera3de5onDataIdb(interactionDataID);
				idTty.setEc1Interab268onTypeIda(questionId);				
				
				result = idTtyDao.insertInteractionDataToQuestion(idTty);
				
				//Interaction Data 关联 问卷
				InteracDataToInterac idTit = new InteracDataToInterac();
				idTit.setEc1InteractionDataEc1Interactionec1InteractionDataIdb(interactionDataID);
				idTit.setEc1InteractionDataEc1Interactionec1InteractionIda(surveryId);	
				
				result = idTitDao.insertInteractionDataToInteraction(idTit);
				
				//Interaction Data 关联 会员
				InteracDataToMember idTmb = new InteracDataToMember();
				idTmb.setEc1InteractionDataEc1Memberec1InteractionDataIdb(interactionDataID);
				idTmb.setEc1InteractionDataEc1Memberec1MemberIda(mbid);	
				
				result = idTmbDao.insertInteractionDataToMember(idTmb);
				
				i ++;
			}else{
				hasnext = false;
			}
		}
		
		//会员 关联 互动（参与此互动的会员清单）
		MemberToInteraction mbTit = new MemberToInteraction();
		mbTit.setEc1MemberEc1Interactionec1MemberIdb(mbid);
		mbTit.setEc1MemberEc1Interactionec1InteractionIda(surveryId);			
		
		int result = mbTitDao.insertMemberToInteraction(mbTit);
		
		
		
		return true;
		
		
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
