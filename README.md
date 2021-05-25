# Logging Instrumentation Examples

This project shows both Scala and Java examples of logging instrumentation driven by config file, using [logback-bytebuddy](https://tersesystems.github.io/terse-logback/guide/instrumentation/).

The code in these examples does an HTTPS call to google.com and instruments the `SSLContext` so that you can see the TLS related operations start up automatically in the JDK code base.  

Please see the READMEs in the given projects for more details.
