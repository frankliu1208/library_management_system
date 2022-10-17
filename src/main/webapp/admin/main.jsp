<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<%--    this is the home page of this management system when logging in sucessfully, only for administrator--%>
    <meta charset="utf-8">
    <title>Cloudlibrary-Book management system</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/_all-skins.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
    <script src="https://kit.fontawesome.com/c2a154e786.js" crossorigin="anonymous"></script>

    <script type="text/javascript">
        function SetIFrameHeight() {
            var iframeid = document.getElementById("iframe");
            if (document.getElementById) {
                /*  set the height of content display section = pages' height  */
                iframeid.height = document.documentElement.clientHeight;
            }
        }
    </script>
</head>

<body class="hold-transition skin-green sidebar-mini">
<div class="wrapper">
    <!-- page header -->
    <header class="main-header">
        <!-- Logo -->
        <a href="${pageContext.request.contextPath}/admin/main.jsp" class="logo">
            <span class="logo-lg"><b>Cloudlibrary-Book management system</b></span>
        </a>
        <!-- Header navigation -->
        <nav class="navbar navbar-static-top">
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <li class="dropdown user user-menu">
                        <a>
                            <img src="${pageContext.request.contextPath}/img/user.jpg" class="user-image"
                                 alt="User Image">
                            <span class="hidden-xs">${USER_SESSION.name}</span>
                        </a>
                    </li>
                    <li class="dropdown user user-menu">
                        <a href="${pageContext.request.contextPath}/user/logout">
                            <span class="hidden-xs">Logout</span>
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <!-- page header /-->



    <!-- navigation sidebar  -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- /.search form -->
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <ul class="sidebar-menu">
                <li id="admin-index">
                    <a href="main.jsp">
                        <i class="fa fa-dashboard"></i> <span>Main page</span>
                    </a>
                </li>
                <!-- Model 1: Personal management -->
                <li id="admin-login">
                    <a href="${pageContext.request.contextPath}/user/search" target="iframe">
                        <i class="fa fa-circle-o"></i>Personal management
                    </a>
                </li>
                <!-- Model 2: Book management -->
                <li class="treeview">
                    <a href="#">
                        <i class="fa fa-folder"></i>
                        <span>Book management</span>
                        <span class="pull-right-container">
				       			<i class="fa fa-angle-left pull-right"></i>
				   		 	</span>
                    </a>

                    <ul class="treeview-menu">
                        <%--     sub model 2.1  --%>
                        <li>
                            <a href="${pageContext.request.contextPath}/book/search" target="iframe">
                                <i class="fa fa-circle-o"></i>Book borrowing
                            </a>
                        </li>
                        <%--     sub model 2.2  --%>
                        <li>
                            <a href="${pageContext.request.contextPath}/book/searchBorrowed" target="iframe">
                                <i class="fa fa-circle-o"></i>Current borrowing
                            </a>
                        </li>
                         <%--     sub model 2.3  --%>
                        <li>
                            <a href="${pageContext.request.contextPath}/record/searchRecords" target="iframe">
                                <i class="fa fa-circle-o"></i>Borrowing record
                            </a>
                        </li>
                    </ul>

                </li>

            </ul>
        </section>

    </aside>


    <!-- newly book display section -->
    <div class="content-wrapper">
        <iframe width="100%" id="iframe" name="iframe" onload="SetIFrameHeight()"
                frameborder="0" src="${pageContext.request.contextPath}/book/selectNewbooks"></iframe>
    </div>
</div>
</body>
</html>