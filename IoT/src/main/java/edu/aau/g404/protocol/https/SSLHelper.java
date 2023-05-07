package edu.aau.g404.protocol.https;

import java.security.cert.X509Certificate;
import javax.net.ssl.*;

/**
 * SSLHelper class is a utility class that disables SSL certificate verification for the application.
 * This class shouldn't really be used in a production environment, but it is useful for testing purposes.
 */
public final class SSLHelper {
    /**
     * Disables SSL certificate verification for the application.
     * This method should only be used in development and testing environments.
     * Using this method in a production environment is a security risk.
     * @throws Exception If an error occurs while disabling SSL certificate verification.
     */
    public static void disableSSLVerification() throws Exception {
        // Create a trust manager that does not perform any certificate verification
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        // Install the trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
    }
}