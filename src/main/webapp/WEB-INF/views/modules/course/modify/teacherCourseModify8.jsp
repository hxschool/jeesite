<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程内容管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
	<link rel="stylesheet" href="${ctxStatic}/modules/teacher/common.css" />
<link rel="stylesheet"
	href="${ctxStatic}/modules/teacher/admin.css" />


<script type="text/javascript">
	var msg = "${message}";
	if (msg != "") {
		alert(msg);
	}
</script>
</head>

<body>
	
	<div class="container">
		<div class="row">
		<div class="span12">
				<div class="div-module-add-curs">
					<h6><img class="image-path-1" src="${ctxStatic}/modules/img/circle.jpg"/>
						<a href="#">课程列表</a><img class="image-path-2" src="${ctxStatic}/modules/img/zhexian.jpg"/>${course.cursName}
					</h6>
				</div>
				
				<form action="teacherCourse_Modify_8_modifyNoteByCursId" method="post"
					enctype="multipart/form-data" class="form-horizontal">
					<input type="hidden" name="id" value="${course.id}">
					<div class="div-inf">
						<div class="div-inf-title">（一）与相关课程的分工衔接</div>
						<div class="div-inner-text">
							<textarea name="cursNote1">${course.cursNote1 }</textarea>
						</div>
					</div>
					<div class="div-inf">
						<div class="div-inf-title">（二）其他说明</div>
						<div class="div-inner-text">
							<textarea name="cursNote2">${course.cursNote2 }</textarea>
						</div>
					</div>
					<div class="div-btn">
							<input type="submit" value="提交" class="btn">
						</div>
				</form>
			</div>
		</div>
	</div>
	
</body>
</html>
