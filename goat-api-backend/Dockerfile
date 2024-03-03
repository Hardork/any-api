# Docker 镜像构建
# @author <a href="https://github.com/Hardork">老山羊</a>
#
# Run the web service on container startup.

# Docker 镜像构建
# @author <a href="https://github.com/Hardork">老山羊</a>
#
FROM openjdk:8
VOLUME /home/api/interface
ADD goat-api-backend-0.0.1.jar goat-api-backend-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/goat-api-backend-0.0.1.jar", "--spring.profiles.active=prod", "-DDUBBO_IP_TO_BIND=121.36.48.205"]