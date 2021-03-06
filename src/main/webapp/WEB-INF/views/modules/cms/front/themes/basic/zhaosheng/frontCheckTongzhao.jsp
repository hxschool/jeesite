<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>哈尔滨信息工程学院-哈尔滨信息工程学院录取查询系统</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="renderer" content="webkit" />
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
<link rel="apple-touch-icon"
	href="${ctxStatic}/campus-account/images/apple-touch-icon.png">
<link rel="apple-touch-icon" sizes="114x114"
	href="${ctxStatic}/campus-account/images/apple-touch-icon-retina.png">

<script src="${ctxStatic}/campus-account/js/jquery-1.11.1.min.js"
	type="text/javascript"></script>
<link
	href="${ctxStatic}/campus-account/css/bundle-bundle_bootstrap_head.css"
	type="text/css" rel="stylesheet" media="screen, projection" />

<link
	href="${ctxStatic}/campus-account/css/bundle-bundle_theme.WbAce.fonts_head.css"
	type="text/css" rel="stylesheet" media="screen, projection" />
<!--[if IE 7]><link href="${ctxStatic}/campus-account/css/font-awesome-ie7.css" type="text/css" rel="stylesheet" media="screen, projection" /><![endif]-->

<link
	href="${ctxStatic}/campus-account/css/bundle-bundle_ui.WbAce_head.css"
	type="text/css" rel="stylesheet" media="screen, projection" />

<link
	href="${ctxStatic}/campus-account/css/bundle-bundle_theme.WbAce_head.css"
	type="text/css" rel="stylesheet" media="screen, projection" />




<!--[if lte IE 8]><link href="${ctxStatic}/campus-account/css/ace-ie.css" type="text/css" rel="stylesheet" media="screen, projection" /><![endif]-->

