package com.voligov.movieland.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class CustomHttpClient {
    private static final Logger log = LoggerFactory.getLogger(CustomHttpClient.class);
    private static CloseableHttpClient client;

    static {
        SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, (chain, authType) -> true);
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                    builder.build());
            client = HttpClients.custom().setSSLSocketFactory(
                    sslSocketFactory).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            log.error("Exception creating Http client: ", e);
        }
    }

    public static HttpEntity post(HttpPost httpPost) throws IOException {
        CloseableHttpResponse response = client.execute(httpPost);
        return response.getEntity();
    }

    public static HttpEntity post(HttpPost httpPost, Integer connectionTimeout, Integer socketTimeout) throws IOException {
        setRequestBuilder(httpPost, connectionTimeout, socketTimeout);
        return post(httpPost);
    }

    public static HttpEntity get(HttpGet httpGet) throws IOException {
        CloseableHttpResponse response = client.execute(httpGet);
        return response.getEntity();
    }

    public static HttpEntity get(HttpGet httpGet, Integer connectionTimeout, Integer socketTimeout) throws IOException {
        setRequestBuilder(httpGet, connectionTimeout, socketTimeout);
        return get(httpGet);
    }

    private static void setRequestBuilder(HttpRequestBase httpRequest, Integer connectionTimeout, Integer socketTimeout) {
        RequestConfig.Builder builder = RequestConfig.custom();
        if (connectionTimeout != null) {
            builder.setConnectionRequestTimeout(connectionTimeout);
        }
        if (socketTimeout != null) {
            builder.setSocketTimeout(socketTimeout);
        }
        httpRequest.setConfig(builder.build());
    }
}
