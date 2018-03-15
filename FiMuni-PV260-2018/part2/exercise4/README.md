Exercise 4
==========

We will start an sample service using Weaver utility, similarly to Exercise 3.

Copy `$DEMOS_HOME/FiMuni-PV260-2018/exercise4/max-speed.cfg` to `$WEAVER_HOME/projects` and run the following command in a separate terminal window:

`$WEAVER_HOME/bin/weaver.sh -c $WEAVER_HOME/projects/max-speed.cfg`

The Weaver will start a simple HTTP Echo service in 10 threads returning a response in 5 milliseconds.
But only when you are asking at speed slower than 500 requests per seconds. Anything above that
is immediately refused with an error response code. So the service is actually developed quite well
and resilient to overloading.

Copy `$DEMOS_HOME/FiMuni-PV260-2018/exercise4/max-speed.xml` to `$PERFCAKE_HOME/resources/scenarios` and run:

`$PERFCAKE_HOME/bin/perfcake.sh -s max-speed`

In the console, you can see the output and mainly the average response time.

Open the scenario file `$PERFCAKE_HOME/resources/scenarios/max-speed.xml` in your favorite text editor
and try changing the values on line 5. Upon changing the value and saving the file, run the performance
test again. You can see that upon increasing the required speed you are able to get much shorter response
time. This looks pretty cool. You service can handle a lot!

Let's verify we are still getting the correct response for values higher than 500. Try uncommenting line 9 and running the test
again. But for PerfCake not to fill you terminal with error messages, we will add one more command line parameter:

`$PERFCAKE_HOME/bin/perfcake.sh -s max-speed -Dperfcake.fail.fast=true`

The higher the value for speed, the sooner will your test end because the service was not returning
correct values. This is a sort of an emergency break. Comment the line 9 again and uncomment lines 27 and 30 through 35.

This is a more sophisticated validation that will tell us, how many times the service request actually failed.

Try to experiment with various values for the speed and running the scenario (you can ommit the additional parameter now).

The higher number you set, the more calls will fail. You can see that in the final validation report.

```
2016-10-12 21:55:43,246 INFO  {org.perfcake.validation.ValidationManager} === Validation Statistics ===
= Overall validated 10000 messages of which 9881 passed and 119 failed.
= Thread [PerfCake-1-sender-thread-1]: Totally validated 997 messages of which 988 passed and 9 failed.
= Thread [PerfCake-1-sender-thread-2]: Totally validated 991 messages of which 977 passed and 14 failed.
= Thread [PerfCake-1-sender-thread-5]: Totally validated 998 messages of which 988 passed and 10 failed.
= Thread [PerfCake-1-sender-thread-6]: Totally validated 1004 messages of which 989 passed and 15 failed.
= Thread [PerfCake-1-sender-thread-3]: Totally validated 995 messages of which 984 passed and 11 failed.
= Thread [PerfCake-1-sender-thread-10]: Totally validated 996 messages of which 987 passed and 9 failed.
= Thread [PerfCake-1-sender-thread-4]: Totally validated 1008 messages of which 997 passed and 11 failed.
= Thread [PerfCake-1-sender-thread-9]: Totally validated 1005 messages of which 993 passed and 12 failed.
= Thread [PerfCake-1-sender-thread-7]: Totally validated 1005 messages of which 989 passed and 16 failed.
= Thread [PerfCake-1-sender-thread-8]: Totally validated 1001 messages of which 989 passed and 12 failed.
=== End of statistics. ===
2016-10-12 21:55:43,246 INFO  {org.perfcake.validation.ValidationManager} The validator thread finished with the result: there were validation errors.
```

So obviously, a fast response does not automatically mean a good thing and a correct response.
Fortunately, PerfCake allows you to recognize this with a minimal impact on the test itself.

You can now stop the Weaver by pressing Ctrl+C.