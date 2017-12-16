<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>楼宇管理</title>
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
		<li class="active"><a href="${ctx}/school/schoolRoot/">楼宇列表</a></li>
		<shiro:hasPermission name="school:schoolRoot:edit"><li><a href="${ctx}/school/schoolRoot/form">楼宇添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="schoolRoot" action="${ctx}/school/schoolRoot/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>数据值</th>
				<th>标签名</th>
				<th>类型</th>
				<th>description</th>
				<th>更新时间</th>
				<th>remarks</th>
				<shiro:hasPermission name="school:schoolRoot:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="schoolRoot">
			<tr>
				<td><a href="${ctx}/school/schoolRoot/form?id=${schoolRoot.id}">
					${schoolRoot.value}
				</a></td>
				<td>
					${schoolRoot.label}
				</td>
				<td>
					${schoolRoot.type}
				</td>
				<td>
					${schoolRoot.description}
				</td>
				<td>
					<fmt:formatDate value="${schoolRoot.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${schoolRoot.remarks}
				</td>
				<shiro:hasPermission name="school:schoolRoot:edit"><td>
    				<a href="${ctx}/school/schoolRoot/form?id=${schoolRoot.id}">修改</a>
					<a href="${ctx}/school/schoolRoot/delete?id=${schoolRoot.id}" onclick="return confirmx('确认要删除该楼宇吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>