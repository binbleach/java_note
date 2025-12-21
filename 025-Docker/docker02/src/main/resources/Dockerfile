# 基础镜像使用java
FROM openjdk:8
# 作者
MAINTAINER hjbin<2901038144@qq.com>
# VOLUME 指定临时文件目录为/tmp，在主机/var/lib/docker目录下创建了一个临时文件并链接到容器的/tmp
VOLUME /tmp/docker02-2.0.0
# 将jar包添加到容器中并更名为docker02-2.0.0.jar
ADD docker02-2.0.0.jar docker02-2.0.0.jar
# 运行jar包
RUN bash -c 'touch /docker02-2.0.0.jar'
ENTRYPOINT ["java","-jar","/docker02-2.0.0.jar"]
#暴露6001端口作为微服务
EXPOSE 6002
