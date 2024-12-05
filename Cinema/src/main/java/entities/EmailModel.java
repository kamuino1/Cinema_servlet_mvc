package entities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        this.email = "example@gmail.com";
        this.password = "";
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
        
        String folderPath = "mailsave";
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdir();
        }

        String fileName = "email_" + System.currentTimeMillis() + ".txt";
        File file = new File(folder, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("To: " + recipient + "\n");
            writer.write("Subject: " + subject + "\n");
            writer.write("Content:\n" + content);
        }
    }
}
