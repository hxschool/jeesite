<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/workConfigDate/">考勤信息列表</a></li>
		<shiro:hasPermission name="sys:workConfigDate:edit"><li><a href="${ctx}/sys/workConfigDate/form">考勤信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="workConfigDate" action="${ctx}/sys/workConfigDate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>考勤日期：</label>
				<form:input path="configDate" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>创建者：</label>
				<form:input path="createBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>考勤日期</th>
				<th>创建者</th>
				<th>更新时间</th>
				<th>remarks</th>
				<shiro:hasPermission name="sys:workConfigDate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="workConfigDate">
			<tr>
				<td><a href="${ctx}/sys/workConfigDate/form?id=${workConfigDate.id}">
					${workConfigDate.configDate}
				</a></td>
				<td>
					${workConfigDate.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${workConfigDate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${workConfigDate.remarks}
				</td>
				<shiro:hasPermission name="sys:workConfigDate:edit"><td>
    				<a href="${ctx}/sys/workConfigDate/form?id=${workConfigDate.id}">修改</a>
					<a href="${ctx}/sys/workConfigDate/delete?id=${workConfigDate.id}" onclick="return confirmx('确认要删除该考勤信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>