package example

import ch.qos.logback.classic.LoggerContext

import java.net.MalformedURLException
import java.net.URL

import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLPeerUnverifiedException
import java.io.IOException

/**
 * This is an example class that demonstrates logging instrumentation.
 *
 * You can see here there is no code modification at all, everything is
 * done through the agent.
 */
object Runner {

  def main(args: Array[String]): Unit = {
    new SLF4JExample().run()

    stopLogback()
  }

  class SLF4JExample extends Runnable {
    private val logger = org.slf4j.LoggerFactory.getLogger(getClass)
    def run(): Unit = {
      try {
        val url = new URL("https://www.google.com/")
        val con = url.openConnection.asInstanceOf[HttpsURLConnection]
        printCertificates(con)
        logger.info("Successfully completed operation to URL {}", url)
      } catch {
        case e: MalformedURLException =>
          logger.error("Malformed URL", e)
        case e: IOException =>
          logger.error("IO exception", e)
      }
    }

    private def printCertificates(con: HttpsURLConnection): Unit = {
      try {
        System.out.println("Response Code : " + con.getResponseCode)
        System.out.println("Cipher Suite : " + con.getCipherSuite)
        System.out.println("\n")
        val certs = con.getServerCertificates
        for (cert <- certs) {
          System.out.println("Cert Type : " + cert.getType)
          System.out.println("Cert Hash Code : " + cert.hashCode)
          System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey.getAlgorithm)
          System.out.println("Cert Public Key Format : " + cert.getPublicKey.getFormat)
          System.out.println("\n")
        }
      } catch {
        case e: SSLPeerUnverifiedException =>
          logger.error("Peer not found", e)
      }
    }
  }

  def stopLogback(): Unit = {
    // http://logback.qos.ch/manual/configuration.html#stopContext
    org.slf4j.LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext].stop()
  }

}
