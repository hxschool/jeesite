<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>校历校准管理</title>
	<meta name="decorator" content="default"/>
	
	<style>

body
{
	font-family:'微软雅黑';
	background-color:#EBEAEB;
}

#contest{
	background-color:#FFF;
	/*position:absolute;
	top:7%;
	left:13%;
	margin-left:auto;
	margin-right:auto;*/
	position:relative;
	top:20px;
	margin:0 auto;
	width:1000px;
	height:800px;
	
	box-shadow:0px 0px 50px #000;
	border-radius:5px;
}

.psw
{
	width:160px;
	height:25px;
	background-color:#6e6e6e;
	border-radius:3px;
	border:0px;
	color:#f9f9f9;
	font-family:'微软雅黑';
	padding-left:5px;
}

.a
{
	display:block;

	border:1px solid;
	border-radius:3px;
	border-color:#09F;
	color:#000;
	padding:4px 10px;
	margin-top:2px;

	box-shadow:#6e6e6e 0 0 3px;
	text-decoration:none;
}
.a:hover
{
	background-color:#C6B19A;
	color:#FFF;
	box-shadow:#2d2d2d 0 0 3px;	
}

.prime_a:hover
{
	color:#0CF;
}

table
{
	border-collapse:collapse;
	font-size:15px;
	/*border-color:#000;*/
}

td{
	width:200px;
	height:50px;
	border:1px solid #000000;
}
#up{
	position:absolute;
	background-color:#ffffff;
	left:50%;
	top:300px;
	margin-top:-205px;
	margin-left:-200px;
	z-index:6;
	border-radius:6px;
	-webkit-box-shadow:0 3px 7px rgba(0, 0, 0, 0.3);
	box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
}
#teacherUp{
	position:absolute;
	background-color:#ffffff;
	left:50%;
	top:300px;
	margin-top:-205px;
	margin-left:-200px;
	z-index:6;
	border-radius:6px;
	-webkit-box-shadow:0 3px 7px rgba(0, 0, 0, 0.3);
	box-shadow: 0 3px 7px rgba(0, 0, 0, 0.3);
}
#mask
{
	background-color:#2d2d2d;
	opacity:0;
	filter:alpha(opacity=0);
	position:fixed;
	width:100%;
	height:100%;
	top:0px;
	left:0px;
	z-index:-5;
}

.course_text{
	text-align:center;
}

.admin_tips
{
	font-size:14px;
	line-height:30px;
	color:#999;
}
</style>
</head>
<body>

<body>
<br>
<br>
	<div class="container">
		<div class="row">
			<div class="span12">
				<fmt:formatDate value="<%=new java.util.Date()%>" pattern="MM"
					var="mm" />
				<fmt:formatDate value="<%=new java.util.Date()%>" pattern="dd"
					var="dd" />
				<div id="mask"></div>
				
					<form name="form0">
						<input type="text" id="year" name="year" style="display: none;"
							value="${yearTerm}" /> <input type="text" id="servers_time"
							name="servers_time" style="display: none;" value="${mm}@${dd}">
						<input type="text" name="year_rili" id="year_rili"
							style="display: none;" value="${courseCalendar.calendarYear}" />
						<input type="text" name="month_rili" id="month_rili"
							style="display: none;" value="${courseCalendar.calendarMonth}" />
						<input type="text" name="day_rili" id="day_rili"
							style="display: none;" value="${courseCalendar.calendarDay}" />


						<div id="top">
							第&nbsp;<select id="week_select" onchange="change_week()"
								style="width: 100px;">
								<option value="01" selected>1</option>
								<%
									for (int $i = 2; $i <= 9; $i++)
										out.println("<option value=\"" + '0' + $i + "\">" + $i + "</option>");
									for (int $i = 10; $i <= 20; $i++)
										out.println("<option value=\"" + $i + "\">" + $i + "</option>");
								%>
							</select>&nbsp;周 &nbsp;&nbsp;&nbsp;&nbsp; 学院:&nbsp;&nbsp;<select
								name="h_school" id="h_school" class="h_school"
								onchange="change()" style="width: 100px;">

							</select> &nbsp;&nbsp;&nbsp;&nbsp;机房:&nbsp;&nbsp; <select id="address"
								class="address" onchange="change_address()"
								style="width: 100px;">
							</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="button"
								; onclick="window_print()" ; name="win_print" ; id="win_print"
								value="打印" class="button" />
						</div>
						<p>
						<table id="s_week" style="font-size: 10px; height: 550px;">
							<tr align="center">
								<td></td>
								<td>星期一</td>
								<td>星期二</td>
								<td>星期三</td>
								<td>星期四</td>
								<td>星期五</td>
								<td>星期六</td>
								<td>星期日</td>
							</tr>
							<%
								//生成空表格
								int i = 1;
								int j = 2;
								for (int $row = 1; $row <= 5; $row++) {
									out.println("<tr height=\"100px;\">");
									out.println("<td align=\"center\">" + i + "-" + j + "节</td>");
									i += 2;
									j += 2;
									for (int $cell = 1; $cell <= 7; $cell++) {
										out.println("<td></td>");
									}
									out.println("</tr>");
								}
							%>
						</table>
					</form>
				
			</div>
		</div>
	</div>

	<!-- div层[排一个课]-->
