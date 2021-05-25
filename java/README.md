# Java Logging Instrumentation Example

This is a simple Gradle project that demonstrates how to use `logback-bytebuddy` as a Java agent to instrument code with logging statements.

Please see the [instrumentation page](https://tersesystems.github.io/terse-logback/0.16.2/guide/instrumentation/) for further details.

## Usage

To run the agent, you must first run `gradlew installDist` which will create a script with the agent in the appropriate place in the command line.

```
./gradlew installDist
cd build/install/java-logging-instrumentation-example
./bin/java-logging-instrumentation-example 
```

## Output

Because the `logback.conf` file contains

```hocon
logback.bytebuddy {
  service-name = "scala-logging-instrumentation-example"
  tracing {  
    "javax.net.ssl.SSLContext" = ["*"]
  }
}
```

and the `logback.xml` file contains:

```xml
<logger name="javax.net.ssl" level="TRACE"/> 
```

The following output from `SSLContext` will be instrumented (using a call to `https://google.com`):

```
FoI3Xm52As06O0Qbm7EAAA 7:27:55.371 [TRACE] j.n.s.SSLContext -  entering: javax.net.ssl.SSLContext.getDefault() with arguments=[] from source SSLContext.java:98
FoI3Xm52Atw6O0Qbm7EAAA 7:27:55.387 [TRACE] j.n.s.SSLContext -  entering: javax.net.ssl.SSLContext.getInstance(java.lang.String) with arguments=[Default] from source SSLContext.java:166
FoI3Xm52A146O0Qbm7EAAA 7:27:55.516 [TRACE] j.n.s.SSLContext -  exiting: javax.net.ssl.SSLContext.getInstance(java.lang.String) with arguments=[Default] => (return_type=javax.net.ssl.SSLContext return_value=javax.net.ssl.SSLContext@563a89b5) from source SSLContext.java:169
FoI3Xm52A18dHaINzdiAAA 7:27:55.517 [TRACE] j.n.s.SSLContext -  exiting: javax.net.ssl.SSLContext.getDefault() with arguments=[] => (return_type=javax.net.ssl.SSLContext return_value=javax.net.ssl.SSLContext@563a89b5) from source SSLContext.java:101
FoI3Xm52A2A6O0Qbm7EAAA 7:27:55.518 [TRACE] j.n.s.SSLContext -  entering: javax.net.ssl.SSLContext.getSocketFactory() with arguments=[] from source SSLContext.java:311
FoI3Xm52A2E6O0Qbm7EAAA 7:27:55.519 [TRACE] j.n.s.SSLContext -  exiting: javax.net.ssl.SSLContext.getSocketFactory() with arguments=[] => (return_type=javax.net.ssl.SSLSocketFactory return_value=sun.security.ssl.SSLSocketFactoryImpl@270b6b5e) from source SSLContext.java:311
Response Code : 200
Cipher Suite : TLS_AES_128_GCM_SHA256


Cert Type : X.509
Cert Hash Code : 189723033
Cert Public Key Algorithm : EC
Cert Public Key Format : X.509


Cert Type : X.509
Cert Hash Code : 1544128074
Cert Public Key Algorithm : RSA
Cert Public Key Format : X.509


FoI3Xm52BGA6O0Qbm7EAAA 7:27:55.775 [INFO ] e.Runner$SLF4JExample -  Successfully completed operation to URL https://www.google.com/
```