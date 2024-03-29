# Курсовая работа по Spring Framework

### Идея - web-приложение для изучения английского

Приложение написано с использованием **Spring Boot** и **Maven** (по требованиям курсовой нужно было использовать **Gradle**, и IDEA автоматически сгенерировала build.gradle по файлу pom.xml) 

Конфигурация для работы с базой данных **PostgreSQL** находится в файле **application.properties**. Для работы с БД реализованы **сущности** Word («Слово») и Person («Пользователь»). Сущности помещены аннотацией **@Entity** и связаны с таблицами аннотациями **@Table**. Работа с БД происходит через **классы-сервисы**, которые имеют в себе объекты **классов-репозиториев** **(JPA)**. В **классах-сервисах** реализована необходимая логика обработки данных, которые поступают из БД через **классы-репозитории**.

Конфигурация **Spring Security** находится в файле **SecurityConfig**. Запросы, связанные с **входом/регистрацией**, принимает **контроллер** AuthController. Spring Security настраивает страницы и методы, которые доступны различным **ролям**.

После входа пользователь попадает на страницу /tasks, где представлены основные задания, уровни и переход на страницу «Аккаунт». Запросы на страницу /tasks обрабатывает **контроллер TasksController**. TasksController с помощью **@AuthenticationPricipal** загружает аутентифицированного пользователя, считывает его уровень и загружает из БД слова уровня. Также TasksController обрабатывает логику всех упражнений, теста, повышения уровня пользователя после теста.

Для реализации интерфейса используется шаблонизатор **Thymeleaf**, который позволяет создавать динамические веб-страницы и взаимодействует с моделью. Для дизайна используется библиотека **Bootstrap**.
