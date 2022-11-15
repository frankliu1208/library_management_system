
// implement when the contents in the lending section timing tag changes
function cg() {
    $("#savemsg").attr("disabled", false);
    var rt = $("#time").val().split("-");
    var ny = new Date().getFullYear();
    var nm = new Date().getMonth() + 1;
    var nd = new Date().getDate();
    if (rt[0] < ny) {
        alert("the date cannot be earlier than today")
        $("#savemsg").attr("disabled", true);
    } else if (rt[0] == ny) {
        if (rt[1] < nm) {
            alert("the date cannot be earlier than today")
            $("#savemsg").attr("disabled", true);
        } else if (rt[1] == nm) {
            if (rt[2] < nd) {
                alert("the date cannot be earlier than today")
                $("#savemsg").attr("disabled", true);
            } else {
                $("#savemsg").attr("disabled", false);
            }
        }
    }
}

// #id :   choose through id property
//on(): add one or more event handling for the selected element
//  jQuery attr()  set or change the value of property

//点击借阅图书时执行
function borrow() {
    var url =getProjectPath()+ "/book/borrowBook";
    $.post(url, $("#borrowBook").serialize(), function (response) {
        alert(response.message)
        if (response.success == true) {
            window.location.href = getProjectPath()+"/book/search";
        }
    })
}


//text() -   set or return the written text of selected elements
// html() -  set or return the content of selected elements
// val() -   set or return the value of field



//  reset the content in the input tag of adding and editing windows
function resetFrom() {
    $("#aoe").attr("disabled",true)
    var $vals=$("#addOrEditBook input"); // all the input tag in the adding modal window is empty, without data from backend,  L125
    $vals.each(function(){
        $(this).attr("style","").val("")
    });
}


//  reset the style in the input tag of adding and editing windows
function resetStyle() {
    $("#aoe").attr("disabled",false)
    var $vals=$("#addOrEditBook input");
    $vals.each(function(){
        $(this).attr("style","")
    });
}

// belong to book management module,  Home page sub-module, when clicking the borrow button in books_new.jsp, call the findBookById method here
// search book info according to id, then re/send the info to the pages
// 2 param:  id is the book id that user wants to borrow, doname has "edit" or "borrow", it will lead to different code
function findBookById(id, doname) {

    resetStyle()
    var url = getProjectPath()+"/book/findById?id=" + id;
    $.get(url, function (response) {
        // in books.jsp, when clicking edit button, will call this method.  if it is editing the book, send the below info(get from backend) to books.jsp L130, fill the input tags of modal window, later the admin can edit these infos.
        if(doname=='edit'){
            $("#ebid").val(response.data.id);  // ebid is a hidden input tag, it is not displayed in books.jsp, but addOrEdit() will use it
            $("#ebname").val(response.data.name);
            $("#ebisbn").val(response.data.isbn);
            $("#ebpress").val(response.data.press);
            $("#ebauthor").val(response.data.author);
            $("#ebpagination").val(response.data.pagination);
            $("#ebprice").val(response.data.price);
            $("#ebstatus").val(response.data.status);
        }
        //in books_new.jsp, when clicking borrow button, will call findBookById method here,  if it is borrowing the book, send all below infos coming from backend, to bookmodal.jsp, fill the input tags of modal window
        if(doname=='borrow'){
            $("#savemsg").attr("disabled",true)
            $("#time").val("");
            $("#bid").val(response.data.id);  // bid is a hidden input tag in book_modal.jsp
            $("#bname").val(response.data.name);  // fill this data into bname input tag in book_modal.jsp
            $("#bisbn").val(response.data.isbn);  // fill this data into bisbn input tag in book_modal.jsp
            $("#bpress").val(response.data.press);  // fill this data into bpress input tag in book_modal.jsp
            $("#bauthor").val(response.data.author);  // fill this data into bauthor input tag in book_modal.jsp
            $("#bpagination").val(response.data.pagination);// fill this data into bpagination input tag in book_modal.jsp
        }
    })
}



//  come from books.jsp L158, when clicking save(either for adding or editing functions)
function addOrEdit() {
    // get the book id from books.jsp L127
    var ebid = $("#ebid").val();
    // if book id exists, it is editing operation
    if (ebid > 0) {
        var url = getProjectPath()+"/book/editBook";
        $.post(url, $("#addOrEditBook").serialize(), function (response) {
            alert(response.message)
            if (response.success == true) {
                window.location.href = getProjectPath()+"/book/search"; // after editing, search all books and display them
            }
        })
    }
    //if book id does not exist, it is adding operation, add button is in books.jsp L28
    else {
        var url = getProjectPath()+"/book/addBook";
        $.post(url, $("#addOrEditBook").serialize(), function (response) {
            alert(response.message)
            if (response.success == true) {
                window.location.href = getProjectPath()+"/book/search";
            }
        })
    }
}


