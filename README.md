# AIGC Detector: gateway
This component serves as the gateway for the AIGC Detector system.
Including this one, the components of the AIGC Detector system are as follows:
- `gateway`: (this component) serves as the main interaction point between the frontend web application and the backend services.
- [`auth`](https://github.com/muangcrys/AIGC-auth): responsible for user authentication and authorization.
- [`webservice`](https://github.com/muangcrys/AIGC-webservice): frontend web application that provides the user interface for the AIGC Detector system.
- [`image-consumer`](https://github.com/muangcrys/AIGC-image-consumer): backend worker that processes images and predicts whether they are AI-generated or not.
- [`text-consumer`](https://github.com/muangcrys/AIGC-text-consumer): backend worker that processes text and predicts whether it is AI-generated or not.

In addition, the system also requires a number of messaging and database services:
- RabbitMQ: used for messaging between the gateway and the backend workers.
- postgreSQL: used for storing user data and authentication information.
- DynamoDB: used for storing the queries and the results of the image and text analysis.

# Getting Started
