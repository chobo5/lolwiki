<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang='en'>
    <header>
        <meta charset='UTF-8'>
        <title>중고 장터</a></title>
         <h1><a href='/home'>중고 장터</a></h1>
         <form action='/goods/list'>
            <div>
            <input name='keyword' type='text' placeholder='검색어를 입력해주세요'>
            </div>
            <button>검색</button>
         </form>

   <c:if test= "${empty loginUser}">
        <a href='/auth/join'>회원가입</a>
        <a href='/auth/login'>로그인</a>
   </c:if>
   <c:if test= "${not empty loginUser}">
        <span>${nickname}</span>
        <a href='/auth/logout'>로그아웃</a>
        <a href='/auth/mypage'>마이페이지</a>
   </c:if>
   </header>
   <body>


