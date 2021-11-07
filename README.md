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