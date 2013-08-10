function getHTTPRequestObject() {
    var xmlHttpRequest;
    /*@cc_on
     @if (@_jscript_version >= 5)
     try {
     xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
     } catch (exception1) {
     try {
     xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
     } catch (exception2) {
     xmlHttpRequest = false;
     }
     }
     @else
     xmlhttpRequest = false;
     @end @*/

    if (!xmlHttpRequest && typeof XMLHttpRequest != 'undefined') {
        try {
            xmlHttpRequest = new XMLHttpRequest();
        } catch (exception) {
            xmlHttpRequest = false;
        }
    }
    return xmlHttpRequest;
}

function parseJson(text) {
    return JSON.parse(text);
    ;
}

function getDataForChart() {

    var chart;
    var chartData = [];
    var httpRequester = getHTTPRequestObject();
    httpRequester.open("POST", "/charts", false);
    httpRequester.send();
    if (httpRequester.readyState == 4) {
        chart = parseJson(httpRequester.responseText);
    }

    for (var i = 0; i < chart.length; i++) {
        for (var key in chart[i]) {
            var dat = {};
            dat.category = key;
            dat.litres = chart[i][key];
            chartData.push(dat);
        }
    }

    return chartData;
}

var data;

function createData() {
    data = getDataForChart();
    AmCharts.ready(function () {
        // PIE CHART
        chart = new AmCharts.AmPieChart();
        chart.dataProvider = data;
        chart.titleField = "category";
        chart.valueField = "litres";
        chart.outlineColor = "#FFFFFF";
        chart.outlineAlpha = 0.8;
        chart.outlineThickness = 2;
        chart.fontSize = 11;
        chart.labelsEnabled = false;
        chart.startDuration = 0;
//        chart.sequencedAnimation = true;
//        chart.balloonText = "";
//        chart.removeLegend();
//        chart.balloon.enabled = false;


        legend = new AmCharts.AmLegend();
        legend.align = 'center';
        legend.markerType = 'circle';
        chart.addLegend(legend);


        // WRITE
        chart.write("chartdiv");
    });


}