//  implement when clicking return button in book_borrowed.jsp L70
//  belongs to third sub-module: current borrowing
function returnBook(bid) {
    var r = confirm("Do you confirm the returning of the book?");
    if (r) {
        var url = getProjectPath()+"/book/returnBook?id=" + bid;
        $.get(url, function (response) {
            alert(response.message)
            //successfully return the book, then refresh the overall list
            if (response.success == true) {
                window.location.href = getProjectPath()+"/book/searchBorrowed";
            }
        })
    }
}


//  confirm that the books have been returned, only for admin, implement when clicking returnConfirm button in book_borrowed
//  belongs to third sub-module: current borrowing
function returnConfirm(bid) {
    var r = confirm("Do you confirm that the books have been returned?");
    if (r) {
        var url = getProjectPath()+"/book/returnConfirm?id=" + bid;
        $.get(url, function (response) {
            alert(response.message)
            //successfully return the book, then refresh the overall list
            if (response.success == true) {
                window.location.href = getProjectPath()+"/book/searchBorrowed";
            }
        })
    }
}


//  check if the book info input is complete or not in book info window
function checkval(){
    var $inputs=$("#addOrEditTab input")
    var count=0;
    $inputs.each(function () {
        if($(this).val()==''||$(this).val()=="cannot be empty"){
            count+=1;
        }
    })
    // release the freezing status of confirm button if input is complete
    if(count==0){
        $("#aoe").attr("disabled",false)
    }
}


//页面加载完成后，给图书模态窗口的输入框绑定失去焦点和获取焦点事件
$(function() {
    var $inputs=$("#addOrEditBook input")
    var eisbn="";
    $inputs.each(function () {
        //给输入框绑定失去焦点事件
        $(this).blur(function () {
            if($(this).val()==''){
                $("#aoe").attr("disabled",true)
                $(this).attr("style","color:red").val("不能为空！")
            }
            else if($(this).attr("name")=="isbn"&&eisbn!==$(this).val()){
                if($(this).val().length!=13){
                    $("#aoe").attr("disabled",true)
                    alert("必须为13位数的标准ISBN，请重新输入！")
                    $(this).val("")
                }
            }else{
                checkval()
            }
        }).focus(function () {
            if($(this).val()=='不能为空！'){
                $(this).attr("style","").val("")
            }else{
                $(this).attr("style","")
            }
            if($(this).attr("name")=="isbn"){
                eisbn=$(this).val();
            }
        })
    })
});


//  reset the content in the input tag of add and edit windows
function resetUserFrom() {
    $("#savemsg").attr("disabled",true)
    $("#addmsg").html("")
    var $vals=$("#addUer input");
    $vals.each(function(){
        $(this).attr("style","").val("")
    });

}

// comes from user.jsp L90, the ajax request from this method will send data to UserController findById method
// response is the User-class-object from findUserById method in UserController
function findUserById(uid) {
    var url = getProjectPath()+"/user/findById?id=" + uid;
    // only update the value in the editing window, in L169 - 180 in user.jsp, did not change the displaying table in the front page
    $.get(url, function (response) {
        $("#uid").val(response.id);
        $("#uname").val(response.name);
        $("#pw").val(response.password);
        $("#urole").val(response.role);
        $("#uemail").val(response.email);
        $("#uhire").val(response.hiredate);
    })
}

// comes from user.jsp L196,  when user clicks modify button to edit the user information, the ajax request will send data to UserController
// response is the returning Result-class-object from editUser method in UserController
function editUser() {
    var url =getProjectPath()+ "/user/editUser";
    $.post(url, $("#editUser").serialize(), function (response) {
        alert(response.message)
        if (response.success == true) {
            window.location.href = getProjectPath()+"/user/search";
        }
    })
}

// comes from user.jsp L123 - 130
function changeVal() {
    $("#addmsg").html("")
}
// comes from user.jsp L123 - 130,  check the input in the adding window
function checkVal() {
    $("#savemsg").attr("disabled", false);
    $("#addmsg").html("")
    var adduname = $("#adduname").val();
    var adduemail = $("#adduemail").val();
    var addPw = $("#addPw").val();
    var addtime = $("#time").val();
    if ($.trim(adduname) == '') {
        $("#savemsg").attr("disabled", true);
        $("#addmsg").html("Name cannot be empty")
    } else {
        checkName(adduname);
        if ($.trim(adduemail) == '') {
            $("#savemsg").attr("disabled", true);
            $("#addmsg").html("Email cannot be empty")
        } else if ($.trim(adduemail) != '') {
            checkEmail(adduemail);
                if ($.trim(addPw) == '') {
                $("#savemsg").attr("disabled", true);
                $("#addmsg").html("Password cannot be empty")
            }else if($.trim(addPw) != ''){
                if($.trim(addtime) == ''){
                    $("#savemsg").attr("disabled", true);
                    $("#addmsg").html("Joining date cannot be empty")
                }else{
                    cg()
                }
            }
        }
    }
}

