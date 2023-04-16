# Simplified PDA Demo Application Overview

This repository contains a basic Process Driven Applications (PDA) demo application created using [Quarkus](https://quarkus.io) and the [Kogito](https://kogito.kie.org/) process engine. The purpose of this demo is to demonstrate key concepts of Process Driven Applications.

The application includes three services:

1. **Complaints Service** (`de.thi.complaints`): A Quarkus service that handles restaurant complaint submissions.
2. **Sentiment Analysis Service** (`de.thi.sentiment`): A Quarkus service that assesses the sentiment of the complaint. For simplicity, this demo uses a basic user task, but in a real-world scenario, a more advanced tool like ChatGPT could be used.
3. **Archive Service** (`de.thi.archiv`, optional): A Quarkus service with a simple REST API to store complaints. This service can be integrated into the Complaints Service.

## Walkthrough Complaint Process

![Complaint Business Process](/documentation/complaints-process.png)

👉 Open the file https://github.com/d135-1r43/restaurant-complaints/blob/master/de.thi.complaints/src/main/resources/complaints.bpmn in VS Code and analyse the properties of the process, especially the process variables under 'Process Data' to get a better understanding. 

The Complaint Process uses several best-practice patterns. 

### Asynchronous implemention via Intermediate Message Event

In 'Ask for Sentiment' the process will throw a message and will immediatly wait for the returned message in 'Get Sentiment'. The process is agnostic of the actual implementation. The sentiment definition could be done manually or via an NLP -- the process simply does not care. 

To achieve this kind of decoupling we will have to define several things:

* In 'Ask for Sentiment' we will have to define a message. We do this in the BPMN editor UI and enter the freetext name `text` at the field 'Message' in the dropdown 'Implementation/Execution'.
* In 'Data Assignments' we will define the variable `complaintText` to be assigned to the message. 

👉 Analyse the properties of 'Ask for Sentiment' now in VS Code. 
