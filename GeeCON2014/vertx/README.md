Sample server for GeeCON 2014 demonstration. Combines some "bad applications'" practices.

To start the server without PerfCake agent attached, run with:
$ mvn package exec:exec -DskipTests

To start the server with PerfCake agent attached, listening on "localhost", run with:
$ mvn package exec:exec -DskipTests -P agent

To start the server with PerfCake agent attached, listening on a hostname, run with:
$ mvn package exec:exec -DskipTests -P agent -Dagent.host=<hostname>