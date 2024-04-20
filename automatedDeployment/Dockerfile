# 使用基础的Java镜像，构建基jdk17的beibei-monitor镜像
FROM xldevops/jdk17-lts

# 将构建的 JAR 文件复制到容器中
COPY beibei-monitor-server-0.0.1-SNAPSHOT.jar /usr/src/myapp/app.jar

# 运行 Spring Boot 应用程序
ENTRYPOINT ["java","-jar","/usr/src/myapp/app.jar"]
