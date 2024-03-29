<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/header.jsp"></jsp:include>
   <c:if test= "${not empty loginUser}">
        <br>
        <br>
        <div>
        <a href='/app/goods/add'>상품 등록하기</a>
        </div>
   </c:if>

<jsp:include page="/footer.jsp"></jsp:include>



