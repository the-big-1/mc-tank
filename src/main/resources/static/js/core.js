/*
--------------------------------------------------------------
  Project: McTank
  File: Core JS File
--------------------------------------------------------------
 */
"use strict";
$(document).ready(function() {
    /* -- Menu js -- */
    $.sidebarMenu($('.vertical-menu'));
    $(function() {
        for (var a = window.location, abc = $(".vertical-menu a").filter(function() {
            return this.href === a;
        }).addClass("active").parent().addClass("active"); ;) {
            if (!abc.is("li")) break;
            abc = abc.parent().addClass("in").parent().addClass("active");
        }
    });
    /* -- Menu Hamburger -- */
    $(".ham").on("click", function(e) {
        e.preventDefault();
        $("body").toggleClass("toggle-menu");
        $(".ham img").toggle();
        var ham = $("#compact")[0];
        ham.innerText === "Compact Mode" ? $(ham).text("Enabled") : $(ham).text("Compact Mode");
    });
    /* -- Menu Topbar Hamburger -- */
    $(".topbar-toggle-hamburger").on("click", function(e) {
        e.preventDefault();
        $("body").toggleClass("topbar-toggle-menu");
        $(".topbar-toggle-hamburger img").toggle();
    });
    /* -- Menu Scrollbar -- */
    /*$('.vertical-menu').slimscroll({
        height: '700px',
        position: 'right',
        size: "7px",
        color: '#CFD8DC',
    });*/
    /* -- Media Size -- */
    function mediaSize() {
        if (window.matchMedia('(max-width: 767px)').matches) {
            $("body").removeClass("toggle-menu");
            $(".ham img.menu-hamburger-close").hide();
            $(".ham img.menu-hamburger-collapse").show();
        }
    }
    mediaSize();
    window.addEventListener('resize', mediaSize, false);
    /* -- Bootstrap Popover -- */
    $('[data-toggle="popover"]').popover();
    /* -- Bootstrap Tooltip -- */
    $('[data-toggle="tooltip"]').tooltip();
});