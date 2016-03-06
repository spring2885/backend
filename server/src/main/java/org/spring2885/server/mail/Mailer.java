package org.spring2885.server.mail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface Mailer {
    void send(String email, String templateName, String title, 
            Map<String, String> data) throws IOException, URISyntaxException;
}
