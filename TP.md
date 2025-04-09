## ****Тест-план для тестирования проекта «Авансовый отчет»****

### ****1\. Введение****

## Цель документа

Документ «Тест-план ИС Авансовый отчет» преследует следующие цели:

- Описать элементы ИС Авансовый отчет, которые будут проверены в процессе тестирования;
- Описать область тестирования, содержащую список и параметры тестируемой функциональности;
- Описать методы тестирования, содержащие последовательность действий, описание тестовых данных, данные проходимых тестов;
- Описать тестовые задачи, определить критерии невозможности дальнейшего проведения/завершения тестирования;
- Описать содержание тестовой среды, определить ожидаемые результаты тестирования;
- Задать календарный план.

# Цель и задачи тестирования

## Цель тестирования

Общей целью всех работ является снижение рисков внедрения доработок в ИС Авансовый отчет.

## Задачи тестирования

- Выявить максимальное количество дефектов при функциональном тестировании
- Провести кроссбраузерное тестирование на следующих браузерах: Internet Explorer, Opera, Google Chrome, Firefox;
- Провести регрессионное тестирование и найти дефекты в существующем функционале системы
- Актуализировать регрессионную тестовую модель

# Тестируемые бизнес-процессы

В процессе оценки качества системы ИС Авансовый отчет будут протестированы следующие функции:

**1\. Функции работы с отчетами**

| **Функция** | **Что тестируется** |
| --- | --- |
| Создание отчета | Валидация полей (даты, суммы, назначение), привязка к филиалу/сотруднику/счету |
| Редактирование отчета | Обновление полей (особенно recognizedAmount, comments), проверка прав доступа |
| Удаление отчета | Каскадное удаление, обработка зависимых сущностей |
| Просмотр списка отчетов | Фильтрация по филиалу, дате, сотруднику; пагинация |
| Генерация PDF | Корректность форматирования, подстановка данных (филиал, суммы, подписи) |

**2\. Функции справочников**

| **Функция** | **Что тестируется** |
| --- | --- |
| Управление филиалами | CRUD для Department, валидация уникальности названия |
| Управление должностями | CRUD для Job, связь с Employee |
| Управление сотрудниками | CRUD для Employee, привязка к филиалу/должности |
| Управление счетами | CRUD для Account, валидация формата названия |

**3\. Интеграционные функции**

| **Функция** | **Что тестируется** |
| --- | --- |
| Связь отчетов и справочников | Автоподстановка departmentId при выборе сотрудника |
| Экспорт данных | Выгрузка в Excel/PDF (проверка кодировки, структуры) |
| API-эндпоинты | Ответы на GET/POST/PUT/DELETE запросы (статусы, JSON-схемы) |

**4\. Бизнес-логика**

| **Функция** | **Что тестируется** |
| --- | --- |
| Расчет признанной суммы | Автоматический пересчет при изменении amountIssued или dateApproved |
| Валидация дат | Проверка, что dateReceived ≤ dateApproved |
| Назначение номера отчета | Автогенерация reportNumber в формате {номер}/{филиал} |

**5\. UI-функции**

| **Функция** | **Что тестируется** |
| --- | --- |
| Форма создания отчета | Валидация полей в реальном времени, подсказки об ошибках |
| Диалоги подтверждения | Отмена/подтверждение удаления, сохранения |
| Адаптивность интерфейса | Отображение на мобильных устройствах, таблицы с горизонтальным скроллом |

**6\. Технические аспекты**

| **Функция** | **Что тестируется** |
| --- | --- |
| Логирование | Запись действий (создание/удаление отчетов) в лог |
| Безопасность | Проверка JWT-токенов, ролевой доступ (например, запрет редактирования чужих отчетов) |
| Производительность | Время генерации PDF при 100+ отчетах, нагрузка на БД |

# Тестируемые элементы системы

<table><tbody><tr><th><p><strong>Тип элемента</strong></p></th><th><p><strong>Виды проверки</strong></p></th></tr><tr><td><p>Текстовые поля для ввода</p></td><td><ul><li>Условия и корректность отображения;</li><li>Возможность вводить данные;</li><li>Минимальные и максимальные ограничения на количество вводимых символов;</li><li>Ограничения по маске на ввод данных;</li><li>Обязательность заполнения;</li></ul></td></tr><tr><td><p>Поля для ввода даты</p></td><td><ul><li>Условия и корректность отображения;</li><li>Возможность вводить данные;</li><li>Ввод прошлой, текущей или будущей даты;</li><li>Ограничения по маске на ввод данных;</li><li>Обязательность заполнения;</li></ul></td></tr><tr><td><p>Поля с выпадающим списком</p></td><td><ul><li>Условия и корректность отображения;</li><li>Возможность выбирать данные из списка;</li><li>Состав значений справочника;</li><li>Обязательность заполнения;</li></ul></td></tr><tr><td><p>Поля с контекстным поиском</p></td><td><ul><li>Условия и корректность отображения;</li><li>Возможность вводить данные;</li><li>Функционал поиска значения в справочнике по введенным данным;</li><li>Обязательность заполнения;</li></ul></td></tr><tr><td><p>Поля для поиска</p></td><td><ul><li>Условия и корректность отображения;</li><li>Возможность вводить данные;</li><li>Функционал поиска значения в БД по введенным данным;</li><li>Обязательность заполнения;</li></ul></td></tr><tr><td><p>Поля типа «чекбокс»</p></td><td><ul><li>Условия и корректность отображения;</li><li>Возможность включать и выключать значения;</li><li>Возможность включать несколько значений</li><li>Обязательность заполнения;</li></ul></td></tr><tr><td><p>Поля типа «радиобаттон»</p></td><td><ul><li>Условия и корректность отображения;</li><li>Возможность выбирать значение;</li><li>Невозможность выбрать несколько значений;</li><li>Обязательность заполнения;</li></ul></td></tr><tr><td><p>Блоки, содержащие в себе несколько элементов</p></td><td><ul><li>Условия и корректность отображения;</li><li>Состав элементов блока;</li></ul></td></tr><tr><td><p>Кнопки</p></td><td><ul><li>Условия и корректность отображения;</li><li>Возможность нажать кнопку;</li><li>Корректность выполнения требуемой функции кнопки;</li></ul></td></tr><tr><td><p>Поля типа «Файл»</p></td><td><ul><li>Условия и корректность отображения;</li><li>Возможность прикрепить файл;</li><li>Ограничения по размеру и типу файла;</li><li>Обязательность заполнения;</li></ul></td></tr><tr><td><p>Ссылки</p></td><td><ul><li>Условия и корректность отображения;</li><li>Возможность нажать ссылку;</li><li>Корректность ссылки (ведет ли ссылка на указываемый ресурс);</li></ul></td></tr><tr><td><p>Подсказки к полям, баннеры, инфоблоки</p></td><td><ul><li>Условия и корректность отображения;</li></ul></td></tr><tr><td><p>Функционал черновиков</p></td><td><ul><li>Возможность сохранить и загрузить черновик заявления;</li><li>Возможность редактирования черновика и его повторного сохранения;</li></ul></td></tr><tr><td><p>Функционал подписи заявления ЭЦП</p></td><td><ul><li>Возможность подписать заявление электронной подписью;</li></ul></td></tr></tbody></table>

### ****2\. Область тестирования****

Тестирование будет охватывать:

- API эндпоинты, реализованные в Controller.
- Взаимодействие с базой данных через Repository.
- Логика бизнес-слоя (ServiceImpl).
- Обработка исключений.
- Кросс-оригинальный доступ (@CrossOrigin).
- Валидация входных данных.
- **Функциональное тестирование**: Проверка создания, получения, обновления и удаления отчетов.
- **Обработка ошибок и граничных условий**.
- **Производительность и нагрузочное тестирование**.
- **Безопасность** (SQL-инъекции, XSS, доступность API).

### ****3\. Объекты тестирования****

**Тестируемые компоненты:**

- **REST API:**
    - GET /api/(accounts, departments, employees, jobs, report) – получение списка записей.
    - POST /api/(accounts, departments, employees, jobs, report) – создание записи.
    - GET /api/(accounts, departments, employees, jobs, report)/{id} – получение записи по ID.
    - DELETE /api/(accounts, departments, employees, jobs, report)/{id} – удаление записи по ID.
    - PUT /api/(accounts, departments, employees, jobs, report)/{id} – обновление записей.
- **Связь с базой данных:**
    - Корректное сохранение, обновление, удаление данных.
- **Ошибки и исключения:**
    - Обработка NotFoundException.
    - Корректные HTTP-коды.
- **Безопасность:**
    - Кросс-доменные запросы.

### ****4\. Стратегия тестирования****

**4.1 Функциональное тестирование**  
Будут проверены следующие аспекты:  
✅ Корректное создание записи.  
✅ Получение списка записей.  
✅ Получение записи по ID.  
✅ Удаление записи.  
✅ Обновление записи.

**4.2 Нефункциональное тестирование**

- **Производительность** (тесты нагрузки и стресс-тестирование).
- **Безопасность** (попытки SQL-инъекций, XSS, CSRF).
- **Юзабилити** (удобство работы с API).

**4.3 Автоматизация тестирования**

- Написание API-тестов в **Postman** или **RestAssured (Java)**.
- Покрытие юнит-тестами с использованием **JUnit** и **Mockito**.
- Интеграционные тесты с **Spring Boot Test**.

### ****5\. Сценарии тестирования****

#### **5.1 Тестирование Account**

