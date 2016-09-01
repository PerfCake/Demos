OpenAlt 2016 Live coding session
================================

Instructions status: DRAFT - will be marked as final no later than 24 hours before the conference start

Please do not follow the instructions until you can see the final status!

Preparation before the session:
1. Download PerfCake 7.0 distribution from www.perfcake.org
2. Unzip into any directory
3. Make sure you have JDK 8 and Maven >3.3.9 installed
4. Make sure you can execute the following command without an error (requires Internet connection):
   $ bin/perfcake.sh -s http
5. This is enough to be able to run the demos, however, we recommend preparing your local Maven repository as it downloads a lot of files by the following command in this directory:
   $ mvn install -DskipTests

Demos
=====

Scenario 1: Include Performance testing into your application for daily verification
------------------------------------------------------------------------------------

TBD

Scenario 2: Complex system testing
----------------------------------

We will develop a performance test scenario where we will read a response from a different message
channel then where we sent the original request. Several responses will be aggregated to one. And yet we
will be able to measure the performance.

This cover a use case where there are several (micro-)services connected together and communicating using various protocols.

Scenario 3: Monitor threads activity and address coordinated omission problem
-----------------------------------------------------------------------------

TBD