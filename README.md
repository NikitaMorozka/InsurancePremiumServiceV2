# InsurancePremiumServiceV2

Расчёт стоимости страховки с несколькими модулями.

## Модули

- **insurance-calculator-app**: Основное приложение для расчёта страховых премий. [Подробнее](insurance-calculator-app/README.md)
- **black-list-app**: Приложение для управления чёрным списком клиентов. [Подробнее](black-list-app/README.md)
- **doc-generator-app**: Приложение для генерации документов (например, PDF). [Подробнее](doc-generator-app/README.md)
- **database-init**: Скрипты для инициализации базы данных.

## Требования

- Java 21-22
- Gradle
- MySQL (для инициализации базы данных)
- Docker (опционально, для работы с `docker-compose.yml`)

## Установка и запуск 

Для работы в IDE вы можете запустить только insurance-calculator-app и black-list-app
1. Склонируйте репозиторий:
   ```bash
   git clone https://github.com/NikitaMorozka/InsurancePremiumServiceV2.git
   cd InsurancePremiumServiceV2
2. Скомпилируйте проект:
    ```bash
   ./gradlew build
3. Запустите поэтапно:
   1) insurance-calculator-app
   2) black-list-app

## Запуск с Docker

Проект можно запустить с использованием Docker и Docker Compose. Ниже приведены инструкции для сборки образов и запуска сервисов.

### Требования
- Установленный Docker.
- Установленный Docker Compose.
- Доступ к файлу `docker-compose.yml` в корне репозитория.

### Предварительная загрузка образов
Перед запуском необходимо загрузить образы `mysql:8.0` и `rabbitmq:3-management` с Docker Hub. Выполните следующие команды:

```bash
docker pull mysql:8.0
docker pull rabbitmq:3-management
```
Скомпилируйте проект:
```bash
./gradlew build
```

Сборка образов для каждого приложения выполняется с использованием Docker. Выполните следующие команды в корне репозитория:
```bash
docker build -t black-list-app:1.0.9 .
docker build -t doc-generator-app:1.0.9 .
docker build -t insurance-calculator-app:1.0.9 .
```

Запуск сервисов
После сборки образов запустите все сервисы с помощью Docker Compose:

Убедитесь, что вы находитесь в корне репозитория, где находится docker-compose.yml.
Запустите контейнеры в фоновом режиме:

```bash
docker-compose up -d
```

Остановка сервисов
Чтобы остановить и удалить контейнеры:

```bash
docker-compose down 
```
Это веб-приложение в котором есть:
- REST API
- WEB UI
- Реляционная база данных
- Hexagonal architecture
- Автоматические миграции базы данных
- Юнит, интеграционные и acceptance тесты
- Реальные бизнес требования
- Множество дизайн паттернов и библиотек
- VCS Git, Build Tool Gradle, CI/GitHub Actions

## Основные темы проекта:

- Архитектура приложения
- Дизайн паттерны
- Работа с требованиями
- Надёжность
- Тестирование
- Развертывание
- Observability
- DDD
- Clean code

## Технологии и инструменты используемые в проекте

- VCS, Git, GitHub
- Build Tool Gradle
- Spring Framework, Spring Boot
- Design patterns: IoC, Builder, DTO, Factory, Strategy, etc.
- REST, WEB MVC
- SQL, MySQL, H2, JDBC, ORM, JPA, Hibernate, Liquibase
- JUnit, Mockito
- Lombok, Immutable Objects
- Logging, metrics
- JAR, WAR, Java Web Server, Apache Tomcat
- JSON, XML
- Containers, Docker
- Message Broker, RabbitMQ

## Чему я хочу научиться выполняя проект ?

- Осознанно строить архитектуру приложения исходя из функциональных и нефункциональных требований и ограничений.
- Реализовывать компоненты архитектуры в коде.
- Использовать компонентный подход, тестирование, observability, надёжность и другие архитектурные характеристики на практике.
- Применять clean code, DDD, TDD, CI, и другие практики для создания качественных ИТ продуктов.
- Получите опыт работы с реальным проектом, который включает в себя взаимодействие с командой, управление задачами и соблюдение сроков.
- Приобретёте коммерческий опыт, который включает в себя навыки работы в условиях, приближенных к реальным бизнес требованиям и ожиданиям.
