# Beibei监控系统 (Beibei Monitoring System)

## 项目介绍

Beibei监控系统是一个全面的服务器监控管理平台，用于实时监控和管理服务器资源、性能指标和系统状态。系统由三个主要组件构成：

1. **服务端 (Spring Boot)**: 处理数据存储、分析和API服务
2. **数据采集器 (Rust)**: 高效收集被监控服务器的性能指标和系统数据
3. **前端 (Vue + Quasar UI)**: 提供直观、美观的用户界面，展示监控数据和管理功能

## 系统架构

### 服务端 (beibei-monitor-server)
- 基于Spring Boot框架开发
- 接收、处理和存储来自数据采集器的监控数据
- 提供RESTful API和WebSocket接口
- 数据存储：MySQL(结构化数据)、InfluxDB(时序数据)
- 消息队列：RabbitMQ
- 缓存：Redis

### 数据采集器 (beibei-monitor-client)
- 使用Rust语言开发，高性能、低资源占用
- 采集系统指标：CPU使用率、内存使用情况、磁盘I/O、网络流量等
- 支持多平台部署
- 实时向服务端推送数据

### 前端 (beibei-monitor-web-final)
- 使用Vue.js框架和Quasar UI组件库开发
- 响应式设计，支持多设备访问
- 实时数据可视化
- 系统管理和配置界面

## 部署指南

### 使用Docker Compose部署

系统支持通过Docker Compose一键部署，包含所有必要组件。

#### 前提条件
- Docker 19.03+
- Docker Compose 1.27+
- 至少2GB可用内存
- 至少10GB可用磁盘空间

#### 部署步骤

1. 克隆项目仓库
   ```bash
   git clone [仓库地址]
   cd beibei-monitor-jwt
   ```

2. 准备后端服务构建
   ```bash
   # 将编译好的JAR文件放入automatedDeployment目录
   cp beibei-monitor-server/target/beibei-monitor-server-0.0.1-SNAPSHOT.jar automatedDeployment/
   ```

3. 准备前端构建
   ```bash
   # 确保前端构建文件已经准备好
   cp -r beibei-monitor-web-final/dist automatedDeployment/nginx/
   ```

4. 启动所有服务
   ```bash
   cd automatedDeployment
   docker-compose up -d
   ```

5. 访问系统
   - 前端界面: http://localhost:80
   - 后端API: http://localhost:8080
   - InfluxDB: http://localhost:8086
   - RabbitMQ管理界面: http://localhost:15672 (用户名: admin, 密码: admin)

### 手动部署

#### 服务端部署
1. 确保已安装JDK 17或更高版本
2. 配置MySQL、InfluxDB、Redis和RabbitMQ
3. 修改配置文件中的连接参数
4. 运行JAR文件
   ```bash
   java -jar beibei-monitor-server-0.0.1-SNAPSHOT.jar
   ```

#### 数据采集器部署
1. 在需要监控的服务器上运行数据采集器
   ```bash
   ./beibei-monitor-client.exe
   ```
2. 配置采集器连接到监控服务端

#### 前端部署
1. 使用Nginx或其他Web服务器部署前端静态文件
2. 配置API反向代理

## 系统配置

### 数据库配置
- MySQL: 用于存储用户数据、配置信息和关系型数据
- InfluxDB: 用于存储时序监控数据

### 消息队列配置
- RabbitMQ: 用于服务组件间通信和数据缓冲

### 缓存配置
- Redis: 用于缓存常用数据和提高系统响应速度

## 安全建议

1. 修改默认密码，特别是MySQL和RabbitMQ的默认登录凭据
2. 配置防火墙，限制对管理接口的访问
3. 定期更新系统组件
4. 考虑为Web界面配置HTTPS

## 常见问题

1. **Q: 数据采集器无法连接到服务器**
   A: 检查网络连接和防火墙设置，确保服务器端口已开放

2. **Q: 监控数据显示延迟**
   A: 检查InfluxDB性能和网络延迟，可能需要调整采集频率

3. **Q: 系统占用资源过高**
   A: 调整数据采集间隔和保留策略，优化数据库查询
