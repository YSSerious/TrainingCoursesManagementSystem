<%--
  Created by IntelliJ IDEA.
  User: Alex_Frankiv
  Date: 29.10.2016
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>
<div class="col-sm-6 col-sm-offset-3 login-form-wrapper">
    <div class="container-fluid col-sm-10 col-sm-offset-1 login-form">
        <div class="col-sm-12 col-sm-offset-0">
            <h2 class="text-center"><spring:message code="welcome"/></h2>
        </div>
        <div class="row">
        </div>
        <form class="form-horizontal" action="<c:url value='/login'/>" method="POST">
            <div class="col-sm-8 col-sm-offset-2">
                <c:if test="${param.error != null}">
                    <div class="login-error">
                        <spring:message code="login.fail.msg"/>
                    </div>
                    <br/>
                </c:if>
                <c:if test="${param.logout != null}">
                    <div class="login-success">
                        <spring:message code="logout.success.msg"/>
                    </div>
                    <br/>
                </c:if>
                <div class="form-group">
                    <input type="email" name="username" class="form-control" placeholder="<spring:message code="login.email.placeholder"/>">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="<spring:message code="login.password.placeholder"/>">
                </div>
            </div>
            <div class="col-sm-8 col-sm-offset-2 bottom-row">
                <div class="col-md-7 ">
                    <label class="remember-me checkbox-inline"><input type="checkbox"  name="tcms-remember-me-param"><spring:message code="remember.me"/></label>
                </div>
                <div class="col-md-4 col-md-offset-1">
                    <button type="submit" class="btn btn-default"><spring:message code="sign.in"/></button>
                </div>
            </div>
        </form>
    </div>
</div>
<%@include file="footer.jsp"%>