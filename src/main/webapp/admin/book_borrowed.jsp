<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%--belong to book management module:  2nd sub-module "current borrowing" --%>
    <meta charset="utf-8">
    <title>Current borrowing</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"  charset="gb2312" ></script>
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
</head>

<body class="hold-transition skin-red sidebar-mini">

<div class="box-header with-border">
    <h3 class="box-title">Current borrowing</h3>
</div>
<div class="box-body">
    <!--data search area on the top -->
    <div class="box-tools pull-right">
        <div class="has-feedback">
            <form action="${pageContext.request.contextPath}/book/searchBorrowed" method="post">
                Book name：<input name="name" value="${search.name}">&nbsp&nbsp&nbsp&nbsp
                Book auther：<input name="author" value="${search.author}">&nbsp&nbsp&nbsp&nbsp
                Publisher：<input name="press" value="${search.press}">&nbsp&nbsp&nbsp&nbsp
                <input class="btn btn-default" type="submit" value="Search">
            </form>
        </div>
    </div>


    <!--data list-->
    <div class="table-box">

        <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center">
            <thead>
            <tr>
                <th class="sorting_asc">Book name</th>
                <th class="sorting">Book auther</th>
                <th class="sorting">Publisher</th>
                <th class="sorting">ISBN</th>
                <th class="sorting">Book status</th>
                <th class="sorting">Borrower</th>
                <th class="sorting">Borrow date</th>
                <th class="sorting">Expected return date</th>
                <th class="text-center">Operation</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageResult.rows}" var="book">
                <tr>
                    <td>${book.name}</td>
                    <td>${book.author}</td>
                    <td>${book.press}</td>
                    <td>${book.isbn}</td>
                    <td>
                        <c:if test="${book.status ==1}">Borrowing</c:if>
                        <c:if test="${book.status ==2}">Returning</c:if>
                    </td>
                    <td>${book.borrower}</td>
                    <td>${book.borrowTime}</td>
                    <td>${book.returnTime}</td>
                    <td class="text-center">
                        <%--   when clicking return button, go to my.js returnBook method      --%>
                        <c:if test="${book.status ==1}">
                            <button type="button" class="btn bg-olive btn-xs" onclick="returnBook(${book.id})">Return
                            </button>
                        </c:if>
                        <c:if test="${book.status ==2}">
                            <button type="button" class="btn bg-olive btn-xs" disabled="true">In process:Returning</button>
                         <%--     when clicking return Confirm, go to my.js returnConfirm method  --%>
                         <c:if test="${USER_SESSION.role =='ADMIN'}">
                            <button type="button" class="btn bg-olive btn-xs" onclick="returnConfirm(${book.id})">
                                    Confirm returning
                            </button>
                          </c:if>
                        </c:if>
                    </td>
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

    pageargs.total = Math.ceil(${pageResult.total}/(pageargs.pagesize));
    pageargs.cur = ${pageNum}
	pageargs.gourl = "${gourl}"
    bookVO.name = "${search.name}"
    bookVO.author = "${search.author}"
    bookVO.press = "${search.press}"

    pagination(pageargs);
</script>
</html>