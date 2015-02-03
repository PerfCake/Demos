function drawConcatChart(dataArray, chartDiv, columns, xAxis, yAxis, chartName) {
   var data = new google.visualization.DataTable();
   data.addColumn('string', dataArray[0][0]);
   for (var i = 1; i < dataArray[0].length; i++) {
      data.addColumn('number', dataArray[0][i]);
   }

   data.addRows(dataArray.slice(1));
   var options = {
      enableInteractivity : true,
      title : chartName,
      height : 500,
      hAxis : {
         title : xAxis,
         minValue : 0.0,
         gridlines : {
            count : 15
         },
         minorGridlines : {
            count : 11
         },
         //format : '#.#####'
      },
      vAxis : {
         title : yAxis,
         minValue : 0.0,
         gridlines : {
            count : 13
         },
         minorGridlines : {
            count : 1
         },
         format : "#.#"
      },
      linewidth : 1,
      pointSize : 0,
      interpolateNulls: true,
   }
   var view = new google.visualization.DataView(data)
   view.setColumns(columns)

   var chart = new google.visualization.ChartWrapper({
      'chartType': 'LineChart',
      'dataTable': view,
      'containerId': chartDiv,
      'options': options,
   });

   chart.draw();
}