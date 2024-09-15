# Email Service with Kafka Integration

## Overview

The Email Service is a microservice designed to handle email sending with support for asynchronous processing via Kafka. This service allows you to send emails directly through REST endpoints or by producing messages to a Kafka topic. It leverages Spring Boot for application management and Kafka for message queuing, making it scalable and efficient for high-throughput email operations.

## How It Works

### Email Sending Mechanisms

1. **Direct Email Sending**:
   - **Endpoint**: `/email/send-email`
   - **Description**: Allows you to send an email directly by providing recipient details, subject, body, and attachments.
   - **Request Parameters**:
     - `to`: Recipient's email address.
     - `file`: Optional attachment file.
   - **Response**: Success or failure status of the email sending operation.

2. **Kafka-Based Email Sending**:
   - **Kafka Topic**: Configured with the topic name `CONSTANTS.KEY_KAFKA_EMAIL_TOPIC`.
   - **Description**: Sends email requests to a Kafka topic. The service listens to this topic, processes the messages, and sends emails accordingly.
   - **Message Format**: JSON payload that matches the `EmailerInfoForKafka` DTO.

### Kafka Integration

#### Kafka Producer

The producer is responsible for sending messages to Kafka topics. Here’s how to use it:

- **Message Format**: 
  ```json
  {
    "recipientsList": ["recipient@example.com"],
    "mailBody": "Hello, this is a test email.",
    "subject": "Test Email Subject"
  }
  ```

- **Kafka Configuration**:
  Configure the producer with the Kafka bootstrap servers and the serializer settings for key and value.
  ```yaml
  kafka:
    bootstrap-servers: ${KAFKA_SERVER:localhost:9092}
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
  ```

#### Kafka Consumer

The consumer processes messages from the Kafka topic and sends emails. It is configured to handle both string messages and JSON messages formatted as `EmailerInfoForKafka`.

- **Consumer Configuration**:
  ```yaml
  kafka:
    consumer:
      json:
        group-id: json-group
        auto-offset-reset: earliest
        key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
        value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
        properties:
          spring.json.value.default.type: in.rajlabs.EmailService.dto.EmailerInfoForKafka
  ```

## Integration Guidelines for Other Microservices

### Producing Messages

To produce messages to the Kafka topic:

1. **Message Format**:
   Use the `EmailerInfoForKafka` DTO format to structure your messages:
   ```json
   {
     "recipientsList": ["recipient@example.com"],
     "mailBody": "Hello, this is a test email.",
     "subject": "Test Email Subject"
   }
   ```

2. **Kafka Producer Setup**:
   Configure your Kafka producer to send messages to the correct topic. Make sure to use the JSON serializer for the message value.

### Consuming Messages

To consume messages and integrate with the Email Service:

1. **Kafka Consumer Setup**:
   Ensure that your Kafka consumer is configured to read from the same topic and can handle both string and JSON formats. Configure deserializers accordingly.

2. **Message Handling**:
   The service uses the `KafkaConsumerService` class to handle messages. If the message is in JSON format and matches the `EmailerInfoForKafka` DTO, it will be processed to send emails. Otherwise, it will be logged as an error.

### Configuration

Ensure the following environment variables and application properties are set:

- `KAFKA_SERVER`: Kafka server address (e.g., `192.168.1.1:9092`).
- `EMAIL_USERNAME`: Email username for authentication.
- `EMAIL_PASSWORD`: Email password for authentication.

#### Example Configuration in `application.yml`:

```yaml
kafka:
  bootstrap-servers: ${KAFKA_SERVER:localhost:9092}
  consumer:
    json:
      group-id: json-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.value.default.type: in.rajlabs.EmailService.dto.EmailerInfoForKafka
  producer:
    key-serializer: org.apache.kafka.common.serialization.StringSerializer
    value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
```

## Testing

1. **Direct Email Sending**:
   - Use the `/email/send-email` endpoint to test direct email sending.

2. **Kafka-Based Email Sending**:
   - Use Kafka tools or your Kafka producer setup to send test messages to the configured Kafka topic. Ensure the messages are in the correct format.

## Troubleshooting

- **Kafka Connection Issues**: Verify Kafka server is running and accessible. Check network configurations and Kafka settings.
- **Message Parsing Errors**: Ensure Kafka messages are correctly formatted. Check logs for parsing errors and adjust configurations if needed.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

