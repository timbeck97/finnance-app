FROM openjdk:8
WORKDIR /app
COPY target/finance-0.0.1-SNAPSHOT.jar /app/finance.jar
ENTRYPOINT ["java", "-jar", "finance.jar"]


FROM node:16.13 as node
WORKDIR ../frontend
COPY . .
RUN npm install
RUN npm run build --prod
# Stage 2
FROM nginx:alpine
COPY ./nginx.conf /etc/nginx/nginx.conf
COPY --from=node /app/dist/finnances-app /usr/share/nginx/html

# Expose port 80
EXPOSE 80