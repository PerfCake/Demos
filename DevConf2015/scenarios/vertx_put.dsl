scenario "vertx-put"
   run 3.m with 25.threads
   generator "DefaultMessageGenerator"
   sender "HttpSender" target "http://localhost:8080" method "PUT"
   reporter "ThroughputStatsReporter" minimumEnabled "false" maximumEnabled "false"
      destination "ConsoleDestination" every 5.s
      destination "ChartDestination" every 1.s yAxis "Throughput [iterations/s]" group "${perfcake.scenario}_throughput" name "Throughput (GC=${useGc:false})" attributes "Average"
   reporter "MemoryUsageReporter" memoryLeakDetectionEnabled "true" usedMemoryTimeWindowSize 240 memoryDumpOnLeak "true" performGcOnMemoryUsage "${useGc:false}"
      destination "ConsoleDestination" every 5.s
      destination "ChartDestination" every 1.s yAxis "Memory Usage [MiB]" group "${perfcake.scenario}_memory" name "Memory Usage (GC=${useGc:false})" attributes "Used,Total,Max,UsedTrend"
   message file:"message.txt" validate "putValidator"
   validation enabled
      validator "RegExpValidator" id "putValidator" pattern "Consumed.*"
end