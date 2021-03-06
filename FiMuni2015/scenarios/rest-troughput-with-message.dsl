scenario "rest-troughput-with-message"
   run 3.m with ${thread.count:125}.threads
   generator "DefaultMessageGenerator"
   sender "HttpSender" target "http://localhost:8080" method "${http.method:POST}"
   reporter "WarmUpReporter"
   reporter "ThroughputStatsReporter" minimumEnabled "false" maximumEnabled "false"
      //destination "CsvDestination" every 1.s path "${perfcake.scenario}-throughput-stats-${perfcake.run.timestamp}.csv"
      destination "ChartDestination" every 1.s name "Throughput" group "throughput" outputDir "rest-troughput-with-message-charts" yAxis "Throughput [iterations/s]" attributes "Result,Average"
      destination "ConsoleDestination" every 5.s
   message file:"message.txt" validate "msgValidator"
   validation enabled
      validator "RegExpValidator" id "msgValidator" pattern "Consumed.*"
end