| **ID** | **Название** | **Входные данные** | **Ожидаемый результат** |
| --- | --- | --- | --- |
| **Тест-кейсы для AccountMapper** |     |     |     |
| 1   | Преобразование Account → AccountDTO (корректные данные) | Account(id=1, name="Test") | AccountDTO(id=1, name="Test") |
| 2   | Преобразование null → null | null | null |
| 3   | Преобразование Account с null полями | Account(id=null, name=null) | AccountDTO(id=null, name=null) |
| 4   | Преобразование Account с частично заполненными полями | Account(id=1, name=null) | AccountDTO(id=1, name=null) |
| 5   | Преобразование AccountDTO → Account (корректные данные) | AccountDTO(id=1, name="Test") | Account(id=1, name="Test") |
| 6   | Преобразование null → null | null | null |
| 7   | Преобразование AccountDTO с null полями | AccountDTO(id=null, name=null) | Account(id=null, name=null) |
| 8   | Преобразование AccountDTO с частично заполненными полями | AccountDTO(id=1, name=null) | Account(id=1, name=null) |
| 9   | Пустое имя (name = "") | Account(id=1, name="") | AccountDTO(id=1, name="") |
| 10  | Максимальное значение id | Account(id=Long.MAX_VALUE, name="Max") | AccountDTO(id=Long.MAX_VALUE, name="Max") |
| 11  | Минимальное значение id | Account(id=Long.MIN_VALUE, name="Min") | AccountDTO(id=Long.MIN_VALUE, name="Min") |
| 12  | Очень длинное имя (проверка на обрезку, если есть ограничения) | Account(id=1, name="VeryLongName...") | AccountDTO(id=1, name="VeryLongName...") |
| **Тест-кейсы для AccountNotFoundException** |     |     |     |
| 13  | Создание исключения с текстом ошибки | new AccountNotFoundException("Account not found") | Исключение с message = "Account not found", статус 404 |
| 14  | Создание исключения с пустым сообщением | new AccountNotFoundException("") | Исключение с message = "", статус 404 |
| 15  | Создание исключения с null-сообщением | new AccountNotFoundException(null) | Исключение с message = null, статус 404 |
| 16  | Проверка аннотации @ResponseStatus | Проверка класса исключения | Аннотация @ResponseStatus(HttpStatus.NOT_FOUND) присутствует |
| 17  | Проверка наследования | Проверка класса исключения | Наследуется от RuntimeException |
| **Тест-кейсы для AccountServiceImpl** |     |     |     |
| 18  | Получение всех аккаунтов (непустой список) | В БД есть 2 аккаунта: Account(id=1, name="A"), Account(id=2, name="B") | List&lt;AccountDTO&gt; с 2 элементами: \[AccountDTO(1, "A"), AccountDTO(2, "B")\] |
| 19  | Получение всех аккаунтов (пустая БД) | В БД нет записей (accountRepository.findAll() возвращает пустой список) | Пустой List&lt;AccountDTO&gt; |
| 20  | Проверка маппинга в DTO | Account(id=1, name=null) | AccountDTO(1, null) |
| 21  | Успешное создание аккаунта | AccountDTO(null, "New Account") | Возвращает AccountDTO с id (сгенерирован БД) и именем "New Account" |
| 22  | Создание аккаунта с null-полями | AccountDTO(null, null) | Возвращает AccountDTO с id и name=null |
| 23  | Проверка вызова accountRepository.save() | Любой AccountDTO | accountRepository.save() вызывается ровно 1 раз |
| 24  | Успешное получение аккаунта по ID | id=1 (существует в БД) | Optional.of(AccountDTO(1, "A")) |
| 25  | Получение несуществующего аккаунта | id=999 (нет в БД) | Optional.empty() |
| 26  | Передача null в качестве ID | id=null | Optional.empty() (или NPE, если accountRepository.findById(null) не поддерживается) |
| 27  | Удаление существующего аккаунта | id=1 (есть в БД) | accountRepository.deleteById(1) вызывается 1 раз |
| 28  | Удаление несуществующего аккаунта | id=999 (нет в БД) | Исключение (зависит от реализации accountRepository) |
| 29  | Передача null в качестве ID | id=null | Исключение (например, IllegalArgumentException) |
| 30  | Успешное обновление аккаунта | id=1, AccountDTO(1, "Updated") (аккаунт с id=1 есть в БД) | Возвращает AccountDTO(1, "Updated") |
| 31  | Обновление несуществующего аккаунта | id=999, AccountDTO(999, "Fail") | Исключение AccountNotFoundException |
| 32  | Обновление с null-полем в DTO | id=1, AccountDTO(1, null) | Возвращает AccountDTO(1, null) |
| 33  | Проверка вызова accountRepository.save() | Корректные входные данные | save() вызывается 1 раз |
| 34  | Проверка инъекции зависимостей | Создание AccountServiceImpl | Все поля (accountRepository, accountMapper) не null |
| 35  | Проверка аннотаций класса | Проверка класса AccountServiceImpl | Имеет аннотацию @Service |
| **Тест-кейсы для AccountController** |     |     |     |
| 36  | Успешное получение списка аккаунтов (непустой список) | GET /api/accounts  <br>В accountServiceImpl есть данные: \[AccountDTO(1, "A"), AccountDTO(2, "B")\] | 200 OK, тело: \[AccountDTO(1, "A"), AccountDTO(2, "B")\] |
| 37  | Получение пустого списка | GET /api/accounts  <br>В accountServiceImpl нет данных (пустой список) | 200 OK, тело: \[\] |
| 38  | Проверка заголовка CORS | Запрос с заголовком Origin: <http://example.com> | В ответе есть заголовок Access-Control-Allow-Origin: \* |
| 39  | Успешное создание аккаунта | POST /api/accounts  <br>Тело: {"name": "New Account"} | 200 OK, тело: AccountDTO(id, "New Account") (id сгенерирован) |
| 40  | Создание с пустым телом | POST /api/accounts  <br>Тело: {} | 200 OK (или 400 Bad Request, если есть валидация) |
| 41  | Создание с null-полем | POST /api/accounts  <br>Тело: {"name": null} | 200 OK, тело: AccountDTO(id, null) |
| 42  | Успешное получение аккаунта | GET /api/accounts/1  <br>В accountServiceImpl есть AccountDTO(1, "A") | 200 OK, тело: AccountDTO(1, "A") |
| 43  | Запрос несуществующего ID | GET /api/accounts/999  <br>В accountServiceImpl нет данных | 404 Not Found |
| 44  | Запрос с некорректным ID (буквы) | GET /api/accounts/abc | 400 Bad Request (если Spring валидирует путь) |
| 45  | Успешное удаление | DELETE /api/accounts/1  <br>Аккаунт с id=1 существует | 204 No Content |
| 46  | Удаление несуществующего ID | DELETE /api/accounts/999 | 404 Not Found |
| 47  | Проверка вызова сервиса | DELETE /api/accounts/1 | accountServiceImpl.deleteAccount(1) вызван 1 раз |
| 48  | Успешное обновление | PUT /api/accounts/1  <br>Тело: {"name": "Updated"}  <br>Аккаунт с id=1 существует | 200 OK, тело: AccountDTO(1, "Updated") |
| 49  | Обновление несуществующего ID | PUT /api/accounts/999  <br>Тело: {"name": "Fail"} | 404 Not Found |
| 50  | Обновление с пустым телом | PUT /api/accounts/1  <br>Тело: {} | 200 OK (или 400, если есть валидация) |
| 51  | Несоответствие ID в пути и теле | PUT /api/accounts/1  <br>Тело: {"id": 2, "name": "Conflict"} | 200 OK (ID в пути приоритетен) |
| 52  | Проверка аннотаций класса | Запрос любого метода | Заголовок Access-Control-Allow-Origin: \* (CORS) |
| 53  | Неподдерживаемый метод | PATCH /api/accounts/1 | 405 Method Not Allowed |

#### **5.2 Тестирование Department**

