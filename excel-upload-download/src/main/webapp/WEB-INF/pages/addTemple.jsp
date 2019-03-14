<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<title></title>

	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- 引入bootstrap -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" >
	<!-- 引入JQuery  bootstrap.js-->
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="${pageContext.request.contextPath}/js/moment-with-locales.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/lang/zh-cn.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/calendar.js"></script>
</head>
<body>
<!-- 顶栏 -->
<!-- 中间主体 -->
<div class="container" id="content">
	<div class="row">
		<div class="col-md-10">
			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="row">
						<h1 style="text-align: center;">新增模板信息</h1>
					</div>
				</div>
				<div class="panel-body">
					<form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/addTemple" id="editfrom" method="post" enctype="multipart/form-data">
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">表单号</label>
							<div class="col-sm-10">
								<input class="form-control" id="tableCode" name="tableCode" placeholder="请输入表单号">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">表单名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="inputTableName" name="tableName" placeholder="请输入表名称">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">版本号</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="inputVersion" name="version" placeholder="请输入版本号">
							</div>
						</div>
						<div class="form-group">
							<div class="input-group" style="margin-right:11%;">
								<span class="input-group-addon">时间范围:</span> 
								<input type="text" onFocus="var endTime=$dp.$('endTime');WdatePicker({onpicked:function(){endTime.focus();},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d-%H:%m:%s\'}',minDate:'#F{$dp.$D(\'endTime\',{M:-12})}'})"
									class="Wdate form-control" style="height: 34px;border: 1px solid #cccccc;" id="startTime" name="startTime"
									placeholder="请选择起始时间"> 
									<span
									class="input-group-addon">至</span> 
									<input type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'#F{$dp.$D(\'startTime\',{M:12})||\'%y-%M-%d-%H:%m:%s\'}'})"
									class="Wdate form-control" style="height: 34px;border: 1px solid #cccccc;"  id="endTime" name="endTime"
									placeholder="请选择结束时间">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-10">
								<input type="file" name="file" id="file">
	        					<!-- <button id="uploadFile">模板上传</button> -->
        					</div>
						</div>
						
						<div class="form-group" style="text-align: center">
							<button class="btn btn-default" type="submit">提交</button>
							<button class="btn btn-default" type="reset">重置</button>
						</div>
					</form>
				</div>

			</div>

		</div>
	</div>
</div>

<div class="container" id="footer">
	<div class="row">
		<div class="col-md-12"></div>
	</div>
</div>
</body>
<script type="text/javascript">
    $("#nav li:nth-child(1)").addClass("active")
</script>
</html>