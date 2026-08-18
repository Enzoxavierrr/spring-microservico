package br.pucrs.microdemo.microservico1.config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {
    @Bean
    public RestTemplate restTemplate() throws GeneralSecurityException {
        return new RestTemplate(new HttpsLocalRequestFactory());
    }

    private static class HttpsLocalRequestFactory extends SimpleClientHttpRequestFactory {
        private final SSLSocketFactory sslSocketFactory;
        private final HostnameVerifier hostnameVerifier = (hostname, session) -> true;

        HttpsLocalRequestFactory() throws GeneralSecurityException {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, confiarEmTodosOsCertificados(), new SecureRandom());
            this.sslSocketFactory = sslContext.getSocketFactory();
        }

        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
            if (connection instanceof HttpsURLConnection httpsConnection) {
                httpsConnection.setSSLSocketFactory(sslSocketFactory);
                httpsConnection.setHostnameVerifier(hostnameVerifier);
            }

            super.prepareConnection(connection, httpMethod);
        }

        private TrustManager[] confiarEmTodosOsCertificados() {
            return new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };
        }
    }
}
