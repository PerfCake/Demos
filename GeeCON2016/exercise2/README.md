Exercise 2
==========

We will use the application from Exercise 1 as a sample service.

So make sure your WildFly server is still running as described in Exercise 1 README.md.

Switch to `$DEMOS_HOME/GeeCON2016/exercise1/finished` and run `mvn wildfly:deploy`.

The sample application should be up and running. Its implementation can be seen in 
`$DEMOS_HOME/GeeCON2016/exercise1/finished/src/main/java/org/perfcake/examples/geecon/RestService.java`,
method `forwardService()`.

The implementation is really dummy and one should and developing a synchronized service method is not
a very clever idea. However, we are demonstrating the concept of aggregating messages and sending them
somewhere else.

Examine `http2.xml` performance test scenario and copy it to `$PERFCAKE_HOME/resources/scenarios`.

Run the scenario by:
 
`$PERFCAKE_HOME/bin/perfcake.sh -s http2`

Now you have created the following loop:
PerfCake -&gt; localhost:8080 -&gt; Sample service (aggregating the messages to bunches of three) -&gt;
localhost:9090 -&gt; PerfCake

Now you can switch the WildFly server off by Ctrl+C. We won't need it in the next Exercises.