/**
 * Created by root on 17-5-16.
 */
var questionBiChart = echarts.init(document.getElementById('charts_question'), 'shine');
var option = {
    title: {
        text: '问题堆叠图'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross',
            label: {
                backgroundColor: '#6a7985'
            }
        },
        formatter: function (param) {
            var arr = new Array();
            arr.push(param[0].name + '<br>');
            var total = 0;
            for (var i = param.length; i > 0; i--) {
                total += param[i - 1].value;
                arr.push(param[i - 1].marker + param[i - 1].seriesName + ':' + param[i - 1].value + '<br/>');
            }
            arr.push('总计：' + total + '<br>');
            return arr.join('');
        }
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            boundaryGap: false
        }
    ],
    yAxis: [
        {
            type: 'value'
        }
    ]
};

questionBiChart.setOption(option);
$(function () {
    $.localAjax('/user/chartsData/questionBi', {}, function (res) {
        console.log(res);
        var arrQuestion = new Array();
        var sourceArr = new Array();
        $.each(res.data.values, function (index, item) {
            arrQuestion.push(
                {
                    name: index,
                    type: 'line',
                    stack: '总量',
                    areaStyle: {normal: {}},
                    data: item
                }
            );
            sourceArr.push(index);
        });
        questionBiChart.setOption({
            legend: {
                data: sourceArr
            },
            xAxis: [
                {
                    data: res.data.times
                }
            ],
            series: arrQuestion
        });

    })
})