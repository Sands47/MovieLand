package com.voligov.movieland.util.logging;

import java.io.IOException;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.sendgrid.*;
import com.voligov.movieland.util.Constant;


public class EmailAppender extends AppenderBase<ILoggingEvent> {
    private SendGrid sendGrid;

    @Override
    public void start() {
        sendGrid = new SendGrid(Constant.SENDGRID_API_KEY);
        super.start();
    }

    public void append(ILoggingEvent event) {
        Email from = new Email("movieland@email.com");
        String subject = "ERROR : " + event.getTimeStamp();
        Email to = new Email("sands47@gmail.com");
        Content content = new Content("text/plain", event.getMessage());
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            Response response = sendGrid.api(request);
            System.out.println(response.statusCode);
            System.out.println(response.body);
            System.out.println(response.headers);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
