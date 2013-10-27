function getDataForChartByJQ(month, year){
    var chartData = [];
    $.ajax({
        async: false,
        data: 'cq=true'+'&month='+month+'&year='+year,
        url: '/charts',
        type: 'POST',
        dataType: 'json',
        success: function(data, textStatus){
            if(textStatus == 'success'){
                chartData = data;
            }
        }
    });
    return chartData;
}

function createChart(dataForChart){
    $(function () {
        $('#chartdiv').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: true
            },
            title: {
                text: 'Траты за месяц по категориям'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        format: '<b>{point.name}</b>: {point.y:.2f} руб'
                    }
                }
            },
            series: [{
                type: 'pie',
                name: 'Траты',
                data: dataForChart
            }]
        });
    });
}

function createDataPicker(month, year){
    $('.date-picker').datepicker( {
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
        dateFormat: 'MM yy',
        onClose: function(dateText, inst) {
            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
            $(this).datepicker('setDate', new Date(year, month, 1));
            var newData = getDataForChartByJQ(month,year);
            createChart(newData);
        }
    });
    $('#startDate').datepicker('setDate',new Date(year, month, 1));
}

$(document).ready(function(){
        var month = (new Date()).getMonth();
        var year = (new Date()).getFullYear();
        var chartData = getDataForChartByJQ(month, year);
        createDataPicker(month, year);
        createChart(chartData);
    }
);


