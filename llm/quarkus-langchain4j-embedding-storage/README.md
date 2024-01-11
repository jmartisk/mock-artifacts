# Usage
- Choose one of the embedding store implementations in pom.xml, adjust application.properties accordingly
- Run
- To just search for related embeddings:
  - Call `curl -G http://localhost:8080/embeddings --data-urlencode 'question=Does Charlie have a bulldozer?'`
- To ask the model a question:
  - Call `curl -G http://localhost:8080/question --data-urlencode 'question=Does Charlie have a bulldozer?'`