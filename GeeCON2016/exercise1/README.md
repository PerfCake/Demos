Exercise 1
==========

Useful Maven goals:

 * `mvn package` - creates war file
 * `mvn wildfly:deploy` - deploys the application
 * `mvn wildfly:undeploy` - un-deploys the application
 * `mvn integration-test` - deploys app., runs integration tests, un-deploys app.
 * `mvn verify` - run tests, deploys app., runs integration tests, un-deploys app.

Solution
--------

Start in the directory `exercise`.

This is an ordinary REST enabled application with a simple service class.

There is an integration test that can deploy the application to an application server and
verify that it is available. To give it a try, we first start the application server.

Open a separate terminal window and run
`$WILDFLY_HOME/bin/standalone.sh`

The server starts in a bit. Then switch to `$DEMOS_HOME/GeeCON2016/exercise1/exercise` and run:

`mvn clean install`

All should pass smoothly ending with BUILD SUCCESS.

Now we will be adding a performance test for this service. Let's assume somebody already developed the test scenario for us.

Copy `$DEMOS_HOME/GeeCON2016/exercise1/finished/src/test/resources/perfcake` to `$DEMOS_HOME/GeeCON2016/exercise1/exercise/src/test/resources/perfcake`.

Now we'll add the PerfCake plugin to `$DEMOS_HOME/GeeCON2016/exercise1/exercise/pom.xml`:

```xml
   <plugin>
      <groupId>org.perfcake</groupId>
      <artifactId>perfcake-maven-plugin</artifactId>
      <version>7.1</version>
      <configuration>
         <scenario>http</scenario>
         <log4j2-config>src/test/resources/log4j2.xml</log4j2-config>
      </configuration>
      <executions>
         <execution>
            <id>perfcake-scenario-run</id>
            <phase>integration-test</phase>
            <goals>
               <goal>scenario-run</goal>
            </goals>
         </execution>
      </executions>
      <dependencies>
         <dependency>
            <groupId>org.perfcake</groupId>
            <artifactId>perfcake</artifactId>
            <version>7.1</version>
         </dependency>
      </dependencies>
   </plugin>
```

Now you can run the same Maven command as before once more:

`mvn clean install`

The performance test is executed and its results produces as configured in the scenario 
(on console and in `$DEMOS_HOME/GeeCON2016/exercise1/exercise/target/perfcake-results-*.csv`).

Now you can have a look into `$DEMOS_HOME/GeeCON2016/exercise1/finished/pom.xml` where the configuration
is slightly more sophisticated to allow skipping test execution and using a property to configure PerfCake
version. But these are just some Maven related touches that have nothing to do with the fact that you
have just integrated a performance test into a JavaEE Maven based project!