<div id="up"; style="display:none; width:400px; height:400px;">
<form action="" name="form" style="margin-left:30px; margin-top:50px;">
	<input  type="hidden" style="display:none" name="time" id="time" /> <!-- 存放时间地址字段-->
    <p>
	相关说明:&nbsp;&nbsp;<p>
  	<textarea name="tips" id="tips" style="height:100px; width:300px;"></textarea>
	<p>
	<input  name="add" type="button" value="添加" onclick="resure()" class="button" />&nbsp;&nbsp;&nbsp;&nbsp;
	<input  name="over" type="button" value="返回" onclick="cancel()" class="button"/>
</form>


</div>

<div id="teacherUp"; style="display:none; width:400px; height:400px;">
	<div id="putong">
        <form action="" name="form" style="margin-left:30px; margin-top:50px;">
            <p>
            <a onclick="putongke()" class="prime_a">普通课</a>&nbsp;|&nbsp;<a onclick="renxuanke()" class="prime_a">任选课</a>
            <input  type="text" style="display:none" name="teacherTime" id="teacherTime" /> <!-- 存放时间地址字段-->
            <p>
            
           学院:&nbsp;&nbsp;
           <select name="school" id="school" onChange="change_school(document.form.school.options[document.form.school.selectedIndex].value)">
                <option value="" selected="selected">==请选择学院==</option>
            </select>
            <p>专业:&nbsp;&nbsp;
            <select name="major"  id="major" onChange="change_major(document.form.major.options[document.form.major.selectedIndex].value)">
              <option value=""selected="selected">==请选择专业==</option>
            </select>
            <p>年级:&nbsp;&nbsp;
            <select name="grade"  id="grade" onchange="change_grade(document.form.grade.options[document.form.grade.selectedIndex].value)">
              <option value="" selected="selected">==请选择年级==</option>
            </select>
            <p>班级:&nbsp;&nbsp;
            <select name="w_class" id="w_class">
              <option value="" selected="selected">==请选择班级==</option>
            </select>
            <p>课程:&nbsp;&nbsp;
            <select name="course" id="course">
            <option value="" selected="selected">==请选择课程==</option>
            <option value=00000150>大型数据库管理与应用</option><option value=00000196>网络工程规划课程设计</option><option value=00000324>111</option>                        </select>
            <p>
            备注:&nbsp;&nbsp;<input type="text" id="teacherTips" name="tips"/>
            <p>
            <input  name="add" id="teacherAdd" type="button" value="添加" onclick="teacherResure()" class="button" />&nbsp;&nbsp;&nbsp;&nbsp;
            <input  name="over" id="teacherOver" type="button" value="返回" onclick="teacherCancel()"  class="button"/>
        </form>

	</div>
</div>


