<%@include file="header.jsp"%>
    <div class="col-sm-6 col-sm-offset-3 form-wrapper">
        <div class="container-fluid col-sm-10 col-sm-offset-1 login-form">
            <div class="col-sm-12 col-sm-offset-0">
                <h2 class="text-center">Welcome to TCMS!</h2>
            </div>
            <div class="row">
            </div>
            <form class="form-horizontal">
                <div class="col-sm-8 col-sm-offset-2">
                    <div class="form-group">
                        <input type="email" class="form-control" placeholder="Enter your email">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" placeholder="Enter your password">
                    </div>
                </div>
                <div class="col-sm-8 col-sm-offset-2 bottom-row">
                    <div class="col-sm-5 col-sm-offset-1">
                        <label class="remember-me"><input type="checkbox">Remember me</label>
                    </div>
                    <div class="col-sm-5">
                        <button type="submit" class="btn btn-default">Submit</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
<%@include file="footer.jsp"%>