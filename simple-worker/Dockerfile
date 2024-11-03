FROM eclipse-temurin:21-jdk-alpine as builder
WORKDIR /opt/app
COPY build/distributions/simple-worker.zip /opt/app
RUN unzip simple-worker.zip

FROM eclipse-temurin:21-jre-alpine
ARG ZEEBE_ADDRESS
ENV ZEEBE_ADDRESS=$ZEEBE_ADDRESS
WORKDIR /opt/app
COPY --from=builder /opt/app .
CMD simple-worker/bin/simple-worker

