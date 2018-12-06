<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生成绩管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#element_id').cxSelect({ 
				  url: '${ctx}/sys/office/treeLink',
				  selects: ['province', 'city', 'area'], 
				  jsonName: 'name',
				  jsonValue: 'value',
				  jsonSub: 'sub'
				}); 
			
			$("#btnExport").click(function() {
				top.$.jBox.confirm("确认要导出成绩数据吗？", "系统提示", function(v, h, f) {
					if (v == "ok") {
						$("#searchForm").attr("action", "${ctx}/student/studentCourse/export");
						$("#searchForm").submit();
					}
				}, {
					buttonsFocus : 1
				});
				top.$('.jbox-body .jbox-icon').css('top', '55px');
			});
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
			<br /> <input id="btnImportSubmit" class="btn btn-primary"
				type="submit" value="   导    入   " /> <a
				href="${ctx}/student/studentCourse/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/student/studentCourse/">学生成绩列表</a></li>
		<shiro:hasPermission name="student:studentCourse:edit"><li><a href="${ctx}/student/studentCourse/form">学生成绩添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="studentCourse" action="${ctx}/student/studentCourse/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<div id="element_id">


				<li><label>学院：</label>
				
					<select class="province input-medium"><option>请选择</option></select>
				</li>

				<li><label>专业：</label>
				
					<select id="city" class="city input-medium" style="width:178px"><option>请选择</option></select>
				</li>

				<li><label>班级：</label>
				
					<select id="area" class="area input-medium" name="clazzId" style="width:178px"><option>请选择</option></select>
		
				</li>
			
			</div>
			<li><label>教师姓名：</label>
					<form:input path="teacher.tchrName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="clearfix"></li>
			
			<li><label>学期：</label>
					<select name="termYear" class="input-medium">
						<option value="">请选择</option>
						<c:forEach items="${fns:termYear()}" var="term">
							<option value="${term.key}">${term.value}</option>
						</c:forEach>
					</select>
			</li>
			
			<li><label>课程名称：</label>
				<form:input path="course.cursName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>学号：</label>
				<form:input path="student.studentNumber" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			
			<li><label>学生姓名：</label>
				<form:input path="student.name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input
				id="btnExport" class="btn btn-primary" type="button" value="导出" /> <input
				id="btnImport" class="btn btn-primary" type="button" value="导入" />
			</li>
			
			
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>学期</th>
				<th>课程名称</th>
				<th>任课教师</th>
				<th>姓名</th>
				<th>课堂表现成绩</th>
				<th>综合成绩</th>
				<th>实验成绩</th>
				<th>期末成绩</th>
				<th>期中成绩</th>
				
				<th>作业成绩</th>
				
				
				<th>状态标记</th>
				<th>更新时间</th>

				<shiro:hasPermission name="student:studentCourse:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studentCourse">
			<tr>
				<td>
				<a href="${ctx}/student/studentCourse/form?id=${studentCourse.id}">
					${fns:termYear()[studentCourse.termYear]}
					</a>
				</td>
				<td>
					${studentCourse.course.cursName}
				</td>
				<td>
					${studentCourse.teacher.tchrName}
				</td>
				<td>
					${studentCourse.student.name}
				</td>
				<td>
					${studentCourse.classEvaValue}
				</td>
				<td>
					${studentCourse.evaValue}
				</td>
				<td>
					${studentCourse.expEvaValue}
				</td>
				<td>
					${studentCourse.finEvaValue}
				</td>
				<td>
					${studentCourse.midEvaValue}
				</td>
				
				<td>
					${studentCourse.workEvaValue}
				</td>
				
				
				<td>
					${studentCourse.status}
				</td>
				<td>
					<fmt:formatDate value="${studentCourse.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>

				<shiro:hasPermission name="student:studentCourse:edit"><td>
    				<a href="${ctx}/student/studentCourse/form?id=${studentCourse.id}">修改</a>
					<a href="${ctx}/student/studentCourse/delete?id=${studentCourse.id}" onclick="return confirmx('确认要删除该学生成绩吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>