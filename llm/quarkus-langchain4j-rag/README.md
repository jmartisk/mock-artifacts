# Usage
- Choose one of the embedding store implementations in pom.xml, adjust application.properties accordingly
- Run

## To just search for related embeddings:
```
curl -G http://localhost:8080/embeddings --data-urlencode 'question=How much does a CyberHouse cost?'
```

## To ask the model a question:
```
curl -G http://localhost:8080/question --data-urlencode 'question=How much does a CyberHouse cost?'
```

## Chatting via the web page
Open `http://localhost:8080/`

## Chatting with the websocket directly via wscat
```
wscat -w 300 -P -c "ws://127.0.0.1:8080/chatbot"
... and just enter your questions
```

## Accessing the database manually

### PgVector
??? TODO

### Redis
```
redis-cli
FT.SEARCH "embedding-index" "*" LIMIT 0 10
```