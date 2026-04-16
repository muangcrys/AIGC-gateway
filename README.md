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
To run the complete system, you need to set up all the components and services.

## Prerequisites
To run RabbitMQ, postgreSQL, and DynamoDB, you can use Docker. Make sure you have Docker installed on your machine.
The specifications are supplied in the `docker-compose.yml` file. Simply run the following command in the terminal:
```bash
docker-compose up
```
This will start all the required services.

## Components Setup
Each repository on GitHub contains a docker image that you can use to run the components. The images are listed under
the packages section of the respective repositories. Information about how to pull the images are also available in the page.

If you prefer to create the images yourself, each repository contains a `Dockerfile` that you can use to build the image.
Additionally, the commands to build and run the images are also available in the respective repositories, inside `docker_commands.sh` files.
The commands will create the images and run the containers with default variables (not intended for production). You can modify the configurations
through environment variables.

## External Resources
The system relies on the following external resources:
- `image-consumer` relies on the [`Organika/sdxl-detector`](https://huggingface.co/Organika/sdxl-detector) model from Hugging Face for image analysis. If you want to build the image yourself, you must download the model (e.g. through `huggingface-cli`) and place it under `./sdxl-detector` directory.
- `text-consumer` relies on the [`fakespot/roberta-base-ai-text-detection-v1`](https://huggingface.co/fakespot-ai/roberta-base-ai-text-detection-v1) model. If you want to build the image yourself, you must download the model and place it under `./fakespot-ai-roberta-base` directory.

These directory names are fixed (see the respective `Dockerfile`s) and must be in the same directory as the `Dockerfile`s when you build the images.

# Using the System
Once you have all the components and services running, you can access the frontend web application at `http://localhost:80` (if you are using the default configurations). From there, you can register an account, log in, and start using the AIGC Detector system to analyze images and text.