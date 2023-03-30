# 基础镜像
FROM openjdk:8-jdk-alpine

# 安装 Maven
RUN apk add --no-cache curl tar bash
ARG MAVEN_VERSION=3.8.8
ARG OPENAI_KEY
ARG USER_HOME_DIR="/root"
RUN mkdir -p /usr/share/maven && \
    curl -fsSL https://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 && \
    ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

# 安装 Git
RUN apk add --no-cache git

# 拷贝项目代码
RUN git clone https://github.com/huhuaheng/openai.git /usr/src/openai
WORKDIR /usr/src/openai

# 将阿里的 Maven 源替换为默认 Maven 源
RUN sed -i 's/http:\/\/repo.maven.apache.org\/maven2/https:\/\/maven.aliyun.com\/repository\/central/g' /usr/share/maven/conf/settings.xml


# 打包应用
RUN mvn package -DskipTests

# 设置环境变量
ENV JAVA_OPTS=""
ENV APP_HOME /usr/app

# 创建目录并拷贝文件
RUN mkdir -p $APP_HOME
RUN cp target/openai-0.0.1-SNAPSHOT.jar $APP_HOME/openai-0.0.1-SNAPSHOT.jar

# 暴露端口
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "/usr/app/openai-0.0.1-SNAPSHOT.jar","--openai.token=$OPENAI_KEY"]