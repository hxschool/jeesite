/**
 * Copyright &copy; 2018-2025 <a href="http://www.greathiit.com">哈尔滨信息工程学院</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.web.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.thinkgem.jeesite.common.annotation.SameUrlData;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.AccountValidatorUtil;
import com.thinkgem.jeesite.common.utils.IdcardValidator;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.api.ems.KdniaoTrackQueryAPI;
import com.thinkgem.jeesite.modules.api.ems.KdniaoTrackResponse;
import com.thinkgem.jeesite.modules.api.ems.KdniaoTrackTraces;
import com.thinkgem.jeesite.modules.cms.entity.Site;
import com.thinkgem.jeesite.modules.cms.utils.CmsUtils;
import com.thinkgem.jeesite.modules.out.ems.entity.RsStudentEms;
import com.thinkgem.jeesite.modules.out.ems.service.RsStudentEmsService;
import com.thinkgem.jeesite.modules.out.rs.entity.RsStudent;
import com.thinkgem.jeesite.modules.out.rs.service.RsStudentService;
import com.thinkgem.jeesite.modules.out.sc.entity.RsStudentResult;
import com.thinkgem.jeesite.modules.out.sc.service.RsStudentResultService;
import com.thinkgem.jeesite.modules.out.system.entity.SystemStudent;
import com.thinkgem.jeesite.modules.out.system.service.SystemStudentService;
import com.thinkgem.jeesite.modules.recruit.entity.student.RecruitStudent;
import com.thinkgem.jeesite.modules.recruit.service.student.RecruitStudentService;
import com.thinkgem.jeesite.modules.sys.entity.SysConfig;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilter;
import com.thinkgem.jeesite.modules.sys.service.SysConfigService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 网站Controller
 * @author ThinkGem
 * @version 2013-5-29
 */
@Controller
@RequestMapping(value = "${frontPath}/2020")
public class FrontZhaoShengController extends BaseController{
	
	@Autowired
	private RsStudentService rsStudentService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SystemStudentService systemStudentService;
	@Autowired
	private RsStudentResultService rsStudentResultService;
	@Autowired
	private RsStudentEmsService rsStudentEmsService;
	@Autowired
	private RecruitStudentService recruitStudentService;
	@Autowired
	private SysConfigService sysConfigService;
	
	private SysConfig config ;
	private Site site;
	 @ModelAttribute 
	 public void get(Model model) {
		 site = CmsUtils.getSite(Site.defaultSiteId());
		 config = sysConfigService.getModule(Global.SYSCONFIG_EXAM);
			model.addAttribute("config", config);
	 }
	
	/**
	 * 网站首页
	 */
	@RequestMapping(value = {"index", ""})
	public String index(Model model) {
		
		model.addAttribute("isIndex", true);
		return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/index";
	}

	
	@RequestMapping(value = "skip_{module}")
	public String frontCheckMobile(@PathVariable("module") String module,String hc_form_sfzh,Model model) {
		
		module = module.substring(0, 1).toUpperCase() + module.substring(1);
		
		SystemStudent systemStudent = systemStudentService.getByIdCard(hc_form_sfzh);
		if(!StringUtils.isEmpty(systemStudent)){
			model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, "该用户已注册,请输入身份证号码进行相关数据操作.");
			return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckJieguo";
		}
		
