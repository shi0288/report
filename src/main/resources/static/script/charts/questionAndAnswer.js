/**
 * Created by root on 17-5-16.
 */
var myChart = echarts.init(document.getElementById('questionAndAnswer'), 'shine');
var option = {
    baseOption: {
        title: {
            text: '问题回复来源分布图',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            data: []
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                },
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        series: [
            {
                type:'pie',
                roseType : 'radius',

                data:[]
            },
            {
                type:'pie',
                roseType : 'radius',

                data:[]
            }

        ]
    },
    media: [
        {
            option: {
                legend: {
                    left: 'center',
                    bottom: 0,
                    orient: 'horizontal'
                },
                series: [
                    {
                        radius: [30, '50%'],
                        center: ['25%', '50%']
                    },
                    {
                        radius: [30, '50%'],
                        center: ['75%', '50%']
                    }
                ]
            }
        },
        {
            query: {
                maxWidth: 500
            },
            option: {
                legend: {
                    left: 0,
                    top: '10%',
                    orient: 'vertical'
                },
                series: [
                    {
                        radius: [20, '50%'],
                        center: ['50%', '30%']
                    },
                    {
                        radius: [30, '50%'],
                        center: ['50%', '75%']
                    }


                ]
            }
        }
    ]
};
myChart.setOption(option);
$(function () {
    $.localAjax('/user/chartsData/questionAndAnswer', {}, function (res) {
        var arrQuestion = new Array();
        var arrAnswer = new Array();
        $.each(res.data.types, function (index, item) {
            arrQuestion.push({name: item, value: res.data.quesInfo[item]});
            arrAnswer.push({name: item, value: res.data.answerInfo[item]});
        });
        myChart.setOption({
            legend: {
                data: res.data.types
            },
            series: [
                {
                    name: '问题分布',
                    data: arrQuestion
                },
                {
                    name: '回复分布',
                    data: arrAnswer
                }
            ]
        });


    })
})