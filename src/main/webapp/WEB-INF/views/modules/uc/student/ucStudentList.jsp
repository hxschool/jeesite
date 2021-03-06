<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学籍信息管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/layer/css/layui.css"/>
 <script type="text/javascript" src="${ctxStatic}/layer/layui.js"></script>
	<script type="text/javascript">
	
	
		$(document).ready(function() {
			$("#btnExport").click(function() {
				top.$.jBox.confirm("确认要导出学籍数据吗？", "系统提示", function(v, h, f) {
					if (v == "ok") {
						$("#searchForm").attr("action", "${ctx}/uc/student/export");
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
		
		
		
		function batchAction(action){
			layer.prompt({title: "操作备注", formType: 2}, function(text, index){
				$("#action").val(action);
				$("#description").val(text);
				var id = document.getElementsByName("ids");// 获取全选复选框
				var ids = "";
				for (var i = 0; i < id.length; i++) {
					if (id[i].checked) {
						ids += id[i].value + ",";
					}
				}
				ids = ids.substring(0, ids.length - 1);// 干掉字符串最后一个逗号
				$("#searchForm").attr("action","${ctx}/uc/student/batchAction?ids=" + ids);
				$("#searchForm").submit();
			    layer.close(index);
			});
		}
	
		function getIds(){
			var id = document.getElementsByName("ids");// 获取全选复选框
			var ids = "";
			for (var i = 0; i < id.length; i++) {
				if (id[i].checked) {
					ids += id[i].value + ",";
				}
			}
			ids = ids.substring(0, ids.length - 1);
			return ids;
		}
	</script>
</head>
<body>

<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/uc/student/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');">
			<br /> <input id="uploadFile" name="file" type="file"
				style="width: 330px" /><br />
			<br /> <input id="btnImportSubmit" class="btn btn-primary"
				type="submit" value="   导    入   " /> <a
				href="${ctx}/uc/student/import/template">下载模板</a>
		</form>
	</div>

	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/uc/student/">学籍信息</a></li>
		<shiro:hasPermission name="uc:ucStudent:add"><li><a href="${ctx}/uc/student/form">学籍添加</a></li></shiro:hasPermission>
	
	</ul>
	<form:form id="searchForm" modelAttribute="ucStudent" action="${ctx}/uc/student/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<li><label>考生号：</label>
				<form:input path="exaNumber" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>学号：</label>
				<form:input path="studentNumber" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>真实姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>身份证号码：</label>
				<form:input path="idCard" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<li class="clearfix"></li>
			<li><label>年级：</label>
				<form:input path="currentLevel" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			
			
			<li><label>学历：</label>
				
				<form:select path="edu" class="input-medium " style="width:175px">
							<option value="">请选择</option>
							<form:options items="${fns:getDictList('student_edu')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
			</li>
			<li><label>学制：</label>
						<form:select path="studentLength" class="input-medium " style="width:175px">
							<option value="">请选择</option>
							<form:options items="${fns:getDictList('student_school_system')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
			</li>
			
			<li><label>学籍状态：</label>
						<form:select path="learning" class="input-medium " style="width:175px">
							<option value="">请选择</option>
							<form:options items="${fns:getDictList('student_learning')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
			</li>
			
			<li class="clearfix"></li>
			
			<li class="btns"><label></label><input id="btnSubmit" class="btn btn-primary" type="submit" name="search" value="查询"/>
			<input
				id="btnExport" class="btn btn-primary" type="button" value="导出" /> <input
				id="btnImport" class="btn btn-primary" type="button" value="导入" />
				
				<input type="hidden" name="action" id="action" value="${param.action}"/>
				<input type="hidden" name="description" id="description"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-hover table-expandable">
		<thead>
			<tr>
					<shiro:hasPermission
							name="uc:student:operation">
				<th><input type=checkbox name="selid" id="checkId" onclick="checkAll(this, 'ids')"/> </th>
				</shiro:hasPermission>
				<th>真实姓名</th>
				<th>性别</th>
				<th>出生日期</th>
				<th>身份证号码</th>

				<th>学院名称</th>
				<th>专业名称</th>
				<th>学历</th>
				<th>学制</th>
				<th>学籍状态</th>
				<th>入学日期</th>
				
				
				<shiro:hasPermission name="uc:ucStudent:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ucStudent">
			<tr>
				<shiro:hasPermission
							name="uc:student:operation"><td><input type="checkbox" name="ids" value="${ucStudent.id}" /></td>  </shiro:hasPermission>

				<td>
					${ucStudent.name}
				</td>
				<td>
					${fns:getDictValue(ucStudent.gender, 'sex', '男')}
				</td>
				<td>
		<fmt:parseDate value="${ucStudent.birthday}" pattern="yyyyMMdd"  var="date1"/>
		<fmt:formatDate pattern="yyyy-MM-dd" value="${date1}" />	
				</td>
				<td>
					${fn:substring(ucStudent.idCard, 0, 10)}****${fn:substring(ucStudent.idCard, 14,18)}
				</td>
				
				<td>
					${ucStudent.departmentName}
				</td>
				<td>
					${ucStudent.majorName}
				</td>

				<td>
					${fns:getDictLabel(ucStudent.edu,'student_edu','')}
				</td>
				<td>
					${fns:getDictLabel(ucStudent.studentLength,'student_school_system','')}
				</td>
				<td>
					${fns:getDictLabel(ucStudent.learning,'student_learning','')}
				</td>
				<td>
					${ucStudent.startDate}
				</td>

			
				<shiro:hasPermission name="uc:ucStudent:edit"><td>
    				<a class="btn btn btn-info" href="${ctx}/uc/student/form?id=${ucStudent.id}">修改</a>
    				
    				<shiro:hasPermission name="uc:student:tingyong">
					<a class="btn btn-small btn-danger" href="${ctx}/uc/student/delete?id=${ucStudent.id}" onclick="return confirmx('确认要删除该学籍信息吗？', this.href)">停用</a>
					</shiro:hasPermission>
				</td></shiro:hasPermission>
			</tr>
			<tr>
				<td colspan="14" >
					
					<table class="table table-bordered">
							<thead>
								<tr>
									<th>身份证号</th>
									<th>录取学院</th>
									<th>录取专业</th>
									<th>数学</th>
									<th>语文</th>
									<th>外语</th>
									<th>综合</th>
									<th>总分</th>
									<th>特长</th>
									<th>联系电话</th>
									<th>省份</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${ucStudent.idCard}</td>
									<td>${ucStudent.departmentName}</td>
									<td>${ucStudent.majorName}</td>
									<td>${fn:substring(ucStudent.shuxue, 0, fn:indexOf(ucStudent.shuxue,"."))}</td>
									<td>${fn:substring(ucStudent.yuwen, 0, fn:indexOf(ucStudent.yuwen,"."))}</td>
									<td>${fn:substring(ucStudent.waiyu, 0, fn:indexOf(ucStudent.waiyu,"."))}</td>
									<td>${fn:substring(ucStudent.zonghe, 0, fn:indexOf(ucStudent.zonghe,"."))}</td>
									<td>${fn:substring(ucStudent.zongfen, 0, fn:indexOf(ucStudent.zongfen,"."))}</td>
									<td>${fn:substring(ucStudent.techang, 0, fn:indexOf(ucStudent.techang,"."))}</td>
									<td>${recruitStudent.phone}</td>
									<td>${recruitStudent.province}</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
		</c:forEach>
		</tbody>
				<shiro:hasPermission
							name="uc:student:operation">
		<tfoot><tr>
			<th ><input type=checkbox name="selid" id="checkId" onclick="checkAll(this, 'ids')"/></th><th colspan="15"> 
			
			<!-- <a href="#" onclick="batchBox('${ctx}/uc/student/deleteList')" class="btn btn-primary">批量停用</a> -->
			
			<c:if test="${param.action!=null and param.action!=1 }">
				<shiro:hasPermission
							name="uc:student:batch">
				<!-- <a href="#" onclick="batchAction(1)" class="btn ">${fns:getDictLabel(1,'student_learning','')}</a> -->
				<a href="#" onclick="batchAction(2)" class="btn btn-info">${fns:getDictLabel(2,'student_learning','')}</a>
				<a href="#" onclick="batchAction(7)" class="btn btn-success">${fns:getDictLabel(7,'student_learning','')}</a>
				<a href="#" onclick="batchAction(8)" class="btn btn-danger">${fns:getDictLabel(8,'student_learning','')}</a>
				</shiro:hasPermission>
			</c:if>
			</th>
			</tr>
		</tfoot>
		</shiro:hasPermission>
	</table>
	<div class="pagination">${page}</div>

	<div id="student_status_2" style="display: none">
		
	</div>


</body>
</html>