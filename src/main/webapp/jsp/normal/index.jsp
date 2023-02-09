<%--
  Created by IntelliJ IDEA.
  User: oceanzhao
  Date: 2023/1/16
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/common/head.jsp" %>
<%@include file="/jsp/normal/funclist.jsp" %>

<style>
    * {
        margin: 0;
        padding: 0;
    }

    li {
        list-style: none;
    }

    .big_box {
        position: relative;
        float: left;
        width: 1342px;
        height: 510px;
        /*margin: 100px auto;*/
        overflow: hidden;
    }

    .big_box ul {
        position: absolute;
        left: 0;
        width: 600%;
        height: 400px;
    }

    .big_box ul li {
        float: left;
    }

    .big_box ul li img {
        width: 1342px;
        height: 510px;
    }

    .leftBtn {
        position: absolute;
        top: 50%;
        left: 0;
        transform: translateY(-50%);
        background-color: rgba(255, 255, 255, .5);
        width: 30px;
        height: 30px;
        line-height: 30px;
        text-align: center;
    }

    .rightBtn {
        position: absolute;
        top: 50%;
        right: 0;
        transform: translateY(-50%);
        background-color: rgba(255, 255, 255, .5);
        width: 30px;
        height: 30px;
        line-height: 30px;
        text-align: center;
    }

    .big_box ol {
        position: absolute;
        bottom: 0;
        /* 这里别给ol限制宽度，让小圆圈的个数自动把ol撑大 */
        left: 50%;
        transform: translateX(-50%);
        height: 10%;
        line-height: 10%;
        text-align: center;
    }

    .big_box ol li {
        float: left;
        width: 20px;
        height: 20px;
        background-color: rgba(0, 0, 0, .5);
        border-radius: 50%;
        margin-left: 5px;
    }

    .big_box ol .current {
        background-color: rgb(211, 29, 29);
    }

    .leftBtn,
    .rightBtn,
    ol li {
        cursor: pointer;
    }
</style>
<div class="right">
    <div class="indexBanner">
        <div class="big_box">
            <!-- 轮播的图片 -->
            <ul>
                <li>
                    <img src="/images/banner1.jpg" alt="">
                </li>
                <li>
                    <img src="/images/banner2.jpg" alt="">
                </li>
                <li>
                    <img src="/images/banner3.jpg" alt="">
                </li>
                <li>
                    <img src="/images/banner4.jpg" alt="">
                </li>
            </ul>
            <!-- 左右按键 -->
            <div class="leftBtn">&lt</div>
            <div class="rightBtn">&gt</div>
            <!-- 小圆圈 -->
            <ol>
            </ol>
        </div>
        <script>
            var timer;
            var big_box = document.querySelector('.big_box');
            var ul = big_box.querySelector('ul');
            var ol = big_box.querySelector('ol');
            var lis_img = ul.querySelectorAll('li');
            var left = document.querySelector('.leftBtn');
            var right = document.querySelector('.rightBtn');
            var num = 0;  //记录要滑到第几张图片

            function animate(obj, target) {
                var timer1 = setInterval(function () {
                    var current = obj.offsetLeft;
                    var step = 10;
                    step = current > target ? -step : step;
                    // 下面要包括等于的情况，否则会发生抖动
                    if (Math.abs(current - target) <= Math.abs(step)) {
                        clearInterval(timer1);
                        obj.style.left = target + 'px';
                    }
                    else {
                        obj.style.left = current + step + 'px';
                    }
                }, 10)
            }

            //小圆圈样式改变
            function circlechange(circles, circle) {
                if (circle == lis_img.length) {
                    circle = 0;
                }
                //排他思想设置小圆圈样式
                //排他思想第一步:先把所有的小圆圈样式去掉
                for (var i = 0; i < circles.length; i++) {
                    circles[i].className = "";
                }
                //排他思想第二步:把当前图片对应的小圆圈设置样式
                circles[circle].className = "current";
            }

            //在页面刚加载进来就执行代码
            window.addEventListener('load', function () {
                //设置小圆点的个数
                for (var i = 0; i < lis_img.length; i++) {
                    var li = document.createElement('li');
                    ol.appendChild(li);
                    // 给小圆圈添加自定义属性
                    li.setAttribute('index', i);
                    //一开始第一个小圆圈就是被选中状态
                    if (i == 0) {
                        li.className = "current";
                    }
                    //给小圆圈添加点击处理事件
                    li.addEventListener('click', function () {
                        //排他思想实现小圆圈样式改变
                        for (var j = 0; j < ol.children.length; j++) {
                            ol.children[j].className = "";
                        }
                        this.className = "current";
                        //实现点击小圆圈后图片滑动
                        var index = this.getAttribute('index');
                        animate(ul, -index * big_box.offsetWidth);
                        // 在图片滑动的同时对应的小圆圈样式也要发生改变，所以调用animate函数同时调用circlechange函数
                        circlechange(circles, index);
                    })
                }
                //为了实现无缝衔接的切换图片，要把第一张图片克隆到最后一张图片的附近
                var circles = ol.querySelectorAll('li');
                // cloneNode函数若括号里面是true，则是深拷贝，false则是浅拷贝
                var li_img = ul.children[0].cloneNode(true);
                ul.appendChild(li_img);

                //点击右箭头向右滑动
                right.addEventListener('click', function () {
                    //下面if代码是实现向右滑动的无缝衔接，不懂的建议自己手动模拟一遍
                    if (num >= lis_img.length) {
                        num = 0;
                        //注意改变属性left的值后面一定要跟px，否则没有效果
                        ul.style.left = 0 + 'px';
                    }
                    num++;
                    animate(ul, -num * big_box.offsetWidth);
                    circlechange(circles, num);
                })
                //点击左箭头向左滑动
                left.addEventListener('click', function () {
                    //下面if代码是实现向左滑动的无缝衔接，不懂的建议自己手动模拟一遍
                    if (num <= 0) {
                        num = lis_img.length;
                        ul.style.left = -lis_img.length * big_box.offsetWidth + 'px';
                    }
                    num--;
                    animate(ul, -num * big_box.offsetWidth);
                    circlechange(circles, num);
                })

                //实现自动播放----因为自动播放的功能和向右滑动的功能一样，所以直接调用向右滑动的函数
                timer = setInterval(function () {
                    right.click();
                }, 2000)

                //鼠标放到盒子上停止自动播放
                big_box.addEventListener('mouseover', function () {
                    clearInterval(timer);
                })

                //鼠标离开自动播放
                big_box.addEventListener('mouseout', function () {
                    clearInterval(timer);
                    //在重新创建一个定时器时最好先清除一下定时器
                    timer = setInterval(function () {
                        right.click();
                    }, 3000)
                })
            })
        </script>
    </div>
</div>
<%@include file="/jsp/common/foot.jsp" %>