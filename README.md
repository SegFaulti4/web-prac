# Web-приложение
## Описание страниц
![Alt text](doc/pages.png)

Ссылки на страницу Авторизации, Поиск пользователей и Список разделов доступны из любой страницы.

### Список разделов
1. Лого форума
2. Красивое оформление
3. Ссылки на разделы форума
4. (Для модераторов) Кнопка "Скрыть раздел для общего доступа"
5. (Для модераторов) Кнопка "Добавить новый раздел"

### Авторизация
1. Поля для заполнения данных (Login/пароль)
2. Кнопка "Войти"

### Поиск пользователей
1. Поиск по имени
2. Фильтрация по участию в темах
3. (Для модераторов) Кнопка "Добавить пользователя"

### Страница пользователя
1. Имя пользователя, статус
2. Список тем, в которых пользователь участвует
3. (Для модераторов) Кнопка "Заблокировать пользователя"

### Содержимое раздела
1. Список тем
2. Число сообщений по каждой теме
3. Время создания темы
4. Автор темы
5. Поиск по названию
6. Кнопка "Создать новую тему"
7. (Для модераторов) Кнопка "Удалить тему"

### Содержимое темы
1. Шапка темы (название, автор, время создания)
2. Список сообщений по теме (время, автор, нумерация, ссылка на другое сообщение в теме)
3. Кнопка "Отправить сообщение"
4. Переход на страницы темы
5. (Для модераторов) Кнопка "Удалить сообщение"

## Возможные сценарии использования
- Участие в обсуждении по теме
    1. Авторизация по логину/паролю на странице Авторизации
    2. Переход на страницу Списка разделов
    3. Переход на страницу Содержимого раздела
    4. Поиск темы по названию, переход на страницу Содержимого темы
    5. Выбрать сообщение для ответа либо начать новое сообщение
    6. (опционально) Добавить вложение(я)
- Добавление новой темы в разделе
    1. Авторизация по логину/паролю на странице Авторизации
    2. Переход на страницу Списка разделов
    3. Переход на страницу Содержимого раздела
    4. Создание новой темы через кнопку "Создать новую тему"
    5. Заполнение полей данных новой темы
- Обновление статуса своего пользователя
    1. Авторизация по логину/паролю на странице Авторизации
    2. Переход на страницу Поиска пользователей
    3. Переход на свою страницу по кнопке "Мой профиль"
    4. Редактирование профиля по кнопке "Редактировать профиль"
    5. Заполнение нового статуса
- Поиск общих с другим пользователем тем для обсуждения
    1. Поиск пользователя на соответствующей странице либо по сообщению в теме
    2. Переход на страницу пользователя
    3. Поиск по списку тем, в которых пользователь принимает участие
- (Для модераторов) Бан пользователя по имени
    1. Авторизация по логину/паролю на странице Авторизации в качестве модератора
    2. Переход на страницу Поиска пользователя
    3. Поиск пользователя по имени в поисковой строке
    4. Переход на страницу пользователя
    5. Кнопка "Заблокировать пользователя"
- (Для модераторов) Удаление нежелательной темы
    1. Авторизация по логину/паролю на странице Авторизации в качестве модератора
    2. Переход на страницу Списка разделов
    3. Переход на страницу Содержимого раздела
    4. Поиск темы по названию
    5. Кнопка "Удалить тему"

## Схема базы данных
![Alt text](doc/db.png)

