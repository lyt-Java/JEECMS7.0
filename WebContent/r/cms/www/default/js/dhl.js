/**
 * @fileOverview
 * js for maxthon website
 * @author niezhan <niezhan@maxthon.net>
 * @version 2011-03-01
 */




 $(document).ready(function () {

/**
 * 登陆框弹出
 */
    $("#header-login").click(
        function () {
            $("#login-box").slideToggle("fast");
            return false;
        }
    );

    $("#login-box").click(
        function (event) {
            event.stopPropagation();
        }
    )

    $("body").click(
        function () {
            if("#login-box:visible"){
                $("#login-box").slideUp("fast");
            }
        }
    )

    $("#login-box-close").click(
        function () {
            $("#login-box").slideUp("fast");
        }
    )





/**
 * 语言选择框弹出
 */
    $("#language").hover(
        function () {
            $(this).find(".all-languages").toggle();
        }
    );




/**
 * 傲游服务弹出
 */
    $("#max-service").hover(
        function () {
            $(".all-service").toggle();
        }
    );



/**
 * 导航栏当前页面高亮显示
 */
    $("#index-body li.menu-list-index").addClass("current");
    $("#company-body li.menu-list-company").addClass("current");
    $("#mx2-body li.menu-list-mx2").addClass("current");
    $("#mx3-body li.menu-list-mx3").addClass("current");
    $("#mm-body li.menu-list-mm").addClass("current");
	$("#lab-body li.menu-list-lab").addClass("current");



/**
 * 下拉菜单
 */
    $(".menu ul.menu-list > li").hover(
        function () {
            $(this).find("> a").addClass("hover");
            $(this).find(".menu-sublist").slideDown(100);
        },
        function () {
            $(this).find("> a").removeClass("hover");
            $(this).find(".menu-sublist").slideUp(100);
        }
    );




/**
 * 首页slide轮换
 * currentIndex: 当前slide的index
 * slideCount: slide的数量
 */
    var currentIndex = 1;
    var slideCount = 3;

/**
 * 显示选定slide
 */
    function getSlideShow() {
        $("#slide .slide-box").hide();
        $("#slide #slide-box-" + currentIndex).fadeIn();
        $("#slide-show a.slide-show-content").removeClass("current");
        $("#slide-show a.slide-show-content:eq(" + (currentIndex - 1) + ")").addClass("current");
/*         if(currentIndex === 4){
            document.getElementById("slide-show-1").style.display = "none";
            document.getElementById("slide-show-4").style.display = "";
        }
        if(currentIndex === 1){
            document.getElementById("slide-show-4").style.display = "none";
            document.getElementById("slide-show-1").style.display = "";
        } */
    }


/**
 * 控制上一张还是下一张，到达极值后，循环显示
 * @param {string} direction 轮换方向 默认为next
 */
    function getNextSlideShow(direction) {
        if (!direction || direction == "next") {
            currentIndex++;
            if (currentIndex >= (slideCount + 1)) {
                currentIndex = 1;
            }
            
        }
        else if (direction == "previous") {
            currentIndex--;
            if (currentIndex <= 0) {
                currentIndex = slideCount;
            }
        }
        getSlideShow();
    }

    $("#slide-show a.slide-show-content").click(
        function () {
            currentIndex = parseInt($(this).attr("id").replace("slide-show-",""));
            getSlideShow();
            return false;
        }
    );

    $("#slide-show a#slide-show-previous").click(
        function () {
            getNextSlideShow("previous");
            return false;
        }
    );

    $("#slide-show a#slide-show-next").click(
        function () {
            getNextSlideShow();
            return false;
        }
    );

    //setInterval(getNextSlideShow, 6000);





/**
 * 首页update-new轮换
 */
    var currentNewsIndex = 1;
    var newsCount = 2;
    var startUpdateNews;

    function hideUpdateNews() {
        $("p#update-news-" + currentNewsIndex).show().css("top", "2").css("opacity", "1").animate({
                top: '15',
                opacity: 0
            }, 400);
    }

    function showUpdateNews() {
        $("p#update-news-" + currentNewsIndex).show().css("top", "-8px").css("opacity", "0").animate({
                top: '2',
                opacity: 1
            }, 400);
    }

    function getNextUpdateNews(direction) {
        hideUpdateNews();

        if (!direction || direction == "next") {
            currentNewsIndex++;
            if (currentNewsIndex >= (newsCount + 1)) {
                currentNewsIndex = 1;
            }
        }
        else if (direction == "previous") {
            currentNewsIndex--;
            if (currentNewsIndex <= 0) {
                currentNewsIndex = newsCount;
            }
        }

        showUpdateNews();
    }
    
    startUpdateNews = setInterval(getNextUpdateNews, 6000);

    $("a#update-news-control-up").click(
        function () {
            getNextUpdateNews("previous");
            return false;
        }
    );

    $("a#update-news-control-down").click(
        function () {
            getNextUpdateNews();
            return false;
        }
    );
    
    $("#update-news-control").mouseenter(
        function () {
            clearInterval(startUpdateNews);
        }
    ).mouseleave(
        function () {    
            startUpdateNews = setInterval(getNextUpdateNews, 6000);
        }
    )





/**
 * 首页奖项弹出
 */
    $("#show-pop-awards").hover(
        function () {
            $("#pop-awards").show();
        },
        function () {
            $("#pop-awards").hide();
        }
    ).click(
        function () {
            return false;
        }
    );




/**
 * MX2、MX3授权下载链接 展开效果
 */
    $(".link-download-more").toggle(
        function () {
            $(this).css("background-image", "url(/images/icon-link-download-less.gif)");
            $(".link-download").animate({
                height: '70'
            }, 300);
            $("p.link-download-all-links").toggle();
            return false;
        },
        function () {
            $(this).css("background-image", "url(/images/icon-link-download-more.gif)");
            $(".link-download").animate({
                height: '45'
            }, 300);
            $("p.link-download-all-links").toggle();
            return false;
        }
    )


/**
 * MX3、MM弹出遮罩层以及视频
 */
    function showVideo(videosrc) {
        $("body").append('<div id="block"></div><div id="video-player"><embed src="'
            + videosrc
            + '" quality="high" width="480" height="400" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash"></embed><a href="#" class="video-player-close">关闭</a></div>');
        $("#block").css("height", $(document).height() + "px").css("opacity", "0.5").show();
        $("#video-player").css("top", ($(window).height() - 400) / 2 + "px")
            .css("left", ($(window).width() - 480) / 2 + "px");
    }

    $("a#mx-demovideo").click(
        function () {
            var videosrc = $(this).attr("href");
            showVideo(videosrc);
            return false;
        }
    )

    $("a.video-player-close").live("click", function () {
            $('#block').remove();
            $('#video-player').remove();
        }
    )

    $(".awards-says").hover(
        function () {
            var saysTop = $(this).offset().top - 481;
            var saysQuote = $(this).find("q").html();
            if (saysTop > "50") {
                $("#awards-says-Gary-Schare").css("top", "-165px");
            }
            else {
                $("#awards-says-Gary-Schare").css("top", "0");
            }

            var saysTopAfter = $(this).offset().top - 481;

            $("#sidebar-awards-quote").css("top", saysTopAfter).html(saysQuote);
        }
    )


    $("#love-diandian-more").click(
        function () {
            var moreText = $(this).html();
            $("#love-diandian-more-detail").slideToggle();
            if (moreText == "展开查看所有内容") {
                $(this).html("收起当前查看内容");
            }
            else {
                $(this).html("展开查看所有内容");
            }
            return false;
        }
    )

    function formatNum(num) {
        var arrNumber = num.toString().split("").reverse();
        var strNumber = [];
        var len = arrNumber.length - 1;
        for (var i=0;i<len;i++) {
            strNumber.unshift(arrNumber[i]);
            if(i%3 == 2) strNumber.unshift(",");
        }
        strNumber.unshift(arrNumber[len]);
        strNumber = strNumber.join("");
        return strNumber;
    }

    function downloadCount() {
        $.get('/api/inout.php' + '?' + Math.random(), function(data) {
            counter = formatNum(parseInt(data));
            $('#download-mx3-count').add('#download-mx2-count').html(counter);
        });
    }
    // downloadCount();
    // setInterval(downloadCount,1000);
    
    
    
    $("#love-donation-btn").click(
        function () {
            return false;
        }
    )

    
    
    
/**
 * 首页滚动新闻
 */
    function newsScroll(id) {
        var box = $("#"+id+ " span");
        $("<span>").html(box.html()).appendTo(box.parent());
        var w = box.width();
        var i = 0;
        var move = function () {
            box[0].style.marginLeft = -i+"px";
            i++;
            if (i === w) {
                i = 0;
            }
        };
        var auto = setInterval(move, 40);
        
        $("#index-news span a").hover(function (){
            clearInterval(auto);
        }, function (){
            auto = setInterval(move, 40);
        })
        
    }
    
    if (document.getElementById("index-news")){
        newsScroll("index-news");
    }
})

