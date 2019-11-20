/**
 * Copyright &copy; 2018-2025 <a href="http://www.greathiit.com">哈尔滨信息工程学院</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.student.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 变动进度表Entity
 * @author 变动进度表
 * @version 2019-08-19
 */
public class StudentStatusLog extends DataEntity<StudentStatusLog> {
	
	private static final long serialVersionUID = 1L;
	private String logType;
	private Student student;		// 用户号
	private String status;		// 状态
	private String description;		// 操作过程
	
	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public StudentStatusLog() {
		super();
	}

	public StudentStatusLog(String id){
		super(id);
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Length(min=0, max=10, message="状态长度必须介于 0 和 10 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}