| **ID** | **Название** | **Входные данные** | **Ожидаемый результат** |
| --- | --- | --- | --- |
| **Тест-кейсы для DepartmentMapper** |     |     |     |
| 1   | Преобразование Department → DepartmentDTO (корректные данные) | Department(id=1, name="Test") | DepartmentDTO(id=1, name="Test") |
| 2   | Преобразование null → null | null | null |
| 3   | Преобразование Department с null полями | Department(id=null, name=null) | DepartmentDTO(id=null, name=null) |
| 4   | Преобразование Department с частично заполненными полями | Department(id=1, name=null) | DepartmentDTO(id=1, name=null) |
| 5   | Преобразование DepartmentDTO → Department (корректные данные) | DepartmentDTO(id=1, name="Test") | Department(id=1, name="Test") |
| 6   | Преобразование null → null | null | null |
| 7   | Преобразование DepartmentDTO с null полями | DepartmentDTO(id=null, name=null) | Department(id=null, name=null) |
| 8   | Преобразование DepartmentDTO с частично заполненными полями | DepartmentDTO(id=1, name=null) | Department(id=1, name=null) |
| 9   | Пустое имя (name = "") | Department(id=1, name="") | DepartmentDTO(id=1, name="") |
| 10  | Максимальное значение id | Department(id=Long.MAX_VALUE, name="Max") | DepartmentDTO(id=Long.MAX_VALUE, name="Max") |
| 11  | Минимальное значение id | Department(id=Long.MIN_VALUE, name="Min") | DepartmentDTO(id=Long.MIN_VALUE, name="Min") |
| 12  | Очень длинное имя (проверка на обрезку, если есть ограничения) | Department(id=1, name="VeryLongName...") | DepartmentDTO(id=1, name="VeryLongName...") |
| **Тест-кейсы для DepartmentNotFoundException** |     |     |     |
| 13  | Создание исключения с текстом ошибки | new DepartmenttNotFoundException("Department not found") | Исключение с message = " Department not found", статус 404 |
| 14  | Создание исключения с пустым сообщением | new DepartmentNotFoundException("") | Исключение с message = "", статус 404 |
| 15  | Создание исключения с null-сообщением | new DepartmentNotFoundException(null) | Исключение с message = null, статус 404 |
| 16  | Проверка аннотации @ResponseStatus | Проверка класса исключения | Аннотация @ResponseStatus(HttpStatus.NOT_FOUND) присутствует |
| 17  | Проверка наследования | Проверка класса исключения | Наследуется от RuntimeException |
| **Тест-кейсы для DepartmentServiceImpl** |     |     |     |
| 18  | Получение всех аккаунтов (непустой список) | В БД есть 2 аккаунта: Department(id=1, name="A"), Department(id=2, name="B") | List&lt; DepartmentDTO&gt; с 2 элементами: \[DepartmentDTO(1, "A"), DepartmentDTO(2, "B")\] |
| 19  | Получение всех аккаунтов (пустая БД) | В БД нет записей (departmentRepository.findAll() возвращает пустой список) | Пустой List&lt; DepartmentDTO&gt; |
| 20  | Проверка маппинга в DTO | Department(id=1, name=null) | DepartmentDTO(1, null) |
| 21  | Успешное создание аккаунта | DepartmentDTO(null, "New Department") | Возвращает DepartmentDTO с id (сгенерирован БД) и именем "New Department" |
| 22  | Создание департамента с null-полями | DepartmentDTO(null, null) | Возвращает DepartmentDTO с id и name=null |
| 23  | Проверка вызова departmentRepository.save() | Любой DepartmentDTO | departmentRepository.save() вызывается ровно 1 раз |
| 24  | Успешное получение департамента по ID | id=1 (существует в БД) | Optional.of(DepartmentDTO(1, "A")) |
| 25  | Получение несуществующего департамента | id=999 (нет в БД) | Optional.empty() |
| 26  | Передача null в качестве ID | id=null | Optional.empty() (или NPE, если departmentRepository.findById(null) не поддерживается) |
| 27  | Удаление существующего департамента | id=1 (есть в БД) | departmentRepository.deleteById(1) вызывается 1 раз |
| 28  | Удаление несуществующего департамента | id=999 (нет в БД) | Исключение (зависит от реализации departmentRepository) |
| 29  | Передача null в качестве ID | id=null | Исключение (например, IllegalArgumentException) |
| 30  | Успешное обновление департамента | id=1, DepartmentDTO(1, "Updated") (аккаунт с id=1 есть в БД) | Возвращает DepartmentDTO(1, "Updated") |
| 31  | Обновление несуществующего департамента | id=999, DepartmentDTO(999, "Fail") | Исключение DepartmentNotFoundException |
| 32  | Обновление с null-полем в DTO | id=1, DepartmentDTO(1, null) | Возвращает DepartmentDTO(1, null) |
| 33  | Проверка вызова departmentRepository.save() | Корректные входные данные | save() вызывается 1 раз |
| 34  | Проверка инъекции зависимостей | Создание DepartmentServiceImpl | Все поля (departmentRepository, departmentMapper) не null |
| 35  | Проверка аннотаций класса | Проверка класса DepartmentServiceImpl | Имеет аннотацию @Service |
| **Тест-кейсы для** Department**Controller** |     |     |     |
| 36  | Успешное получение списка департаментов (непустой список) | GET /api/departments  <br>В departmentServiceImpl есть данные: \[DepartmentDTO(1, "A"), DepartmentDTO(2, "B")\] | 200 OK, тело: \[DepartmentDTO(1, "A"), DepartmentDTO(2, "B")\] |
| 37  | Получение пустого списка | GET /api/departments  <br>В departmentServiceImpl нет данных (пустой список) | 200 OK, тело: \[\] |
| 38  | Проверка заголовка CORS | Запрос с заголовком Origin: <http://example.com> | В ответе есть заголовок Access-Control-Allow-Origin: \* |
| 39  | Успешное создание департамента | POST /api/department  <br>Тело: {"name": "New Department "} | 200 OK, тело: DepartmentDTO(id, "New Department") (id сгенерирован) |
| 40  | Создание с пустым телом | POST /api/departments  <br>Тело: {} | 200 OK (или 400 Bad Request, если есть валидация) |
| 41  | Создание с null-полем | POST /api/departments  <br>Тело: {"name": null} | 200 OK, тело: DepartmentDTO(id, null) |
| 42  | Успешное получение департамента | GET /api/departments/1  <br>В departmentServiceImpl есть DepartmentDTO(1, "A") | 200 OK, тело: DepartmentDTO(1, "A") |
| 43  | Запрос несуществующего ID | GET /api/departments/999  <br>В departmentServiceImpl нет данных | 404 Not Found |
| 44  | Запрос с некорректным ID (буквы) | GET /api/departments/abc | 400 Bad Request (если Spring валидирует путь) |
| 45  | Успешное удаление | DELETE /api/departments/1  <br>Департамент с id=1 существует | 204 No Content |
| 46  | Удаление несуществующего ID | DELETE /api/departments/999 | 404 Not Found |
| 47  | Проверка вызова сервиса | DELETE /api/departments/1 | departmentServiceImpl.deleteDepartment (1) вызван 1 раз |
| 48  | Успешное обновление | PUT /api/departments/1  <br>Тело: {"name": "Updated"}  <br>Департамент с id=1 существует | 200 OK, тело: DepartmentDTO(1, "Updated") |
| 49  | Обновление несуществующего ID | PUT /api/departments/999  <br>Тело: {"name": "Fail"} | 404 Not Found |
| 50  | Обновление с пустым телом | PUT /api/departments/1  <br>Тело: {} | 200 OK (или 400, если есть валидация) |
| 51  | Несоответствие ID в пути и теле | PUT /api/departments/1  <br>Тело: {"id": 2, "name": "Conflict"} | 200 OK (ID в пути приоритетен) |
| 52  | Проверка аннотаций класса | Запрос любого метода | Заголовок Access-Control-Allow-Origin: \* (CORS) |
| 53  | Неподдерживаемый метод | PATCH /api/departments/1 | 405 Method Not Allowed |

#### **5.3 Тестирование Employee**

| **ID** | **Название** | **Входные данные** | **Ожидаемый результат** |
| --- | --- | --- | --- |
| **Тест-кейсы для EmployeeMapper** |     |     |     |
| 1   | Преобразование **Employee** → **Employee**DTO (корректные данные) | **Employee**(id=1, name="Test") | **Employee**DTO(id=1, name="Test") |
| 2   | Преобразование null → null | Null | null |
| 3   | Преобразование **Employee** с null полями | **Employee**(id=null, name=null) | EmployeeDTO(id=null, name=null) |
| 4   | Преобразование Employee с частично заполненными полями | Employee (id=1, name=null) | EmployeeDTO(id=1, name=null) |
| 5   | Преобразование EmployeeDTO → Employee (корректные данные) | EmployeeDTO(id=1, name="Test") | Employee (id=1, name="Test") |
| 6   | Преобразование null → null | null | null |
| 7   | Преобразование EmployeeDTO с null полями | EmployeeDTO(id=null, name=null) | Employee (id=null, name=null) |
| 8   | Преобразование EmployeeDTO с частично заполненными полями | EmployeeDTO(id=1, name=null) | Employee (id=1, name=null) |
| 9   | Пустое имя (name = "") | Employee(id=1, name="") | EmployeeDTO(id=1, name="") |
| 10  | Максимальное значение id | Employee(id=Long.MAX_VALUE, name="Max") | EmployeeDTO(id=Long.MAX_VALUE, name="Max") |
| 11  | Минимальное значение id | Employee (id=Long.MIN_VALUE, name="Min") | EmployeeDTO(id=Long.MIN_VALUE, name="Min") |
| 12  | Очень длинное имя (проверка на обрезку, если есть ограничения) | Employee(id=1, name="VeryLongName...") | EmployeeDTO(id=1, name="VeryLongName...") |
| **Тест-кейсы для** Employee**NotFoundException** |     |     |     |
| 13  | Создание исключения с текстом ошибки | new EmployeeNotFoundException("Employee not found") | Исключение с message = "Employee not found", статус 404 |
| 14  | Создание исключения с пустым сообщением | new EmployeeNotFoundException("") | Исключение с message = "", статус 404 |
| 15  | Создание исключения с null-сообщением | new EmployeeNotFoundException(null) | Исключение с message = null, статус 404 |
| 16  | Проверка аннотации @ResponseStatus | Проверка класса исключения | Аннотация @ResponseStatus(HttpStatus.NOT_FOUND) присутствует |
| 17  | Проверка наследования | Проверка класса исключения | Наследуется от RuntimeException |
| **Тест-кейсы для** Employee**ServiceImpl** |     |     |     |
| 18  | Получение всех аккаунтов (непустой список) | В БД есть 2 сотрудника: Employee(id=1, name="A"), Employee (id=2, name="B") | List&lt;EmployeeDTO&gt; с 2 элементами: \[EmployeeDTO(1, "A"), EmployeeDTO(2, "B")\] |
| 19  | Получение всех аккаунтов (пустая БД) | В БД нет записей (employeeRepository.findAll() возвращает пустой список) | Пустой List&lt;EmployeeDTO&gt; |
| 20  | Проверка маппинга в DTO | Employee(id=1, name=null) | EmployeeDTO(1, null) |
| 21  | Успешное создание сотрудника | EmployeeDTO(null, "New Employee") | Возвращает EmployeeDTO с id (сгенерирован БД) и именем "New Employee" |
| 22  | Создание сотрудника с null-полями | EmployeeDTO(null, null) | Возвращает EmployeeDTO с id и name=null |
| 23  | Проверка вызова employeeRepository.save() | Любой EmployeeDTO | employeeRepository.save() вызывается ровно 1 раз |
| 24  | Успешное получение сотрудника по ID | id=1 (существует в БД) | Optional.of(EmployeeDTO(1, "A")) |
| 25  | Получение несуществующего сотрудника | id=999 (нет в БД) | Optional.empty() |
| 26  | Передача null в качестве ID | id=null | Optional.empty() (или NPE, если employeeRepository.findById(null) не поддерживается) |
| 27  | Удаление существующего сотрудника | id=1 (есть в БД) | employeeRepository.deleteById(1) вызывается 1 раз |
| 28  | Удаление несуществующего сотрудника | id=999 (нет в БД) | Исключение (зависит от реализации employeeRepository) |
| 29  | Передача null в качестве ID | id=null | Исключение (например, IllegalArgumentException) |
| 30  | Успешное обновление сотрудника | id=1, EmployeeDTO(1, "Updated") (аккаунт с id=1 есть в БД) | Возвращает EmployeeDTO(1, "Updated") |
| 31  | Обновление несуществующего сотрудника | id=999, EmployeeDTO(999, "Fail") | Исключение EmployeeNotFoundException |
| 32  | Обновление с null-полем в DTO | id=1, EmployeeDTO(1, null) | Возвращает EmployeeDTO(1, null) |
| 33  | Проверка вызова departmentRepository.save() | Корректные входные данные | save() вызывается 1 раз |
| 34  | Проверка инъекции зависимостей | Создание EmployeeServiceImpl | Все поля (employeeRepository, employeeMapper) не null |
| 35  | Проверка аннотаций класса | Проверка класса EmployeeServiceImpl | Имеет аннотацию @Service |
| **Тест-кейсы для** Employee**Controller** |     |     |     |
| 36  | Успешное получение списка департаментов (непустой список) | GET /api/employees  <br>В employeeServiceImpl есть данные: \[EmployeeDTO(1, "A"), EmployeeDTO(2, "B")\] | 200 OK, тело: \[EmployeeDTO(1, "A"), EmployeeDTO(2, "B")\] |
| 37  | Получение пустого списка | GET /api/employees  <br>В employeeServiceImpl нет данных (пустой список) | 200 OK, тело: \[\] |
| 38  | Проверка заголовка CORS | Запрос с заголовком Origin: <http://example.com> | В ответе есть заголовок Access-Control-Allow-Origin: \* |
| 39  | Успешное создание департамента | POST /api/employee  <br>Тело: {"name": "New Employee "} | 200 OK, тело: EmployeeDTO(id, "New Employee") (id сгенерирован) |
| 40  | Создание с пустым телом | POST /api/employees  <br>Тело: {} | 200 OK (или 400 Bad Request, если есть валидация) |
| 41  | Создание с null-полем | POST /api/employees  <br>Тело: {"name": null} | 200 OK, тело: EmployeeDTO(id, null) |
| 42  | Успешное получение департамента | GET /api/employees/1  <br>В employeeServiceImpl есть EmployeeDTO(1, "A") | 200 OK, тело: EmployeeDTO(1, "A") |
| 43  | Запрос несуществующего ID | GET /api/employees/999  <br>В employeeServiceImpl нет данных | 404 Not Found |
| 44  | Запрос с некорректным ID (буквы) | GET /api/employees/abc | 400 Bad Request (если Spring валидирует путь) |
| 45  | Успешное удаление | DELETE /api/employees/1  <br>Департамент с id=1 существует | 204 No Content |
| 46  | Удаление несуществующего ID | DELETE /api/employees/999 | 404 Not Found |
| 47  | Проверка вызова сервиса | DELETE /api/employees/1 | employeeServiceImpl.deleteEmployee (1) вызван 1 раз |
| 48  | Успешное обновление | PUT /api/employees/1  <br>Тело: {"name": "Updated"}  <br>Департамент с id=1 существует | 200 OK, тело: EmployeeDTO(1, "Updated") |
| 49  | Обновление несуществующего ID | PUT /api/employees/999  <br>Тело: {"name": "Fail"} | 404 Not Found |
| 50  | Обновление с пустым телом | PUT /api/employees/1  <br>Тело: {} | 200 OK (или 400, если есть валидация) |
| 51  | Несоответствие ID в пути и теле | PUT /api/employees/1  <br>Тело: {"id": 2, "name": "Conflict"} | 200 OK (ID в пути приоритетен) |
| 52  | Проверка аннотаций класса | Запрос любого метода | Заголовок Access-Control-Allow-Origin: \* (CORS) |
| 53  | Неподдерживаемый метод | PATCH /api/employees/1 | 405 Method Not Allowed |

