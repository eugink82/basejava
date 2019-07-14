<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css"/>
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <p>
        <a href="resume?action=add"><img src="img/add.png"/></a>
    </p>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:set var="totalSize" value="${resumes.size()}" scope="session"/>
        <c:set var="chunkSize" value="5" scope="session"/>
        <c:set var="start" value="${param.start}" scope="page"/>
        <c:set var="finish" value="${start+chunkSize-1}"/>
        <c:forEach items="${resumes}" var="resume" begin="${start}" end="${finish}">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
                </td>
                <td><a href="resume?action=delete&uuid=${resume.uuid}"><img src="img/delete.png"></a></td>
                <td><a href="resume?action=edit&uuid=${resume.uuid}"><img src="img/pencil.png"></a></td>

            </tr>
        </c:forEach>
    </table>
    <c:if test="${totalSize>5}">
        <c:forEach var="selection" begin="0" end="${totalSize-1}" step="${chunkSize}">
            <c:choose>
                <c:when test="${totalSize>(selection+chunkSize)}">
                    <a href="?start=${selection}"><b>(${selection+1}-${selection+chunkSize})</b></a>
                </c:when>
                <c:otherwise>
                    <a href="?start=${selection}"><b>(${selection+1}-${totalSize})</b></a>
                </c:otherwise>
            </c:choose>

        </c:forEach>
    </c:if>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
