<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/header.jsp"></jsp:include>
<form action="/auth/changepw" method="post">
        <div>
        현재 비밀번호: <input name="currentpw" type="password">
        </div>
        <div>
        새로운 비밀번호: <input name="newpw1" type="password">
        </div>
        <div>
        세로운 비밀번호 확인: <input name="newpw2" type="password">
        </div>
        <button>확인</button>
        </form>
<jsp:include page="/footer.jsp"></jsp:include>