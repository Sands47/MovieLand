package com.voligov.movieland.util.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static com.voligov.movieland.util.Constant.*;


public class EmailAppender extends AppenderBase<ILoggingEvent> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private CloseableHttpClient httpclient;
    private Gson gson = new Gson();

    @Override
    public void start() {
        SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, (chain, authType) -> true);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            httpclient = HttpClients.custom().setSSLSocketFactory(
                    sslsf).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            log.warn("Exception creating Http client: ", e);
        }
        super.start();
    }

    public void append(ILoggingEvent event) {
        try {
            HttpPost httpPost = new HttpPost(SENDGRID_URI);
            httpPost.setHeader("Authorization", "Bearer " + SENDGRID_API_KEY);
            httpPost.setHeader("Content-Type", "application/json");
            String message = BRACKET_OPEN + new Date(event.getTimeStamp()) + BRACKET_CLOSE +
                    SPACE + event.getThreadName() + SPACE + event.getLevel().levelStr +
                    SPACE + event.getLoggerName() + " - " + event.getMessage();
            String bodyJson = buildSendgridMessage(ERROR_EMAIL_RECIEVER, ERROR_EMAIL_SENDER,
                    "ERROR occured", message);
            StringEntity body = new StringEntity(bodyJson);
            httpPost.setEntity(body);
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                EntityUtils.consume(entity);
            }
        } catch (IOException e) {
            log.warn("Exception while sending email: ", e);
        }
    }

    private String buildSendgridMessage(String emailTo, String emailFrom, String subject, String message) {
        JsonObject jsonObject = new JsonObject();
        JsonObject toEmail = new JsonObject();
        toEmail.addProperty(EMAIL, emailTo);
        JsonArray toEmailArray = new JsonArray();
        toEmailArray.add(toEmail);
        JsonObject to = new JsonObject();
        to.add("to", toEmailArray);
        JsonArray personalizations = new JsonArray();
        personalizations.add(to);
        jsonObject.add("personalizations", personalizations);

        JsonObject fromEmail = new JsonObject();
        fromEmail.addProperty(EMAIL, emailFrom);
        jsonObject.add("from", fromEmail);
        jsonObject.addProperty("subject", subject);

        JsonObject content = new JsonObject();
        content.addProperty("type", "text/plain");
        content.addProperty("value", message);
        JsonArray contentArray = new JsonArray();
        contentArray.add(content);
        jsonObject.add("content", contentArray);

        return gson.toJson(jsonObject);
    }
}
