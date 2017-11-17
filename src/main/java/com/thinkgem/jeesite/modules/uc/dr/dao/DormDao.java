/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.uc.dr.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.uc.dr.entity.Dorm;

/**
 * 寝室信息DAO接口
 * @author 赵俊飞
 * @version 2017-10-11
 */
@MyBatisDao
public interface DormDao extends CrudDao<Dorm> {
	Dorm getByStudentNumber(@Param("studentNumber")String studentNumber);
}