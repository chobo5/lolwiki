<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="/header.jsp"></jsp:include>
    <form action="/app/auth/join" method="post" enctype="multipart/form-data">
        <div>
        프로필 사진: <input name="photo" type="file">
        </div>
        <div>
        닉네임: <input name="nickname" type="text">
        </div>
        <div>
        연락처: <input name="phoneNo" type="text">
        </div>
        <div>
        비밀번호: <input name="password1" type="password">
        </div>
        <div>
        비밀번호 확인: <input name="password2" type="password">
        </div>
        <button>완료</button>
    </form>
<jsp:include page="/footer.jsp"></jsp:include>