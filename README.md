# Проект автоматизированного тестирования

![Banner](assets/banner.svg)

Проект содержит автоматизированные тесты для веб-приложения, мобильного приложения и API.

## Структура проекта

```
JavaTestTaskFinal/
├── src/
│   ├── main/
│   │   └── java/
│   └── test/
│       ├── java/
│       │   ├── pages/
│       │   │   ├── web/          # Page Objects для веб-тестов
│       │   │   └── mobile/        # Page Objects для мобильных тестов
│       │   ├── tests/
│       │   │   ├── web/           # Веб-тесты для habr.com
│       │   │   ├── mobile/        # Мобильные тесты для Wikipedia
│       │   │   └── api/           # API-тесты
│       │   └── utils/             # Утилиты (WebDriverManager, ConfigReader)
│       └── resources/
│           ├── config.properties  # Конфигурационные параметры
│           └── testng.xml         # Конфигурация TestNG
├── pom.xml                        # Maven конфигурация
└── README.md                      # Документация
```

## Технологический стек

- **Язык**: Java 17
- **Сборка**: Maven
- **Веб-тестирование**: Selenium WebDriver 4.15.0, TestNG 7.8.0
- **Мобильное тестирование**: Appium Java Client 9.0.0, TestNG 7.8.0
- **API-тестирование**: REST Assured 5.3.2
- **Управление драйверами**: WebDriverManager 5.6.2

## Требования

### Общие требования
- JDK 17 или выше
- Maven 3.6+
- Git

### Для веб-тестирования
- Браузер Chrome, Firefox или Edge (WebDriverManager автоматически загрузит драйверы)

### Для мобильного тестирования
- Appium Server (локальный или через Appium Desktop)
- Android SDK
- Android эмулятор или реальное устройство
- Установленное приложение Wikipedia на устройстве/эмуляторе

## Установка и настройка

### Шаг 1: Проверка системных требований

Убедитесь, что установлены следующие компоненты:

1. **JDK 17 или выше**
   ```bash
   java -version
   ```
   Должна отображаться версия 17 или выше.

2. **Maven 3.6+**
   ```bash
   mvn -version
   ```
   Должна отображаться версия 3.6 или выше.

3. **Браузер Chrome, Firefox или Edge** (для веб-тестов)
   - Браузер должен быть установлен в системе
   - WebDriverManager автоматически загрузит необходимые драйверы

### Шаг 2: Клонирование проекта (если используется Git)
```bash
git clone https://github.com/Zhidkov-Aleksandr/JavaTestTaskFinal.git
cd JavaTestTaskFinal
```

### Шаг 3: Установка зависимостей Maven

Выполните команду для загрузки всех зависимостей:
```bash
mvn clean install
```

Эта команда:
- Загрузит все зависимости из `pom.xml` (Selenium, TestNG, Appium, REST Assured, WebDriverManager)
- Скомпилирует исходный код
- Запустит тесты (если не хотите запускать тесты на этом этапе, используйте `mvn clean install -DskipTests`)

### Шаг 4: Настройка конфигурации

Откройте файл `src/test/resources/config.properties` и настройте параметры:

```properties
# Web Testing Configuration
web.base.url=https://habr.com
web.browser=chrome
web.timeout=10

# Mobile Testing Configuration
appium.server.url=http://localhost:4723
android.platform.name=Android
android.platform.version=11.0
android.device.name=emulator-5554
android.app.package=org.wikipedia
android.app.activity=org.wikipedia.main.MainActivity
android.app.path=

# API Testing Configuration
api.base.url=https://api.github.com
```

