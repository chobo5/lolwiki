<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="/header.jsp"></jsp:include>
    <form action="/goods/add" method="post" enctype="multipart/form-data">
        <div>
        상품 사진: <input multiple name="photos" type="file">
        </div>
        <div>
        상품 이름: <input name="name" type="text">
        </div>
        <div>
        상품 가격: <input name="price" type="text">
        </div>
        <div>
        상품 설명: <input name="spec" type="textarea">
        </div>
        <button>등록</button>
    </form>
<jsp:include page="/footer.jsp"></jsp:include>