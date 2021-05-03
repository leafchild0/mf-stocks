FROM node:10 AS ui-build
WORKDIR /usr/src/app
COPY . ./user-account-app/
RUN cd user-account-app && npm install && npm run build

FROM node:10 AS server-build
WORKDIR /root/
COPY --from=ui-build /usr/src/app/user-account-app .
RUN npm install

EXPOSE 3006

CMD ["node", "./server.js"]
