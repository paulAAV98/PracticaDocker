FROM node

ENV NG_CLI_ANALYTICS="ci"

RUN npm install -g @angular/cli
RUN npm install -g  axios

COPY . .
EXPOSE 4200

WORKDIR front

RUN npm install

CMD cd ./front
CMD ng analytics off
CMD ng serve --host 0.0.0.0