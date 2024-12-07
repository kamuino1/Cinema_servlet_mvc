package entities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * User entity
 */
public class EmailModel {
    private String host;
    private String port;
    private String email;
    private String password;
    private String recipient;
    private String subject;
    private String content;

    public EmailModel(String recipient, String subject, String content) {
        this.host = "smtp.gmail.com";
        this.port = "587";
        this.email = "tienp323@gmail.com";
        this.password = "iaqi upve vgsr yvhc";
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EmailModel{" + "host=" + host + ", port=" + port + ", email=" + email + ", password=" + password + '}';
    }
    
    public void saveEmailToFile() throws IOException {
        String folderPath = "D:\\1\\1tttn\\Cinema_servlet_mvc\\Cinema\\src\\main\\java\\mailsave";
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdir();
        }

        String fileName = "email_" + System.currentTimeMillis() + ".txt";
        File file = new File(folder, fileName);

        // Sử dụng OutputStreamWriter với UTF-8
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {
            writer.write("To: " + recipient + "\n");
            writer.write("Subject: " + subject + "\n");
            writer.write("Content:\n" + content);
        }
    }

    public boolean sendMail(){
        
        // Cấu hình JavaMail
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Xác thực tài khoản email
        javax.mail.Session s = javax.mail.Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            // Tạo email
            Message message = new MimeMessage(s);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content);

            // Gửi email
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
