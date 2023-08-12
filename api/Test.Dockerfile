ARG JAVA_VERSION="17"

FROM amazoncorretto:${JAVA_VERSION}

WORKDIR /workspace

COPY . /workspace
