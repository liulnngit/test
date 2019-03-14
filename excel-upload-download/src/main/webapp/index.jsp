<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@ include file="WEB-INF/commons/taglibs.jsp"%> --%>
<html>
<head>
    <meta charset="UTF-8">
    <title>1104报表报送</title>
    <%-- <%@ include file="WEB-INF/commons/sourceFile.jsp"%> --%>
</head>
<body>
    <h2>上传模板维护</h2>
    
	<%-- <form action="${pageContext.request.contextPath}/excelUpload.do" method="post" enctype="multipart/form-data">
        <input type="file" name="file" id="file">
        <button id="uploadFile">上传</button>
    </form> 
    <br/>
    
    <a href="${pageContext.request.contextPath }/file/download/excel.do">下载</a>
    
    <br/><br/>
    <form action="${pageContext.request.contextPath}/excelUpdate.do" method="post" enctype="multipart/form-data">
        <input type="file" name="file" id="file">
        <button id="uploadFile">更新</button>
    </form> 
    
    <br/><br/> --%>
    
    <form action="${pageContext.request.contextPath}/toAddTemple" method="post" >
        <button id="addTemple">增加模板</button>
    </form>
    
    <form action="${pageContext.request.contextPath}/listTemple" method="post" >
        <button id="uploadFile">展示模板信息</button>
    </form> 
    
     <form action="${pageContext.request.contextPath}/dataUpload" method="post" enctype="multipart/form-data">
        <input type="file" name="file" id="file">
        <button id="dataUpload">数据上传</button>
    </form>
    
</body>

</html>
