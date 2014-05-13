google.setOnLoadCallback(loadData);

function drawChart(dataArray) {
   var data = google.visualization.arrayToDataTable(dataArray);
   var options = {
      enableInteractivity : true,
      title : 'Performance',
      height : 500,
      hAxis : {
         title : 'Time of test',
         minValue : 0.0,
         //maxValue : 0.2,
         gridlines : {
            count : 15
         },
         minorGridlines : {
            count : 11
         },
         //format : '#.#####'
      },
      vAxis : {
         title : 'Iterations per second',
         minValue : 0.0,
         // maxValue : 1200.0,
         gridlines : {
            count : 13
         },
         minorGridlines : {
            count : 1
         },
         format : "#.#"
      },
      curveType: 'function',
      linewidth : 1,
      pointSize : 0,
      /*series : {
         1 : {
            lineWidth : 0,
            pointSize : 1
         }
      }*/
   }
   var dashboard = new google.visualization.Dashboard(document.getElementById('dashboard_div'))
   var view = new google.visualization.DataView(data)
   view.setColumns([0, 5])
   //view.setColumns([0, 5, 7])

   var chart = new google.visualization.ChartWrapper({
      'chartType': 'LineChart',
      'dataTable': view,
      'containerId': 'chart_div',
      'options': options,
   });

   var slider = new google.visualization.ControlWrapper({
      'controlType': 'NumberRangeFilter',
      'containerId': 'filter_div',
      'options': {
         'filterColumnIndex': 1,
         'minValue': 0,
         'maxValue': 7200
      },
    // Explicitly positions the thumbs at position 3 and 8,
    // out of the possible range of 1 to 10.
    'state': {'lowValue': 0, 'highValue': 7200}
   });

   dashboard.bind(slider, chart);
   dashboard.draw(view)
}