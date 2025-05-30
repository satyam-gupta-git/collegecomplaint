<%@ include file="/includes/header.jsp" %>

<div class="row">
    <div class="col-md-6 offset-md-3">
        <div class="card login-form">
            <div class="card-header">
                <h3 class="text-center">Login</h3>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="form-group">
                        <label for="username" class="required">Username</label>
                        <input type="text" class="form-control" id="username" name="username" required>
                    </div>
                    <div class="form-group">
                        <label for="password" class="required">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Login</button>
                </form>
                <div class="text-center mt-3">
                    <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Register here</a></p>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/includes/footer.jsp" %> 