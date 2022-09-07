<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <!-- meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Personal management</title>
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
    <h3 class="box-title">Personal management</h3>
</div>
<div class="box-body">
    <!-- Data sheet -->
    <div class="table-box">
        <!--tool bar-->
        <div class="pull-left">
            <div class="form-group form-inline">
                <div class="btn-group">
                    <button type="button" class="btn btn-default" title="Add" data-toggle="modal"
                            data-target="#addModal" onclick="resetUserFrom()"><i class="fa fa-file-o" ></i> Add
                    </button>
                </div>
            </div>
        </div>
        <div class="box-tools pull-right">
            <div class="has-feedback">
                <form action="${pageContext.request.contextPath}/user/search" method="post">
                    ID：<input name="id" value="${user.id}">&nbsp&nbsp&nbsp&nbsp
                    Name：<input name="name" value="${user.name}">&nbsp&nbsp&nbsp&nbsp
                    <input class="btn btn-default" type="submit" value="Search">
                </form>
            </div>
        </div>
    </div>
    <!--tool bar/-->
    <!--Data list-->
    <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center">
        <thead>
        <tr class="text-center">
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Joining date</th>
            <th>Employment status</th>
            <th>Operation</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageResult.rows}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.hiredate}</td>
                <td>
                    <c:if test="${user.status == 0}">
                        Employed
                    </c:if>
                    <c:if test="${user.status == 1}">
                        Left
                    </c:if>

                </td>
                <td class="text-center">
                    <c:if test="${user.status == 0}">
                        <button type="button" class="btn bg-olive btn-xs" data-toggle="modal" data-target="#editModal"
                                onclick="findUserById(${user.id})">Modify
                        </button>
                        &nbsp&nbsp&nbsp&nbsp
                        <button type="button" class="btn bg-olive btn-xs" onclick="delUser(${user.id})">Left</button>
                    </c:if>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <!--Data list/-->
    <div id="pagination" class="pagination"></div>
</div>
<!-- Data sheet /-->
</div>
<!-- /.box-body -->
<tm-pagination conf="paginationConf"></tm-pagination>

<!-- Adding window -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 id="myModalLabel">Personal information</h3>
            </div>
            <div class="modal-body">
                <form id="addUser">
                    <table class="table table-bordered table-striped" width="800px">
                        <tr>
                            <td>Personal name</td>
                            <td><input class="form-control" placeholder="Personal name" id="adduname" onblur="checkVal()" onfocus="changeVal()" name="name"></td>
                            <td>Company email</td>
                            <td><input class="form-control" placeholder="Company email" id="adduemail" onblur="checkVal()" onfocus="changeVal()"name="email"></td>
                        </tr>
                        <tr>
                            <td>Joining date</td>
                            <td><input type="date" class="form-control" name="hiredate" id="time"  onchange="checkVal()"></td>
                            <td>Login password</td>
                            <td><input class="form-control" placeholder="Login password" id="addPw" onblur="checkVal()" onfocus="changeVal()" name="password"></td>
                        </tr>
						<tr>
							<td>User role</td>
							<td>
								<select class="form-control" name="role" value="USER">
									<option value="USER">User</option>
									<option value="ADMIN">Administrator</option>
								</select>
							</td>
                            <td colspan="2"><span style="color: red" id="addmsg"></span></td>
						</tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" id="savemsg" disabled="true"
                        onclick="saveUser()">Save
                </button>
                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Close</button>
            </div>
        </div>
    </div>
</div>

<!-- Editing window -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 id="myModalLabe2">Personal information</h3>
            </div>
            <div class="modal-body">
                <form id="editUser">
                    <table class="table table-bordered table-striped" width="800px">

                        <tr>
                            <td>Name</td>
                            <td><input class="form-control"  readonly name="name" id="uname"></td>
                            <td>ID</td>
                            <td><input class="form-control"  readonly name="id" id="uid"></td>
                        </tr>
                        <tr>
                            <td>Company email</td>
                            <td><input class="form-control" readonly name="email" id="uemail" >
                            </td>
                            <td>Joining date</td>
                            <td><input class="form-control" readonly name="hiredate" id="uhire" ></td>
                        </tr>
                        <tr>
                            <td>Login password</td>
                            <td><input class="form-control" type="password"  name="password" id="pw"></td>
							<td>User role</td>
							<td>
								<select class="form-control" id="urole" name="role" >
									<option value="USER">User</option>
									<option value="ADMIN">Administrator</option>
								</select>
							</td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" onclick="editUser()">Save</button>
                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Close</button>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    //total pages
    pageargs.total=Math.ceil(${pageResult.total}/pageargs.pagesize);

    pageargs.cur=${pageNum}
     pageargs.gourl="${gourl}"
    userVO.id="${search.id}"
    userVO.name="${search.name}"
    pagination(pageargs);
</script>
</html>