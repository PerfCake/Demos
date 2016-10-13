Exercise 3
==========

We will start an ill-behaved sample service using Weaver utility.

Copy `$DEMOS_HOME/GeeCON2016/exercise3/coord.cfg` to `$WEAVER_HOME/projects` and run the following command in a separate terminal window:

`$WEAVER_HOME/bin/weaver.sh -c $WEAVER_HOME/projects/coord.cfg`

The Weaver will start a simple HTTP Echo service in 100 threads in total. However, one of these
threads has a very long response time. Does our test find this?

Copy `$DEMOS_HOME/GeeCON2016/exercise3/coord.xml` to `$PERFCAKE_HOME/resources/scenarios` and run:

`$PERFCAKE_HOME/bin/perfcake.sh -s coord`

See the output in `$PERFCAKE_HOME/reports`.

You should see something similar to the first chart in `$DEMOS_HOME/GeeCON2016/exercise3/reports/coord-charts/index.html`

It seems pretty fine, doesn't it? Let's have a look...

Delete the result charts so that they do not get mixed with the new ones (remove the `$PERFCAKE_HOME/reports` directory completely).

And add one more reporter to `$PERFCAKE_HOME/resources/scenarios/coord.xml` so that we see how the threads are utilized:

```xml
   <reporter class="ClassifyingReporter">
      <property name="attribute" value="threadId" />
      <property name="prefix" value="thread_" />
      <destination class="ConsoleDestination">
         <period type="percentage" value="100"/>
         <property name="foreground" value="2" />
      </destination>
      <destination class="ChartDestination">
         <period type="percentage" value="100"/>
         <property name="yAxis" value="Thread usage [messages]"/>
         <property name="group" value="threads"/>
         <property name="name" value="Thread usage"/>
         <property name="attributes" value="thread*"/>
         <property name="type" value="bar"/>
         <property name="outputDir" value="reports/${perfcake.scenario}-charts"/>
      </destination>
   </reporter>     
```

Again, run:

`$PERFCAKE_HOME/bin/perfcake.sh -s coord`

See the output in `$PERFCAKE_HOME/reports`. And on the second chart, you can notice a missing bar. That is a thread
that actually did not do nothing. One of your 100 clients did not receive a response at all. 
All other received a reply in less than a couple of milliseconds. But do you want to loose that one customer?
What is the price of a customer for you?

But there is more serious issue. What if some more customers were affected in the same time? We just did not
have the opportunity to figure as the thread was blocked. We cannot continue adding threads in the peace
of original responses to figure out. That that would exhaust system resources. But we can call math for help.
 
We will add one more reporter to `$PERFCAKE_HOME/resources/scenarios/coord.xml`:

```xml
   <reporter class="ResponseTimeHistogramReporter">
      <property name="filter" value="true"/>
      <property name="maxExpectedValue" value="15000"/>
      <destination class="ChartDestination">
         <period type="percentage" value="100"/>
         <property name="yAxis" value="HDR Response time [ms]"/>
         <property name="group" value="hdr"/>
         <property name="name" value="HDR Response Time"/>
         <property name="attributes" value="perc*"/>
         <property name="type" value="bar"/>
         <property name="outputDir" value="reports/${perfcake.scenario}-charts"/>
      </destination>
   </reporter>
```

Delete the old reports (`rm -rf $PERFCAKE_HOME/reports`) and start over:

`$PERFCAKE_HOME/bin/perfcake.sh -s coord`

Now you should get a report similar to `$DEMOS_HOME/GeeCON2016/exercise3/reports/coord-charts/index.html`.

What you can see in the last chart is a High Definition Histogram. The algorithm was developed by Gil Tene from Azul systems.
And it is a very smart way of demonstrating a real impact of a performance issue on your customers.
What you see in this chart is likely to happen if your customers keep coming at the peace they are usually
used to. Customers are not limited like threads. So they won't block. And this is the impact.

So from something that looked quite fine at the beginning, we got to something really terrible.

You can now stop the Weaver by pressing Ctrl+C.