# 빌드된 .jar 파일을 실행할 openjdk 베이스 이미지
FROM openjdk:17-jdk-slim

# 빌드된 .jar 파일을 복사 (실제 파일 이름 사용)
COPY build/libs/price-storage-service-0.0.1-SNAPSHOT.jar /app/main-service.jar

# 컨테이너 시작 시 실행될 명령어
ENTRYPOINT ["java", "-jar", "/app/main-service.jar"]