#### **5.4 Тестирование Job**

| **ID** | **Название** | **Входные данные** | **Ожидаемый результат** |
| --- | --- | --- | --- |
| **Тест-кейсы для JobMapper** |     |     |     |
| 1   | Преобразование **Job** → **Job**DTO (корректные данные) | **Job**(id=1, name="Test") | **Job**DTO(id=1, name="Test") |
| 2   | Преобразование null → null | Null | null |
| 3   | Преобразование **Job** с null полями | **Job**(id=null, name=null) | JobDTO(id=null, name=null) |
| 4   | Преобразование Job с частично заполненными полями | Job (id=1, name=null) | JobDTO(id=1, name=null) |
| 5   | Преобразование JobDTO → Job (корректные данные) | JobDTO(id=1, name="Test") | Job (id=1, name="Test") |
| 6   | Преобразование null → null | null | null |
| 7   | Преобразование JobDTO с null полями | JobDTO(id=null, name=null) | Job (id=null, name=null) |
| 8   | Преобразование JobDTO с частично заполненными полями | JobDTO(id=1, name=null) | Job (id=1, name=null) |
| 9   | Пустое имя (name = "") | Job(id=1, name="") | JobDTO(id=1, name="") |
| 10  | Максимальное значение id | Job(id=Long.MAX_VALUE, name="Max") | JobDTO(id=Long.MAX_VALUE, name="Max") |
| 11  | Минимальное значение id | Job (id=Long.MIN_VALUE, name="Min") | JobDTO(id=Long.MIN_VALUE, name="Min") |
| 12  | Очень длинное имя (проверка на обрезку, если есть ограничения) | Job(id=1, name="VeryLongName...") | JobDTO(id=1, name="VeryLongName...") |
| **Тест-кейсы для** Job**NotFoundException** |     |     |     |
| 13  | Создание исключения с текстом ошибки | new JobNotFoundException("Job not found") | Исключение с message = "Job not found", статус 404 |
| 14  | Создание исключения с пустым сообщением | new JobNotFoundException("") | Исключение с message = "", статус 404 |
| 15  | Создание исключения с null-сообщением | new JobNotFoundException(null) | Исключение с message = null, статус 404 |
| 16  | Проверка аннотации @ResponseStatus | Проверка класса исключения | Аннотация @ResponseStatus(HttpStatus.NOT_FOUND) присутствует |
| 17  | Проверка наследования | Проверка класса исключения | Наследуется от RuntimeException |
| **Тест-кейсы для** Job**ServiceImpl** |     |     |     |
| 18  | Получение всех аккаунтов (непустой список) | В БД есть 2 сотрудника: Job(id=1, name="A"), Job (id=2, name="B") | List&lt;JobDTO&gt; с 2 элементами: \[JobDTO(1, "A"), JobDTO(2, "B")\] |
| 19  | Получение всех аккаунтов (пустая БД) | В БД нет записей (jobRepository.findAll() возвращает пустой список) | Пустой List&lt;JobDTO&gt; |
| 20  | Проверка маппинга в DTO | Job(id=1, name=null) | JobDTO(1, null) |
| 21  | Успешное создание сотрудника | JobDTO(null, "New Job") | Возвращает JobDTO с id (сгенерирован БД) и именем "New Job" |
| 22  | Создание сотрудника с null-полями | JobDTO(null, null) | Возвращает JobDTO с id и name=null |
| 23  | Проверка вызова jobRepository.save() | Любой JobDTO | jobRepository.save() вызывается ровно 1 раз |
| 24  | Успешное получение сотрудника по ID | id=1 (существует в БД) | Optional.of(JobDTO(1, "A")) |
| 25  | Получение несуществующего сотрудника | id=999 (нет в БД) | Optional.empty() |
| 26  | Передача null в качестве ID | id=null | Optional.empty() (или NPE, если jobRepository.findById(null) не поддерживается) |
| 27  | Удаление существующего сотрудника | id=1 (есть в БД) | jobRepository.deleteById(1) вызывается 1 раз |
| 28  | Удаление несуществующего сотрудника | id=999 (нет в БД) | Исключение (зависит от реализации jobRepository) |
| 29  | Передача null в качестве ID | id=null | Исключение (например, IllegalArgumentException) |
| 30  | Успешное обновление сотрудника | id=1, JobDTO(1, "Updated") (аккаунт с id=1 есть в БД) | Возвращает JobDTO(1, "Updated") |
| 31  | Обновление несуществующего сотрудника | id=999, JobDTO(999, "Fail") | Исключение JobNotFoundException |
| 32  | Обновление с null-полем в DTO | id=1, JobDTO(1, null) | Возвращает JobDTO(1, null) |
| 33  | Проверка вызова departmentRepository.save() | Корректные входные данные | save() вызывается 1 раз |
| 34  | Проверка инъекции зависимостей | Создание JobServiceImpl | Все поля (jobRepository, jobMapper) не null |
| 35  | Проверка аннотаций класса | Проверка класса JobServiceImpl | Имеет аннотацию @Service |
| **Тест-кейсы для** Job**Controller** |     |     |     |
| 36  | Успешное получение списка департаментов (непустой список) | GET /api/jobs  <br>В jobServiceImpl есть данные: \[JobDTO(1, "A"), JobDTO(2, "B")\] | 200 OK, тело: \[JobDTO(1, "A"), JobDTO(2, "B")\] |
| 37  | Получение пустого списка | GET /api/jobs  <br>В jobServiceImpl нет данных (пустой список) | 200 OK, тело: \[\] |
| 38  | Проверка заголовка CORS | Запрос с заголовком Origin: <http://example.com> | В ответе есть заголовок Access-Control-Allow-Origin: \* |
| 39  | Успешное создание департамента | POST /api/job  <br>Тело: {"name": "New Job "} | 200 OK, тело: JobDTO(id, "New Job") (id сгенерирован) |
| 40  | Создание с пустым телом | POST /api/jobs  <br>Тело: {} | 200 OK (или 400 Bad Request, если есть валидация) |
| 41  | Создание с null-полем | POST /api/jobs  <br>Тело: {"name": null} | 200 OK, тело: JobDTO(id, null) |
| 42  | Успешное получение департамента | GET /api/jobs/1  <br>В jobServiceImpl есть JobDTO(1, "A") | 200 OK, тело: JobDTO(1, "A") |
| 43  | Запрос несуществующего ID | GET /api/jobs/999  <br>В jobServiceImpl нет данных | 404 Not Found |
| 44  | Запрос с некорректным ID (буквы) | GET /api/jobs/abc | 400 Bad Request (если Spring валидирует путь) |
| 45  | Успешное удаление | DELETE /api/jobs/1  <br>Департамент с id=1 существует | 204 No Content |
| 46  | Удаление несуществующего ID | DELETE /api/jobs/999 | 404 Not Found |
| 47  | Проверка вызова сервиса | DELETE /api/jobs/1 | jobServiceImpl.deleteJob (1) вызван 1 раз |
| 48  | Успешное обновление | PUT /api/jobs/1  <br>Тело: {"name": "Updated"}  <br>Департамент с id=1 существует | 200 OK, тело: JobDTO(1, "Updated") |
| 49  | Обновление несуществующего ID | PUT /api/jobs/999  <br>Тело: {"name": "Fail"} | 404 Not Found |
| 50  | Обновление с пустым телом | PUT /api/jobs/1  <br>Тело: {} | 200 OK (или 400, если есть валидация) |
| 51  | Несоответствие ID в пути и теле | PUT /api/jobs/1  <br>Тело: {"id": 2, "name": "Conflict"} | 200 OK (ID в пути приоритетен) |
| 52  | Проверка аннотаций класса | Запрос любого метода | Заголовок Access-Control-Allow-Origin: \* (CORS) |
| 53  | Неподдерживаемый метод | PATCH /api/jobs/1 | 405 Method Not Allowed |

