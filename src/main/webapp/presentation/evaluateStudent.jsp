<%--
  Created by IntelliJ IDEA.
  User: Alex_Frankiv
  Date: 14.11.2016
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<div class="container col-sm-offset-3 col-sm-6">
    <br/>
    <label>Evaluation of student: </label> ${student.firstName} ${student.secondName} ${student.lastName}
    <br/>
    <label>For meeting: </label> ${meeting.name}
    <br>
    <label>In:</label> ${meeting.place}
    <br>
    <label>At:</label> ${meeting.time}
    <br/>
    <br/>
    <h3>Current results: </h3>
    <form class="form-group" action="#" method="post">
        <ul class="list-group">
            <c:forEach items="${results}" var="result">
                <li class="list-group-item container-fluid">
                    <div class="row">
                        <label class="col-xs-offset-1">${result.criterion.title}</label>
                    </div>
                    <div class="row">
                        <input class="col-xs-offset-2 col-sm-6 result-comment" type="text" required value="${result.commentary}">
                        <select class="col-xs-offset-1 result-mark">
                            <option <c:if test="${result.mark.value eq 0}">selected</c:if>>0</option>
                            <option <c:if test="${result.mark.value eq 1}">selected</c:if>>1</option>
                            <option <c:if test="${result.mark.value eq 2}">selected</c:if>>2</option>
                            <option <c:if test="${result.mark.value eq 3}">selected</c:if>>3</option>
                            <option <c:if test="${result.mark.value eq 4}">selected</c:if>>4</option>
                            <option <c:if test="${result.mark.value eq 5}">selected</c:if>>5</option>
                        </select>
                    </div>
                </li>
            </c:forEach>
        </ul>
        <br/>
        <br/>
        <label>Review comment: </label>
        <textarea class="form-control review-comment" rows="5">${review.commentary}</textarea>
        <br/>
        <button class="btn btn-primary pull-right" onclick="do_evaluate()"><spring:message code="submit.btn"/></button>
    </form>
</div>
<%@include file="footer.jsp" %>