<script>
var time_array = new Array();//日历数组
//页面加载完成后执行change()填表格操作
$(document).ready(function()
{
	$('#top').cxSelect({ 
		  url: '${ctx}/school/schoolRoot/treeLink',
		  selects: ['h_school', 'address'], 
		  jsonName: 'name',
		  jsonValue: 'value',
		  jsonSub: 'sub'
		}); 
	change();
   //document.form0.h_school.options[04].selected = true; 
});
function rili(year,month,day)
{
	flag = 0;
	flag_year = 0;
	if((year%4==0 && year%100!=0) || year%400==0)
	{
		flag = 1;
	}
	else
		flag = 0;
	for(i=1;i<=20;i++)
	{
		time_array[i] = new Array();
		for(j = 1;j<=7;j++)
		{	
			if(month==13)
			{
				month=1;
				flag_year = 1;
			}
			time_array[i][j] = month+"月"+day+"日";
			if((month==1 || month==3 ||month==5 ||month==7 ||month==8 ||month==10 ||month==12) && day==31)
			{
				day=1;
				month++;
			}
			else if(day==28 && month==2 && flag == 0)
			{
				day=1;
				month++;
			}
			else if(day==29 && month==2 && flag == 1)
			{
				day=1;
				month++;
			}
			else if((month==4 ||month==6 ||month==9 ||month==11) &&day==30)
			{
				day=1;
				month++;
			}
			else
				day++;
		}
	}
	return flag_year;
}
//打印页面
function window_print()
{
	 $("#win_print").fadeOut(0);
	var table_temp = document.getElementById("s_week");
	for(i=1;i<=5;i++)
	{
		for(j=1;j<=7;j++)
		{	if(table_temp.rows[i].cells[j].innerHTML.indexOf("点此加锁")!=-1)
			{
				table_temp.rows[i].cells[j].innerHTML="";
			}
			if(table_temp.rows[i].cells[j].innerHTML.indexOf("删除")!=-1)
			{
				table_temp.rows[i].cells[j].innerHTML=table_temp.rows[i].cells[j].innerHTML.replace("删除","");
			}
		}
	}
	window.print();
	change();
	$("#win_print").fadeIn(0);
}
//机房下拉列表改变时调用函数
function change_address()
{
	change();
}
//第几周下拉列表改变时调用函数
function change_week()
{
	change();
}

//获取当前表单数据
function change()
{
	
	var table_temp = document.getElementById("s_week");//表格 
	var year = $("#year").val();//获取年份
	var h_school = $("#h_school").children('option:selected').val();
	var address = $("#address").children('option:selected').val();
	var temp = document.getElementById("week_select");//获取周次
	week= temp.options[temp.selectedIndex].value;
	time = year+''+h_school+''+address+''+week;
	
	//日历相关
	var week_rili = temp.options[temp.selectedIndex].text;
	
	//alert(time);
	var year_rili = $("#year_rili").val();
	var month_rili = $("#month_rili").val(); 
	var day_rili  = $("#day_rili").val();
	var flag_year = rili(year_rili,month_rili,day_rili);
	
	table_temp.rows[0].cells[1].innerHTML = "星期一 ["+time_array[week_rili][1]+"]";
	table_temp.rows[0].cells[2].innerHTML = "星期二 ["+time_array[week_rili][2]+"]";
	table_temp.rows[0].cells[3].innerHTML = "星期三 ["+time_array[week_rili][3]+"]";
	table_temp.rows[0].cells[4].innerHTML = "星期四 ["+time_array[week_rili][4]+"]";
	table_temp.rows[0].cells[5].innerHTML = "星期五 ["+time_array[week_rili][5]+"]";
	table_temp.rows[0].cells[6].innerHTML = "星期六 ["+time_array[week_rili][6]+"]";
	table_temp.rows[0].cells[7].innerHTML = "星期日 ["+time_array[week_rili][7]+"]";
	//调用动态填写表格函数
	chuancan(time);

}

