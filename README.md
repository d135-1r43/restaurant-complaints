# PDA Demo Application

This repository contains a small and simple PDA demo application written in 
[Quarkus](https://quarkus.io) with the process engine [Kogito](https://kogito.kie.org/).

It consists of two services:

* `de.thi.complaints` is a Quarkus service for the business process where a restaurant complaint is sesnt
* `de.thi.sentiment` is a Quarkus service of the integration process where the sentiment of the complaint is determined. For the sake of simplicity it is a simple user task, yet using something like ChatGPT would be the real-world scenario.