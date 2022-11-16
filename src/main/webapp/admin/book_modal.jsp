<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%-- belong to Main page module  --%>
<!-- this modal window is hided by default,  in books_new.jsp, when clicking "borrow", call findBookById in my.js,
 send async request to backend, get the data and fill them in this modal window (refer to L89 - 97 in my.js)-->
<div class="modal fade" id="borrowModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="myModalLabel">Books information</h3>
            </div>
            <div class="modal-body">
                <form id="borrowBook">
                    <table class="table table-bordered table-striped" width="1200px">
                        <%--book id did not display in this modal window--%>
                        <span><input type="hidden" id="bid" name="id"></span>
                        <tr>
                            <td>Book name</td>
                            <td><input class="form-control" readonly name="name" id="bname"></td>
                            <td>ISBN</td>
                            <td><input class="form-control" readonly name="isbn" id="bisbn"></td>
                        </tr>
                        <tr>
                            <td>Publisher</td>
                            <td><input class="form-control" readonly name="press" id="bpress"></td>
                            <td>Writer</td>
                            <td><input class="form-control" readonly name="author" id="bauthor"></td>
                        </tr>
                        <tr>
                            <td>Book page</td>
                            <td><input class="form-control" readonly name="pagination" id="bpagination"></td>
                            <td>Return date<span style="color: red">*</span></td>
                            <%-- when the below input changes，call the cg() method--%>
                            <td><input class="form-control" type="date" name="returnTime" id="time" onchange="cg()">
                            </td>
                        </tr>
                    </table>
                </form>
            </div>

            <div class="modal-footer">
                <%-- when clicking save button, hide the modal window, and call the borrow method L32  in my.js--%>
                <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" onclick="borrow()"
                        disabled="true" id="savemsg">Save
                </button>
                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Close</button>
            </div>
        </div>
    </div>
</div>
