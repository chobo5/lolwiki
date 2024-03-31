<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="/header.jsp"></jsp:include>
    <form action="/app/auth/login" method="post">
        <div>
        닉네임: <input name="nickname" type="text" value="${savedNickname}">
        </div>
        <div>
        비밀번호: <input name="password" type="password">
        </div>
        <div>
        닉네임 저장: <input name="saveNickname" type="checkbox">
        </div>
        <button>로그인</button>
    </form>
<jsp:include page="/footer.jsp"></jsp:include>