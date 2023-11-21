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

## Writing poems and having them sent by email

```
curl localhost:8080/poem/mushroom/5
```

This will write a poem about mushrooms, 5 lines long, and sends it to
test@test.com via the SMTP server (view at http://0.0.0.0:8025/#).

