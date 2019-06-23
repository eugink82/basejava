<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="+text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css"/>
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <hr>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType,com.urise.webapp.model.Sections>"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.Sections"/>
    <h3>${sectionEntry.key.title}</h3>
    <c:choose>
        <c:when test="${sectionEntry.key=='OBJECTIVE' || sectionEntry.key=='PERSONAL'}">
            <p>${sectionEntry.value}</p>
        </c:when>
        <c:when test="${sectionEntry.key=='ACHIEVEMENT' || sectionEntry.key=='QUALIFICATIONS'}">
            <ul>
                <c:forEach var="desc" items="<%=((ListSection)section).getList()%>">
                    <li>${desc}</li>
                </c:forEach>
            </ul>
        </c:when>
        <c:when test="${sectionEntry.key=='EXPERIENCE' || sectionEntry.key=='EDUCATION'}">
            <c:forEach var="company" items="<%=((CompanySection)section).getCompanies()%>">
                <c:if test="${company.homepage.urlIsExists()}">
                    <p><a href='${company.homepage.url}'>${company.homepage.name}</a></p>
                </c:if>
                <c:if test="${!company.homepage.urlIsExists()}">
                    <p>${company.homepage.name}</p>
                </c:if>
                <c:forEach var="pos" items="${company.positions}">
                    <table width="100%">
                        <tr>
                            <td rowspan="2" width="15%"
                                style="padding: 5px; vertical-align: top;">${pos.startDate.month.value}/${pos.startDate.year}/
                                - ${pos.endDate.month.value}/${pos.endDate.year}</td>
                            <td><b>${pos.title}</b></td>
                        </tr>
                        <tr>
                            <td>${pos.description}</td>
                        </tr>
                    </table>
                </c:forEach>
            </c:forEach>
        </c:when>
    </c:choose>

    </c:forEach>
    </p>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