#### **5.5 Тестирование Report**

| **ID** | **Название** | **Входные данные** | **Ожидаемый результат** |
| --- | --- | --- | --- |
| **Тест-кейсы для ReportMapper** |     |     |     |
| 1   | Корректное преобразование Report → ReportDTO | Report со всеми заполненными полями (включая вложенные сущности: Department, Job, Employee, Account) | ReportDTO с идентичными значениями всех полей |
| 2   | Преобразование null → null | null | null |
| 3   | Проверка null-полей в Report | Report с dateReceived=null, comments=null | ReportDTO с dateReceived=null, comments=null |
| 4   | Проверка граничных значений | Report с amountIssued=0, recognizedAmount=MAX_DOUBLE | ReportDTO с теми же значениями |
| 5   | Корректное преобразование ReportDTO → Report | ReportDTO со всеми заполненными полями + валидные Department, Job, Employee, Account | Report с идентичными значениями всех полей и ссылками на переданные сущности |
| 6   | Преобразование null → NPE | ReportDTO=null (остальные аргументы не null) | NullPointerException (или IllegalArgumentException, если добавлена валидация) |
| 7   | Проверка null-полей в ReportDTO | ReportDTO с purpose=null, dateApproved=null | Report с purpose=null, dateApproved=null |
| 8   | Проверка граничных значений | ReportDTO с amountIssued=0, recognizedAmount=MAX_DOUBLE | Report с теми же значениями |
| 9   | Проверка null-сущностей | ReportDTO валиден, но Department=null | Report с department=null (или исключение, если требуется обязательная связь) |
| 10  | Передача Report с незаполненными вложенными сущностями (department=null) | ReportDTO с departmentId=null (или исключение, если зависимости обязательны) |     |
| 11  | Преобразование ReportDTO с отрицательными суммами (amountIssued=-1) | Report с amountIssued=-1 (или исключение, если есть валидация на положительные значения) |     |
| **Тест-кейсы для** Report**NotFoundException** |     |     |     |
| 12  | Создание исключения с сообщением | new ReportNotFoundException("Report 123 not found") | Исключение с message = "Report 123 not found", статус 404 |
| 13  | Создание исключения с пустым сообщением | new ReportNotFoundException("") | Исключение с message = "", статус 404 |
| 14  | Создание исключения с null-сообщением | new ReportNotFoundException(null) | Исключение с message = null, статус 404 |
| 15  | Проверка аннотации @ResponseStatus | Проверка класса исключения | Аннотация @ResponseStatus(HttpStatus.NOT_FOUND) присутствует |
| 16  | Проверка наследования | Проверка класса исключения | Наследуется от RuntimeException |
| 17  | Проверка HTTP-статуса при выбросе исключения | Исключение выбрасывается в контроллере | Ответ с HTTP-статусом 404 Not Found |
| 18  | Проверка стека вызовов (stack trace) | Исключение выбрасывается в сервисе | Стек содержит метод, где исключение было создано |
| 19  | Проверка логирования | Исключение логируется в контроллере | Сообщение об ошибке записано в лог |
| **Тест-кейсы для** Report**ServiceImpl** |     |     |     |
| 20  | Успешное создание отчета | Корректный ReportDTO + все сущности существуют | Возвращает ReportDTO с присвоенным reportNumber |
| 21  | Отсутствует Department | ReportDTO с несуществующим departmentId | Исключение RuntimeException("Department not found") |
| 22  | Отсутствует Job | ReportDTO с несуществующим jobId | Исключение RuntimeException("Job not found") |
| 23  | Проверка генерации reportNumber | В БД 3 отчета для департамента | Новый отчет получает reportNumber=4 |
| 24  | Получение всех отчетов (непустой список) | В БД 2 отчета | List&lt;ReportDTO&gt; с 2 элементами |
| 25  | Получение пустого списка | В БД нет отчетов | Пустой List&lt;ReportDTO&gt; |
| 26  | Успешное получение отчетов по критериям | Все ID существуют + есть подходящие отчеты | List&lt;ReportDTO&gt; с отчетами для указанного департамента |
| 27  | Несуществующий Department | departmentId=999 | Исключение RuntimeException("Department not found") |
| 28  | Нет отчетов для критериев | ID валидны, но отчетов нет | Пустой List&lt;ReportDTO&gt; |
| 29  | Успешное получение отчета | reportId=1 (существует) | ReportDTO с данными отчета |
| 30  | Несуществующий отчет | reportId=999 | Исключение RuntimeException("Report not found") |
| 31  | Успешное обновление | reportId=1 + валидный ReportDTO | Возвращает обновленный ReportDTO |
| 32  | Несуществующий отчет | reportId=999 | Исключение RuntimeException("Report not found") |
| 33  | Несуществующий Department | Некорректный departmentId в DTO | Исключение RuntimeException("Department not found") |
| 34  | Успешное удаление | reportId=1 (существует) | Отчет удален из БД |
| 35  | Несуществующий отчет | reportId=999 | Исключение RuntimeException("Report not found") |
| 36  | Удаление отчетов по критериям | Все ID валидны + есть отчеты | Отчеты удалены из БД |
| 37  | Несуществующий Department | departmentId=999 | Исключение RuntimeException("Department not found") |
| 38  | Нет отчетов для удаления | ID валидны, но отчетов нет | Ничего не удаляется (нет ошибки) |
| **Тест-кейсы для** Report**Controller** |     |     |     |
| 39  | Успешное получение всех отчетов | GET /api/reports  <br>В сервисе есть 2 отчета | 200 OK, тело: List&lt;ReportDTO&gt; с 2 элементами |
| 40  | Получение пустого списка | GET /api/reports  <br>В сервисе нет отчетов | 200 OK, тело: \[\] |
| 41  | Проверка CORS | Запрос с Origin: <http://example.com> | В заголовках: Access-Control-Allow-Origin: \* |
| 42  | Успешное создание отчета | POST /api/reports  <br>Тело: валидный ReportDTO | 201 Created, тело: ReportDTO с reportNumber |
| 43  | Ошибка при невалидных данных | POST /api/reports  <br>Тело: ReportDTO с несуществующим departmentId | 500 Internal Server Error (или кастомный статус) |
| 44  | Создание с пустым телом | POST /api/reports  <br>Тело: {} | 400 Bad Request (если есть валидация) |
| 45  | Успешное получение отчетов по критериям | GET /api/reports/department/1/job/2/employee/3/account/4  <br>Все ID существуют | 200 OK, непустой List&lt;ReportDTO&gt; |
| 46  | Несуществующий Department | GET /api/reports/department/999/... | 500 Internal Server Error (или 404 Not Found) |
| 47  | Нет отчетов для критериев | Валидные ID, но отчетов нет | 200 OK, тело: \[\] |
| 48  | Успешное получение отчета | GET /api/reports/1  <br>Отчет существует | 200 OK, тело: ReportDTO |
| 49  | Несуществующий отчет | GET /api/reports/999 | 404 Not Found |
| 50  | Некорректный ID (буквы) | GET /api/reports/abc | 400 Bad Request |
| 51  | Успешное обновление | PUT /api/reports/1  <br>Тело: валидный ReportDTO | 200 OK, тело: обновленный ReportDTO |
| 52  | Обновление несуществующего отчета | PUT /api/reports/999 | 404 Not Found (или 500) |
| 53  | Невалидные данные в DTO | PUT /api/reports/1  <br>Тело: {"departmentId": 999} | 500 Internal Server Error |
| 54  | Успешное удаление | DELETE /api/reports/1 | 204 No Content |
| 55  | Удаление несуществующего отчета | DELETE /api/reports/999 | 404 Not Found |
| 56  | Успешное удаление по критериям | DELETE /api/reports/department/1/... | 204 No Content |
| 57  | Несуществующий Department | DELETE /api/reports/department/999/... | 500 Internal Server Error |

#### **5.5 Тестирование фронтенда**

