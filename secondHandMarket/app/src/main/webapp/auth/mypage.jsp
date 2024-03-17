<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="/header.jsp"></jsp:include>
    <form action="/auth/mypage" method="post" enctype="multipart/form-data">
                <div>
                <img src="/upload/user/${userPhoto.path}" width=250 height=250>
                </div>
                <div>
                프로필사진: <input name="photo" type="file">
                </div>
                <div>
                닉네임: <input name="nickname" type="text" value="${loginUser.nickname}">
                </div>
                <div>
                연락처: <input name="phoneNo" type="text" value="${loginUser.phoneNo}">
                </div>
                <button>변경</button>
                </form>
                <a href="/auth/changepw">비밀번호 변경</a>
                <br>
                <br>
                <h3>판매 상품</h3>
                <c:forEach items="${goodsOfUser}" var="goods">
                    <p><a href="/goods/modify?no=${goods.no}">${goods.name}</a></p>
                    <c:forEach items="${goods.photoList}" var="photo">
                        <img src="/upload/goods/${photo.path}" width=250 height=250>
                    </c:forEach>
                    <p>${goods.price}</p>
                    <p>${goods.spec}</p>
                    <p>${goods.regDate}</p>
                    <p>--------------------------------------------------------</p>
                </c:forEach>
<jsp:include page="/footer.jsp"></jsp:include>