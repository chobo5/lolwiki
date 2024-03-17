<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="/header.jsp"></jsp:include>

    <c:forEach items="${goodsPhotos}" var="photo">
        <img src="/upload/goods/${photo.path}" width=250 height=250>
    </c:forEach>
        <div>
        <h3>상품명</h3>
        <p>${goods.name}</p>
        <br>
        <h3>가격</h3>
        <p>${goods.price}</p>
        <br>
        <h3>설명</h3>
        <p>${goods.spec}</p>
        <br>
        <h3>등록일</h3>
        <p>${goods.regDate}</p>
        </div>
        
<jsp:include page="/footer.jsp"></jsp:include>