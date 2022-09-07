<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>New book recommendation</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
</head>

<body class="hold-transition skin-red sidebar-mini">

<div class="box-header with-border">
    <h3 class="box-title">New book recommendation</h3>
</div>

<div class="box-body">
    <!-- sheet -->
    <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center">
        <thead>
        <tr>
            <th class="sorting_asc">Book name</th>
            <th class="sorting">Book auther</th>
            <th class="sorting">Publisher</th>
            <th class="sorting">ISBN</th>
            <th class="sorting">Book status</th>
            <th class="sorting">Borrower</th>
            <th class="sorting">Borrow time</th>
            <th class="sorting">Estimated return date</th>
            <th class="text-center">Operation</th>
        </tr>
        </thead>
        <tbody>
<%--  comes from BookController, selectNewbooks()      --%>
        <c:forEach items="${pageResult.rows}" var="book">
            <tr>
                <td> ${book.name}</td>
                <td>${book.author}</td>
                <td>${book.press}</td>
                <td>${book.isbn}</td>
                <td>
                    <c:if test="${book.status ==0}">Can be borrowed</c:if>
                    <c:if test="${book.status ==1}">Has been borrowed</c:if>
                    <c:if test="${book.status ==2}">Is being returned</c:if>
                </td>
                <td>${book.borrower}</td>
                <td>${book.borrowTime}</td>
                <td>${book.returnTime}</td>
                <td class="text-center">
<%--  in the onclick event, when clicking the button, go to the findBookById method in my.js         --%>
                    <c:if test="${book.status ==0}">
                        <button type="button" class="btn bg-olive btn-xs" data-toggle="modal" data-target="#borrowModal"
                                onclick="findBookById(${book.id},'borrow')"> Borrow
                        </button>
                    </c:if>
                    <c:if test="${book.status ==1 ||book.status ==2}">
                        <button type="button" class="btn bg-olive btn-xs" disabled="true">Borrow</button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<%--引入存放模态窗口的页面, 这里为什么要引入 ??--%>
<jsp:include page="/admin/book_modal.jsp"></jsp:include>
</body>
</html>
