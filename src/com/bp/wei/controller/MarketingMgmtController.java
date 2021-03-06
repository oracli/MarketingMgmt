package com.bp.wei.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bp.wei.model.Marketinginfo;
import com.bp.wei.model.MarketinginfoWithBLOBs;
import com.bp.wei.model.Member;
import com.bp.wei.model.MemberinfoWithBLOBs;
import com.bp.wei.model.Followerinfo;
import com.bp.wei.model.Questionnaire;
import com.bp.wei.service.MarketingMgmtService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping
public class MarketingMgmtController {
	public static Logger log = LoggerFactory.getLogger(MarketingMgmtController.class);
	
	@Autowired
	MarketingMgmtService  marktingService;
	//营销活动入口
	@RequestMapping(value="marketingentry", method = RequestMethod.GET)
	public String redirectGomarketingentry(){		
		return "marketingentry";
	}	
	//活动首页
	@RequestMapping(value="marketingindex", method = RequestMethod.GET)
	public String redirectMarketingindex(){	
		return "marketingindex";
	}
	//活动报名
	@RequestMapping(value="marketingsignup", method = RequestMethod.GET)
	public String redirectMarketingsignup(){		
		return "marketingsignup";
	}
	//签到
	@RequestMapping(value="signinpage", method = RequestMethod.GET)
	public String redirectSigninpage(){	
		return "signinpage";
	}
	//调查反馈
	@RequestMapping(value="feedbacksurvey", method = RequestMethod.GET)
	public String redirectFeedbacksurvey(){	
		return "feedbacksurvey";
	}
	//进入报名调查页面
	@RequestMapping(value="signupsurvey", method = RequestMethod.GET)
	public String redirectSignupsurvey(){	
		return "signupsurvey";
	}
	//进入取消报名页面
	@RequestMapping(value="signupcancel", method = RequestMethod.GET)
	public String redirectSignupcancel(){	
		return "signupcancel";
	}
	//获得在用的营销活动
	@RequestMapping(value="getMarketinglist", method = RequestMethod.GET)
	public @ResponseBody Marketinginfo findMarketinglist(){
		
		Marketinginfo result = marktingService.getMarketinglist();
		
		System.out.println("@@@@@@@@@@@result: " + result.toString());
		
		return result;
	}
	//search marketing info
	@RequestMapping(value="getmarketing", method = RequestMethod.GET)
	public @ResponseBody MarketinginfoWithBLOBs findMarketing(String id){
		log.debug("###########memberid: " + id);
		if(id == null || id.length() == 0){
			return null;
		}
		log.debug("###########memberid: " + id);
		MarketinginfoWithBLOBs marketing = marktingService.getMarketing(id);
		log.debug("###########" + marketing.getName());
		return marketing;
	}	
	//获取问题列表
	@RequestMapping(value="getQuestionnaire", method = RequestMethod.GET)
	public @ResponseBody Questionnaire getQuestionnaire(String id){
		if(id == null || id.length() <= 0){
			log.error("Invalid questionnaire id from UI.");
			return null;
		}
		Questionnaire result = marktingService.getQuestionnaireById(id);
		if(result == null){
			log.error("No questionnaire definition.");
			return null;
		}
		System.out.println("@@@@@@@@@@@result: " + result.toString());
		return result;
	}
	//提交答案
	@RequestMapping(value="submitSurvey", method = RequestMethod.POST)
	public ModelAndView submitSurvey(HttpServletRequest request){	
		log.debug("setSurveryResult start...");
		String surveryId = request.getParameter("sid");
		System.out.println("survery id: " + surveryId);
		int i = 1;
		

		boolean hasnext = true;
		while(hasnext){
			String questionId = request.getParameter("qid_" + i);
			System.out.println("question id: " + questionId);
			if(questionId != null && questionId.length() > 0){
				String answer = request.getParameter(questionId);
				System.out.println("answer id:。。。。。。。。。。 " + answer);
				i ++;
			}else{
				hasnext = false;
			}
		}
		
		
		//if(hasnext){
			
			boolean blResult = marktingService.setInteractionData(request);
		//}
		

		ModelAndView result = new ModelAndView();
		
		result.setViewName("msg_success");		
			
		return result;
	}	
	
	
	//search marketing info for 签到
	@RequestMapping(value="getmarketingforsignin", method = RequestMethod.GET)
	public @ResponseBody MarketinginfoWithBLOBs findmMarketingForSignin(String id){
		log.debug("###########memberid: " + id);
		if(id == null || id.length() == 0){
			return null;
		}
		log.debug("###########memberid: " + id);
		MarketinginfoWithBLOBs marketing = marktingService.getMarketingForSignin(id);
		log.debug("###########" + marketing.getName());
		return marketing;
	}
	//提交签到
	@RequestMapping(value="submitSignin", method = RequestMethod.POST)
	public ModelAndView submitSignin(HttpServletRequest request){	
		log.debug("submitSignin start...");
		String marketingId = request.getParameter("mkname");
		System.out.println("marketing................... id: " + marketingId);
		
		String memberId = request.getParameter("mbname");
		System.out.println("member................... id: " + memberId);
		
		String particId = request.getParameter("particname");
		System.out.println("partic................... id: " + particId);		
		
		boolean blResult = marktingService.setParticipateData(request);
		
		
		ModelAndView result = new ModelAndView();
		result.setViewName("marketingentry");		
		return result;
	}
	
	
	
	
	
	
	
	
	
