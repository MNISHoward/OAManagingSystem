$(function () {
	$('#echarts-main').height(parseInt($(window).height()-73));
	var myChart = echarts.init(document.getElementById('echarts-main'));

	
	 var colors = ['#5793f3', '#d14a61', '#675bba'];
	var paramIn = {
			service : 'assetTypeService',
			method : 'findAllAssetData',
			param : null,
			success : function (data) {
				var name=[], leaddata=[], surplusdata =[], totaldata = [];
				$(data.param.vos).each(function (index, value) {
					name.push(value.name);
					leaddata.push(value.lendQuantity);
					surplusdata.push(value.surplusQuantity);
					totaldata.push(value.totalQuantity);
				})
				var option = {
			     color: colors,

			     tooltip: {
			         trigger: 'axis',
			         axisPointer: {
			             type: 'cross'
			         }
			     },
			     grid: {
			         right: '20%'
			     },
			     toolbox: {
			         feature: {
			             dataView: {show: true, readOnly: false},
			             restore: {show: true},
			             saveAsImage: {show: true}
			         }
			     },
			     legend: {
			         data:['剩余量','借出量','资产总量']
			     },
			     xAxis: [
			         {
			             type: 'category',
			             axisTick: {
			                 alignWithLabel: true
			             },
			             data: name,
			         }
			     ],
			     yAxis: [
			        
			         {
			             type: 'value',
			             name: '资产总量',
			             min: 0,
			             max: 100,
			             position: 'left',
			             axisLine: {
			                 lineStyle: {
			                     color: colors[2]
			                 }
			             },
			             axisLabel: {
			                 formatter: '{value} 个'
			             }
			         }
			     ],
			     series: [
			         {
			             name:'剩余量',
			             type:'bar',
			             data:surplusdata
			         },
			         {
			             name:'借出量',
			             type:'bar',
			             data:leaddata
			         },
			         {
			             name:'资产总量',
			             type:'line',
			             data:totaldata
			         }
			     ]
			 };


			 myChart.setOption(option);
			}
		};
	ajax.query(paramIn);
})
	 
//@ sourceURL=asset.js