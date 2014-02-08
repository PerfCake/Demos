PerfCake Tutorial - Custom plugin - CoolSender
================================================

Get PerfCake binary distribution
--------------------------------
* Get PerfCake
```sh
git clone https://github.com/PerfCake/PerfCake PerfCake.git
```
* Switch to devel branch
```sh
git checkout devel
```
* Assembly binary distribution
```sh
mvn -f PerfCake.git/pom.xml clean assembly:assembly -DskipTests
```
* Extract the binary distribution
```sh
unzip -o perfcake-bin PerfCake.git/target/perfcake-2.0-SNAPSHOT-bin.zip
```
* Install PerfCake into local maven repository to be available as a dependency
```sh
mvn -f PerfCake.git/pom.xml sources:jar install -DskipTests
```

Create new plugin from a template
---------------------------------
* Get plugin template
```sh
git clone https://github.com/PerfCake/Plugins Plugins.git
```
* Start with plugin template
```sh
cp -r Plugins.git/perfcake-plugin-template perfcake-plugin-coolsender
```
* Edit plugin's pom.xml
```sh
sed -i -e 's/${plugin.name}/coolsender/g' perfcake-plugin-coolsender/pom.xml
sed -i -e 's/${perfcake.version}/2\.0-SNAPSHOT/g' perfcake-plugin-coolsender/pom.xml
sed -i -e 's/${plugin.version}/0\.0\.1-SNAPSHOT/g' perfcake-plugin-coolsender/pom.xml
```

Write CoolSender code
---------------------
* Import perfcake-plugin-coolsender Maven project into Eclipse
* Create new Java class called org.cool.CoolSender that extends org.perfcake.message.sender.AbstractSender
* Fill in doSend method

```java
package org.cool;

import java.io.Serializable;
import java.util.Map;

import org.perfcake.PerfCakeException;
import org.perfcake.message.Message;
import org.perfcake.message.sender.AbstractSender;
import org.perfcake.reporting.MeasurementUnit;

public class CoolSender extends AbstractSender {

   private String coolProperty;

   @Override
   public void init() throws Exception {}

   @Override
   public void close() throws PerfCakeException {}

   @Override
   public Serializable doSend(Message message, Map<String, String> properties, MeasurementUnit mu) throws Exception {
      final String msg = message.getPayload().toString();
      System.out.println("CoolSender is sending '" + msg + "' with " + coolProperty + " out to " + getTarget() + "!");
      return msg;
   }

   public String getCoolProperty() {
      return coolProperty;
   }

   public void setCoolProperty(String coolProperty) {
      this.coolProperty = coolProperty;
   }

}
```

Build and deploy CoolSender plugin library
------------------------------------------
* Build the library
```sh
mvn -f perfcake-plugin-coolsender/pom.xml clean package
```
* Deploy the plugin library into PerfCake distribution
```sh
cp -f perfcake-plugin-coolsender/target/perfcake-plugin-coolsender-0.0.1-SNAPSHOT.jar perfcake-bin/lib/plugins
```

Create scenario that uses CoolSender
------------------------------------
* Create scenario file (perfcake-bin/resources/scenarios/cool.xml):
```xml
<?xml version="1.0" encoding="utf-8"?>
<scenario xmlns="urn:perfcake:scenario:1.0">
   <properties/>
   <generator class="DefaultMessageGenerator" threads="1">
      <run type="time" value="5000"/> <!-- 5s -->
   </generator>
   <sender class="org.cool.CoolSender">
      <property name="target" value="Out there!"/>
      <property name="coolProperty" value="iceCream"/>
   </sender>
   <reporting>
      <reporter class="AverageThroughputReporter">
         <destination class="ConsoleDestination">
            <period type="time" value="1000" /> <!-- 1s -->
         </destination>
      </reporter>
   </reporting>
   <messages>
      <message uri="cool-message.txt"/>
   </messages>
</scenario>
```
* Create message file (perfcake-bin/resources/messages/cool-message.xml):
```
CoOl MeSsAgE
```

Execute the cool scenario
-------------------------
```sh
perfcake-bin/bin/perfcake.sh -s cool
```