# Dockerfile-backend
# 使用 Maven 镜像作为构建阶段
FROM maven:3.8.1-openjdk-17-slim AS build

# 将源代码复制到容器中
COPY src /home/app/src
COPY pom.xml /home/app

# 在容器中构建应用
RUN mvn -f /home/app/pom.xml clean package

# 使用 OpenJDK 镜像作为运行阶段
FROM openjdk:17-jre-slim

# 将构建的 JAR 文件复制到容器中
COPY --from=build /home/app/target/my-app-1.0-SNAPSHOT.jar /usr/local/lib/my-app.jar

# 设置容器的入口点为 Java 应用的主类
ENTRYPOINT ["java","-jar","/usr/local/lib/my-app.jar"]