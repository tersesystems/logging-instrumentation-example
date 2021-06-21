package example;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;

public class Runner {
    public static void main(String[] args) {
        startLogback();

        final SLF4JExample slf4JExample = new SLF4JExample();
        slf4JExample.run();

        stopLogback();
    }

    private static void startLogback() {
        // ensure this starts before JUL logging gets underway
        org.slf4j.LoggerFactory.getILoggerFactory();
    }

    static class SLF4JExample implements Runnable {
        Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

        @Override
        public void run() {
            try {
                URL url = new URL("https://www.google.com/");
                final HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                printCertificates(con);
                logger.info("Successfully completed operation to URL {}", url);
            } catch (IOException e) {
                logger.error("IO exception", e);
            }
        }

        private void printCertificates(HttpsURLConnection con) throws IOException {
            System.out.println("Response Code : " + con.getResponseCode());
            System.out.println("Cipher Suite : " + con.getCipherSuite());
            System.out.println("\n");
            Certificate[] certs = con.getServerCertificates();
            for (Certificate cert: certs) {
                System.out.println("Cert Type : " + cert.getType());
                System.out.println("Cert Hash Code : " + cert.hashCode());
                System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
                System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
                System.out.println("\n");
            }
        }
    }

    static void stopLogback() {
        // http://logback.qos.ch/manual/configuration.html#stopContext
        ((LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory()).stop();
    }

}
