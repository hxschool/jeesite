<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>选课信息表管理</title>
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
		<li class="active"><a href="${ctx}/select/selectCourse/">选课信息表列表</a></li>
		<shiro:hasPermission name="select:selectCourse:edit"><li><a href="${ctx}/select/selectCourse/form">选课信息表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="selectCourse" action="${ctx}/select/selectCourse/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="button button-primary button-rounded button-small" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>remarks</th>
				<shiro:hasPermission name="select:selectCourse:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="selectCourse">
			<tr>
				<td><a href="${ctx}/select/selectCourse/form?id=${selectCourse.id}">
					<fmt:formatDate value="${selectCourse.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${selectCourse.remarks}
				</td>
				<shiro:hasPermission name="select:selectCourse:edit"><td>
    				<a href="${ctx}/select/selectCourse/form?id=${selectCourse.id}">修改</a>
					<a href="${ctx}/select/selectCourse/delete?id=${selectCourse.id}" onclick="return confirmx('确认要删除该选课信息表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>