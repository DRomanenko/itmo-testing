# Настройка тестов с Selenide.

## Создание проекта для тестирования

1. Создадим **Gradle**-проект;
2. Изменим `build.gradle`:
    - Включим плагин `io.qameta.allure`:
    ```kotlin
    plugins {
        id 'io.qameta.allure' version '2.6.0'
    }
    ```
    - Добавим необходимые зависимости:
    ```kotlin
    def allureVersion = '2.17.2'
    def junitVersion = '5.8.2'
    
    dependencies {
    testImplementation 'org.jetbrains.kotlin:kotlin-stdlib:1.6.0'
    testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0'
    testImplementation 'com.codeborne:selenide:6.1.2'
    
        testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    
        testRuntimeOnly("io.qameta.allure:allure-junit5:$allureVersion")
        testCompileOnly("io.qameta.allure:allure-java-commons:$allureVersion")
        testCompileOnly("io.qameta.allure:allure-attachments:$allureVersion")
        testCompileOnly("io.qameta.allure:allure-generator:$allureVersion")
        testCompileOnly("io.qameta.allure:allure-httpclient:$allureVersion")
    }
    ```
    - Добавим необходимые параметры по примеру:
      ```kotlin
         'selenide.<param>' : System.getProperty("selenide.<param>") ?: '<default>'
      ```

   Пример: [**build.gradle**](build.gradle)

3. Напишем тест с использованием **Selenide**

   Пример: 
   - [**AuthTest**](src/test/kotlin/com/github/dromanenko/selenide/AuthTest.kt)
   - [**RecipesTest**](src/test/kotlin/com/github/dromanenko/selenide/RecipesTest.kt)

## Allure

1. С текущей конфигурацией результаты тестов будут в папке `$projectDir/build/allure-results`;
2. Для просмотра отчета `gradle allureServe`.

## Selenoid

1. Если используете не стандартный `Selenoid` сервер:
    - `'selenide.remote' : System.getProperty("selenide.remote") ?: 'http://localhost:4444/wd/hub'`
2. Поднять `Selenoid` (см. [**инструкцию**](https://aerokube.com/selenoid/latest/)):
    - Скачать https://github.com/aerokube/cm/releases/latest;
    - Запустить `Docker` и выполнить `.\cm_windows_amd64.exe selenoid start --vnc`;
3. При запуске тестов они будут работать на заданном `Selenoid` сервере.

## GitHub Actions

---

- `GitHub Actions` поднимает приложение, стартует `Selenoid` сервер, на фоне которого запускаются тесты;

---

1. Настроим **workflow**:
   ```yaml
    Selenide-Tests:
    name: Selenide Tests - ${{ matrix.browser }}
    runs-on: ubuntu-latest
    
        strategy:
          fail-fast: false
          matrix:
            browser: [ chrome, firefox ]
    
        services:
          postgres:
            image: postgres:14
            env:
              POSTGRES_USER: postgres
              POSTGRES_PASSWORD: postgres
              POSTGRES_DB: postgres
            ports:
              - 5432:5432
            options: --health-cmd pg_isready --health-interval 10s --health-timeout 5s --health-retries 5
        needs: [ Backend-Tests, Frontend-Tests ]
        steps:
          - name: Checkout
            uses: actions/checkout@v2
    
          - name: Setup Node.js (v${{ env.NODE_VERSION }})
            uses: actions/setup-node@v2
            with:
              node-version: ${{ env.NODE_VERSION }}
    
          - name: Install dependencies
            working-directory: client
            run: npm install
    
          - name: Setup JDK-${{ env.JDK_VERSION }}
            uses: actions/setup-java@v2
            with:
              distribution: 'temurin'
              java-version: ${{ env.JDK_VERSION }}
              cache: 'maven'
    
          - name: Start Backend
            working-directory: backend
            run: mvn spring-boot:run &
    
          - name: Run Cypress Tests
            uses: cypress-io/github-action@v2
            with:
              browser: chrome
              working-directory: client
              start: npm start
              wait-on: 'http://localhost:5000/recipes, http://localhost:3000'
              wait-on-timeout: 120
              headless: true
    
          - name: Start Selenoid
            uses: Xotabu4/selenoid-github-action@v2
            with:
              selenoid-start-arguments: |
                --args "-timeout 100s" --browsers="chrome;firefox"
    
          - name: Run Selenide Tests
            working-directory: selenide
            run: gradle test -Dselenide.browser=${{ matrix.browser }}
    
          - name: Upload Allure Reports Artifact
            if: always()
            uses: actions/upload-artifact@v2
            with:
              name: selenide-allure-results-${{ matrix.browser }}
              path: selenide/build/allure-results
   ```
   Отчеты по отдельным запускам на разных браузерах будут собраны в отдельные артефакты.
2. Можно добавить `job` в `workflow` для сборки артефактов в один отчет: 

   Пример: [build-and-test.yml](../.github/workflows/github-actions-build-test.yml)