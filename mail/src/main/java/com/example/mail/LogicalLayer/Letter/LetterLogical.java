package com.example.mail.LogicalLayer.Letter;

import java.util.Properties;
import javax.mail.*;

public class LetterLogical {

    public static final String mainEmail = "your-email";
    private static final String password = "your-password";

    public static Session getSession() {
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mainEmail, password);
                }
            }); 
        
        return session;
    }
}
