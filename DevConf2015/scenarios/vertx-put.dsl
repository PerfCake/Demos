scenario "vertx-put"
   run 3.m with 25.threads
   generator "DefaultMessageGenerator"
   sender "HttpSender" target "http://localhost:8080" method "PUT"
   reporter "WarmUpReporter"
   reporter "ThroughputStatsReporter" minimumEnabled "false" maximumEnabled "false"
      destination "CsvDestination" every 1.s path "${perfcake.scenario}-throughput-stats-${perfcake.run.timestamp}.csv"
      destination "ConsoleDestination" every 1.s
   reporter "MemoryUsageReporter" memoryLeakDetectionEnabled "true" usedMemoryTimeWindowSize 240
      destination "CsvDestination" every 1.s path "${perfcake.scenario}-memory-usage-${perfcake.run.timestamp}.csv"
      destination "ConsoleDestination" every 1.s
   message file:"message.txt" validate "putValidator"
   validation enabled
      validator "RegExpValidator" id "putValidator" pattern "Consumed.*"
end