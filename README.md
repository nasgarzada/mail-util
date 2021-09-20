# Introduction

`email-util` is simple, declarative utility library. Key features are:

- declarative email building;
- text or html rendering;
- annotation support for binding object value to template;
- built-in smtp support for simple or multipart messages;
- support for sending mails from other channels;

# Examples 

### Multipart mail sender 

Following example shows multipart message building and sending:

```java

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Email.builder()
                .from("nicat.asgerzade.9889@gmail.com")
                .to("nicat.asgerzade.9889@gmail.com", false)
                .subject("Just testing my new library")
                .withRenderer()
                .path("template.txt")
                .bindingObject(new Example("Nijat Asgarzada"))
                .build()
                .withAttachment()
                .file("image.png")
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

```
In the example, you can see that, we have used built in `MultipartMessageSender` object for sending message. We passed properties file to object. 
Properties file is like that:

```properties
mail.smtp.auth=true
mail.smtp.starttls.enable=true

mail.smtp.host=smtp.gmail.com
mail.smtp.port=587
mail.username=nicat.asgerzade.9889@gmail.com 
mail.password= <your app password>
mail.transport.protocol=smtp

mail.content.type=text/html  #if you don't set default is text/plain
mail.default.charset=UTF-8  #if you don't set it's default value
```
Another factor is ` @Bind(key = "user")` annotation. 
This annotation enables replacing `user` field in template with value of `fullName` field of `Example` object.

### Custom mail sender

Another key feature of util is customization of sender method. 
If you don't need sending via smtp, then you can implement your own logic. 
In the following example, I've implemented sending mail entity to queue:

```java
public class QueueSender<Void> implements Sender<Void> {
    private final AmqpTemplate amqpTemplate;
    private final String queueName;
    private final ObjectMapper objectMapper;
    
    public QueueSender(AmqpTemplate amqpTemplate, String queueName, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.queueName = queueName;
        this.objectMapper = objectMapper;
    }

    @Override
    public Void send(MailEntity mailEntity) {
        this.amqpTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(mailEntity));
        return null;
    }
}


```

