<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cloudlibrary-Book management system</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webbase.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages-login-manage.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
</head>
<body>
<div class="loginmanage">
    <div class="py-container">
        <h4 class="manage-title">Cloudlibrary-Book management system</h4>
        <div class="loginform">
            <ul class="sui-nav nav-tabs tab-wraped">
                <li class="active">
                    <h3>Account login</h3>
                </li>
            </ul>

            <div class="tab-content tab-wraped">
                <%--login reminder sentence  --%>
                <span style="color: red">${msg}</span>
                <div id="profile" class="tab-pane  active">

                    <form id="loginform" class="sui-form" action="${pageContext.request.contextPath}/user/login"
                          method="post">
                        <div class="input-prepend"><span class="add-on loginname">Username</span>
                            <input type="text" placeholder="email" class="span2 input-xfat" name="email">
                        </div>
                        <div class="input-prepend"><span class="add-on loginpwd">Password</span>
                            <input type="password" placeholder="please enter the password" class="span2 input-xfat" name="password">
                        </div>
                        <div class="logined">
                            <%--    A target attribute with the value of “_self” opens the linked document in the same frame as it was clicked               --%>
                            <a class="sui-btn btn-block btn-xlarge btn-danger"  href='javascript:document:loginform.submit();' target="_self"> Login </a>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    /**
     * login timeout,  if session is out of date, the login.jsp is embedded into the iframe
     */
    var _topWin = window;
    while (_topWin != _topWin.parent.window) {
        _topWin = _topWin.parent.window;
    }
    if (window != _topWin)
        _topWin.document.location.href = '${pageContext.request.contextPath}/admin/login.jsp';
</script>

</html>