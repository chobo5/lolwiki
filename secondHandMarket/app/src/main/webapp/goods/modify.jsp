<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="/header.jsp"></jsp:include>

<form action="/goods/modify" method="post" enctype="multipart/form-data">
        <div>
        <input name="no" value="${goods.no}" type="hidden">
        </div>
        <c:forEach items="${goodsPhotos}" var="photo">
            <div>
            <img src="/upload/goods/${photo.path}" width=250 height=250>
            <a href="/goods/delete/photo?no=${photo.no}">[삭제]</a>
            </div>
        </c:forEach>
        <div>
        사진 추가: <input multiple name="photos" type="file">
        </div>
        <div>
        <h3>상품명</h3>
        <input name="name" value="${goods.name}">
        </div>
        <div>
        <h3>가격</h3>
        <input name="price" value="${goods.price}">
        </div>
        <div>
        <h3>설명</h3>
        <input name="spec" value="${goods.spec}">;
        </div>
        <div>
        <h3>등록일</h3>
        <input readonly type="text" value="${goods.regDate}">
        </div>
        <button>변경</button>
        </form>

        <form action="/goods/delete" method="post">
        <input name="no" value="${goods.no}" type="hidden">
        <button>삭제</button>
        </form>
        
<jsp:include page="/footer.jsp"></jsp:include>