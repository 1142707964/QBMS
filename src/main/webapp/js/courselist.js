var addBtn = document.getElementById("addCourse");
var noAdd = document.getElementById("no_add");
// var delBtn = document.getElementsByClassName("deleteCourse");
// var noDel = document.getElementById("no_del");
//普通用户【我的课程】的界面展示
$(".courseTD").on("click",function(){
    questionObj = $(this);
    window.location.href="/jsp/qbank.do?method=query&queryCourseName="+questionObj.attr("cname");
});
addBtn.onclick = function() {
    $('.zhezhao').css('display', 'block');
    $('#remove1').css('display', 'block');
    $('#newCourse').focus();
}
noAdd.onclick = function() {
    $('.zhezhao').css('display', 'none');
    $('#remove1').css('display', 'none');
}
// delBtn[0].onclick = function() {
//     $('#remove2').css('display', 'block');
// }
// $(".deleteCourse").on("click",function(){
//     $('#remove2').css('display', 'block');
// });
// noDel.onclick = function() {
//     $('#remove2').css('display', 'none');
// }
