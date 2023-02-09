var userObj;

//用户管理页面上点击修改按钮弹出修改框(userlist.jsp)
function updateUser(obj){
    $.ajax({
        type:"GET",
        url:"/jsp/user.do",
        data:{method:"update",uid:obj.attr("userid"),newRole:$("#role").val()},
        dataType:"json",
        success:function(data){
            if(data.updateResult == "true"){//修改成功：隐藏弹窗
                console.log("修改成功");
                cancleBtn();
                window.location.href="/jsp/user.do?method=query";
            }else if(data.updateResult == "false"){//修改失败
                console.log("修改失败");
            }
        },
        error:function(data){
            alert("请求错误");
        }
    });
}

//用户管理页面上点击删除按钮弹出删除框(userlist.jsp)
function deleteUser(obj){
    $.ajax({
        type:"GET",
        url:"/jsp/user.do",
        data:{method:"delete",uid:obj.attr("userid"),realName:obj.attr("realname")},
        dataType:"json",
        success:function(data){
            if(data.deleteResult == "true"){//删除成功：隐藏弹窗
                console.log("删除成功");
                $('#remove1').css('display', 'none');
                window.location.href="/jsp/user.do?method=query";
            }else if(data.deleteResult == "false"){//删除失败
                console.log("删除失败");
            }
        },
        error:function(data){
            alert("请求错误");
        }
    });
}

function openYesOrNoDLG(){
    $('.zhezhao').css('display', 'block');
    $('#updateView').fadeIn();
}

function cancleBtn(){
    $('.zhezhao').css('display', 'none');
    $('#updateView').fadeOut();
}

$(function(){
    $('#no_update').click(function () {
        cancleBtn();
    });

    $('#yes_update').click(function () {
        updateUser(userObj);
    });

    $(".modifyUser").on("click",function(){
        userObj = $(this);
        openYesOrNoDLG();
    });

    $('#no_delete').on("click",function(){
        $('#remove1').css('display', 'none');
    });

    $('#yes_delete').on("click",function(){
        deleteUser(userObj);
    });

    $(".deleteUser").on("click",function(){
        userObj = $(this);
        $('#remove1').css('display', 'block');
    });


});