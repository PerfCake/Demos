scenario "echo"
   run 20.s with 20.threads
   generator "DefaultMessageGenerator"
   sender "PlainSocketSender" target "${server.host:localhost}:${server.port:12321}"
   reporter "ThroughputStatsReporter"
      destination "ConsoleDestination" every 1.s
      destination "ChartDestination" every 500.ms yAxis "Throughput" group "perf" name "Echo in ${language:unknown}" attributes "Minimum, Maximum, Average, Result"
   message content:"I'm a fish!"
end