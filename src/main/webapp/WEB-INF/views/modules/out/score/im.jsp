<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考试成绩单管理</title>
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">应新生数据初始化导入(${config.termYear })</a></li>
	</ul>
	
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/out/score/bill/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');">
			<br /> <input id="uploadFile" name="file" type="file"
				style="width: 330px" /><br />
			<br /> <input id="btnImportSubmit" class="btn btn-primary"
				type="submit" value="   导    入   " /> <a
				href="${ctx}/out/score/bill/import/template">下载模板</a>
		</form>
	</div>
	
	<form:form id="searchForm" modelAttribute="rsScoreBill" action="${ctx}/out/score/bill/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns">
			
			<input
				id="btnImport" class="btn btn-primary" type="button" value="成绩导入" />
				
				<a class="btn btn-primary"
				href="${ctx}/out/score/bill/import/template">下载模板</a>
				
				
			</li>
			<li class="clearfix"></li>
		</ul>
		<fieldset>
			<legend>使用说明</legend>
			${config.message }
		</fieldset>
	</form:form>
	
	<sys:message content="${message}"/>
	
</body>
</html>