**Параметры для веб-тестов:**
- `web.base.url` - URL тестируемого сайта (по умолчанию: https://habr.com)
- `web.browser` - браузер для тестов (chrome, firefox, edge)
- `web.timeout` - таймаут ожидания элементов в секундах

**Параметры для мобильных тестов:**
- `appium.server.url` - URL Appium сервера (по умолчанию: http://localhost:4723)
- `android.platform.name` - название платформы (Android)
- `android.platform.version` - версия Android (например, 11.0)
- `android.device.name` - имя устройства/эмулятора (проверьте через `adb devices`)
- `android.app.package` - package приложения Wikipedia (org.wikipedia)
- `android.app.activity` - главная activity приложения
- `android.app.path` - путь к APK файлу (если нужно установить приложение автоматически)

## Пошаговая инструкция для веб-тестов

### Подготовка окружения для веб-тестов

1. **Установите браузер** (Chrome, Firefox или Edge)
   - Chrome рекомендуется для стабильности тестов
   - Браузер должен быть установлен в системе по умолчанию

2. **Настройте config.properties**
   - Укажите браузер в `web.browser=chrome` (или firefox, edge)
   - Убедитесь, что `web.base.url=https://habr.com` корректен

3. **Проверьте подключение к интернету**
   - Сайт habr.com должен быть доступен

### Запуск веб-тестов

**Вариант 1: Запуск всех веб-тестов через Maven**

**Для Windows PowerShell:**
```powershell
mvn test "-Dtest=tests.web.*"
```

**Для Linux/Mac или Git Bash:**
```bash
mvn test -Dtest=tests.web.*
```

**Альтернативный способ (работает везде):**
```bash
mvn test -Dtest=tests.web.HomePageTest,tests.web.SearchTest,tests.web.NavigationTest,tests.web.ArticleTest
```

**Вариант 2: Запуск конкретного тестового класса**
```bash
mvn test -Dtest=HomePageTest
mvn test -Dtest=SearchTest
mvn test -Dtest=NavigationTest
mvn test -Dtest=ArticleTest
```

**Вариант 3: Запуск через TestNG XML**
```bash
mvn test
```
Тесты запустятся согласно конфигурации в `src/test/resources/testng.xml`

**Вариант 4: Запуск через IDE (IntelliJ IDEA)**
1. Откройте проект в IntelliJ IDEA
2. Откройте файл `src/test/resources/testng.xml`
3. Правый клик → Run 'testng.xml'
4. Или откройте любой тестовый класс и нажмите Run

**Ожидаемый результат:**
- Браузер автоматически откроется
- WebDriverManager загрузит необходимый драйвер
- Тесты выполнятся последовательно
- Отчеты будут сохранены в `test-output/`

## Пошаговая инструкция для мобильных тестов

### Подготовка окружения для мобильных тестов

#### Шаг 1: Установка Android SDK

1. Скачайте и установите **Android Studio**
   - Скачать можно с официального сайта: https://developer.android.com/studio
   - Установите Android SDK и инструменты командной строки

2. Настройте переменные окружения:
   ```bash
   # Windows
   set ANDROID_HOME=C:\Users\<YourUser>\AppData\Local\Android\Sdk
   set PATH=%PATH%;%ANDROID_HOME%\platform-tools;%ANDROID_HOME%\tools
   
   # Linux/Mac
   export ANDROID_HOME=$HOME/Android/Sdk
   export PATH=$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools
   ```

3. Проверьте установку:
   ```bash
   adb version
   ```

#### Шаг 2: Настройка Android эмулятора

1. **Создайте эмулятор в Android Studio:**
   - Откройте Android Studio
   - Tools → Device Manager
   - Create Device
   - Выберите устройство (например, Pixel 4)
   - Выберите системный образ (рекомендуется API 30 или выше)
   - Завершите создание эмулятора

2. **Запустите эмулятор:**
   - В Device Manager нажмите Play рядом с созданным эмулятором
   - Дождитесь полной загрузки Android

3. **Проверьте подключение:**
   ```bash
   adb devices
   ```
   Должно отобразиться что-то вроде:
   ```
   List of devices attached
   emulator-5554    device
   ```
   Запомните имя устройства (например, `emulator-5554`)

#### Шаг 3: Установка приложения Wikipedia

**Вариант 1: Установка через Google Play (рекомендуется)**
1. Откройте Google Play на эмуляторе
2. Найдите и установите приложение "Wikipedia"
3. Убедитесь, что приложение установлено и запускается

**Вариант 2: Установка через APK**
1. Скачайте APK файл Wikipedia с официального источника
2. Установите через adb:
   ```bash
   adb install path/to/wikipedia.apk
   ```

**Вариант 3: Автоматическая установка через тесты**
1. Поместите APK файл в папку проекта
2. Укажите полный путь в `config.properties`:
   ```properties
   android.app.path=C:/path/to/wikipedia.apk
   ```

#### Шаг 4: Установка и запуск Appium Server

**Вариант 1: Установка через npm (рекомендуется)**
```bash
npm install -g appium
npm install -g appium-doctor
appium-doctor --android
```

**Вариант 2: Использование Appium Desktop**
1. Скачайте Appium Desktop: https://github.com/appium/appium-desktop/releases
2. Установите и запустите Appium Desktop
3. Нажмите "Start Server" (по умолчанию на порту 4723)

**Вариант 3: Установка через npm с UiAutomator2**
```bash
npm install -g appium
appium driver install uiautomator2
```

**Запуск Appium Server:**
```bash
appium
```
Должно появиться сообщение: `[Appium] Appium REST http interface listener started on 0.0.0.0:4723`

#### Шаг 5: Настройка config.properties для мобильных тестов

Откройте `src/test/resources/config.properties` и настройте:

```properties
# Mobile Testing Configuration
appium.server.url=http://localhost:4723
android.platform.name=Android
android.platform.version=11.0
android.device.name=emulator-5554
android.app.package=org.wikipedia
android.app.activity=org.wikipedia.main.MainActivity
android.app.path=
```

**Важно:**
- `android.device.name` - должно совпадать с выводом `adb devices`
- `android.platform.version` - версия Android на эмуляторе
- `android.app.package` и `android.app.activity` - для Wikipedia обычно не требуют изменений

**Определение appPackage и appActivity:**
Если нужно определить правильные значения:
```bash
adb shell dumpsys window | grep -E 'mCurrentFocus|mFocusedApp'
```
Или используйте Appium Inspector для проверки.

### Запуск мобильных тестов

**Вариант 1: Запуск всех мобильных тестов**

**Перед запуском убедитесь:**
1. Appium Server запущен (в отдельном терминале: `appium`)
2. Android эмулятор запущен и подключен (проверьте: `adb devices`)
3. Приложение Wikipedia установлено на эмуляторе

**Для Windows PowerShell (рекомендуется):**
```powershell
# Указать конкретные классы - самый надежный способ
mvn test -Dtest=tests.mobile.SearchTest,tests.mobile.ArticleTest
```

**Для Linux/Mac или Git Bash:**
```bash
# Указать конкретные классы
mvn test -Dtest=tests.mobile.SearchTest,tests.mobile.ArticleTest

# Или использовать паттерн (может не работать с TestNG XML)
mvn test -Dtest=tests.mobile.*
```

**Примечание:** Если тесты не находятся при использовании паттерна `*`, используйте явное указание классов через запятую.

**Вариант 2: Запуск конкретного тестового класса**
```bash
mvn test -Dtest=SearchTest
mvn test -Dtest=ArticleTest
```

**Вариант 3: Запуск через TestNG XML**
```bash
mvn test
```

**Вариант 4: Запуск через IDE**
1. Убедитесь, что Appium Server запущен
2. Убедитесь, что эмулятор запущен и подключен
3. Откройте тестовый класс в IDE
4. Нажмите Run

**Ожидаемый результат:**
- Appium подключится к эмулятору
- Приложение Wikipedia откроется автоматически
- Тесты выполнятся последовательно
- Отчеты будут сохранены в `test-output/`

**Если тесты пропускаются (Skipped):**
1. Проверьте, что Appium Server запущен: откройте http://localhost:4723/status в браузере
2. Проверьте подключение эмулятора: `adb devices` (должно показать устройство)
3. Убедитесь, что `android.device.name` в `config.properties` совпадает с выводом `adb devices`
4. Проверьте, что приложение Wikipedia установлено на эмуляторе
5. Проверьте логи тестов для детального сообщения о причине пропуска

### Проверка настройки мобильного окружения

Перед запуском тестов убедитесь:

1. ✅ **Android эмулятор запущен и загружен**
   - Эмулятор должен быть полностью загружен (не в процессе загрузки)
   - Проверьте в Android Studio Device Manager

2. ✅ **Приложение Wikipedia установлено**
   - Установите через Google Play на эмуляторе
   - Или через APK: `adb install path/to/wikipedia.apk`

3. ✅ **Appium Server запущен на порту 4723**
   - Проверьте: откройте http://localhost:4723/status в браузере
   - Должен вернуться JSON с `"ready": true`

4. ✅ **Эмулятор подключен через ADB**
   - Выполните: `adb devices`
   - Должно показать устройство (например: `emulator-5554    device`)
   - Если устройство не показывается, перезапустите эмулятор

5. ✅ **`config.properties` настроен правильно**
   - `android.device.name` должен совпадать с выводом `adb devices`
   - `android.platform.version` должна соответствовать версии Android на эмуляторе
   - `android.app.package` и `android.app.activity` должны быть правильными для Wikipedia

6. ✅ **Имя устройства в config.properties совпадает с выводом `adb devices`**
   - Если `adb devices` показывает `emulator-5554`, то в config.properties должно быть `android.device.name=emulator-5554`

### Диагностика проблем с мобильными тестами

Если тесты пропускаются (Skipped), выполните следующие проверки:

1. **Проверка Appium Server:**
   ```powershell
   # Откройте в браузере или через curl
   curl http://localhost:4723/status
   ```
   Должен вернуться JSON с `"ready": true`

2. **Проверка подключения эмулятора:**
   ```powershell
   adb devices
   ```
   Должно показать устройство в статусе `device`

3. **Проверка установки Wikipedia:**
   ```powershell
   adb shell pm list packages | findstr wikipedia
   ```
   Должно показать `package:org.wikipedia`

4. **Проверка настроек:**
   - Откройте `src/test/resources/config.properties`
   - Убедитесь, что `android.device.name` совпадает с выводом `adb devices`
   - Проверьте, что `android.platform.version` соответствует версии Android на эмуляторе

## Запуск тестов

> **Важно для Windows PowerShell:** При использовании шаблонов с `*` (звездочкой) в PowerShell необходимо заключать параметр `-Dtest` в двойные кавычки, например: `mvn test "-Dtest=tests.web.*"`. В Linux/Mac или Git Bash кавычки не требуются.

### Общий запуск всех тестов
```bash
mvn test
```
**Примечание:** Эта команда запустит все тесты (веб, мобильные, API) согласно конфигурации в `testng.xml`

### Запуск только веб-тестов

**Для Windows PowerShell:**
```powershell
mvn test "-Dtest=tests.web.*"
```

**Для Linux/Mac или Git Bash:**
```bash
mvn test -Dtest=tests.web.*
```

**Альтернативный способ (работает везде):**
```bash
mvn test -Dtest=tests.web.HomePageTest,tests.web.SearchTest,tests.web.NavigationTest,tests.web.ArticleTest
```

### Запуск только мобильных тестов

**Важно:** Перед запуском мобильных тестов убедитесь, что:
1. Appium Server запущен (`appium`)
2. Android эмулятор запущен и подключен (`adb devices`)
3. Приложение Wikipedia установлено на эмуляторе

**Для Windows PowerShell:**
```powershell
# Способ 1: Указать конкретные классы (рекомендуется)
mvn test -Dtest=tests.mobile.SearchTest,tests.mobile.ArticleTest

# Способ 2: Использовать паттерн (может не работать из-за TestNG XML)
mvn test "-Dtest=tests.mobile.*"
```

**Для Linux/Mac или Git Bash:**
```bash
# Способ 1: Указать конкретные классы (рекомендуется)
mvn test -Dtest=tests.mobile.SearchTest,tests.mobile.ArticleTest

# Способ 2: Использовать паттерн
mvn test -Dtest=tests.mobile.*
```

**Способ 3: Запуск через TestNG XML (запустит все тесты)**
```bash
mvn test
```

### Запуск только API-тестов

**Для Windows PowerShell:**
```powershell
mvn test "-Dtest=tests.api.*"
```

**Для Linux/Mac или Git Bash:**
```bash
mvn test -Dtest=tests.api.*
```

**Альтернативный способ (работает везде):**
```bash
mvn test -Dtest=tests.api.ApiTest
```

### Запуск конкретного тестового класса
```bash
mvn test -Dtest=HomePageTest
mvn test -Dtest=SearchTest
mvn test -Dtest=ArticleTest
```

### Запуск через TestNG XML
```bash
mvn test
```
Тесты запускаются согласно конфигурации в `src/test/resources/testng.xml`

### Запуск через IDE

**IntelliJ IDEA:**
1. Откройте проект: File → Open → выберите папку проекта
2. Дождитесь индексации и загрузки зависимостей Maven
3. Для запуска всех тестов:
   - Откройте `src/test/resources/testng.xml`
   - Правый клик → Run 'testng.xml'
4. Для запуска конкретного теста:
   - Откройте тестовый класс (например, `HomePageTest.java`)
   - Правый клик → Run 'HomePageTest'

**Eclipse:**
1. Установите TestNG плагин через Eclipse Marketplace
2. Импортируйте проект: File → Import → Existing Maven Projects
3. Для запуска:
   - Правый клик на `testng.xml` → Run As → TestNG Suite
   - Или правый клик на тестовом классе → Run As → TestNG Test

## Описание тестов

### Веб-тесты (habr.com)

#### HomePageTest
- `testOpenHomePage` - Проверка открытия главной страницы
- `testLogoDisplayed` - Проверка отображения логотипа
- `testNavigationLinksPresent` - Проверка наличия навигационных ссылок
- `testSearchInputDisplayed` - Проверка отображения поля поиска
- `testArticlesPresent` - Проверка наличия статей на главной странице

#### SearchTest
- `testSearchFunctionality` - Проверка выполнения поиска
- `testSearchWithDifferentQueries` - Параметризованный тест поиска
- `testSearchResults` - Проверка результатов поиска

#### NavigationTest
- `testNavigationToSection` - Проверка навигации по разделам
- `testLogoClick` - Проверка клика по логотипу
- `testUrlAfterNavigation` - Проверка корректности URL после навигации

#### ArticleTest
- `testOpenArticle` - Проверка открытия статьи
- `testArticleElements` - Проверка элементов статьи

### Мобильные тесты (Wikipedia)

#### SearchTest
- `testSearchArticle` - Проверка поиска статьи по ключевому слову
- `testSearchWithDifferentQueries` - Параметризованный тест поиска
- `testOpenArticleFromSearch` - Проверка открытия статьи из результатов поиска

#### ArticleTest
- `testArticleTitle` - Проверка заголовка открытой статьи
- `testArticleContent` - Проверка содержимого статьи
- `testScrollArticle` - Проверка прокрутки статьи

### API-тесты

#### ApiTest
- `testGitHubApiAvailability` - Проверка доступности GitHub API
- `testGetUserInfo` - Проверка получения информации о пользователе
- `testGetUserRepositories` - Проверка получения репозиториев пользователя
- `testNonExistentUser` - Проверка обработки несуществующего пользователя
- `testApiResponseStructure` - Проверка структуры ответа API

## Архитектура проекта

### Page Object Model (POM)

Проект использует паттерн **Page Object Model (POM)** для организации кода, что обеспечивает:
- Разделение логики тестов и взаимодействия с элементами
- Переиспользование кода
- Упрощение поддержки при изменении интерфейса

**Структура Page Objects:**

**Веб-тесты:**
- `BasePage` - Базовый класс с общими методами (ожидания, клики, ввод текста)
- `HomePage` - Page Object для главной страницы habr.com
- `SearchResultsPage` - Page Object для страницы результатов поиска
- `ArticlePage` - Page Object для страницы статьи

**Мобильные тесты:**
- `BaseMobilePage` - Базовый класс для мобильных Page Objects
- `MainPage` - Page Object для главного экрана Wikipedia
- `SearchResultsPage` - Page Object для результатов поиска
- `ArticlePage` - Page Object для экрана статьи

**Особенности реализации:**
- Использование `@FindBy` аннотаций для локаторов
- Явные ожидания через `WebDriverWait` и `ExpectedConditions`
- Инкапсуляция логики взаимодействия с элементами в методах Page Objects
- Базовые классы содержат общие методы для избежания дублирования

### Утилиты

**ConfigReader** - Утилита для чтения конфигурационных параметров
- Читает параметры из `config.properties`
- Предоставляет методы для получения настроек веб, мобильных и API тестов

**WebDriverManager** - Управление WebDriver для веб-тестов
- Автоматическая загрузка драйверов через WebDriverManager
- Поддержка Chrome, Firefox, Edge
- Создание и управление экземпляром WebDriver
- Настройка WebDriverWait с таймаутами

**AppiumDriverManager** - Управление AppiumDriver для мобильных тестов
- Настройка UiAutomator2Options с capabilities
- Подключение к Appium Server
- Управление жизненным циклом AndroidDriver

### Использование ожиданий (Waits)

Проект использует **явные ожидания (Explicit Waits)** для стабильности тестов:

- `WebDriverWait` с настраиваемым таймаутом (10 секунд по умолчанию)
- `ExpectedConditions.visibilityOf()` - ожидание видимости элемента
- `ExpectedConditions.elementToBeClickable()` - ожидание кликабельности элемента
- Обработка исключений для гибкой работы с динамическими элементами

### Локаторы

Локаторы организованы в Page Objects с использованием:
- CSS селекторы (основной метод)
- Альтернативные селекторы для повышения устойчивости
- Аннотации `@FindBy` для декларативного описания элементов
- Динамический поиск элементов при необходимости

## Отчеты

После выполнения тестов отчеты TestNG можно найти в:
- `test-output/index.html` - HTML отчет
- `test-output/testng-results.xml` - XML отчет

## Решение проблем

### Проблемы с веб-тестами

1. **Драйвер не найден:**
   - WebDriverManager должен автоматически загрузить драйверы
   - Проверьте подключение к интернету
   - Убедитесь, что браузер установлен

2. **Элементы не найдены:**
   - Проверьте селекторы в Page Objects
   - Убедитесь, что сайт доступен
   - Увеличьте timeout в `config.properties`

3. **Предупреждения о CDP (Chrome DevTools Protocol):**
   - При использовании Chrome 145 могут появляться предупреждения: `Unable to find CDP implementation matching 145`
   - Это **не критично** и не влияет на работу тестов
   - Chrome 145 - очень новая версия, и точная версия CDP v145 может быть недоступна в Selenium 4.15.0
   - Selenium автоматически использует ближайшую совместимую версию CDP (v119)
   - Тесты работают корректно даже с этими предупреждениями
   - Для полного устранения предупреждений можно дождаться обновления Selenium с поддержкой Chrome 145

### Проблемы с мобильными тестами

1. **Appium не подключается:**
   - Убедитесь, что Appium Server запущен
   - Проверьте URL в `config.properties`
   - Проверьте подключение устройства: `adb devices`

2. **Приложение не найдено:**
   - Убедитесь, что Wikipedia установлен на устройстве
   - Проверьте `appPackage` и `appActivity` в `config.properties`
   - Если используете APK, укажите путь в `android.app.path`

3. **Элементы не найдены:**
   - Используйте Appium Inspector для проверки селекторов
   - Убедитесь, что приложение полностью загрузилось
   - Увеличьте время ожидания

### Проблемы с API-тестами

1. **API недоступен:**
   - Проверьте подключение к интернету
   - Убедитесь, что базовый URL корректен
   - Проверьте, не требуется ли аутентификация

## Зависимости проекта

Все зависимости определены в `pom.xml`:

### Основные зависимости

**Selenium WebDriver** (4.15.0)
- Для автоматизации веб-браузеров
- Поддержка Chrome, Firefox, Edge

**TestNG** (7.8.0)
- Фреймворк для тестирования
- Поддержка параметризации, группировки тестов

**Appium Java Client** (9.0.0)
- Клиент для автоматизации мобильных приложений
- Поддержка Android через UiAutomator2

**REST Assured** (5.3.2)
- Библиотека для API тестирования
- Упрощенная работа с HTTP запросами

**WebDriverManager** (5.6.2)
- Автоматическая загрузка и управление драйверами браузеров
- Не требует ручной установки драйверов

**SLF4J** (2.0.9)
- Логирование в проекте

### Проверка зависимостей

Для проверки, что все зависимости загружены:
```bash
mvn dependency:tree
```

Для загрузки зависимостей без компиляции:
```bash
mvn dependency:resolve
```