function chuancan(selected)
{
	
	//selected为前12位，即没有节次和第几周
	//JQ的ajax返回id为selected的数据
	$.ajax({
   		type: "POST",
  		url: "ajaxChangeTable",
  	 	data: "time_add="+selected,
   		success: function(msg)
		{
   			
			var temp = document.getElementById("s_week");
			var change,cnt=0;
			change=msg.split("@");//分割返回数据
			//alert(msg);
			if(msg  == '')
			{
				for(i=1;i<=5;i++)
					for(j=1;j<=7;j++)
						temp.rows[i].cells[j].innerHTML="";
			}
			else
			{
				for(i=1;i<=5;i++)
				{
					for(j=1;j<=7;j++)
					{
					
						lock = change[cnt].substr(0,1);
						change[cnt] = change[cnt].substr(1);
						tips = change[cnt].split("备注:");
						//alert(lock);
						if(lock=='2')
						{
							temp.rows[i].cells[j].innerHTML=change[cnt]+"<div class=\"course_text\"><a style = \"color:red;\" class=\"prime_a\" onclick=\"deleted("+selected+","+i+","+j+")\">删除</a></div>";
						}
						else if(lock=='0')
						{
							temp.rows[i].cells[j].innerHTML="<div class=\"course_text\" >管理员已加锁<p style=\"margin:0px;\">备注:"+tips[1]+"<p></div><div class=\"course_text\" ><a class=\"btn btn-mini btn-danger\" onclick=\"deleted("+selected+","+i+","+j+")\">删除</a></div>"
						}
						else
						{
							var temp = document.getElementById("s_week");
							temp.rows[i].cells[j].innerHTML="<div class=\"course_text\"><a onclick=\"paike("+selected+","+i+","+j+")\"  class=\"btn btn-mini btn-info\">加锁</a> <a onclick=\"teacherPaike("+selected+","+i+","+j+")\"  class=\"btn btn-mini btn-success\">排课</a>   <div>";
						}
						cnt++;
					}
				}
			}
   		}
	   }); 
}

function time_limit(xingqi)
{	
	var t = document.getElementById("week_select");//获取周次
	var zhou = t.options[t.selectedIndex].text;
	var servers_time = $("#servers_time").val().split("@");
	var local_time=time_array[zhou][xingqi];

	local_time=local_time.replace("月","日");
	local_time=local_time.split("日");
	var local_mon=parseInt(local_time[0]);
	var servers_mon=parseInt(servers_time[0]);
	var local_day=parseInt(local_time[1]);
	var servers_day=parseInt(servers_time[1]);

	if(servers_mon<local_mon)
	{
		//alert(servers_mon+"<"+local_mon);
		return 1;
	}
	else if(servers_mon==local_mon && servers_day<=local_day)
	{
		return 1;
	}
        else if((servers_mon >= 8 && servers_mon <= 12) && local_mon == 1)
        {
            return 1;
        }
	else
	{	return 0;}
}

function teacherPaike(time,row,cell)
{
	if(time_limit(cell)==0)
	{
		alert("日期已过，不允许排课");
	}
	else
	{
		//document.form.add.value="添加"
		var temp = document.getElementById("s_week");
		time_add = time+''+row+''+cell;
		$("#teacherTime").val(time_add)//给存储时间地址ID赋值
		//alert(time_add);
		
			$('#mask').css({'zIndex':'5'});
			$('#mask').animate({'opacity':'0.5'},200);
		
		$('#teacherUp').fadeIn(200);
	}
}

//获取点击坐标 显示div隐藏排课层
function paike(time,row,cell)
{
time_add = time+''+row+''+cell;
$("#time").val(time_add);//给存储时间地址ID赋值
//alert(time_add);

	$('#mask').css({'zIndex':'5'});
	$('#mask').animate({'opacity':'0.8'},200);

$('#up').fadeIn(200);
}

//删除排课
function deleted(time,row,cell)
{
	time_add = time+''+row+''+cell;
	var conf = confirm("是否删除此排课");
	if(conf)
	{
		$.ajax({
   		type: "POST",
  		url: "ajaxDeleteLock",
  	 	data: "time_add="+time_add,
   		success: function(msg)
		{
			//alert(msg);
			if(msg=='1')
			{
				//$('#up').fadeOut(100)
				chuancan(time_add.substr(0,12));
				
			}
			else
			{
				alert("删除失败");
				//$('#up').fadeOut(100);
				chuancan(time_add.substr(0,12));
			}
   		}
	   }); 
	}
}


