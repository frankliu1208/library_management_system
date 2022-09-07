<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Book management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
</head>

<body class="hold-transition skin-red sidebar-mini">
<!-- .box-body -->
<div class="box-header with-border">
    <h3 class="box-title">Book borrowing</h3>
</div>
<div class="box-body">
    <%--：， add button:  if the logged user is admin, display add button--%>
    <c:if test="${USER_SESSION.role =='ADMIN'}">
        <div class="pull-left">
            <div class="form-group form-inline">
                <div class="btn-group">
                    <button type="button" class="btn btn-default" title="Add" data-toggle="modal"
                            data-target="#addOrEditModal" onclick="resetFrom()"> Add
                    </button>
                </div>
            </div>
        </div>
    </c:if>


    <!--toolbar data search -->
    <div class="box-tools pull-right">
        <div class="has-feedback">
            <form action="${pageContext.request.contextPath}/book/search" method="post">
                Book name：<input name="name" value="${search.name}">&nbsp&nbsp&nbsp&nbsp
                Book auther：<input name="author" value="${search.author}">&nbsp&nbsp&nbsp&nbsp
                Publisher：<input name="press" value="${search.press}">&nbsp&nbsp&nbsp&nbsp
                <input class="btn btn-default" type="submit" value="Search">
            </form>
        </div>
    </div>
    <!--toolbar data search /-->

    <!-- data sheet -->
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
                <th class="sorting">Borrowed time</th>
                <th class="sorting">Estimated return date</th>
                <th class="text-center">Operation</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageResult.rows}" var="book">
                <tr>
                    <td> ${book.name}</td>
                    <td>${book.author}</td>
                    <td>${book.press}</td>
                    <td>${book.isbn}</td>
                    <td>
                        <c:if test="${book.status ==0}">Can be borrowed</c:if>
                        <c:if test="${book.status ==1}">has been borrowed</c:if>
                        <c:if test="${book.status ==2}">Returning</c:if>
                    </td>
                    <td>${book.borrower }</td>
                    <td>${book.borrowTime}</td>
                    <td>${book.returnTime}</td>
                    <td class="text-center">
                        <c:if test="${book.status ==0}">
                            <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                    data-target="#borrowModal" onclick="findBookById(${book.id},'borrow')"> Borrow
                            </button>
                            <c:if test="${USER_SESSION.role =='ADMIN'}">
                                <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                        data-target="#addOrEditModal" onclick="findBookById(${book.id},'edit')"> Edit
                                </button>
                            </c:if>
                        </c:if>
                        <c:if test="${book.status ==1 ||book.status ==2}">
                            <button type="button" class="btn bg-olive btn-xs" disabled="true">Borrow</button>
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

<%--引入存放模态窗口的页面--%>
<jsp:include page="/admin/book_modal.jsp"></jsp:include>

<!-- 添加和编辑图书的模态窗口 -->
<div class="modal fade" id="addOrEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="myModalLabel">Book information</h3>
            </div>
            <div class="modal-body">
                <form id="addOrEditBook">
                    <span><input type="hidden" id="ebid" name="id"></span>
                    <table id="addOrEditTab" class="table table-bordered table-striped" width="800px">
                        <%--图书的id,不展示在页面--%>
                        <tr>
                            <td>Book name</td>
                            <td><input class="form-control" placeholder="Book name" name="name" id="ebname"></td>
                            <td>Standard ISBN</td>
                            <td><input class="form-control" placeholder="Standard ISBN" name="isbn" id="ebisbn"></td>
                        </tr>
                        <tr>
                            <td>Publisher</td>
                            <td><input class="form-control" placeholder="Publisher" name="press" id="ebpress"></td>
                            <td>Writer</td>
                            <td><input class="form-control" placeholder="Writer" name="author" id="ebauthor"></td>
                        </tr>
                        <tr>
                            <td>Book page</td>
                            <td><input class="form-control" placeholder="Book page" name="pagination" id="ebpagination"></td>
                            <td>Book price<br/></td>
                            <td><input class="form-control" placeholder="Book price" name="price" id="ebprice"></td>
                        </tr>
                            <tr>
                                <td>Status</td>
                                <td>
                                    <select class="form-control" id="ebstatus" name="status" >
                                        <option value="0">On the shelf</option>
                                        <option value="3">Off the shelf</option>
                                    </select>
                                </td>
                            </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" id="aoe" disabled onclick="addOrEdit()">Save
                </button>
                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Close</button>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    /*分页插件展示的总页数*/
    pageargs.total = Math.ceil(${pageResult.total}/pageargs.pagesize);
    /*分页插件当前的页码*/
    pageargs.cur = ${pageNum}
    /*分页插件页码变化时将跳转到的服务器端的路径*/
    pageargs.gourl = "${gourl}"
    /*保存搜索框中的搜索条件，页码变化时携带之前的搜索条件*/
    bookVO.name = "${search.name}"
    bookVO.author = "${search.author}"
    bookVO.press = "${search.press}"
    /*分页效果*/
    pagination(pageargs);
</script>
</html>