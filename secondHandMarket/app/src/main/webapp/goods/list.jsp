<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="/header.jsp"></jsp:include>
              <c:choose>
                <c:when test="${fn:length(goodsList) > 0}">
                    <table border="1">
                    <thead>
                    <tr> <th>사진</th> <th>상품명</th> <th>가격</th> <th>등록일</th> </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${goodsList}" var="goods">
                        <tr>
                        <td><img src="/upload/goods/${goods.photoList[0].path}" width=150 height=150></td>
                        <td><a href="/goods/view?no=${goods.no}">${goods.name}</a></td>
                        <td>${goods.price}원</td>
                        <td>${goods.regDate}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <h2>등록된 삼품이 없습니다.</h2>
                </c:otherwise>
                </c:choose>
<jsp:include page="/footer.jsp"></jsp:include>