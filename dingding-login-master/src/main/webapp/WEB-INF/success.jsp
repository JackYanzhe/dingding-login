<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
   <%--  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
</head>
<body>
   <h1> Hello ${user.getName()},欢迎你成功登录该系统！！！！！！</h1>
    <table id="contentTable" class="table table-bordered table-condensed">
        <tr>
            <th>姓名</th>
            <th>电话</th>
           
        </tr>
        <div id="mainContent" style="height:1px">
            <tbody>
                    <td>${user.getName()}</td>
                    <td>${user.getMobile()}</td>
                  
                </tr>
        
            </tbody>
        </div>
    </table>
</body>
</html>