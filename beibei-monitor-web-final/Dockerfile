# 使用 Nginx 镜像作为运行阶段
FROM nginx:stable-alpine

# 将构建的文件复制到 Nginx 中
COPY  ./dist /usr/share/nginx/html

# 暴露端口
EXPOSE 80

# 启动 Nginx
CMD ["nginx", "-g", "daemon off;"]