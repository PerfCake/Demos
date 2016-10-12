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

This is enough for exercise no. 2 -4. Exercise 1 requires some more configuration and is mostly useful for Java developers. The same principle can be applied 
to any programming language but you can get the idea simply by observing the demonstration.

Steps to get prepared for Exercise 1:
1. Make sure you have Apache Maven >=3.3.9 installed and working in your terminal (```maven -version``` command tells you the correct version).
2. Download WildFly (Application Server 10.1.0.Final)[http://wildfly.org/downloads/] and make sure it works by running ```bin/standalone.sh``` in its installation
directory (the server should print out no error and you can terminate it by Ctrl+C).
3. In the directory where did you check out the demo sources, go to ```GeeCON2016/exercise1/finished```
and run ```mvn clean install -DskipTests```. This downloads many resources which will save us time and
precious download capacity during the coding session.

Demos
=====

Scenario 1: Include Performance testing into your application for daily verification
------------------------------------------------------------------------------------

In a few steps we will add PerfCake performance testing scenario to a JavaEE Maven based application.

See ```exercise1/README.md```.

Scenario 2: Complex system testing
----------------------------------

We will develop a performance test scenario where we will read a response from a different message
channel then where we sent the original request. Several responses will be aggregated to one. And yet we
will be able to measure the performance.

This cover a use case where there are several (micro-)services connected together and communicating using various protocols.

Scenario 3: Monitor threads activity and address coordinated omission problem
-----------------------------------------------------------------------------

TBD