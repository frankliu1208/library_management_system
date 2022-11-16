<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<%-- front page for book management module:  3th sub-module "borrowing record" --%>
<head>
    <meta charset="utf-8">
    <title>Borrowing record</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"  charset="gb2312"></script>
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
</head>

<body class="hold-transition skin-red sidebar-mini">
<!-- on the top of the page -->
    <div class="box-header with-border">
        <h3 class="box-title">Borrowing record</h3>
    </div>
    <div class="box-body">
        <!--on the top of the page:  data search -->
        <div class="box-tools pull-right">
            <div class="has-feedback">
                <form action="${pageContext.request.contextPath}/record/searchRecords" method="post">
                    <c:if test="${USER_SESSION.role =='ADMIN'}">
                    Borrower：<input name="borrower" value="${search.borrower}">&nbsp&nbsp&nbsp&nbsp
                    </c:if>
                    Book name：<input name="bookname" value="${search.bookname}">&nbsp&nbsp&nbsp&nbsp
                    <button class="btn btn-default" type="submit">Search</button>
                </form>
            </div>
        </div>

        <!-- displaying table in the center of the page -->
        <div class="table-box">
            <!--data sheet-->
            <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center">
                <thead>
                <tr>
                    <th class="sorting">Borrower</th>
                    <th class="sorting_asc">Book name</th>
                    <th class="sorting">Standard ISBN</th>
                    <th class="sorting">Borrowing date</th>
                    <th class="sorting">Return date</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pageResult.rows}" var="record">
                    <tr>
                        <td>${record.borrower}</td>
                        <td>${record.bookname}</td>
                        <td>${record.bookisbn}</td>
                        <td>${record.borrowTime}</td>
                        <td>${record.remandTime}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <%--pagination plugin--%>
            <div id="pagination" class="pagination"></div>
        </div>

    </div>
</body>

<script>
    /* total pages, "$ {pageResult.total}" is being got from RecordController */
    pageargs.total = Math.ceil(${pageResult.total}/pageargs.pagesize);
    /*current page*/
    pageargs.cur = ${pageNum}

    pageargs.gourl = "${gourl}"
    /*  keep the searching condition in the search tag, bring previous searching condition when page changes*/
    recordVO.bookname = "${search.bookname}"
    recordVO.borrower = "${search.borrower}"
    /* pagination effect */
    pagination(pageargs);
</script>
</html>