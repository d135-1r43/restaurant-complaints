# Kogito/Quarkus PDA Example: Restaurant Complaints

This repository contains a basic Process Driven Applications (PDA) demo application created using [Quarkus](https://quarkus.io) and the [Kogito](https://kogito.kie.org/) process engine. The purpose of this demo is to demonstrate key concepts of Process Driven Applications.

The application includes three services:

1. **Complaints Service** (`de.thi.complaints`): A Quarkus service that handles restaurant complaint submissions.
2. **Sentiment Analysis Service** (`de.thi.sentiment`): A Quarkus service that assesses the sentiment of the complaint. For simplicity, this demo uses a basic user task, but in a real-world scenario, a more advanced tool like ChatGPT could be used.
3. **Archive Service** (`de.thi.archiv`, optional): A Quarkus service with a simple REST API to store complaints. This service can be integrated into the Complaints Service.

## Walkthrough Complaint Process

![Complaint Business Process](/documentation/complaints-process.png)

👉 Open the file https://github.com/d135-1r43/restaurant-complaints/blob/master/de.thi.complaints/src/main/resources/complaints.bpmn in VS Code and analyse the properties of the process, especially the process variables under 'Process Data' to get a better understanding. 

👉 Run the **Complaints Service** and start a complaint with the Swagger API. It should be available at http://localhost:8080/q/swagger-ui/. Remove the `sentiment` value, just leave it out. 

![Complaint Process in Swagger](/documentation/complaints-swagger.png)

The Complaint Process uses several best-practice patterns. Let's have a look at them…

### Asynchronous implementation via Intermediate Message Event

ℹ️ Sentiment Analysis is the process of analyzing digital text to determine if the emotional tone of the message is positive, negative, or neutral. In our case, we have a scale from 0 'very angry' to 10 'extremly happy'. 

In 'Ask for Sentiment' the process will throw a message and will immediately wait for the returned message in 'Get Sentiment'. The process is agnostic of the actual implementation. The sentiment definition could be done manually or via an NLP -- the process simply does not care. 

To achieve this kind of decoupling, we will have to define several things:

* In 'Ask for Sentiment' we will have to define a message. We do this in the BPMN editor UI and enter the free text name `text` in the field 'Message' in the dropdown 'Implementation/Execution'.
* In 'Data Assignments' we will define the variable `complaintText` to be assigned to the message. 

👉 Analyze the properties of 'Ask for Sentiment' now in VS Code. 

As we are developing in a cloud world, the implementation of the sentiment analysis will run in a complete different service, in the Sentiment Analysis Service (`de.thi.sentiment`). In order to make the two services communicate with each other, we will use Apache Kafka. Thankfully, Kogito and Quarkus abstract away all the nitty-gritty details. Yet we want to understand them. 

In order for Kogito to use Quarkus, we have some dependencies in our `pom.xml`:

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-smallrye-reactive-messaging-kafka</artifactId>
</dependency>
<dependency>
    <groupId>org.kie.kogito</groupId>
    <artifactId>kogito-addons-quarkus-messaging</artifactId>
</dependency>
``` 

That is enough for the magic to happen: 'Ask for Sentiment' will create a Kafka event with the standard [Cloudevents.io](https://cloudevents.io/). This event will be sent to the Microprofile messaging channel `text`, which is defined as a Kafka channel in our `application.properties`. If you run Quarkus in Dev mode, it will start a Kafka automatically as a Dev Service. Also, if you start another Quarkus service in Dev mode, it will connect to the same Kafka. Learn more about Dev Services at [Dev Services Overview](https://quarkus.io/guides/dev-services).

https://github.com/d135-1r43/restaurant-complaints/blob/d0aa264fafdbdfcc280c4476f4f5bf2f8d2a2002/de.thi.complaints/src/main/resources/application.properties#L6-L12

👉 Analyze the `application.properties` and understand on which topic the BPMN event will be sent to. In the Dev UI, look for the Kafka UI and try to find your cloud event. Understand, what has happenend. Also, try to add a random event with the plus button. 

![Kafka UI](/documentation/complaints-kafka.png)

The **Sentiment Analysis Service** (`de.thi.sentiment`) will implement the sentiment analysis. For the sake of simplicity, it is just a User Task to type in a value between 0 and 10. 

👉 Open the BPMN https://github.com/d135-1r43/restaurant-complaints/blob/master/de.thi.sentiment/src/main/resources/sentiment.bpmn in VS Code. Understand the BPMN, analyze the variables and the properties of the start event, the user task and the end event. 

👉 Start the **Sentiment Analysis Service** and understand that it connects to the same Kafka Dev Service and to the same [Kogito Data Index Dev Service](https://quarkus.io/guides/kogito-dev-services). Open its Dev UI at http://localhost:8081/q/dev.

![Sentiment Process](/documentation/sentiment-process.png)

We want the Sentiment process to start at the event. So we have to make sure…

* that 'Get Text' is a Message Start event
* that it has the same message name under 'Implementation/Execution' as the corresponding Intermediate Throw Event
* that the mapping of the variables under 'Data Assignment' makes sense
* that we configure the topic correctly in the `application.properties`. The incoming channel has the key `mp.messaging.incoming.kogito_incoming_stream`, the outgoing is called `mp.messaging.outgoing.kogito_outgoing_stream`. 

https://github.com/d135-1r43/restaurant-complaints/blob/de8794ef3f2f8a2ea452f5798cd7342eb49a4c9c/de.thi.sentiment/src/main/resources/application.properties#L4-L10

👉 Make a complaint at the **Complaints Service** via Swagger UI. Switch to the **Sentiment Analysis Service** and run the user task to define the sentiment in the Dev UI. Understand why and how now the **Complaints Service** will catch the event at 'Get Sentiment'. Use the log files, the Kafka UI and the Kogito Management Console to deepen your understanding. 