	/////////////////////////////////////backup
	@RequestMapping(value="mypromotion", method = RequestMethod.GET)
	public String redirectMypromotion(){	
		return "mypromotion";
	}

	
	@RequestMapping(value="childinfoadd", method = RequestMethod.GET)
	public String redirectChildinfoadd(){	
		return "childinfoadd";
	}
	
	@RequestMapping(value="childinfoupdate", method = RequestMethod.GET)
	public String redirectChildinfoupdate(){	
		return "childinfoupdate";
	}
	
	@RequestMapping(value="purchaselist", method = RequestMethod.GET)
	public String redirectPurchaselist(){	
		return "purchaselist";
	}
	
	@RequestMapping(value="feedbacklist", method = RequestMethod.GET)
	public String redirectFeedbacklist(){	
		return "feedbacklist";
	}
	
	@RequestMapping(value="feedbackinfoadd", method = RequestMethod.GET)
	public String redirectFeedbackinfoadd(){	
		return "feedbackinfoadd";
	}
	
	@RequestMapping(value="feedbackinfoupdate", method = RequestMethod.GET)
	public String redirectFeedbackinfoupdate(){	
		return "feedbackinfoupdate";
	}
	
	@RequestMapping(value="myfollower", method = RequestMethod.GET)
	public String redirectMyfollower(){	
		return "myfollower";
	}
	
	@RequestMapping(value="myqrcode", method = RequestMethod.GET)
	public String redirectMyqrcode(){	
		return "myqrcode";
	}
	
	@RequestMapping(value="registerinfo", method = RequestMethod.POST)
	public ModelAndView viewRegisterinfo(HttpServletRequest request){	
		log.debug("redirectRegisterinfo start...");
		Member member = new Member();
		member.setName(request.getParameter("membername"));
		log.debug("################" + member.toString());
		ModelAndView result = new ModelAndView();
		//Map<String, Object> modelMap = new HashMap<String, Object>();
		result.setViewName("registerinfo");		
		result.addObject("member", JSONObject.fromObject(member));	
		return result;
	}
	
	@RequestMapping(value="getmember", method = RequestMethod.GET)
	public @ResponseBody Member findMember(int id){
		
		return marktingService.getMemberById(new Integer(id));
		
	}
	
	@RequestMapping(value="setmember", method = RequestMethod.POST)
	public @ResponseBody int setMember(@RequestBody JSONObject strMember){
		log.debug("Start to set member...");
		if(strMember == null){
			log.error("Failed to get member info from UI: " + strMember);
			return -1;
		}
		
		Member member = new Member();
		String mobile = strMember.getString("membermobile");
		if(mobile != null && mobile.length() > 0){
			member.setMobile(mobile);
		}
		String name = strMember.getString("membername");
		if(name != null && name.length() > 0){
			member.setName(name);
		}
		member.setGender("F");
		member.setBirthday("2017-01-01");
		
		int result = marktingService.setMember(member);		
		
		return result;		
	}
	