		return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheck".concat(module);
	}


	@SameUrlData
	@RequestMapping(value = "zhaosheng", method = RequestMethod.POST)
	public String checkZhaosheng(RsStudent rsStudent, HttpServletResponse response, Model model) {
		Site site = CmsUtils.getSite(Site.defaultSiteId());
		
		StringBuffer sb = new StringBuffer();
		
		if(StringUtils.isEmpty(rsStudent.getHc_form_province())){
			sb.append("市（行署）不允许为空");
			sb.append("\r\n");
		}
		if(StringUtils.isEmpty(rsStudent.getHc_form_city())){
			sb.append(" 县（市、区）不允许为空");
			sb.append("\r\n");
		}
		if(StringUtils.isEmpty(rsStudent.getHc_form_kl())){
			sb.append(" 尚未选择科类");
			sb.append("\r\n");
		}
		
		if(StringUtils.isEmpty(rsStudent.getHc_form_xm())){
			sb.append(" 姓名不允许为空");
			sb.append("\r\n");
		}else{
			if(rsStudent.getHc_form_xm().length()>6){
				sb.append(" 姓名长度超限,姓名长度最大为6位");
				sb.append("\r\n");
			}
			if(!AccountValidatorUtil.isChinese(rsStudent.getHc_form_xm())){
				sb.append(" 姓名必须为中文");
				sb.append("\r\n");
			}
		}
		
		
		if(StringUtils.isEmpty(rsStudent.getHc_form_birth())){
			sb.append(" 出生日期不允许为空");
			sb.append("\r\n");
		}
		if(StringUtils.isEmpty(rsStudent.getHc_form_sfzh())){
			sb.append(" 身份证号不允许为空");
			sb.append("\r\n");
		}
		
		if(!IdcardValidator.isValidatedAllIdcard(rsStudent.getHc_form_sfzh())){
			sb.append(" 身份证号信息不合法");
			sb.append("\r\n");
		}
		
		if(StringUtils.isEmpty(rsStudent.getHc_form_bylb())){
			sb.append(" 尚未选择毕业类别");
			sb.append("\r\n");
		}
		
		if(StringUtils.isEmpty(rsStudent.getHc_form_byxx())){
			sb.append(" 尚未选择毕业学校");
			sb.append("\r\n");
		}
		if(StringUtils.isEmpty(rsStudent.getHc_form_sj())){
			sb.append(" 联系方式不能为空");
			sb.append("\r\n");
		}
		if(!AccountValidatorUtil.isMobile(rsStudent.getHc_form_sj())){
			sb.append(" 联系手机号格式不对");
			sb.append("\r\n");
		}
		if(StringUtils.isEmpty(rsStudent.getHc_form_zy1())){
			sb.append(" 尚未选择报考专业1");
			sb.append("\r\n");
		}
		
		if(StringUtils.isEmpty(rsStudent.getHc_form_zytj())){
			sb.append(" 尚未选择专业调剂");
			sb.append("\r\n");
		}
		
		if(StringUtils.isEmpty(rsStudent.getHc_form_jjlxr_fa_gx())){
			sb.append(" 紧急联系人与本人关系不允许为空");
			sb.append("\r\n");
		}
		if(StringUtils.isEmpty(rsStudent.getHc_form_jjlxr_fa_name())){
			sb.append(" 紧急联系人不允许为空");
			sb.append("\r\n");
		}
		
		if(!AccountValidatorUtil.isChinese(rsStudent.getHc_form_jjlxr_fa_name())){
			sb.append(" 紧急联系人必须为中文");
			sb.append("\r\n");
		}
		
		if(StringUtils.isEmpty(rsStudent.getHc_form_jjlxr_fa_tel())){
			sb.append(" 紧急联系人电话不允许为空");
			sb.append("\r\n");
		}
		if(!AccountValidatorUtil.isMobile(rsStudent.getHc_form_sj())){
			sb.append(" 紧急联系人电话手机号格式不对");
			sb.append("\r\n");
		}
		
		
		
		if(!StringUtils.isEmpty(sb.toString())){
			model.addAttribute("message", sb.toString());
			return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckError";
		}
		
		if(StringUtils.isEmpty(rsStudent.getId())){
			String no = systemService.getRsStudentId();
			//报考顺序号
			rsStudent.setHc_form_area(no);
			model.addAttribute("no", "报专业顺序号"+no+"，考生需牢记。");
		}
		try{
			SysConfig config = sysConfigService.getModule(Global.SYSCONFIG_EXAM);
			rsStudent.setTermYear(config.getTermYear());
			rsStudentService.save(rsStudent);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckSuccess";
	}
	
	
	@SameUrlData
	@RequestMapping(value = "jieguo", method = RequestMethod.POST)
	public String checkJieguo(String username, String idCardNumber, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		String captcha = request.getParameter("captcha");
		Site site = CmsUtils.getSite(Site.defaultSiteId());
		if(!ValidateCodeServlet.validate(request,captcha)){
			model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, "图文验证码错误, 请重试.");
			return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckJieguo";
		}
		if(username==null||username.equals("")){
			model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, "姓名不允许为空");
			return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckJieguo";
		}
		
		if(idCardNumber==null||idCardNumber.equals("")){
			model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, "身份证件号不允许为空");
			return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckJieguo";
		}
		String operation = request.getParameter("operation");
		if(!StringUtils.isEmpty(operation)) {

			switch (operation) {
			case "tongzhao": {
				RecruitStudent recruitStudent = new RecruitStudent();
				recruitStudent.setUsername(username);
				recruitStudent.setIdCard(idCardNumber);
				RecruitStudent pojo = recruitStudentService.getRecruitStudent(recruitStudent);
				if(StringUtils.isEmpty(pojo)) {
					model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, "根据姓名和身份证号未查找到相关信息,请联系报考教师");
					return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckJieguo";
				}
				model.addAttribute("systemStudent", pojo);
				return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckTongzhaoResult";
			}
			case "ems": {
				RsStudentEms studentEms = rsStudentEmsService.getByUsernameAndIdCard(username, idCardNumber);
				
				if(StringUtils.isEmpty(studentEms)) {
					model.addAttribute("message", "查询失败,根据姓名身份证未查找到相关EMS信息");
					return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckError";
				}
				
				KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
				StringBuffer sb = new StringBuffer();
				try {
					String result = api.getOrderTracesByJson("EMS", studentEms.getHcFormEms());
					Gson gson = new Gson();
					KdniaoTrackResponse kniaoTrackResponse = gson.fromJson(result, KdniaoTrackResponse.class);
					for (KdniaoTrackTraces kdniaoTrackTraces : kniaoTrackResponse.getTraces()) {
						sb.append(kdniaoTrackTraces.getAcceptTime());
						sb.append("<br>");
						sb.append(kdniaoTrackTraces.getAcceptStation());
						sb.append("<br>");
						sb.append(
								"--------------------------------------------------------------------------------------------");
						sb.append("<br>");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				model.addAttribute("ems", sb.toString());
				model.addAttribute("systemStudent", studentEms);
				model.addAttribute("message", "查询成功");
				return "modules/cms/front/themes/" + site.getTheme() + "/zhaosheng/frontCheckEmsResult";
				
			}
			}
			
			
			
		}
		
		
		SystemStudent systemStudent = systemStudentService.getByUsernameAndIdCard(username, idCardNumber);
		
		RsStudentResult rsStudentResult = new RsStudentResult();
		rsStudentResult.setHcFormXm(username);
		rsStudentResult.setHcFormSfzh(idCardNumber);
		RsStudentResult entity = rsStudentResultService.get(rsStudentResult);
		if(!StringUtils.isEmpty(entity)) {
			
			if(!StringUtils.isEmpty(systemStudent)&&!StringUtils.isEmpty(systemStudent.getHcFormCj())){
				model.addAttribute("systemStudent", systemStudent);
				model.addAttribute("message", "单招考试成绩已发布，录取结果于2018年4月15日后发布");
				return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckChengji";
				
			}
			
			model.addAttribute("systemStudent", entity);
			model.addAttribute("message", "单招考试成绩已发布，录取结果于2018年4月15日后发布");
			return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckResult";
		}
		
		
		if(StringUtils.isEmpty(systemStudent)){
			model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, "根据姓名和身份证号未查找到相关信息,请联系报考教师");
			return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckJieguo";
		}
	
		String ret = "0";
		
		if(!StringUtils.isEmpty(systemStudent.getHcFormZhuangtai())&&!systemStudent.getHcFormZhuangtai().equals("")){
			ret = systemStudent.getHcFormZhuangtai().equals("1")?"1":"2";
		}
		
		if(ret.equals("1")){
			model.addAttribute("message", "查询登记成功");
		}else if (ret.equals("2")){
			model.addAttribute("message", "报考失败,请联系报考教师");
		}else{
			
			model.addAttribute("systemStudent", systemStudent);
			if(!StringUtils.isEmpty(systemStudent.getHcFormCj())) {
				model.addAttribute("message", "已出成绩,等待录取中");
				return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckChengji";
			}
			model.addAttribute("message", "信息审核中");
			return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckZhaoSheng";
		}
		
		return "modules/cms/front/themes/"+site.getTheme()+"/zhaosheng/frontCheckJieguoOk";
	}
	
}
