<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>交易明细管理</title>
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
		<li class="active"><a href="${ctx}/payment/order/order/">交易明细列表</a></li>
		<shiro:hasPermission name="payment:order:order:edit"><li><a href="${ctx}/payment/order/order/form">交易明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="order" action="${ctx}/payment/order/order/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>交易流水编码：</label>
				<form:input path="traderecord.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品编码：</label>
				<form:input path="payId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品标题：</label>
				<form:input path="payTitle" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商品备注：</label>
				<form:input path="payRemark" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>支付金额：</label>
				<form:input path="payAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>支付时间：</label>
				<input name="payTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>错误码：</label>
				<form:input path="errorCode" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>错误描述：</label>
				<form:input path="errorMsg" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:input path="status" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>交易流水编码</th>
				<th>商品编码</th>
				<th>商品标题</th>
				<th>商品备注</th>
				<th>支付金额</th>
				<th>支付时间</th>
				<th>错误码</th>
				<th>错误描述</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="payment:order:order:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="order">
			<tr>
				<td><a href="${ctx}/payment/order/order/form?id=${order.id}">
					${order.traderecord.id}
				</a></td>
				<td>
					${order.payId}
				</td>
				<td>
					${order.payTitle}
				</td>
				<td>
					${order.payRemark}
				</td>
				<td>
					${order.payAmount}
				</td>
				<td>
					<fmt:formatDate value="${order.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${order.errorCode}
				</td>
				<td>
					${order.errorMsg}
				</td>
				<td>
					${order.status}
				</td>
				<td>
					<fmt:formatDate value="${order.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${order.remarks}
				</td>
				<shiro:hasPermission name="payment:order:order:edit"><td>
    				<a href="${ctx}/payment/order/order/form?id=${order.id}">修改</a>
					<a href="${ctx}/payment/order/order/delete?id=${order.id}" onclick="return confirmx('确认要删除该交易明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>