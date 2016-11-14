<%--
  Created by IntelliJ IDEA.
  User: Alex_Frankiv
  Date: 10.11.2016
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>

<div class="col-sm-6 col-sm-offset-3 login-form-wrapper">
    <div class="container-fluid col-sm-10 col-sm-offset-1 login-form role-chooser">
        <div class="col-sm-12 col-sm-offset-0">
            <h3 class="text-center"><spring:message code="roles.title.msg"/> </h3>
        </div>
        <div class="row">
        </div>
        <form class="form-horizontal col-sm-8 col-sm-offset-2" action="set_role/" method="post">
            <div class="form-group">
            <label for="select"></label>
            <select class="form-control"  name="chosenRole" id="select">
            <option value="" disabled selected hidden><spring:message code="roles.placeholder"/> </option>
            <option value="ROLE_ADMIN"><spring:message code="roles.admin.msg"/> </option>
            <option value="ROLE_MENTOR"><spring:message code="roles.mentor.msg"/> </option>
            <option value="ROLE_HR"><spring:message code="roles.hr.msg"/> </option>
            <!--<option>STUDENT</option>-->
            </select>
            </div>
            <button type="submit" class="btn btn-default text-center"><spring:message code="sign.in"/> </button>
        </form>
        </div>
    </div>

<%@include file="footer.jsp"%>