# camunda-platform-8-demo
Zeebe client with Kotlin
 
## Setup
- Start the Zeebe cluster:
  ```shell
  docker-compose up -d
  ```
- Set environment variables if Zeebe is not running locally:
  ```
  ZEEBE_GRPC_ADDRESS=dns://home-server:26500
  ZEEBE_REST_ADDRESS=http://home-server:8080
  # If not using TLS
  ZEEBE_INSECURE_CONNECTION=true
  ```
