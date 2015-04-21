scenario "rest-memory-with-message"
   run 5.m with ${thread.count:125}.threads
   generator "DefaultMessageGenerator"
   sender "HttpSender" target "http://localhost:8080" method "${http.method:POST}"
   reporter "MemoryUsageReporter" memoryLeakDetectionEnabled "true" usedMemoryTimeWindowSize 240 agentHostname "localhost" agentPort "8850"
      //destination "CsvDestination" every 1.s path "${perfcake.scenario}-memory-usage-${perfcake.run.timestamp}.csv"
      destination "ChartDestination" every 1.s name "Memory Usage (${perfcake.scenario})" group "memory" outputDir "${perfcake.scenario}-charts" yAxis "Memory Usage [MiB]" attributes "Used,Total"
      destination "ConsoleDestination" every 5.s
   message file:"message.txt" validate "msgValidator"
   validation enabled
      validator "RegExpValidator" id "msgValidator" pattern "Consumed.*"
end