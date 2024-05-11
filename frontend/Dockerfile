# Stage 1
FROM node:16.13 as node
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod
# Stage 2
FROM nginx:alpine
RUN mkdir -p /opt/finnances-app
COPY ./nginx.conf /etc/nginx/default.conf
#COPY ./nginxconfig.conf /etc/nginx/conf.d/default.conf
COPY --from=node /app/dist/finnances-app /opt/finnances-app

# Expose port 80
EXPOSE 80