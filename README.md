## Приложение

Приложение для хранения ваших любимых рецептов.

##  ДЗ 1 (Тестирование Frontend)
### Main часть:

- [x] Создать репозиторий на GitHub, в котором создать папку client;
- [x] В папке client создать приложение на React;
- [x] Приложение содержит несколько страниц;
- [x] Приложение общается с node.js бэкендом;
- [x] unit тесты;
- [x] component тесты;
- [x] e2e тесты;

### Advanced часть:

- [x] Приложение содержит авторизацию;
- [x] Приложение не собрано из генераторов вида JHipster;
- [x] Написаны тесты для проверки авторизации;
- [x] Существует нескольно наборов тестов (несколько suites);

### Bonus часть:

- [x] Написаны примеры тестов с Jest и Mocha;
- [x] Написана короткая заметка на тему основных отличий между Playwright и Cypress;
- [x] Заметка размещена на [GitHub pages](https://dromanenko.github.io/itmo-testing/hw1);

### Запуск

- [**server**](server) - `port-5000`
```bash
cd server && yarn start
```
- [**client**](client) - `port-3000`
```bash
cd client && yarn start
```
- [**playwright**](client/src/tests/e2e)
```bash
cd client && yarn playwright test src/tests/e2e/*
```
- [**cypress**](client/cypress/integration/Main.spec.js)
```bash
cd client && yarn cypress run
```

## ДЗ 2 (Тестирование Backend)
### Main часть:

- [x] Создать сервис на Java+Spring+PostgreSQL, который имеет как минимум 1 Controller;
- [x] Написать Unit и Component тесты для этого сервиса;
- [x] Использовать TestContainers тестов с DB;
- [x] Использовать Mockito для мокирования тестов с внешним сервисом;
- [x] Написать [документацию](backend/TODO.md) какие тесты еще необходимо написать, но я не успел;

### Advanced часть:

- [x] Сделать взаимодействие сервиса и вашего Frontend приложения;
- [x] Сделать тесты на авторизацию;
- [x] Создать отдельные Spring Test Configruation, которые можно переключать с помощью флага при запуске тестов;
- [x] Сделать генерацию тестовой документации через Asci Doctor(Spring Rest Docs);

### Bonus часть:

- [ ] Придумать функциональность, с которой можно использовать очереди/стримы вида RabbitMQ/Kafka streams;
- [ ] Написать компонентные тесты на эту функциональность(используя TestContainers);

### Запуск
- [**database**](backend/src/main/resources/application.properties) - `port-5432`
```properties
# set username and password
spring.datasource.username=
spring.datasource.password=
```
- [**backend**](backend) - `port-5000`
```bash
cd backend && mvn spring-boot:run
```
- [**client**](client) - `port-3000`
```bash
cd client && yarn start
```
- [**test**](backend/src/test/kotlin/com/github/dromanenko/backend) - `default spring mock`
```bash
cd backend && mvn test
```
- [**test config**](backend/src/test/kotlin/com/github/dromanenko/backend/base/configuration)
```bash
# for Spring Mocks
cd backend && mvn test -D test.configuration=spring 
# for Mockito Mocks
cd backend && mvn test -D test.configuration=mockito
```
- [**asciidoc**](backend/src/main/asciidoc) - `output_dir=target/docs`
```bash
cd backend && mvn generate-resources
```

## ДЗ 3 (CI/CD (GtiHub Actions))
### Main часть:

- [X] Добавить GitHub action для запуска тестов на UI и Backend по пушу из в master ветку.

### Advanced часть:

- [ ] Добавить GitHub action для деплоя приложения UI+BE на Azure/Vercel/Яндекс Облако.

### Bonus часть:

- [ ] Использовать Kubernetes в Azure/Яндекс Облаке для разворачивания среды.