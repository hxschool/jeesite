/**
 * Copyright &copy; 2018-2025 <a href="http://www.greathiit.com">哈尔滨信息工程学院</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.uc.ec.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.uc.ec.dao.UcEmergencyContactDao;
import com.thinkgem.jeesite.modules.uc.ec.entity.UcEmergencyContact;

/**
 * 社交通讯录Service
 * @author 社交通讯录
 * @version 2017-10-11
 */
@Service
@Transactional(readOnly = true)
public class UcEmergencyContactService extends CrudService<UcEmergencyContactDao, UcEmergencyContact> {

	public UcEmergencyContact get(String id) {
		return super.get(id);
	}
	
	public List<UcEmergencyContact> findByParentIdsLike(UcEmergencyContact ucEmergencyContact) {
		return super.findByParentIdsLike(ucEmergencyContact);
	}
	
	public Page<UcEmergencyContact> findPage(Page<UcEmergencyContact> page, UcEmergencyContact ucEmergencyContact) {
		return super.findPage(page, ucEmergencyContact);
	}
	
	@Transactional(readOnly = false)
	public void save(UcEmergencyContact ucEmergencyContact) {
		super.save(ucEmergencyContact);
	}
	
	@Transactional(readOnly = false)
	public void delete(UcEmergencyContact ucEmergencyContact) {
		super.delete(ucEmergencyContact);
	}
	
}