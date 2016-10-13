GeeCON 2016 Live coding session
===============================

Instructions status: DRAFT - will be marked as final no later than 24 hours before the conference start

Please do not follow the instructions until you can see the final status!

Prerequisities:
1. You have JDK 8 installed and working in your terminal (```java -version``` command tells you the correct version).

Preparation before the session:
1. Checkout these demo sources by ```git clone https://github.com/PerfCake/Demos.git```
2. Download and unpack the binary distribution of (Weaver application)[https://github.com/PerfCake/Weaver/releases] that will serve as a broken service for us 
(make sure it works by running ```bin/weaver.sh -c projects/example.cfg``` or ```bin\weaver.bat -c projects\example.cfg``` in its installation directory, terminate it by Ctrl+C).
2. Download PerfCake 7.1 distribution from www.perfcake.org and unzip it into any location.
4. Make sure you can execute the following command without an error in your PerfCake installation (requires Internet connection):
   $ bin/perfcake.sh -s http

This is enough for exercise no. 3 & 4. 
Exercises 1 & 2 require a running application server to host a sample service. 

Steps to get prepared for Exercise 1 & 2:
1. Make sure you have Apache Maven >=3.3.9 installed and working in your terminal (```mvn -version``` command tells you the correct version).
2. Download WildFly (Application Server 10.1.0.Final)[http://wildfly.org/downloads/] and make sure it works by running ```bin/standalone.sh``` in its installation
directory (the server should print out no error and you can terminate it by Ctrl+C).
3. In the directory where did you check out the demo sources, go to ```GeeCON2016/exercise1/finished```
and run ```mvn clean install -DskipTests```. This downloads many resources which will save us time and
precious download capacity during the coding session.

In the Exercises we will refer to the locations where you have placed all the resources and tools as follows:

 * `$DEMOS_HOME` - location where you did checkout these sources (e.g. `/home/user/Demos`)
 * `$PERFCAKE_HOME` - location with unzipped PerfCake 7.1 (e.g. `/home/user/perfcake-7.1`)
 * `$WEAVER_HOME` - location with unzipped Weaver (e.g. `/user/home/weaver-1.0`)
 * `$WILDFLY_HOME` - location with unzipped WildFly 10.1.0.Final (e.g. `/users/home/wildfly-10.1.0.Final`)

Demos
=====

Scenario 1: Include Performance testing into your application for daily verification
------------------------------------------------------------------------------------

In a few steps we will add PerfCake performance testing scenario to a JavaEE Maven based application.
Configure once, run always as part of integration tests.

See ```exercise1/README.md``` for instructions on completing this exercise.

Scenario 2: Complex system testing
----------------------------------

We will develop a performance test scenario where we will read a response from a different message
channel then where we sent the original request. Several responses will be aggregated to one. And yet we
will be able to measure the performance.

This cover a use case where there are several (micro-)services connected together and communicating using various protocols.

See ```exercise2/README.md``` for instructions on completing this exercise.

Scenario 3: Monitor threads activity and address coordinated omission problem
-----------------------------------------------------------------------------

We will create a scenario for measuring response time of a simple service. By running an ordinary
test, everything will look good. But when we start looking in more detail, we will reveal some
serious problems that only PerfCake can help us detect.

See ```exercise3/README.md``` for instructions on completing this exercise.

Scenario 4: Overwhelmed service
-------------------------------

First, we will try to search for the optimal speed of requests for our sample service. We realize
there is a significant improvement when we keep increasing the speed. But is it really the case?
PerfCake can tell us the truth!

See ```exercise4/README.md``` for instructions on completing this exercise.
