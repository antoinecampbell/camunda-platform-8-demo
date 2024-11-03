# camunda-platform-8-demo
Zeebe client with Kotlin
 
## Setup
- Start the Zeebe cluster:
  ```shell
  docker-compose up -d
  ```
- Build worker image
    ```shell
    cd ktor-worker
    docker build --platform linux/amd64 -t ghcr.io/antoinecampbell/ktor-worker:$(cat ../version.txt) .
    docker push ghcr.io/antoinecampbell/ktor-worker:$(cat ../version.txt)
    ```
- Set environment variables if Zeebe is not running locally:
  ```
  ZEEBE_GRPC_ADDRESS=http://home-server:26500
  ZEEBE_REST_ADDRESS=home-server:8080
  ```
