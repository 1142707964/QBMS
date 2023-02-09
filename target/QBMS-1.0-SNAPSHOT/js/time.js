//动态获取时间
function now(){
    var time = new Date();
    var str= "";
    var div = document.getElementById("time");
//    console.log(time);
    var year = time.getFullYear();
    var mon = time.getMonth()+1;
    var day = time.getDate();
    var h = time.getHours();
    var m = time.getMinutes();
    var s = time.getSeconds();
    var week = time.getDay();
    switch (week){
        case 0:week="日";
            break;
        case 1:week="一";
            break;
        case 2:week="二";
            break;
        case 3:week="三";
            break;
        case 4:week="四";
            break;
        case 5:week="五";
            break;
        case 6:week="六";
            break;
    }
    str = year+"年"+toTwo(mon)+"月"+toTwo(day)+"日"+"&nbsp;"+toTwo(h)+":"+toTwo(m)+":"+toTwo(s)+"&nbsp;"+"星期"+week;
    div.innerHTML = str;
}
function toTwo(n){
    if(n <= 9){
        return n = "0" + n;
    }else{
        return n = "" + n;
    }
}
now();
setInterval(now,1000);

