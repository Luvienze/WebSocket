# WebSockets

## What are WebSockets ?
In this article, I will discuss WebSockets and their implementation.
Additionally, I will cover how to integrate Spring Boot Actuator and Spring Boot DevTools into the project.

WebSockets enable real-time communication between clients and servers, making them ideal for use cases such as live chat, messaging, and multiplayer games. 
The WebSocket protocol operates as a transport layer over TCP (Transmission Control Protocol). TCP ensures that the communication is reliable, ordered, and error-checked.


In this example, we will use the STOMP messaging protocol. STOMP (Simple Text Oriented Messaging Protocol) is a simple,
text-based protocol that allows STOMP clients to communicate with any message broker supporting the protocol.

![image](https://github.com/user-attachments/assets/b98769b2-e74d-4a5a-a918-9023902f024c)

### How to use it ? 
First, we need to create a new project. Go to Spring Initializr and add WebSocket to the dependencies.
In addition to WebSocket, it's a good idea to include Spring Boot DevTools for faster restarts and Spring Boot Actuator to monitor the 
application's health and metrics. After selecting your dependencies, generate the project, unzip it, and import it into your IDE. In this case, I'm using IntelliJ IDEA.

![image](https://github.com/user-attachments/assets/aef89ec7-6d09-404f-9f16-a7943be9dd80)

We will build a WebSocket application that greets users by their names when they connect and send their names as messages. 
In the java folder, create a package called entity. Inside this package, create two classes: HelloMessage and Greeting.

### HelloMessage Class

This class will store the user's name, which will be sent to the broker. Define a private field called name, along with constructors, getters, and setters. 
The toString() method isn't necessary, but you can implement it if you want to see the object's state in the console during debugging.
```
public class HelloMessage {
    private String name;

    public HelloMessage() {}

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HelloMessage{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

### Greeting Class

Similarly, the Greeting class will represent the response message that the server sends back to the client. 
It should also include the necessary fields, constructors, and methods.
```
public class Greeting {
    private String content;

    public Greeting() {}

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "content='" + content + '\'' +
                '}';
    }
```

### Controller 

Next, we need a controller to handle incoming messages and return the appropriate responses.
Create a new package named controller and add a GreetingController class.

```
@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }
}

```

In this method:
* @MessageMapping("/hello"): Maps messages sent to the /hello destination to this method.
* @SendTo("/topic/greetings"): The return value of this method will be sent to /topic/greetings.

### WebSocket Configuration

We also need to configure WebSockets in our application. Create a WebSocketConfig class in the config package.

```
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }
}
```
Here:
* configureMessageBroker(): Configures the message broker. We enable a simple, in-memory message broker and set the destination prefixes.
* registerStompEndpoints(): Registers the /gs-guide-websocket endpoint, which clients will use to connect to the WebSocket server.

### Configuring Actuator
To enable monitoring of the application, add the following lines to your application.properties file:

```
management.endpoints.web.exposure.include=*
management.info.env.enabled=true
```

If you'd like to add custom information, include:
```
info.app.name=WebSocket Application
info.app.description=Messaging with WebSocket
info.app.version=1.0.0
```

### Client-Side Setup
Lastly, create a simple browser client. The HTML and JavaScript files should be placed in the resources/static directory in my GitHub. 
In the HTML file, make sure to remove any unnecessary backslashes in the script tag pointing to app.js.

If you're using a different port (e.g., 8080 instead of 8085), be sure to update the port number in both application.properties and your JavaScript file.

### Testing the Application
Once everything is set up, you can test your WebSocket application in different browsers to ensure it works as expected.

![image](https://github.com/user-attachments/assets/02e0d62e-55d1-44e2-9441-e8e72a20dde9)

### Actuator
Let's monitor our application.

![image](https://github.com/user-attachments/assets/8a4c315f-1510-4013-aac4-2c59774c3d6a)

actuator/health

![image](https://github.com/user-attachments/assets/5f6cb4e0-0809-4f84-a0c2-843122d0dabd)

actuator/info

![image](https://github.com/user-attachments/assets/32804c11-ceee-4c09-a27c-9219b08f67a8)

/actuator

You can test other endpoints using actuator.
In conclusion, WebSockets provide a powerful tool for real-time communication between clients and servers.
By leveraging Spring Boot's WebSocket support, along with Actuator for monitoring and DevTools for efficient development,
you can create robust and responsive applications. Whether you're building a chat application, a real-time notification system, or any other interactive service,
WebSockets combined with Spring Boot offer a solid foundation. Feel free to explore further and enhance your implementation with additional features.

Sources:

[Official Spring Boot Website](https://spring.io/guides/gs/messaging-stomp-websocket#scratch)

[Stomp Protoco](https://www.geeksforgeeks.org/stomp-protocol/)

[TCP and STOMP Protocol](https://en.wikipedia.org/wiki/Streaming_Text_Oriented_Messaging_Protocol)

You can follow me on my 

[GitHub](https://github.com/Luvienze)

[Linkedin](https://www.linkedin.com/in/erensonmez7/)

[Medium ](https://medium.com/@snmzern00)

[X](https://x.com/Luvienze)


