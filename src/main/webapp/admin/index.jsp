<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <!-- meta -->
    <meta charset="utf-8">
    <title>cloudlibrary-Book management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/_all-skins.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
    <script type="text/javascript">
        function SetIFrameHeight() {
            var iframeid = document.getElementById("iframe"); //iframe id
            if (document.getElementById) {
                iframeid.height = document.documentElement.clientHeight;
            }
        }
    </script>
</head>
<body class="hold-transition skin-green sidebar-mini">
<div class="wrapper">

    <!-- head of the page -->
    <header class="main-header">
        <!-- Logo -->
        <a href="index.jsp" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>cloudlibrary</b></span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>cloudlibrary-Book management</b></span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top">
            <nav class="navbar navbar-static-top">
                <div class="navbar-custom-menu">
                    <ul class="nav navbar-nav">
                        <li class="dropdown user user-menu">
                            <a >
                                <img src="${pageContext.request.contextPath}/img/user.jpg" class="user-image" alt="User Image">
                                <span class="hidden-xs">${USER_SESSION.name}</span>
                            </a>
                        </li>
                        <li class="dropdown user user-menu">
                            <a href="${pageContext.request.contextPath}/user/logout">
                                <span class="hidden-xs">注销</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>
        </nav>
    </header>
    <!-- head of page /-->

    <!-- nav sidebar -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- /.search form -->
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <ul class="sidebar-menu">
                <li id="admin-index">
                    <a href="index.jsp">
                        <i class="fa fa-dashboard"></i> <span>Main page</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/book/search" target="iframe">
                        <i class="fa fa-circle-o"></i>Book borrowing
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/book/searchBorrowed" target="iframe">
                        <i class="fa fa-circle-o"></i>Current borrowing
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/record/searchRecords" target="iframe">
                        <i class="fa fa-circle-o"></i>Borrowing record
                    </a>
                </li>
            </ul>
        </section>
        <!-- /.sidebar -->
    </aside>
    <!-- nav sidebar /-->

    <!-- content section -->
    <div class="content-wrapper">
        <iframe width="100%" id="iframe" name="iframe" onload="SetIFrameHeight()"
                frameborder="0" src="${pageContext.request.contextPath}/book/selectNewbooks"></iframe>
    </div>
</div>

</body>

</html>