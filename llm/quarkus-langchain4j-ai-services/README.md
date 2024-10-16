# Setup

## API key
Declare your OpenAI API key as the `QUARKUS_LANGCHAIN4J_OPENAI_API_KEY` variable.

## Emails
To be able to send emails, start a SMTP server:

```bash
podman run -p 8025:8025 -p 1025:1025 docker.io/mailhog/mailhog
```

Then view the sent emails at http://0.0.0.0:8025/#

# Usage

## Creating bookings and having them sent by email

```
curl -G localhost:8080/create --data-urlencode "name=John Doe" --data-urlencode "email=john@gmail.com"
```

This will create a booking, and send it to
john@gmail.com via the SMTP server (view at http://0.0.0.0:8025/#).

## Using an image model

```
curl -s -G localhost:8080/image --data-urlencode  "prompt=ant riding a bicycle" | base64 -d > image.jpg
```