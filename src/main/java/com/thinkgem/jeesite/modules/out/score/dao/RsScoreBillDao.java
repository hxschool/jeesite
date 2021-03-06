/**
 * Copyright &copy; 2018-2025 <a href="http://www.greathiit.com">哈尔滨信息工程学院</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.score.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.out.score.entity.RsScoreBill;

/**
 * 考试成绩单DAO接口
 * @author 赵俊飞
 * @version 2017-12-09
 */
@MyBatisDao
public interface RsScoreBillDao extends CrudDao<RsScoreBill> {
	public RsScoreBill getByKsh(@Param("ksh")String ksh);
}