<table><tbody><tr><th><p><strong>ID</strong></p></th><th><p><strong>Название</strong></p></th><th><p><strong>Входные данные</strong></p></th><th><p><strong>Ожидаемый результат</strong></p></th></tr><tr><td colspan="4"><p><strong>Тест-кейсы для EmployeeProvider</strong></p></td></tr><tr><td><p>1</p></td><td><p>Успешная загрузка сотрудников</p></td><td><p>ApiService&nbsp;возвращает&nbsp;[Employee(id: 1, name: "Test")], fetchEmployees()</p></td><td><p>_employees&nbsp;содержит 1 элемент,&nbsp;_isLoading&nbsp;=&nbsp;false</p></td></tr><tr><td><p>2</p></td><td><p>Ошибка загрузки</p></td><td><p>ApiService&nbsp;бросает исключение, fetchEmployees()</p></td><td><p>_employees&nbsp;пуст,&nbsp;_isLoading&nbsp;=&nbsp;false</p></td></tr><tr><td><p>3</p></td><td><p>Добавление сотрудника</p></td><td><p>name = "New", createEmployee()</p></td><td><p>_employees&nbsp;содержит нового сотрудника</p></td></tr><tr><td><p>4</p></td><td><p>Обновление сотрудника</p></td><td><p>id = 1,&nbsp;name = "Updated", updateEmployee()</p></td><td><p>Сотрудник с&nbsp;id=1&nbsp;обновлен</p></td></tr><tr><td><p>5</p></td><td><p>Удаление сотрудника</p></td><td><p>id = 1, deleteEmployee()</p></td><td><p>Сотрудник с&nbsp;id=1&nbsp;удален из&nbsp;_employees</p></td></tr><tr><td colspan="4"><p><strong>Тест-кейсы для JobProvider</strong></p></td></tr><tr><td><p>6</p></td><td><p>Успешная загрузка должностей</p></td><td><p>ApiService&nbsp;возвращает&nbsp;[Job(id: 1, name: "Dev")], fetchJobs()</p></td><td><p>_jobs&nbsp;содержит 1 элемент,&nbsp;_isLoading&nbsp;=&nbsp;false</p></td></tr><tr><td><p>7</p></td><td><p>Добавление должности</p></td><td><p>name = "QA", createJob()</p></td><td><p>_jobs&nbsp;содержит новую должность</p></td></tr><tr><td><p>8</p></td><td><p>Обновление должности</p></td><td><p>id = 1,&nbsp;name = "Senior Dev", updateJob()</p></td><td><p>Должность с&nbsp;id=1&nbsp;обновлена</p></td></tr><tr><td><p>9</p></td><td><p>Удаление должности</p></td><td><p>id = 1, deleteJob()</p></td><td><p>Должность с&nbsp;id=1&nbsp;удалена</p></td></tr><tr><td colspan="4"><p><strong>Тест-кейсы для ReportProvider</strong></p></td></tr><tr><td><p>10</p></td><td><p>Успешная загрузка отчетов</p></td><td><p>ApiService&nbsp;возвращает&nbsp;[Report(...)],fetchAllReports()</p></td><td><p>_reports&nbsp;содержит 1 отчет,&nbsp;_isLoading&nbsp;=&nbsp;false</p></td></tr><tr><td><p>11</p></td><td><p>Загрузка отчетов по департаменту</p></td><td><p>departmentId = 1, fetchReportsByDepartment()</p></td><td><p>_reports&nbsp;содержит отчеты для&nbsp;departmentId=1</p></td></tr><tr><td><p>12</p></td><td><p>Создание отчета</p></td><td><p>Все обязательные поля заполнены, createReport()</p></td><td><p>_reports&nbsp;содержит новый отчет</p></td></tr><tr><td><p>13</p></td><td><p>Обновление отчета</p></td><td><p>Report(id: 1, ...), updateReport()</p></td><td><p>Отчет с&nbsp;id=1&nbsp;обновлен</p></td></tr><tr><td><p>14</p></td><td><p>Удаление отчета</p></td><td><p>reportId = 1, deleteReport()</p></td><td><p>Отчет с&nbsp;id=1&nbsp;удален</p></td></tr><tr><td colspan="4"><p><strong>Тест-кейсы для AccountProvider</strong></p></td></tr><tr><td><p>15</p></td><td><p>Успешная загрузка аккаунтов</p></td><td><p>ApiService&nbsp;возвращает&nbsp;[Account(id: 1, name: "ACC1")], fetchAccounts()</p></td><td><p>_accounts&nbsp;содержит 1 аккаунт,&nbsp;_isLoading&nbsp;=&nbsp;false</p></td></tr><tr><td><p>16</p></td><td><p>Добавление аккаунта</p></td><td><p>name = "NewACC", createAccount()</p></td><td><p>_accounts&nbsp;содержит новый аккаунт</p></td></tr><tr><td><p>17</p></td><td><p>Обновление аккаунта</p></td><td><p>id = 1,&nbsp;name = "UpdatedACC", updateAccount()</p></td><td><p>Аккаунт с&nbsp;id=1&nbsp;обновлен</p></td></tr><tr><td><p>18</p></td><td><p>Удаление аккаунта</p></td><td><p>id = 1, deleteAccount()</p></td><td><p>Аккаунт с&nbsp;id=1&nbsp;удален</p></td></tr><tr><td colspan="4"><p>Тест-кейсы для DepartmentProvider</p></td></tr><tr><td><p>19</p></td><td><p>Успешная загрузка департаментов</p></td><td><p>ApiService&nbsp;возвращает&nbsp;[Department(id: 1, name: "IT")], fetchDepartments()</p></td><td><p>_departments&nbsp;содержит 1 департамент,&nbsp;_isLoading&nbsp;=&nbsp;false</p></td></tr><tr><td><p>20</p></td><td><p>Добавление департамента</p></td><td><p>name = "HR", createDepartment()</p></td><td><p>_departments&nbsp;содержит новый департамент</p></td></tr><tr><td><p>21</p></td><td><p>Обновление департамента</p></td><td><p>id = 1,&nbsp;name = "Finance", updateDepartment()</p></td><td><p>Департамент с&nbsp;id=1&nbsp;обновлен</p></td></tr><tr><td><p>22</p></td><td><p>Удаление департамента</p></td><td><p>id = 1, deleteDepartment()</p></td><td><p>Департамент с&nbsp;id=1&nbsp;удален</p></td></tr><tr><td colspan="4"><p>Критические проверки всех Provider</p></td></tr><tr><td><p><strong>23</strong></p></td><td><p>Изменение&nbsp;_isLoading</p></td><td><p>Вызов любого метода (например,&nbsp;fetchReports())</p></td><td><p>_isLoading&nbsp;становится&nbsp;true&nbsp;во время выполнения и&nbsp;false&nbsp;после завершения (успешного или с ошибкой)</p></td></tr><tr><td><p><strong>24</strong></p></td><td><p>Вызов&nbsp;notifyListeners()</p></td><td><p>Изменение состояния (например, добавление/удаление элемента)</p></td><td><p>Все слушатели (например,&nbsp;Consumer) получают обновленные данные</p></td></tr><tr><td><p><strong>25</strong></p></td><td><p>Обработка ошибок API</p></td><td><p>API возвращает ошибку (например,&nbsp;DioException&nbsp;или&nbsp;404)</p></td><td><p>_isLoading = false, состояние не изменяется (например,&nbsp;_reports&nbsp;остается прежним), ошибка передается в UI (если нужно)</p></td></tr><tr><td colspan="4"><p>Работа с департаментами</p></td></tr><tr><td><p>26</p></td><td><p>GET /departments</p></td><td><p>Сервер возвращает&nbsp;[{id: 1, name: "IT"}]</p></td><td><p>List&lt;Department&gt;&nbsp;с 1 элементом</p></td></tr><tr><td><p>27</p></td><td><p>GET /departments</p></td><td><p>Сервер возвращает&nbsp;500 Internal Error</p></td><td><p>Исключение&nbsp;DioException</p></td></tr><tr><td><p>28</p></td><td><p>POST /departments</p></td><td><p>name = "HR"</p></td><td><p>Department(id: 2, name: "HR")</p></td></tr><tr><td><p>29</p></td><td><p>PUT /departments/1</p></td><td><p>id = 1,&nbsp;newName = "Finance"</p></td><td><p>Department(id: 1, name: "Finance")</p></td></tr><tr><td><p>30</p></td><td><p>DELETE /departments/1</p></td><td><p>id = 1</p></td><td><p>Успешное удаление (код 200)</p></td></tr><tr><td colspan="4"><p>Работа с отчетами</p></td></tr><tr><td><p>31</p></td><td><p>GET /reports</p></td><td><p>Сервер возвращает&nbsp;[{id: 1, ...}]</p></td><td><p>List&lt;Report&gt;&nbsp;с 1 отчетом</p></td></tr><tr><td><p>32</p></td><td><p>GET /reports/department/1</p></td><td><p>departmentId = 1</p></td><td><p>Отчеты для департамента 1</p></td></tr><tr><td><p>33</p></td><td><p>POST /reports</p></td><td><p>Все обязательные поля заполнены</p></td><td><p>Созданный&nbsp;Report&nbsp;с&nbsp;id</p></td></tr><tr><td><p>34</p></td><td><p>PUT /reports/1</p></td><td><p>reportId = 1,&nbsp;reportNumber = 101</p></td><td><p>Обновленный&nbsp;Report</p></td></tr><tr><td><p>35</p></td><td><p>DELETE /reports/1</p></td><td><p>reportId = 1</p></td><td><p>Успешное удаление (код 200)</p></td></tr><tr><td colspan="4"><p>Работа с должностями</p></td></tr><tr><td><p>36</p></td><td><p>GET /jobs</p></td><td><p>Сервер возвращает&nbsp;[{id: 1, name: "Dev"}]</p></td><td><p>List&lt;Job&gt;&nbsp;с 1 элементом</p></td></tr><tr><td><p>37</p></td><td><p>POST /jobs</p></td><td><p>name = "QA"</p></td><td><p>Job(id: 2, name: "QA")</p></td></tr><tr><td><p>38</p></td><td><p>PUT /jobs/1</p></td><td><p>id = 1,&nbsp;newName = "Senior Dev"</p></td><td><p>Job(id: 1, name: "Senior Dev")</p></td></tr><tr><td><p>39</p></td><td><p>DELETE /jobs/1</p></td><td><p>id = 1</p></td><td><p>Успешное удаление (код 200)</p></td></tr><tr><td colspan="4"><p>Работа с сотрудниками</p></td></tr><tr><td><p>40</p></td><td><p>GET /employees</p></td><td><p>Сервер возвращает&nbsp;[{id: 1, name: "John"}]</p></td><td><p>List&lt;Employee&gt;&nbsp;с 1 элементом</p></td></tr><tr><td><p>41</p></td><td><p>POST /employees</p></td><td><p>name = "Alice"</p></td><td><p>Employee(id: 2, name: "Alice")</p></td></tr><tr><td><p>42</p></td><td><p>PUT /employees/1</p></td><td><p>id = 1,&nbsp;newName = "Bob"</p></td><td><p>Employee(id: 1, name: "Bob")</p></td></tr><tr><td><p>43</p></td><td><p>DELETE /employees/1</p></td><td><p>id = 1</p></td><td><p>Успешное удаление (код 200)</p></td></tr><tr><td colspan="4"><p>Работа со счетами</p></td></tr><tr><td><p>44</p></td><td><p>GET /accounts</p></td><td><p>Сервер возвращает&nbsp;[{id: 1, name: "ACC1"}]</p></td><td><p>List&lt;Account&gt;&nbsp;с 1 элементом</p></td></tr><tr><td><p>45</p></td><td><p>POST /accounts</p></td><td><p>name = "ACC2"</p></td><td><p>Account(id: 2, name: "ACC2")</p></td></tr><tr><td><p>46</p></td><td><p>PUT /accounts/1</p></td><td><p>id = 1,&nbsp;newName = "ACC_Updated"</p></td><td><p>Account(id: 1, name: "ACC_Updated")</p></td></tr><tr><td><p>47</p></td><td><p>DELETE /accounts/1</p></td><td><p>id = 1</p></td><td><p>Успешное удаление (код 200)</p></td></tr><tr><td colspan="4"><p><strong>Обработка сетевых ошибок</strong></p></td></tr><tr><td><p>48</p></td><td><p>Отсутствие интернет-соединения</p></td><td><p>Вызов любого метода API при отключенном интернете</p></td><td><p>Исключение&nbsp;DioException&nbsp;с типом&nbsp;DioExceptionType.connectionError</p></td></tr><tr><td><p>49</p></td><td><p>Таймаут соединения</p></td><td><p>Сервер не отвечает в течение 30 сек</p></td><td><p>Исключение&nbsp;DioException&nbsp;с типом&nbsp;DioExceptionType.connectionTimeout</p></td></tr><tr><td colspan="4"><p><strong>Парсинг некорректного JSON</strong></p></td></tr><tr><td><p>50</p></td><td><p>Отсутствие обязательного поля</p></td><td><p>Сервер возвращает&nbsp;{"name": "Test"}&nbsp;без&nbsp;id</p></td><td><p>Исключение&nbsp;FormatException&nbsp;(если&nbsp;id&nbsp;required)</p></td></tr><tr><td><p>51</p></td><td><p>Полностью битый JSON</p></td><td><p>Сервер возвращает&nbsp;"not a json"</p></td><td><p>Исключение&nbsp;FormatException</p></td></tr><tr><td colspan="4"><p><strong>Валидация статусов HTTP</strong></p></td></tr><tr><td><p>52</p></td><td><p>Ошибка 404 (Not Found)</p></td><td><p>Запрос к несуществующему ресурсу (/unknown)</p></td><td><p>Исключение&nbsp;DioException&nbsp;с&nbsp;response.statusCode = 404</p></td></tr><tr><td><p>53</p></td><td><p>Ошибка 401 (Unauthorized)</p></td><td><p>Запрос без авторизационного токена</p></td><td><p>Исключение&nbsp;DioException&nbsp;с&nbsp;response.statusCode = 401</p></td></tr><tr><td><p>54</p></td><td><p>Ошибка 500 (Server Error)</p></td><td><p>Сервер возвращает&nbsp;500 Internal Server Error</p></td><td><p>Исключение&nbsp;DioException&nbsp;с&nbsp;response.statusCode = 500</p></td></tr><tr><td colspan="4"><p>report_pdf_generator.dart</p></td></tr><tr><td><p>55</p></td><td><p>Генерация PDF с валидными данными</p></td><td><p>Корректные&nbsp;Report,&nbsp;Department,&nbsp;Job,&nbsp;Employee,&nbsp;Account</p></td><td><p>PDF создается без ошибок, содержит все переданные данные</p></td></tr><tr><td><p>56</p></td><td><p>Обработка&nbsp;null-значений в отчете</p></td><td><p>Report&nbsp;с&nbsp;recognizedAmount = null</p></td><td><p>PDF создается, пустые поля заменяются на "Н/Д" или аналоги</p></td></tr><tr><td><p>57</p></td><td><p>Проверка загрузки шрифта</p></td><td><p>Отсутствует файл шрифта в&nbsp;assets</p></td><td><p>Исключение&nbsp;Exception&nbsp;с сообщением об ошибке</p></td></tr><tr><td colspan="4"><p>report_table.dart</p></td></tr><tr><td><p>58</p></td><td><p>Отображение списка отчетов</p></td><td><p>reports&nbsp;с 3 элементами</p></td><td><p>Таблица содержит 3 строки (заголовок + 3 отчета)</p></td></tr><tr><td><p>59</p></td><td><p>Обработка пустого списка</p></td><td><p>reports = []</p></td><td><p>Отображается только заголовок таблицы</p></td></tr><tr><td><p>60</p></td><td><p>Нажатие кнопки "Печать"</p></td><td><p>Вызов&nbsp;onPrint&nbsp;с данными отчета</p></td><td><p>Вызывается&nbsp;ReportPdfGenerator.generateAndOpenPdf</p></td></tr><tr><td><p>61</p></td><td><p>Нажатие кнопки "Удалить"</p></td><td><p>Подтверждение удаления в диалоге</p></td><td><p>Вызывается&nbsp;onDelete&nbsp;с&nbsp;reportId</p></td></tr><tr><td><p>62</p></td><td><p>Отображение "Неизвестно" для отсутствующих связей</p></td><td><p>departmentId&nbsp;нет в&nbsp;departmentProvider</p></td><td><p>В ячейке отображается "Неизвестно"</p></td></tr><tr><td colspan="4"><p>report_edit_dialog.dart</p></td></tr><tr><td><p>63</p></td><td><p>Открытие диалога с данными отчета</p></td><td><p>Report&nbsp;с заполненными полями</p></td><td><p>Все поля формы предзаполнены</p></td></tr><tr><td><p>64</p></td><td><p>Валидация обязательных полей</p></td><td><p>Пустые&nbsp;dateReceived,&nbsp;purpose</p></td><td><p>Форма не отправляется, показываются ошибки</p></td></tr><tr><td><p>65</p></td><td><p>Обновление отчета</p></td><td><p>Изменение полей + нажатие "Сохранить"</p></td><td><p>Вызывается&nbsp;onSave&nbsp;с обновленным&nbsp;Report</p></td></tr><tr><td><p>66</p></td><td><p>Выбор из выпадающих списков</p></td><td><p>Изменение&nbsp;jobId,&nbsp;employeeId</p></td><td><p>Значения сохраняются в&nbsp;updatedReport</p></td></tr><tr><td colspan="4"><p>reports_screen.dart</p></td></tr><tr><td><p>67</p></td><td><p>Загрузка данных при открытии</p></td><td><p>reportProvider.reports&nbsp;пуст</p></td><td><p>Вызывается&nbsp;fetchAllReports()</p></td></tr><tr><td><p>68</p></td><td><p>Отображение состояния загрузки</p></td><td><p>reportProvider.isLoading = true</p></td><td><p>Показывается индикатор загрузки (если есть)</p></td></tr><tr><td><p>69</p></td><td><p>Открытие диалога редактирования</p></td><td><p>Нажатие кнопки "Изменить"</p></td><td><p>Открывается&nbsp;ReportEditDialog</p></td></tr><tr><td colspan="4"><h3>reference_screen.dart</h3></td></tr><tr><td><p>70</p></td><td><p>Проверка отображения вкладок справочника</p></td><td><p>1. Открыть экран&nbsp;ReferenceScreen.</p></td><td><p>Отображаются 4 вкладки: "Филиалы", "Должности", "Сотрудники", "Счета".</p></td></tr><tr><td><p>71</p></td><td><p>Проверка добавления нового филиала</p></td><td><p>1. Нажать кнопку "Добавить" на вкладке "Филиалы".<br>2. Ввести название филиала в диалоговом окне.<br>3. Нажать кнопку "Добавить".</p></td><td><p>Новый филиал отображается в списке.</p></td></tr><tr><td><p>72</p></td><td><p>Проверка редактирования филиала</p></td><td><p>1. Нажать кнопку "Редактировать" у существующего филиала.<br>2. Изменить название в диалоговом окне.<br>3. Нажать кнопку "Сохранить".</p></td><td><p>Название филиала изменено в списке.</p></td></tr><tr><td><p>73</p></td><td><p>Проверка удаления филиала</p></td><td><p>1. Нажать кнопку "Удалить" у существующего филиала.<br>2. Подтвердить удаление в диалоговом окне.</p></td><td><p>Филиал удалён из списка.</p></td></tr><tr><td><p>74</p></td><td><p>Проверка добавления новой должности</p></td><td><p>1. Нажать кнопку "Добавить" на вкладке "Должности".<br>2. Ввести название должности в диалоговом окне.<br>3. Нажать кнопку "Добавить".</p></td><td><p>Новая должность отображается в списке.</p></td></tr><tr><td><p>75</p></td><td><p>Проверка редактирования должности</p></td><td><p>1. Нажать кнопку "Редактировать" у существующей должности.<br>2. Изменить название в диалоговом окне.<br>3. Нажать кнопку "Сохранить".</p></td><td><p>Название должности изменено в списке.</p></td></tr><tr><td><p>76</p></td><td><p>Проверка удаления должности</p></td><td><p>1. Нажать кнопку "Удалить" у существующей должности.<br>2. Подтвердить удаление в диалоговом окне.</p></td><td><p>Должность удалена из списка.</p></td></tr><tr><td><p>77</p></td><td><p>Проверка добавления нового сотрудника</p></td><td><p>1. Нажать кнопку "Добавить" на вкладке "Сотрудники".<br>2. Ввести имя сотрудника в диалоговом окне.<br>3. Нажать кнопку "Добавить".</p></td><td><p>Новый сотрудник отображается в списке.</p></td></tr><tr><td><p>78</p></td><td><p>Проверка редактирования сотрудника</p></td><td><p>1. Нажать кнопку "Редактировать" у существующего сотрудника.<br>2. Изменить имя в диалоговом окне.<br>3. Нажать кнопку "Сохранить".</p></td><td><p>Имя сотрудника изменено в списке.</p></td></tr><tr><td><p>79</p></td><td><p>Проверка удаления сотрудника</p></td><td><p>1. Нажать кнопку "Удалить" у существующего сотрудника.<br>2. Подтвердить удаление в диалоговом окне.</p></td><td><p>Сотрудник удалён из списка.</p></td></tr><tr><td><p>80</p></td><td><p>Проверка добавления нового счёта</p></td><td><p>1. Нажать кнопку "Добавить" на вкладке "Счета".<br>2. Ввести название счёта в диалоговом окне.<br>3. Нажать кнопку "Добавить".</p></td><td><p>Новый счёт отображается в списке.</p></td></tr><tr><td><p>81</p></td><td><p>Проверка редактирования счёта</p></td><td><p>1. Нажать кнопку "Редактировать" у существующего счёта.<br>2. Изменить название в диалоговом окне.<br>3. Нажать кнопку "Сохранить".</p></td><td><p>Название счёта изменено в списке.</p></td></tr><tr><td><p>82</p></td><td><p>Проверка удаления счёта</p></td><td><p>1. Нажать кнопку "Удалить" у существующего счёта.<br>2. Подтвердить удаление в диалоговом окне.</p></td><td><p>Счёт удалён из списка.</p></td></tr><tr><td><p>83</p></td><td><p>Проверка отображения индикатора загрузки при загрузке данных</p></td><td><p>1. Имитировать задержку загрузки данных в провайдерах.<br>2. Открыть экран&nbsp;ReferenceScreen.</p></td><td><p>На каждой вкладке отображается индикатор загрузки (CircularProgressIndicator).</p></td></tr><tr><td><p>84</p></td><td><p>Проверка отмены добавления/редактирования/удаления через кнопку "Отмена" в диалоге</p></td><td><p>1. Нажать кнопку "Добавить" на любой вкладке.<br>2. Ввести данные, но нажать "Отмена" в диалоговом окне.<br>3. Повторить для редактирования и удаления (нажать "Отмена" в диалоге подтверждения).</p></td><td><p>Данные не изменяются, диалог закрывается без сохранения.</p></td></tr><tr><td><p>85</p></td><td><p>Проверка валидации пустого поля при добавлении/редактировании</p></td><td><p>1. Нажать кнопку "Добавить" на любой вкладке.<br>2. Оставить поле пустым и попытаться сохранить.<br>3. Повторить для редактирования (оставить поле пустым).</p></td><td><p>Данные не сохраняются, кнопка "Добавить"/"Сохранить" не реагирует на нажатие при пустом поле.</p></td></tr><tr><td colspan="4"><h3>create_report_screen.dart</h3></td></tr><tr><td><p>86</p></td><td><p>Проверка отображения экрана создания отчёта</p></td><td><p>1. Открыть экран&nbsp;CreateReportScreen.</p></td><td><p>Отображаются все элементы интерфейса: выпадающие списки, текстовые поля, кнопки "Создать отчет" и "Просмотр".</p></td></tr><tr><td><p>87</p></td><td><p>Проверка выбора филиала из выпадающего списка</p></td><td><p>1. Нажать на выпадающий список "Филиал".<br>2. Выбрать любой филиал из списка.</p></td><td><p>Выбранный филиал отображается в списке.</p></td></tr><tr><td><p>88</p></td><td><p>Проверка выбора должности из выпадающего списка</p></td><td><p>1. Нажать на выпадающий список "Должность".<br>2. Выбрать любую должность из списка.</p></td><td><p>Выбранная должность отображается в списке.</p></td></tr><tr><td><p>89</p></td><td><p>Проверка выбора сотрудника из выпадающего списка</p></td><td><p>1. Нажать на выпадающий список "Сотрудник".<br>2. Выбрать любого сотрудника из списка.</p></td><td><p>Выбранный сотрудник отображается в списке.</p></td></tr><tr><td><p>90</p></td><td><p>Проверка выбора счёта из выпадающего списка</p></td><td><p>1. Нажать на выпадающий список "Счет".<br>2. Выбрать любой счёт из списка.</p></td><td><p>Выбранный счёт отображается в списке.</p></td></tr><tr><td><p>91</p></td><td><p>Проверка ввода даты получения д/с</p></td><td><p>1. Ввести дату в поле "Дата получения д/с".</p></td><td><p>Введённая дата сохраняется в поле.</p></td></tr><tr><td><p>92</p></td><td><p>Проверка ввода выданной суммы</p></td><td><p>1. Ввести числовое значение в поле "Выданная сумма".</p></td><td><p>Введённое значение сохраняется в поле.</p></td></tr><tr><td><p>93</p></td><td><p>Проверка ввода даты утверждения а/о</p></td><td><p>1. Ввести дату в поле "Дата утверждения а/о".</p></td><td><p>Введённая дата сохраняется в поле.</p></td></tr><tr><td><p>94</p></td><td><p>Проверка ввода назначения</p></td><td><p>1. Ввести текст в поле "Назначение".</p></td><td><p>Введённый текст сохраняется в поле.</p></td></tr><tr><td><p>95</p></td><td><p>Проверка ввода признанной суммы затрат по а/о</p></td><td><p>1. Ввести числовое значение в поле "Признанная сумма затрат по а/о".</p></td><td><p>Введённое значение сохраняется в поле.</p></td></tr><tr><td><p>96</p></td><td><p>Проверка ввода комментариев</p></td><td><p>1. Ввести текст в поле "Комментарии".</p></td><td><p>Введённый текст сохраняется в поле.</p></td></tr><tr><td><p>97</p></td><td><p>Проверка создания отчёта с заполненными обязательными полями</p></td><td><p>1. Заполнить все обязательные поля (филиал, должность, сотрудник, счёт).<br>2. Нажать кнопку "Создать отчет".</p></td><td><p>Отчёт успешно создан, поля очищаются, отображается уведомление об успехе.</p></td></tr><tr><td><p>98</p></td><td><p>Проверка создания отчёта без заполнения обязательных полей</p></td><td><p>1. Оставить обязательные поля пустыми.<br>2. Нажать кнопку "Создать отчет".</p></td><td><p>Отчёт не создаётся, отображается уведомление о необходимости заполнить обязательные поля.</p></td></tr><tr><td><p>99</p></td><td><p>Проверка кнопки "Просмотр"</p></td><td><p>1. Заполнить некоторые поля.<br>2. Нажать кнопку "Просмотр".</p></td><td><p>Отображаются заполненные данные в виде карточек.</p></td></tr><tr><td><p>100</p></td><td><p>Проверка удаления записи из просмотра</p></td><td><p>1. Нажать кнопку "Просмотр".<br>2. Нажать кнопку "Удалить" у любой записи.<br>3. Подтвердить удаление.</p></td><td><p>Запись удаляется из списка просмотра.</p></td></tr><tr><td><p>101</p></td><td><p>Проверка отмены удаления записи из просмотра</p></td><td><p>1. Нажать кнопку "Просмотр".<br>2. Нажать кнопку "Удалить" у любой записи.<br>3. Отменить удаление в диалоговом окне.</p></td><td><p>Запись остаётся в списке просмотра.</p></td></tr><tr><td><p>102</p></td><td><p>Проверка очистки полей после создания отчёта</p></td><td><p>1. Заполнить все поля.<br>2. Нажать кнопку "Создать отчет".<br>3. Проверить состояние полей.</p></td><td><p>Все поля очищены, выпадающие списки сброшены.</p></td></tr><tr><td><p>103</p></td><td><p>Проверка обработки ошибки при создании отчёта</p></td><td><p>1. Имитировать ошибку в&nbsp;reportProvider.createReport.<br>2. Заполнить все обязательные поля.<br>3. Нажать кнопку "Создать отчет".</p></td><td><p>Отображается уведомление об ошибке, поля не очищаются.</p></td></tr></tbody></table>

