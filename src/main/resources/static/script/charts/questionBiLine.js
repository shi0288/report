/**
 * Created by root on 17-5-16.
 */
var questionBiChart = echarts.init(document.getElementById('charts_question'),
		'shine');
var option = {
	title : {
		text : ''
	},
	tooltip : {
		trigger : 'axis'
	},

	toolbox : {
		show : true,
		feature : {
			/*magicType : {
				type : [ 'line', 'bar' ]
			},
			restore : {},*/
			saveAsImage : {}
		}
	},
	xAxis : {
		type : 'category',
		boundaryGap : false
	},
	yAxis : {
		type : 'value'
	}
	
};
questionBiChart.clear();
questionBiChart.setOption(option,{notMerge:false});
$(function() {
	$.localAjax('/user/biData/questionCharts', {}, function(res) {
		console.log(res);
		var arr = new Array();
		var sourceArr = new Array();
		$.each(res.data.values, function(index, item) {
			arr.push({
				name : index,
				type : 'line',
				data : item,
				markPoint : {
					data : [ {
						type : 'max',
						name : '最大值'
					}, {
						type : 'min',
						name : '最小值'
					} ]
				},
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			});
			sourceArr.push(index);
		});
		questionBiChart.setOption({
			legend : {
				data : sourceArr
			},
			xAxis : [ {
				data : res.data.times
			} ],
			series : arr
			
		});
	})

	$('.btn-selectBiCharts').on('click', function() {
		questionBiChart.clear();
		questionBiChart.setOption(option,{notMerge:false});
		var startDate = document.getElementById("startDate").value;
		var endDate = document.getElementById("endDate").value;
		var source = document.getElementById("source").value;
		var cond = {};
		cond.startDate = startDate;
		cond.endDate = endDate;
		cond.source = source;
		$.localAjax('/user/biData/questionCharts', cond, function (res) {
	        console.log(res);
	        var arr = new Array();
	        var sourceArr = new Array();
	        $.each(res.data.values, function (index, item) {
	            arr.push(
	                {
	                    name:index,
	                    type:'line',
	                    data:item,
	                    markPoint: {
	                        data: [
	                            {type: 'max', name: '最大值'},
	                            {type: 'min', name: '最小值'}
	                        ]
	                    },
	                    markLine: {
	                        data: [
	                            {type: 'average', name: '平均值'}
	                        ]
	                    }
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
	            series: arr
	            
	        },false);
	    })
	})
})