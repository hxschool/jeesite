/**
 * Copyright &copy; 2018-2025 <a href="http://www.greathiit.com">哈尔滨信息工程学院</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.rs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.out.rs.dao.RsStudentDao;
import com.thinkgem.jeesite.modules.out.rs.entity.RsStudent;

/**
 * 单招报名申请表Service
 * @author qq773152
 * @version 2017-11-02
 */
@Service
@Transactional(readOnly = true)
public class RsStudentService extends CrudService<RsStudentDao, RsStudent> {
	@Autowired
	private RsStudentDao rsStudentDao;
	public RsStudent get(String id) {
		return super.get(id);
	}
	
	public List<RsStudent> findByParentIdsLike(RsStudent rsStudent) {
		return super.findByParentIdsLike(rsStudent);
	}
	
	public Page<RsStudent> findPage(Page<RsStudent> page, RsStudent rsStudent) {
		return super.findPage(page, rsStudent);
	}
	
	@Transactional(readOnly = false)
	public void save(RsStudent rsStudent) {
		super.save(rsStudent);
	}
	
	@Transactional(readOnly = false)
	public void delete(RsStudent rsStudent) {
		super.delete(rsStudent);
	}
	
}