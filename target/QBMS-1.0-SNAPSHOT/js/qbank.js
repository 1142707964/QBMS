var questionObj;

//题目管理页面的【删除题目】操作
function deleteQuestion(obj){
    $.ajax({
        type:"GET",
        url:"/jsp/qbank.do",
        data:{method:"delete",qid:obj.attr("qid")},
        dataType:"json",
        success:function(data){
            if(data.delResult == "true"){//删除成功：移除删除行
                cancleBtn();
                window.location.href="/jsp/qbank.do?method=query";
            }else if(data.delResult == "false"){//删除失败
                changeDLGContent("对不起，删除题目【"+obj.attr("qid")+"】失败");
            }
        },
        error:function(data){
            alert("请求失败");
        }
    });
}

function openYesOrNoDLG(){
    $('.zhezhao').css('display', 'block');
    $('#removeBi').fadeIn();
}

function cancleBtn(){
    $('.zhezhao').css('display', 'none');
    $('#removeBi').fadeOut();
}
function changeDLGContent(contentStr){
    var p = $(".removeMain").find("p");
    p.html(contentStr);
}

$(function(){
    $('#no').click(function () {
        cancleBtn();
    });

    $('#yes').click(function () {
        deleteQuestion(questionObj);
    });

    $(".deleteQuestion").on("click",function(){
        questionObj = $(this);
        changeDLGContent("您确定要删除题目【"+questionObj.attr("qid")+"】吗？");
        openYesOrNoDLG();
    });

    $(".modifyQuestion").on("click",function(){
        questionObj = $(this);
        window.location.href="/jsp/qbank.do?method=getQId&qid="+questionObj.attr("qid");
    });

    $("#return").on("click",function(){
        window.location.href="/jsp/qbank.do?method=query";
    });

});