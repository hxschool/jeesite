<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据统计</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
	
	<ul class="nav nav-tabs">
		<li  class="active"><a href="${ctx}/uc/student/group">招生统计</a></li>
		<li><a href="${ctx}/uc/student/sex">性别统计</a></li>
		<li><a href="${ctx}/uc/student/region">全国招生统计</a></li>
		<li><a href="${ctx}/uc/student/department">学院统计</a></li>
		<li><a href="${ctx}/uc/student/major"> 专业统计</a></li>
		<li><a href="${ctx}/uc/student/edu">学历统计</a></li>
	</ul>
	
	<form:form id="searchForm" modelAttribute="ucStudent" action="${ctx}/uc/student/group" method="post" class="breadcrumb form-search">
		<div style="margin-top:8px;">
			<label>录取年份：&nbsp;</label><input id="year" name="year" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="${ucStudent.year}" onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			
			&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
			<button type="reset" class="btn btn-default ">重置</button>
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 800px;height:600px;margin:0 auto; "></div>
    
    <script type="text/javascript">
		    var xAxData = [];
		    var serData = [];  
		    <c:forEach var="item" items="${list}" varStatus="status">
		    xAxData.push('${item.NAME}' || ""); 
		    serData.push({  
		        name: '${item.NAME}',  
		        value: ${item.TOTAL} || 0  
		    }); 
			</c:forEach> 
        // 基于准备好的dom，初始化echarts实例
        			var myChart = echarts.init(document.getElementById('main'),'westeros');
		
        // 指定图表的配置项和数据
					option = {
						title : {
							text : '${ucStudent.year}招生统计',
							x : 'left'
						},    tooltip : {
					        trigger: 'item',
					        formatter: "{b}:{c} "
					    },
					            xAxis: {
					                type: 'category',
					                data: xAxData
					            },
					            yAxis: {
					                type: 'value'
					            },
					            series: [{
					                data: serData,
					                type: 'line'
					            }]
					};

					// 使用刚指定的配置项和数据显示图表。
					myChart.setOption(option);
					
					myChart.on("dblclick", function (param){               
			                alert(param.dataIndex);
			                alert(param.value);
			            });


				</script>
</body>
</html>