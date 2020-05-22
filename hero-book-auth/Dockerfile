FROM java:11-jre-alpine

LABEL maintainer=vicker23@163.com

ENV TZ=Asia/Shanghai \
    APP_NAME="hero-book-auth.jar" \
    APP_HOME="/opt/hero-story"

#java environment,远程调试-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=56273
#默认配置512m，其他依赖镜像可修改配置
ENV JAVA_OPTS="-server -Xms512m -Xmx512m -Djava.awt.headless=true -Djava.security.egd=file:/dev/urandom"

RUN apk add --update openssl && ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY build/$APP_NAME $APP_NAME

EXPOSE 8080

WORKDIR $APP_HOME

ENTRYPOINT ["/bin/sh","-c","java $JAVA_OPTS -jar $APP_HOME/$APP_NAME"]