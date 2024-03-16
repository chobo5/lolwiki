<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.io.PrintWriter"%>
<jsp:include page="/header.jsp"></jsp:include>

<h1>오류!</h1>
        <c:if test="${not empty message}">
            <p>${message}</p>
        </c:if>
        <%
        Throwable exception = (Throwable) request.getAttribute("exception");
        if (exception != null) {
            out.println("<pre>");
            out.flush();
            exception.printStackTrace(new PrintWriter(out));
            out.println("</pre>");
        }
        %>

<jsp:include page="/header.jsp"></jsp:include>
        


