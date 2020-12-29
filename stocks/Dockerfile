FROM node:10 AS ui-build
WORKDIR /usr/src/app
COPY . ./stocks-service/
RUN cd stocks-service && npm install && npm run build

FROM node:10 AS server-build
WORKDIR /root/
COPY --from=ui-build /usr/src/app/stocks-service .
RUN npm install

EXPOSE 3080

CMD ["node", "./server.js"]
