# NGINX base image
FROM nginx:1.18.0-alpine

# Install curl
RUN apk add curl --no-cache

COPY /nginx/default.conf /etc/nginx/conf.d/default.conf
COPY /dist/ /usr/share/nginx/html/

# Export port
EXPOSE 8700

# Start application
CMD [ "nginx", "-g", "daemon off;" ]
