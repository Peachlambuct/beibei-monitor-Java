# 构建前端nginx镜像
FROM nginx:latest

# 删除目录下的default.conf文件
RUN rm /etc/nginx/conf.d/default.conf
RUN rm /etc/nginx/nginx.conf

# 将default.conf复制到/etc/nginx/conf.d/下，用本地的default.conf配置来替换nginx镜像里的默认配置
ADD configs/default.conf /etc/nginx/conf.d/
ADD configs/nginx.conf /etc/nginx/

# 将项目根目录所有文件复制到镜像/usr/share/nginx/html/目录下
COPY dist/ /usr/share/nginx/html/

# 启动 Nginx
CMD ["nginx", "-g", "daemon off;"]

