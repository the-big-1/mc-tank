/*
-------------------------------------------
    : Dashboard Overview js :
-------------------------------------------
*/
"use strict";
$(document).ready(function () {
    $.render = function (chart) {
        // noinspection JSUnresolvedFunction -- resolved from Apex.min.js
        chart.render();
    };
    $.renderPieChart = function () {
        var options = {
            chart: {
                type: 'donut',
                width: 240
            },
            plotOptions: {
                pie: {
                    donut: {
                        size: "85%"
                    }
                }
            },
            dataLabels: {
                enabled: false
            },
            colors: ['#0080ff', '#18d26b', '#d4d8de', '#cf0800'],
            series: [30, 30, 45, 25],
            labels: ['McSit', 'McTank', 'McWash', "McZapf"],
            legend: {
                show: true,
                position: 'bottom'
            },
            responsive: [{
                breakpoint: 420,
                options: {
                    chart: {
                        width: 220
                    }
                }
            }]
        };
        var chart = new ApexCharts(
            document.querySelector("#apex-pie-chart"),
            options
        );
        $.render(chart);
    };
    $.renderStackedChart = function () {
        var options = {
            chart: {
                height: 260,
                type: 'bar',
                stacked: true,
                toolbar: {
                    show: false
                },
                zoom: {
                    enabled: false
                }
            },
            plotOptions: {
                bar: {
                    horizontal: false,
                    columnWidth: '30%'
                },
            },
            colors: ['#0080ff', '#18d26b', '#d4d8de', '#cf0800'],
            series: [
                {
                    name: 'McSit',
                    data: [5, 7, 8, 6, 7, 5, 6, 6, 7, 4, 8]
                },
                {
                    name: 'McTank',
                    data: [5, 4, 4, 5, 3, 4, 3, 5, 4, 6, 2]
                },
                {
                    name: 'McWash',
                    data: [5, 4, 4, 5, 3, 4, 3, 5, 4, 6, 2]
                },
                {
                    name: 'McZapf',
                    data: [5, 4, 4, 5, 3, 4, 3, 5, 4, 6, 2]
                }

            ],
            xaxis: {
                type: 'datetime',
                categories: ['01/01/2011 GMT', '01/02/2011 GMT', '01/03/2011 GMT', '01/04/2011 GMT', '01/05/2011 GMT', '01/06/2011 GMT', '01/07/2011 GMT', '01/08/2011 GMT', '01/09/2011 GMT', '01/10/2011 GMT'],
                axisBorder: {
                    show: true,
                    color: 'rgba(0,0,0,0.05)'
                },
                axisTicks: {
                    show: true,
                    color: 'rgba(0,0,0,0.05)'
                }
            },
            grid: {
                row: {
                    colors: ['transparent', 'transparent'], opacity: .2
                },
                borderColor: 'rgba(0,0,0,0.05)'
            },
            legend: {
                show: false
            },
            fill: {
                opacity: 1
            }
        };
        var chart = new ApexCharts(
            document.querySelector("#apex-stacked-bar-chart"),
            options
        );
        $.render(chart);
    };
    $.renderAreaChart = function () {
        var options = {
            chart: {
                type: "area",
                height: 135,
                sparkline: {
                    enabled: true
                }
            }
            ,
            stroke: {
                curve: "smooth",
                width: 3
            }
            ,
            fill: {
                opacity: .05
            }
            ,
            series: [{
                data: [15, 8, 12, 6, 10, 16, 5, 11, 6]
            }
            ],
            yaxis: {
                min: 0
            }
            ,
            colors: ["#0080ff"],
            grid: {
                row: {
                    colors: ['transparent', 'transparent'], opacity: .2
                },
                borderColor: 'transparent'
            },
            tooltip: {
                x: {
                    format: 'dd/MM/yy HH:mm'
                }
            }
        };
        var chart = new ApexCharts(
            document.querySelector("#apex-area-chart"),
            options
        );
        $.render(chart);
    };

    /* -- Apex Pie Chart -- */
    $.renderPieChart();
    /* Apex Stacked Bar Chart */
    $.renderStackedChart();
    /* -----  Apex Area Chart ----- */
    $.renderAreaChart();

    /* Best Product Slider */
    $('.best-product-slider').slick({
        arrows: true,
        dots: false,
        infinite: true,
        adaptiveHeight: true,
        slidesToShow: 1,
        slidesToScroll: 1,
        prevArrow: '<i class="feather icon-chevron-left"></i>',
        nextArrow: '<i class="feather icon-chevron-right"></i>'
    });
});