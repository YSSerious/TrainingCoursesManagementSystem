<%--
  Created by IntelliJ IDEA.
  User: Alex_Frankiv
  Date: 29.10.2016
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@include file="header.jsp"%>
<div class="col-sm-6 col-sm-offset-3 form-wrapper">
    <div class="container-fluid col-sm-10 col-sm-offset-1 login-form">
        <div class="col-sm-12 col-sm-offset-0">
            <h2 class="text-center">Welcome to TCMS!</h2>
        </div>
        <div class="row">
        </div>
        <form class="form-horizontal" action="<c:url value='/login'/>" method="POST">
            <div class="col-sm-8 col-sm-offset-2">
                <div class="form-group">
                    <c:if test="${param.error != null}">
                        <div class="login-error">
                            Invalid username and password.
                        </div>
                        <br/>
                    </c:if>
                    <c:if test="${param.logout != null}">
                        <div class="login-success">
                            You have been logged out!
                        </div>
                        <br/>
                    </c:if>
                    <input type="email" name="username" class="form-control" placeholder="Enter your email">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="Enter your password">
                </div>
            </div>
            <div class="col-sm-8 col-sm-offset-2 bottom-row">
                <div class="col-sm-5 col-sm-offset-1">
                    <label class="remember-me"><input type="checkbox"  name="tcms-remember-me-param">Remember me</label>
                </div>
                <div class="col-sm-5">
                    <button type="submit" class="btn btn-default">Submit</button>
                </div>
            </div>
        </form>
    </div>
</div>
<%@include file="footer.jsp"%>