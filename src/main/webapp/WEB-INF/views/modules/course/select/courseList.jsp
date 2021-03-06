<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>选课信息维护</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnImport").click(function() {
				$.jBox($("#importBox").html(), {
					title : "导入数据",
					buttons : {
						"关闭" : true
					},
					bottomText : "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/student/studentCourse/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');">
			<br /> <input id="uploadFile" name="file" type="file"
				style="width: 330px" /><br />
			<br /> <input id="btnImportSubmit" class="button button-primary button-rounded button-small"
				type="submit" value="   导    入   " /> <a class="button  button-rounded button-small"
				href="${ctx}/student/studentCourse/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/course/select/">信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="course" action="${ctx}/course/select/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input name="course.cursProperty" type="hidden" value="${param.course.cursProperty}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>课程名称：</label>
			 <form:input path="cursName"
					htmlEscape="false" maxlength="50" class="input-medium" /></li>


			<li><label>任课教师：</label> <form:input path="teacher.tchrName"
					htmlEscape="false" maxlength="50" class="input-medium" /></li>
			
			<li><label>学期：</label>
					<select name="cursYearTerm" class="input-medium">
					<option value="">请选择</option>
					<c:forEach items="${fns:termYear()}" var="termYear">
						<option value="${termYear.key}"
							<c:if test="${config.termYear==termYear.key}"> selected="selected" </c:if>>${termYear.key}</option>
					</c:forEach>
			</select></li>
			<li class="btns"><input id="btnSubmit" class="button button-primary button-rounded button-small" type="submit" value="查询"/>
			<input
				id="btnImport" class="button button-primary button-rounded button-small" type="button" value="成绩导入" />
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			
				
				
				<th>课程编号</th>
				<th>课程名称</th>
				<th>任课教师</th>
				<th>开设学期</th>
				<th>学时</th>
				<th>学分</th>
				<th>课程性质</th>
				<th>考核形式</th>
				<th>课程类型</th>
				<th>课程简介</th>
				<th>查看</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="course">
			<tr>
				<td><a href="${ctx}/course/select/student?id=${course.id}">
					${course.cursNum}
				</a></td>
				<td>
					<c:if test="${course.courseTeachingMode!=null}">
						${fns:getDictLabel(course.courseTeachingMode.teacMethod, 'teac_method', '')} -
					</c:if>
					 ${course.cursName}
				</td>
				<td>
					${course.teacher.tchrName}
				</td>
		
				<td>
					${course.cursYearTerm}
				</td>
				<td>
					${course.cursClassHour}
				</td>
				<td>
					${course.cursCredit}
				</td>
				<td>
					${fns:getDictLabel(course.cursProperty, 'course_property', '')}
				</td>
					<td>
					
					${fns:getDictLabel(course.cursProperty, 'course_curs_form', '')}
				</td>
				

				<td>
					
					${fns:getDictLabel(course.cursType, 'course_curs_type', '')}
				</td>
	
				<td>
					<a href="javacript:void(0);" title="${course.cursIntro }">${fns:abbr(course.cursIntro,10)}</a>
				</td>
				<td>
					
    				<a class="button button-primary button-rounded button-small" href="${ctx}/course/select/student?id=${course.id}">查看(${fnc:countStudents(course.id)})</a>
					<a class="button button-primary button-rounded button-small" href="${ctx}/course/course/form?cursProperty=50&id=${course.id}">修改</a>
    				
    				
    				
    				<div class="btn-group ">
						    <button type="button" class="btn btn-default dropdown-toggle " data-toggle="dropdown">导出
						        <span class="caret"></span>
						    </button>
							<ul class="dropdown-menu" role="menu">
								<li>
								<a  href="${ctx}/course/select/export?course.id=${course.id}">签到表</a>
								</li>
								<shiro:hasPermission name="student:studentCourse:export">
									<li><a href="${ctx}/student/studentCourse/export/student?id=${course.id}">成绩信息</a>
									</li>
								</shiro:hasPermission>
								<li><a href="${ctx}/course/course/exportStudentCourse?id=${course.id}">成绩单</a>
								</li>
							</ul>
						</div>
    				
    				
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>