	/////////for follower
	//search myfollower
	@RequestMapping(value="getFollowerlist", method = RequestMethod.GET)
	public @ResponseBody Followerinfo findMyFollowerList(String id){
		log.debug("###########memberid: " + id);
		if(id == null || id.length() == 0){
			return null;
		}
		log.debug("###########memberid: " + id);
		Followerinfo followerinfo = marktingService.getFollowerlist(id);
		log.debug("###########" + followerinfo.toString());
		return followerinfo;
	}
	
	
	/////////for member 
	//insert member
	@RequestMapping(value="setmemberinfo", method = RequestMethod.POST)
	public @ResponseBody int setMemberinfo(@RequestBody JSONObject strMemberinfo){
		
		log.debug("Start to set member...");
		if(strMemberinfo == null){
			log.error("Failed to get member info from UI: " + strMemberinfo);
			return -1;
		}
		
		System.out.println("#################" + strMemberinfo.toString());
		
		//JSONObject jsonObject = JSONObject.fromObject(strMember);
		MemberinfoWithBLOBs memberinfo = new MemberinfoWithBLOBs();
		
		String telnum = strMemberinfo.getString("memberinfotelnum");
		if(telnum != null && telnum.length() > 0){
			memberinfo.setName(telnum);
		}
		
		String titel = strMemberinfo.getString("memberinfotitle");
		if(titel != null && titel.length() > 0){
			memberinfo.setMbTitle(titel);
		}
		
		String mbname = strMemberinfo.getString("memberinfoname");
		if(mbname != null && mbname.length() > 0){
			memberinfo.setMbName(mbname);
		}
		
		String birthday = strMemberinfo.getString("memberinfobird");
		if(birthday != null && birthday.length() > 0){
			memberinfo.setMbBirthday(birthday);
		}
		
		String ifchild = strMemberinfo.getString("memberinfoifchild");
		if(ifchild != null && ifchild.length() > 0){
			memberinfo.setMbChild(ifchild);
		}
		
		String edulevel = strMemberinfo.getString("memberinfoedulevel");
		if(edulevel != null && edulevel.length() > 0){
			memberinfo.setMbEdu(edulevel);
		}
		
		String addr = strMemberinfo.getString("memberinfoaddr");
		if(addr != null && addr.length() > 0){
			memberinfo.setMbAddr(addr);
		}
		
		Followerinfo followerinfo = new Followerinfo();
		followerinfo.setId(strMemberinfo.getString("followid"));
		
		int result = marktingService.insertMemberinfo(memberinfo, strMemberinfo.getString("followid"));
		
		System.out.println("@@@@@@@@@@@@@@result: " + result);
		return result;		
	
	}
	//search member
	@RequestMapping(value="getmemberinfo", method = RequestMethod.GET)
	public @ResponseBody MemberinfoWithBLOBs findMemberinfo(String name){
		
		return marktingService.getMemberinfobyname(new String(name));
		
	}
	//update member 
	@RequestMapping(value="updatememberinfo", method = RequestMethod.POST)
	public @ResponseBody int updateMemberinfo(@RequestBody JSONObject strMemberinfo){
		
		log.debug("Start to update member...");
		if(strMemberinfo == null){
			log.error("Failed to get member info from UI: " + strMemberinfo);
			return -1;
		}
		
		System.out.println("#################" + strMemberinfo.toString());
		
		//JSONObject jsonObject = JSONObject.fromObject(strMember);
		MemberinfoWithBLOBs memberinfo = new MemberinfoWithBLOBs();
		
		
		String mid = strMemberinfo.getString("memberid1");
		if(mid != null && mid.length() > 0){
			memberinfo.setId(mid);
		}
		
		String telnum = strMemberinfo.getString("memberinfotelnum");
		if(telnum != null && telnum.length() > 0){
			memberinfo.setName(telnum);
		}
		
		String titel = strMemberinfo.getString("memberinfotitle");
		if(titel != null && titel.length() > 0){
			memberinfo.setMbTitle(titel);
		}
		
		String mbname = strMemberinfo.getString("memberinfoname");
		if(mbname != null && mbname.length() > 0){
			memberinfo.setMbName(mbname);
		}
		
		String birthday = strMemberinfo.getString("memberinfobird");
		if(birthday != null && birthday.length() > 0){
			memberinfo.setMbBirthday(birthday);
		}
		
		String ifchild = strMemberinfo.getString("memberinfoifchild");
		if(ifchild != null && ifchild.length() > 0){
			memberinfo.setMbChild(ifchild);
		}
		
		String edulevel = strMemberinfo.getString("memberinfoedulevel");
		if(edulevel != null && edulevel.length() > 0){
			memberinfo.setMbEdu(edulevel);
		}
		
		String addr = strMemberinfo.getString("memberinfoaddr");
		if(addr != null && addr.length() > 0){
			memberinfo.setMbAddr(addr);
		}
		
		
		int result = marktingService.updateMemberinfo(memberinfo);
		
		System.out.println("@@@@@@@@@@@@@@result: " + result);
		return result;	
	}
	
	
	//for test data 
	@RequestMapping(value="gettestfollowerid", method = RequestMethod.POST)
	public @ResponseBody JSONObject getTestfollowerid(@RequestBody JSONObject strtestfollowerid){
		
		Map params = new HashMap();
		
		log.debug("Start to set member...");
		if(strtestfollowerid == null){
			log.error("Failed to get child info from UI: " + strtestfollowerid);
			
			params.put("id", "null");
			JSONObject result = JSONObject.fromObject(params);
			
			return result;
		}
		
		System.out.println("#################" + strtestfollowerid.toString());
		
		//JSONObject jsonObject = JSONObject.fromObject(strMember);
		Followerinfo follow = new Followerinfo();
		
		String flname = strtestfollowerid.getString("membertelnumname");
		if(flname != null && flname.length() > 0){
			follow.setName("openid-" + flname);
		}
				
		String flid = marktingService.getTestFollowerinfo(follow);
		
		params.put("id", flid);
		JSONObject result = JSONObject.fromObject(params);
		
		System.out.println("@@@@@@@@@@@@@@result: " + result);
		
		return result;		
	}

}