</head>
<body class=" layout-home">
	<div class="navbar navbar-inverse">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a href="/" class="brand"> <small> <i class="icon-user"></i>
						哈尔滨信息工程学院
				</small>
				</a>
				<!--/.brand-->

				
			</div>
		</div>
	</div>


	<div class="main-container container no-sidebar">
		<div class="main-content">

			<div class="page-content">
				<div class="row-fluid">
					<!--PAGE CONTENT BEGINS HERE-->
					<div class="content">

						<h3>哈尔滨信息工程学院录取查询系统</h3>

						<form action="jieguo"
							method="post" class="form-horizontal" onsubmit="return chechForm();">
							
										<input type="hidden" name="operation" value="tongzhao">
							<div class="widget-box">
								<div class="widget-header widget-header-large header-color-blue">
									<h4>结果查询</h4>
								</div>

								<div class="widget-body">
									<div class="widget-body-inner">
										<div class="widget-main">
											<div id="fuelux-wizard" class="row-fluid hide"
												style="display: block;">
												<ul class="wizard-steps">
													<li class="active" style="min-width: 50%; max-width: 50%;">
														<span class="step">1</span> <span class="title">核对个人信息</span>
													</li>


													<li class="" style="min-width: 50%; max-width: 50%;">
														<span class="step">2</span> <span class="title">完成</span>
													</li>
												</ul>
											</div>


											<hr>

											<div
												class="alert alert-block alert-error ${empty message ? 'hide' : ''}">
												<a class="close" data-dismiss="alert" href="#">×</a>
												${message}
											</div>

											<div class="step-content row-fluid position-relative"
												id="step-container">
												<div class="step-pane active" id="step1">

													<div class="span3">&nbsp;</div>

													<div class="span6">

														<div class="control-group ">

															<label for="name" class="control-label">姓名</label>
															<div class="controls">

																<input type="text" name="username" value="" id="name" placeholder="输入姓名"/>

															</div>
														</div>


														<div class="control-group ">

															<label class="control-label">身份证件号</label>


															<div class="controls">


																<input type="text" name="idCardNumber" value=""
																	id="idCardNumber" class="form-control"  placeholder="输入18位身份证号"/>

															</div>
															
														</div>



														<div class="control-group ">

															<label for="captcha" class="control-label">图文验证码</label>

															<div class="controls">

																<input type="text" name="captcha" size="6" class="form-control"
																	placeholder="右侧图片中的字母" value="" id="captcha"  /> <img
																	src="${pageContext.request.contextPath}/servlet/validateCodeServlet"
																	id="captchaImage" style="height: 30px" onclick="this.src='${pageContext.request.contextPath}/servlet/validateCodeServlet?d='+Math.random();" />
															</div>
														</div>

													</div>

													<div class="span3"></div>

												</div>
											</div>

											<hr>

											<div class="row-fluid wizard-actions">
												

												<button name="_eventId_next" id="_eventId_next"
													onclick="javascript:toNext()"
													class="btn btn-next btn-success">
													下一步 <i class="icon-arrow-right icon-on-right"></i>
												</button>


											</div>
										</div>
									</div>
								</div>
							</div>


						</form>

						<script language="JavaScript">
							$(function() {
								$('btn-exit').click(function() {
									window.close();
								});
							});
							function toNext() {
								var Sys = {}, s;
								(s = (navigator.userAgent.toLowerCase())
										.match(/msie ([\d.]+)/)) ? Sys.ie = s[1]
										: 0;
								if (Sys.ie && Number(Sys.ie) <= 7)
									$('form').submit();
							}
						</script>

					</div>
					<!--PAGE CONTENT ENDS HERE-->
				</div>
				<!--/row-->
			</div>
			<!--/#page-content-->

		</div>
		<!--/#main-content-->
	</div>
	<!--/.fluid-container#main-container-->

	<a href="#" id="btn-scroll-up"
		class="btn-scroll-up btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only bigger-110"></i>
	</a>

	<script
		src="${ctxStatic}/campus-account/js/bundle-bundle_bootstrap_defer.js"
		type="text/javascript"></script>





	<!--[if IE 7]><script src="/campus-account/static/plugins/wb-ace-ui-0.2.3/js/elusive-icons-lte-ie7.js" type="text/javascript" ></script><![endif]-->
	<script
		src="${ctxStatic}/campus-account/js/bundle-bundle_ui.WbAce_defer.js"
		type="text/javascript"></script>


	<script
		src="${ctxStatic}/campus-account/js/bundle-bundle_theme.WbAce_defer.js"
		type="text/javascript"></script>



	<script src="${ctxStatic}/campus-account/js/application.js"
		type="text/javascript"></script>
		<script type="text/javascript">
		function chechForm(){
			var name = $("#name").val();
			var idCardNumber = $("#idCardNumber").val();
			var captcha = $("#captcha").val();
			var ret = "";
			if(name==""){
				ret ="姓名不能为空\r\n";
			}
			if(name.length<2||name>10){
				ret = ret + "姓名长度最小为两位,最大为八位！\r\n";
			}
			if(!check_sfzh(idCardNumber)){
				ret = ret + "身份证信息不合法\r\n";
			}
			if(captcha==""){
				ret = ret+ "验证码信息不合法\r\n";
			}
			if(ret!=""){
				alert(ret);
				return false;
			}
			return true;
		}
		
		function check_sfzh(value) {

			var arrExp = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]; //加权因子  
			var arrValid = [1, 0, "X", 9, 8, 7, 6, 5, 4, 3, 2]; //校验码  
			if (/^\d{17}\d|x$/i.test(value)) {
				var sum = 0,
					idx;
				for (var i = 0; i < value.length - 1; i++) {
					// 对前17位数字与权值乘积求和  
					sum += parseInt(value.substr(i, 1), 10) * arrExp[i];
				}
				// 计算模（固定算法）  
				idx = sum % 11;
				// 检验第18为是否与校验码相等  
				return arrValid[idx] == value.substr(17, 1).toUpperCase();
			} else {
				return false;
			}
		}
		</script>
</body>
</html>
