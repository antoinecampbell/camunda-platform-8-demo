FROM eclipse-temurin:21-jdk-alpine as builder
WORKDIR /opt/app
COPY build/distributions/ktor-worker.zip /opt/app
RUN unzip ktor-worker.zip

FROM eclipse-temurin:21-jre-alpine
ARG ZEEBE_GRPC_ADDRESS
ARG ZEEBE_REST_ADDRESS
ENV ZEEBE_GRPC_ADDRESS=$ZEEBE_GRPC_ADDRESS
ENV ZEEBE_REST_ADDRESS=$ZEEBE_REST_ADDRESS
WORKDIR /opt/app
COPY --from=builder /opt/app .
CMD ktor-worker/bin/ktor-worker

