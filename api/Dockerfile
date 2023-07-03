ARG JAVA_VERSION="17"

FROM amazoncorretto:${JAVA_VERSION}

ARG PKG="sudo curl git openssh-clients tar"
ARG USER_NAME="vscode"
ARG USER_GROUP_NAME="spring"
ARG USER_UID="1000"
ARG USER_GID="1000"

SHELL ["/bin/bash", "-c"]

RUN yum update -y 
RUN echo ${PKG}
RUN yum install -y ${PKG} 
RUN groupadd --gid ${USER_GID} ${USER_GROUP_NAME}
RUN useradd --uid ${USER_UID} --gid ${USER_GROUP_NAME} --shell /bin/bash -m ${USER_NAME}
RUN echo %${USER_GROUP_NAME} ALL=\(ALL\) NOPASSWORD:ALL > /etc/sudoers.d/${USER_GROUP_NAME}
RUN chmod 0440 /etc/sudoers.d/${USER_GROUP_NAME}

COPY . /workspace
WORKDIR /workspace

RUN chown -R ${USER_NAME}:${USER_GROUP_NAME} /workspace

USER ${USER_NAME}