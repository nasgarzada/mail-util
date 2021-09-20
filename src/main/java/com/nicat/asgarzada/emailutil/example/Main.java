package com.nicat.asgarzada.emailutil.example;

import com.nicat.asgarzada.emailutil.annotation.Bind;
import com.nicat.asgarzada.emailutil.core.Email;
import com.nicat.asgarzada.emailutil.sender.smtp.MultipartMessageSender;

public class Main {
    public static void main(String[] args) {
        Email.builder()
                .from("nicat.asgerzade.9889@gmail.com")
                .to("nicat.asgarzade.9889@gmail.com", false)
                .subject("Just testing my new library")
                .withRenderer()
                .path("template.txt")
                .bindingObject(new Example("Nijat Asgarzada"))
                .build()
                .withAttachment()
                .file("hello.png", "image.png")
                .build()
                .send(new MultipartMessageSender("email.properties"));
    }

    public static class Example {
        @Bind(key = "user")
        private String fullName;

        public Example(String fullName) {
            this.fullName = fullName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        @Override
        public String toString() {
            return "Example{" +
                    "fullName='" + fullName + '\'' +
                    '}';
        }
    }
}
