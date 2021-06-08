<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ include file="/WEB-INF/views/common/loginForm.jsp" %> --%>
<%-- <jsp:include page="/WEB-INF/views/common/loginForm.jsp"></jsp:include> --%>
<c:if test="${!empty loginUser }">
	<script>
	  	location.href="index.do";
	</script>
</c:if>
<c:if test="${empty loginUser }">
	<script>
 	  	location.href="loginForm.do";
	</script>
<%-- 	<jsp:forward page="/WEB-INF/views/common/loginForm.jsp"/> --%>
</c:if>