Setup: declare your OpenAI API key as the `QUARKUS_LANGCHAIN4J_OPENAI_API_KEY` variable.

To be able to send emails, start a SMTP server:

```bash
podman run -p 8025:8025 -p 1025:1025 docker.io/mailhog/mailhog
```

Then view the sent emails at http://0.0.0.0:8025/#