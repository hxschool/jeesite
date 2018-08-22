<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>首页</title>
<meta name="decorator" content="xuanke_default_${site.theme}" />
<meta name="description"
	content="哈尔滨信息工程学院-国家示范性软件学院 http://greathiit.com ${site.description} 选课系统" />
<meta name="keywords"
	content="哈尔滨信息工程学院-国家示范性软件学院 http://greathiit.com ${site.keywords} 选课系统" />
</head>
<body>



	<div class="wrap maincontent">

		<div class="container">
			<div class="row">
				<div class="col-xs-12 col-md-3 mt30 side hidden-xs ">

					<div class="widget-box clearfix"
						style="margin: 3px; border-style: solid; border-color: #CC9900; text-align: center;">
						<script type="text/javascript" charset="utf-8"
							src="${ctxStatic}/xuanke/home/Js/showtime.js"></script>
					</div>

					<div class="widget-box clearfix">
						<h2 class="h4 widget-box__title">分类列表(1)</h2>
						<div class="pcss3mm ">
							<ul id="pcss3mm" class="nav nav-pills" role="tablist">
								<li id="cate1"><a href="/xuanke/list-${category.id }.html">常见问题与解答</a></li>
								<li id="cate2"><a href="tel:18801029695">反馈消息</a></li>
							</ul>
						</div>
					</div>



					<div class="widget-box no-border">
						<h2 class="h4 widget-box__title">最新公告</h2>
						<ul class="widget-links list-unstyled">
							<c:forEach items="${articles}" var="article" varStatus="status">
								<c:set var="link"
									value="/xuanke/view-${article.category.id}-${article.id}${urlSuffix}"
									scope="session"></c:set>
								<c:if test="${!empty article.link }">
									<c:set var="link" value="${article.link}" scope="session"></c:set>
								</c:if>
								<li class="widget-links__item"><a href="${link }"
									title="${article.title }">${article.title }</a> <small
									class="text-muted"> ， ${article.hits } 浏览 </small></li>
							</c:forEach>

						</ul>
					</div>


				</div>
				<!-- /.layout-sidebar -->



				<div class="col-xs-12 col-md-9 main mt30">
					<div class="panel panel-default panel-archive">
						<div class="panel-body">
							<!-- Nav tabs -->
							<ul class=" nav nav-pills pb10 mb10 mt10">
								<li class="active"><a href="">已选课程</a></li>
								<c:forEach items="${selectCourses}" var="selectCourse"
									varStatus="status">
									<li><a>${selectCourse.course.cursName }</a></li>
								</c:forEach>
							</ul>
							<div style="min-height: 300px;">
								<form action="${ctx }" method="post"
									id="formsearch">
									<div class="example-code">
										<div class="form-label float-left">
											<label>课程名称：</label>
										</div>
										<div class="form-input col-md-2">
											<input type="text" value="" name="cursName" size="10" />
										</div>
										
										<div class="form-label float-left">
											<label>任课老师：</label>
										</div>
										<div class="form-input col-md-2">
											<input type="text" value="" name="teacher.name" size="10" />
										</div>

										<button type="submit" class="btn medium bg-blue">
											<span class="button-content"> <i
												class="glyph-icon icon-search"></i>查询
											</span>
										</button>

										<a class="btn medium bg-orange"
											href="javascript:clearQuery(this)"> <span
											class="button-content"><i class="glyph-icon icon-undo"></i>
												清空查询</span>
										</a>

									</div>
									<!-- example-code -->
									<div class="divider"></div>
								</form>
								<br>

								<table class="dataTable table text-left" id="dataTables">
									<thead>
										<tr>
											<th>序号</th>
											<th>课程名称</th>
											<th>任课老师</th>
											<th>所属专业</th>
											<th>学时</th>
											<th>学分</th>
											<th class="text-center" width="60">操作</th>
										</tr>
									</thead>

									<tbody>
										<c:forEach items="${courses}" var="course" varStatus="status">
											<tr>
												<td>${status.index+1 }</td>
												<td>${course.cursName}</td>
												<td>${course.teacher.name}</td>
												<td>${course.cursMajor }</td>
												<td>${course.cursClassHour }</td>

												<td>${course.cursCredit}</td>
												<td><c:if test="${isIndex } && ${not empty  fns:getUser().id}">

														<c:set var="bgColor" value="bg-green" scope="application"></c:set>
														<c:set var="bgIcon" value="icon-plus-sign" scope="application"></c:set>
														<c:set var="label" value="选课" scope="application"></c:set>
														<c:forEach items="${selectCourses}" var="selectCourse"
															varStatus="status">
															<c:if test="${course.id==selectCourse.course.id }">
																<c:set var="bgColor" value="bg-orange"
																	scope="application"></c:set>
																<c:set var="label" value="退课" scope="application"></c:set>
																<c:set var="bgIcon" value="icon-minus-sign" scope="application"></c:set>
																
															</c:if>

														</c:forEach>
														<a href="/xuanke/select?id=${course.id }"
															class="btn small ${bgColor }" target="ajaxTodo"><span
															class="button-content"><i
																class="glyph-icon ${bgIcon }"></i> ${label }</span></a>
														
													</c:if>
													<c:if test="${!isIndex or empty  fns:getUser().id}">
														<a href="javascript:void(0)" onclick="showRemark('${course.cursName }','${course.remarks }');"
															class="btn small bg-blue" ><span
															class="button-content">查看</span></a>
													</c:if>
													</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>


			</div>
		</div>
	</div>

	<!-- ${empty  fns:getUser().id} -->

<script type="text/javascript">
function showRemark(title,remark){
	if(remark==""){
		remark = "暂无简介";
	}
	layer.open({
		  type: 1,
		  title: title,
		  shadeClose: true,
		  shade: 0.8,
		  area: ['380px', '320px'],
		  content: "<div style='margin:10px;'>"+remark+"</div>" //iframe的url
		}); 
}
</script>
</body>
</html>