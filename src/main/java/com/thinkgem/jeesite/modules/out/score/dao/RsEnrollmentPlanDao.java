/**
 * Copyright &copy; 2018-2025 <a href="http://www.greathiit.com">哈尔滨信息工程学院</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.score.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.out.score.entity.RsEnrollmentPlan;

/**
 * 招生计划DAO接口
 * @author 赵俊飞
 * @version 2018-01-09
 */
@MyBatisDao
public interface RsEnrollmentPlanDao extends CrudDao<RsEnrollmentPlan> {
	public RsEnrollmentPlan getMajorId(RsEnrollmentPlan entity);
	public RsEnrollmentPlan getRsEnrollmentPlanByMajorName(RsEnrollmentPlan entity);
	
}