//确定按钮 用来加一门课
function resure()
{
	if($("#tips")=="")
	{
		alert("您需要输入说明!");
		$("#tips").focus();
	}
	else
	{
		var student_id = "";
		var course_id = "00000000";
		time_add = $("#time").val();
		tips =  $("#tips").val();;
		//alert(time_add);
		//通过ajax给数据库添加一个课程安排
		$.ajax({
   		type: "POST",
  		url: "ajaxAddLock",
  	 	data: "time_add="+time_add+"&course_id="+course_id+"&student_id="+student_id+"&tips="+tips,
   		success: function(msg)
		{
			//alert(msg);
			if(msg=='1')
			{
				alert("加锁成功");
				chuancan(time_add.substr(0,12));
				$('#mask').animate({'opacity':'0'},function(){$('#mask').css({'zIndex':'-5'});});
				$("#up").fadeOut(100);
				
			}
			else
			{
				alert("加锁失败");
				$('#mask').animate({'opacity':'0'},function(){$('#mask').css({'zIndex':'-5'});});
				$('#up').fadeOut(100);
				chuancan(time_add.substr(0,12));
			}
   		}
	   });
	} 
	//alert(student_id+''+time_add+course_id);
}



//取消按钮，返回并更新页面
function cancel()
{
	$('#mask').animate({'opacity':'0'},function(){$('#mask').css({'zIndex':'-5'});});
	$('#up').fadeOut(500);
	time_add = $("#time").val();
	chuancan(time_add.substr(0,12));
	
}

//确定按钮 用来加一门课
function teacherResure()
{
	if($("#teacherTips")=="")
	{
		alert("您需要输入说明!");
		$("#teacherTips").focus();
	}
	else
	{
		var student_id = "";
		var course_id = "00000000";
		time_add = $("#time").val();
		
		tips =  $("#teacherTips").val();;
		//alert(time_add);
		//通过ajax给数据库添加一个课程安排
		$.ajax({
   		type: "POST",
  		url: "add_lock.ajax.php",
  	 	data: "time_add="+time_add+"&course_id="+course_id+"&student_id="+student_id+"&tips="+tips,
   		success: function(msg)
		{
			//alert(msg);
			if(msg=='1')
			{
				alert("加锁成功");
				chuancan(time_add.substr(0,12));
				$('#mask').animate({'opacity':'0'},function(){$('#mask').css({'zIndex':'-5'});});
				$("#teacherUp").fadeOut(100);
				
			}
			else
			{
				alert("加锁失败");
				$('#mask').animate({'opacity':'0'},function(){$('#mask').css({'zIndex':'-5'});});
				$('#teacherUp').fadeOut(100);
				chuancan(time_add.substr(0,12));
			}
   		}
	   });
	} 
	//alert(student_id+''+time_add+course_id);
}

function teacherCancel()
{
	
	$('#mask').animate({'opacity':'0'},function(){$('#mask').css({'zIndex':'-5'});});
	$('#teacherUp').fadeOut(500);
	time_add = $("#teacherTime").val();
	chuancan(time_add.substr(0,12));
	
}

//普通课
var renxuanke_or_putongke=1;
function putongke()
{
	renxuanke_or_putongke=1;
	//select_ini("school","01");//设置学院默认值为信息学院
	$("#school").attr("disabled",false);
	$("#major").attr("disabled",false);
	$("#grade").attr("disabled",false);
	$("#w_class").attr("disabled",false);
}
//任选课
function renxuanke()
{
	renxuanke_or_putongke=0;
	select_ini("school","");//设置学院默认值为空即｛==请选择学院==｝
	select_ini("major","");
	select_ini("grade","");
	select_ini("w_class",""); 
	//禁用部分下拉列表
	$("#school").attr("disabled",true);
	$("#major").attr("disabled",true);
	$("#grade").attr("disabled",true);
	$("#w_class").attr("disabled",true);
}
function select_ini(select_name,select_value)
{
	var s = document.getElementById(select_name);  
    var ops = s.options;  
    for(var i=0;i<ops.length; i++)
	{  
        var tempValue = ops[i].value;  
        if(tempValue == select_value)  
        {  
            ops[i].selected = true;  
        }  
    }  
}
</script>
</body>
</html>