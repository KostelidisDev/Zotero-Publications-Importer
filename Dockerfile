FROM maven:3-amazoncorretto-21-alpine AS builder
WORKDIR /opt/src
COPY . .
RUN mvn package install

FROM alpine:3
WORKDIR /root/.m2/repository/gr/ihu/ict/zotero-publications-importer
COPY --from=builder /root/.m2/repository/gr/ihu/ict/zotero-publications-importer /root/.m2/repository/gr/ihu/ict/zotero-publications-importer