### ****6\. Инструменты тестирования****

✅ **Postman** – ручное тестирование API.  
✅ **JUnit + Mockito** – юнит-тестирование.  
✅ **Spring Boot Test** – интеграционные тесты.  
✅ **RestAssured** – автоматизированные API-тесты.  
✅ **JMeter** – нагрузочное тестирование.

### ****7\. Требования к среде тестирования****

- **База данных:** MySQL / H2 (тестовая среда).
- **Сборка проекта:** Maven.
- **Сервер приложений:** Tomcat / Spring Boot встроенный сервер.
- **Тестовые данные:** SQL-скрипты для подготовки тестовой базы.

### ****8\. Отчетность и метрики****

**Основные метрики:**

- Количество успешных / неуспешных тестов.
- Покрытие кода тестами (Code Coverage).
- Время выполнения тестов.
- Количество багов по категориям (функциональные, нефункциональные, критичность).

**Инструменты отчетности:**

- **Allure Report** – отчет по автотестам.
- **JIRA / TestRail** – управление тест-кейсами.
- **SonarQube** – анализ покрытия кода тестами.

### ****9\. Риски и ограничения****

Возможные риски:

- Ошибки в логике работы API.
- Проблемы с базой данных.
- Некорректное кэширование или конфликты данных.

Ограничения:

- Кросс-браузерное тестирование не актуально (API не зависит от UI).
- Производительность тестируется отдельно.

### ****10\. Вывод****

Этот тест-план позволит всесторонне проверить API управления учетными записями. Он охватывает функциональные, производительные и безопасностные тесты, обеспечивая высокое качество продукта.