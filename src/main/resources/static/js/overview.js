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
    $.renderPieChart = function (result) {
        var resultTwo = [
            Object.values(result.amountsOnMcDrive).reduce((a,b) => a + b, 0),
            Object.values(result.amountsOnMcSit).reduce((a,b) => a + b, 0),
            Object.values(result.amountsOnMcWash).reduce((a,b) => a + b, 0),
            Object.values(result.amountsOnMcZapf).reduce((a,b) => a + b, 0)
        ];
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
            series: resultTwo,
            labels: ['McDrive', 'McSit', 'McWash', "McZapf"],
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
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/orders/stacked",
            success: function (result) {
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
                           name: 'McDrive',
                           data: Object.values(result.amountsOnMcDrive)
                        },
                        {
                            name: 'McSit',
                            data: Object.values(result.amountsOnMcSit),
                        },
                        {
                            name: 'McWash',
                            data: Object.values(result.amountsOnMcWash)
                        },
                        {
                            name: 'McZapf',
                            data: Object.values(result.amountsOnMcZapf)
                        }
                    ],
                    xaxis: {
                        type: 'datetime',
                        categories: Object.keys(result.amountsOnMcZapf),
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
                $.renderAreaChart(result);
                $.renderPieChart(result);
            }
        });
    };
    $.renderAreaChart = function (result) {
        var resultOne = [
            Object.values(result.amountsOnMcDrive),
            Object.values(result.amountsOnMcSit),
            Object.values(result.amountsOnMcWash),
            Object.values(result.amountsOnMcZapf)
        ];
        var res = [];
        res.push(0);
        for (var i = 0; i < resultOne[0].length; i++){
            res.push(resultOne[0][i] + resultOne[1][i] + resultOne[2][i] + resultOne[3][i]);
        }
        res.push(0);
        var options = {
            chart: {
                type: "area",
                height: 155,
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
                name: "Total orders",
                data: res
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

    /* Apex Stacked Bar Chart */
    $.renderStackedChart();

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