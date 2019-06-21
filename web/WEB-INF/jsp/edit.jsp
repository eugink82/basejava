<%@ page import="com.urise.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.SimpleTextSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css"/>
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>

   <form method="post" action="resume"  enctype="application/x-www-form-urlencoded">
       <input type="hidden" name="uuid" value="${resume.uuid}">
       <dl>
           <dt>Имя:</dt>
           <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" required> </dd>
       </dl>
       <h3>Контакты: </h3>
           <c:forEach var="type" items="<%=ContactType.values()%>">
               <dl>
                   <dt>${type.title}</dt>
                   <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
               </dl>
           </c:forEach>
       <hr>
        <c:forEach var="sections" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(sections)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.Sections"/>
            <c:choose>
                <c:when test="${sections=='OBJECTIVE' || sections=='PERSONAL'}">
                    <h3>${sections.title}</h3>
                    <dd><input type="text" name="${sections}" size="140" value="${section}"></dd>
                </c:when>
                <c:when test="${sections=='ACHIEVEMENT' || sections=='QUALIFICATIONS'}">
                    <h3>${sections.title}</h3>
                    <textarea rows="15" cols="180" name="${sections}">${section}
                    </textarea>
                </c:when>
                <c:when test="${sections=='EXPERIENCE' || sections=='EDUCATION'}">
                    <h3>${sections.title}</h3>
                   <c:forEach var="company" items="<%=((CompanySection)section).getCompanies()%>">
                    <p>
                     <fieldset>
                     <dl>
                         <dt>Организация:</dt>
                         <dd><input type="text" name="companyName" value="${company.homepage.name}" size="50"></dd>
                     </dl>
                      <dl>
                         <dt>Сайт организации:</dt>
                         <dd><input type="text" name="homepageCompany" value="${company.homepage.url}" size="70"></dd>
                      </dl>
                      <c:forEach var="pos" items="${company.positions}">
                       <jsp:useBean id="pos" type="com.urise.webapp.model.Company.Position"/>
                       <dl>
                           <dt>Период ${sections=='EXPERIENCE' ? 'работы:':'обучения:'}</dt>
                           <dd><input type="date" name="workPeriod" value="${pos.startDate}" size="70"></dd>
                           <dd><input type="date" name="workPeriod" value="${pos.endDate}" size="70"></dd>
                       </dl>
                      <dl>
                         <dt>${sections=='EXPERIENCE' ? 'Должность:':'Курс:'}</dt>
                         <dd><input type="text" name="position" value="${pos.title}" size="90"></dd>
                      </dl>
                      <dl>
                         <dt>Описание ${sections=='EXPERIENCE' ? 'работы:':'курса:'}</dt>
                        <dd><textarea name="duties" rows="7" cols="150">${pos.description}</textarea></dd>
                      </dl>

                      </c:forEach>
                   </fieldset>
                    </p>
                   </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>

       <hr>
       <button type="submit">Сохранить</button>
       <button onclick="window.history.back()" type="button">Отменить</button>
   </form>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
