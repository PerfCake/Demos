<?xml version="1.0" encoding="utf-8"?>
scenario "jms"
   run 60.s with ${threads:100}.threads
   generator "DefaultMessageGenerator"
   sender "RequestResponseJmsSender" target "jms/queue/PerfCakeQueue" responseTarget "jms/queue/PerfCakeQueue" receivingTimeout "10000" receiveAttempts "1" connectionFactory "jms/RemoteConnectionFactory" username "guest" password "guestp.1" responseUsername "guest" responsePassword "guestp.1" jndiContextFactory "org.jboss.naming.remote.client.InitialContextFactory" jndiUrl "http-remoting://${server.host:localhost}:8080" jndiSecurityPrincipal "guest" jndiSecurityCredentials "guestp.1" responseJndiSecurityPrincipal "guest" responseJndiSecurityCredentials "guestp.1"/>
   reporter "ThroughputStatsReporter"
      destination "ConsoleDestination" every 10.s
      destination "ChartDestination" every 1.s yAxis "Response Time [ms]" group "${perfcake.scenario}_response_time" name "Response Time (${threads:100} threads)" attributes "Minimum,Maximum,Average,Result" outputDir "${perfcake.scenario}-charts"/>
   <reporting>
   
      <reporter class="ThroughputStatsReporter">
         <destination class="ConsoleDestination">
            <period type="time "10000"/>
         </destination>
         <destination class="CsvDestination">
            <period type="time "500"/>
            <property name="path "${perfcake.scenario}-${threads:100}t-throughput-stats-${perfcake.run.nice.timestamp}.csv"/>
         </destination>
         <destination class="ChartDestination">
            <period type="time "1000"/>
            <property name="yAxis "Throughput [iterations/s]"/>
            <property name="group "${perfcake.scenario}_troughput"/>
            <property name="name "Throughput (${threads:100} threads)"/>
            <property name="attributes "Minimum,Maximum,Average,Result"/>
            <property name="outputDir "${perfcake.scenario}-charts"/>
         </destination>
      </reporter>
      <reporter class="MemoryUsageReporter">
         <property name="agentHostname "${server.host:localhost}"/>
         <property name="agentPort "8850"/>
         <property name="performGcOnMemoryUsage "true"/>
         <destination class="ConsoleDestination">
            <period type="time "10000"/>
         </destination>
         <destination class="CsvDestination">
            <period type="time "1000"/>
            <property name="path "${perfcake.scenario}-${threads:100}t-memory-usage-${perfcake.run.nice.timestamp}.csv"/>
         </destination>
         <destination class="ChartDestination">
            <period type="time "1000"/>
            <property name="yAxis "Memory [MiB]"/>
            <property name="group "${perfcake.scenario}_memory"/>
            <property name="name "Memory (${threads:100} threads)"/>
            <property name="attributes "Used,Total,Max"/>
            <property name="outputDir "${perfcake.scenario}-charts"/>
         </destination>
      </reporter>
   </reporting>
   <messages>
      <message content="I'm a fish!"/>
   </messages>
</scenario>
