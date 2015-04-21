scenario "rest-troughput-without-message"
   run 3.m with ${thread.count:125}.threads
   generator "DefaultMessageGenerator"
   sender "HttpSender" target "http://localhost:8080" method "${http.method:GET}"
   reporter "WarmUpReporter"
   reporter "ThroughputStatsReporter" minimumEnabled "false" maximumEnabled "false"
      //destination "CsvDestination" every 1.s path "${perfcake.scenario}-throughput-stats-${perfcake.run.timestamp}.csv"
      destination "ChartDestination" every 1.s name "Response Time (${perfcake.scenario})" group "response_time" outputDir "${perfcake.scenario}-charts" yAxis "Response Time [ms]" attributes "Result,Average"
      destination "ConsoleDestination" every 5.s
end