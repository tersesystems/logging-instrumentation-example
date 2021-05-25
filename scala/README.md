# Scala Logging Instrumentation Example

This is a simple SBT project that demonstrates how to use `logback-bytebuddy` as a Java agent to instrument code with logging statements.

Please see the [instrumentation page](https://tersesystems.github.io/terse-logback/0.16.2/guide/instrumentation/) for further details.

## Usage

To run the agent, you must first run `sbt stage` which will create a script with the agent in the appropriate place in the command line.

```
sbt clean stage
cd target/universal/stage
./bin/scala-logging-instrumentation-example
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
FoI3Xm5jH1w6O0Qbm7EAAA 6:59:29.018 [TRACE] j.n.s.SSLContext -  entering: javax.net.ssl.SSLContext.getDefault() with arguments=[] from source SSLContext.java:98
FoI3Xm5jH2g6O0Qbm7EAAA 6:59:29.030 [TRACE] j.n.s.SSLContext -  entering: javax.net.ssl.SSLContext.getInstance(java.lang.String) with arguments=[Default] from source SSLContext.java:166
FoI3Xm5jH8YdHaINzdiAAA 6:59:29.130 [TRACE] j.n.s.SSLContext -  exiting: javax.net.ssl.SSLContext.getInstance(java.lang.String) with arguments=[Default] => (return_type=javax.net.ssl.SSLContext return_value=javax.net.ssl.SSLContext@4ad3d266) from source SSLContext.java:169
FoI3Xm5jH8adHaINzdiAAA 6:59:29.131 [TRACE] j.n.s.SSLContext -  exiting: javax.net.ssl.SSLContext.getDefault() with arguments=[] => (return_type=javax.net.ssl.SSLContext return_value=javax.net.ssl.SSLContext@4ad3d266) from source SSLContext.java:101
FoI3Xm5jH8adHaINzdiAAB 6:59:29.132 [TRACE] j.n.s.SSLContext -  entering: javax.net.ssl.SSLContext.getSocketFactory() with arguments=[] from source SSLContext.java:311
FoI3Xm5jH8cdHaINzdiAAA 6:59:29.132 [TRACE] j.n.s.SSLContext -  exiting: javax.net.ssl.SSLContext.getSocketFactory() with arguments=[] => (return_type=javax.net.ssl.SSLSocketFactory return_value=sun.security.ssl.SSLSocketFactoryImpl@15d0849) from source SSLContext.java:311
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


FoI3Xm5jIR8dHaINzdiAAA 6:59:29.469 [INFO ] e.Runner$SLF4JExample -  Successfully completed operation to URL https://www.google.com/
```