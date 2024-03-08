FROM maven:3-openjdk-8-slim as builder
WORKDIR /opt/src
COPY . .
RUN mvn package install
FROM alpine
WORKDIR /root/.m2/repository/gr/ihu/ict/zotero-publications-importer
COPY --from=builder /root/.m2/repository/gr/ihu/ict/zotero-publications-importer /root/.m2/repository/gr/ihu/ict/zotero-publications-importer