function checkName(name, email) {
    var url = getProjectPath()+"/user/checkName?name=" + name;
    $.post(url, function (response) {
        if (response.success != true) {
            $("#savemsg").attr("disabled", true);
            $("#addmsg").html(response.message)
        }
    })
}


function checkEmail(email) {
    var url = getProjectPath()+"/user/checkEmail?email=" + email;
    $.post(url, function (response) {
        if (response.success != true) {
            $("#savemsg").attr("disabled", true);
            $("#addmsg").html(response.message)
        }
    })
}

// save the data from the adding window in user.jsp L148, send ajax to addUser method in UserController.
//  response is the object of Result class
function saveUser() {
    var url =getProjectPath()+"/user/addUser";
    // #addUser is in L119 in user.jsp
    $.post(url, $("#addUser").serialize(), function (response) {
        alert(response.message)
        if (response.success == true) {
            window.location.href =  getProjectPath()+"/user/search";
        }
    })
}

// comes from user.jsp L93
function delUser(uid) {
    var r = confirm("Do you confirm the delete of this person with ID：" + uid);
    if (r) {
        var url = getProjectPath() + "/user/delUser?id=" + uid;  // call the method in L334 in this file
        $.get(url, function (response) {
            alert(response.message)
            if (response.success == true) {
                window.location.href = getProjectPath() + "/user/search";
            }
        })
    }
}


// get the name of current project
    function getProjectPath() {
        // get the path after host path ，ex： cloudlibrary/admin/books.jsp
        // refer:  https://www.w3schools.com/js/js_window_location.asp
        var pathName = window.document.location.pathname;
        //  get the project name with "/" , ex：/cloudlibrary
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);  // substr method:  https://www.runoob.com/jsref/jsref-substr.html#:~:text=substr()%20%E6%96%B9%E6%B3%95%E5%8F%AF%E5%9C%A8,%E5%8F%82%E6%95%B0start%20%E7%9A%84%E5%80%BC%E6%97%A0%E6%95%88%E3%80%82
        return projectName;
    }


    /**
     *  pagination plugin parameter of data displaying page
     * cur :    current pages
     * total :  total pages
     * len :    display how much pages
     * pagesize :   display number of data per page
     * gourl :    jumping path when pages change
     * targetId : pagination id
     */
    var pageargs = {
        cur: 1,
        total: 0,
        len: 5,
        pagesize: 10,
        gourl: "",
        targetId: 'pagination',
        callback: function (total) {
            var oPages = document.getElementsByClassName('page-index');
            for (var i = 0; i < oPages.length; i++) {
                oPages[i].onclick = function () {
                    changePage(this.getAttribute('data-index'), pageargs.pagesize);
                }
            }
            var goPage = document.getElementById('go-search');
            goPage.onclick = function () {
                var index = document.getElementById('yeshu').value;
                if (!index || (+index > total) || (+index < 1)) {
                    return;
                }
                changePage(index, pageargs.pagesize);
            }
        }
    }

    /**
     * searching parameters in book searching lane
     */
    var bookVO = {
        name: '',
        author: '',
        press: ''
    }

    /**
     *  searching parameters in book lending record searching lane
     */
    var recordVO = {
        bookname: '',
        borrower: ''
    }


    var userVO = {
        id: '',
        name: ''
    }

// implemented when page number changes in data displaying page
    function changePage(pageNo, pageSize) {
        pageargs.cur = pageNo;
        pageargs.pagesize = pageSize;
        document.write("<form action=" + pageargs.gourl + " method=post name=form1 style='display:none'>");
        document.write("<input type=hidden name='pageNum' value=" + pageargs.cur + " >");
        document.write("<input type=hidden name='pageSize' value=" + pageargs.pagesize + " >");
        //  if it is the link related to books info search, submit the param in book search input tag
        if (pageargs.gourl.indexOf("book") >= 0) {
            document.write("<input type=hidden name='name' value=" + bookVO.name + " >");
            document.write("<input type=hidden name='author' value=" + bookVO.author + " >");
            document.write("<input type=hidden name='press' value=" + bookVO.press + " >");
        }
        //   if it is the link related to user info search, submit the param in book search input tag 如果跳转的是图书记录查询的相关链接，提交图书记录查询栏中的参数
        if (pageargs.gourl.indexOf("user") >= 0) {
            document.write("<input type=hidden name='id' value=" + userVO.id + " >");
            document.write("<input type=hidden name='name' value=" + userVO.name + " >");
        }
        //  if it is the link related to record  search, submit the param in book search input tag 如果跳转的是图书记录查询的相关链接，提交图书记录查询栏中的参数
        if (pageargs.gourl.indexOf("record") >= 0) {
            document.write("<input type=hidden name='bookname' value=" + recordVO.bookname + " >");
            document.write("<input type=hidden name='borrower' value=" + recordVO.borrower + " >");
        }
        document.write("</form>");
        document.form1.submit();
        pagination(pageargs);
    }


