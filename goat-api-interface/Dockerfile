# Docker 镜像构建
# @author <a href="https://github.com/Hardork">老山羊</a>
#
FROM openjdk:8
VOLUME /home/api/interface
ADD goat-api-interface-0.0.1.jar goat-api-interface-0.0.1.jar
EXPOSE 8123
ENTRYPOINT ["java","-jar", "/goat-api-interface-0.0.1.jar", "--spring.profiles.active=prod"]
