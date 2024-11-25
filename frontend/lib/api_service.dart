import 'dart:convert';
import 'package:http/http.dart' as http;
import '../department.dart';

class ApiService {
  final String departmentsUrl =
      'http://localhost:8081/departments'; // Укажите свой URL бэкенда
  final String departmentUrl =
      'http://localhost:8081/department'; // Укажите свой URL бэкенда
  final http.Client client; // HTTP клиент для отправки запросов

  ApiService({required this.client}); // Инициализация с переданным клиентом

  // Получение всех департаментов
  Future<List<Department>> getDepartments() async {
    final response = await client.get(Uri.parse(departmentsUrl));

    if (response.statusCode == 200) {
      // Декодируем тело ответа как строку UTF-8
      String responseBody = utf8.decode(response.bodyBytes);

      List<dynamic> data = json.decode(responseBody);
      return data
          .map((departmentJson) => Department.fromJson(departmentJson))
          .toList();
    } else {
      throw Exception('Failed to load departments');
    }
  }

  // Создание нового департамента
  Future<Department> createDepartment(Department department) async {
    try {
      final response = await client.post(
        Uri.parse(departmentUrl),
        headers: <String, String>{
          'Content-Type': 'application/json',
        },
        body: json.encode(department.toJson()),
      );

      if (response.statusCode == 200 || response.statusCode == 201) {
        // Если ответ успешен, создаем объект департамента
        final responseData = json.decode(response.body);
        print('Ответ от сервера: ${response.body}');
        return Department.fromJson(
            responseData); // Возвращаем созданный департамент
      } else {
        // Выводим подробности о неудачном ответе
        print('Неудачный ответ от сервера: ${response.statusCode}');
        print('Тело ответа: ${response.body}');
        throw Exception('Failed to create department');
      }
    } catch (e) {
      // Ошибка при отправке запроса
      print('Ошибка при отправке запроса: $e');
      throw Exception('Failed to create department');
    }
  }

  // Обновление департамента
  Future<Department> updateDepartment(Department department) async {
    final response = await client.put(
      Uri.parse('$departmentUrl/${department.id}'),
      headers: <String, String>{
        'Content-Type': 'application/json',
      },
      body: json.encode(department.toJson()),
    );

    if (response.statusCode == 200) {
      return Department.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed to update department');
    }
  }

  // Синхронизация департаментов
  Future<void> syncDepartments() async {
    final url = Uri.parse('$departmentsUrl'); // Убедитесь, что URL правильный
    final headers = {'Content-Type': 'application/json'};

    print('Запуск синхронизации. URL: $url');

    List<Department> departmentsToSync = await getUnsyncedDepartments();
    print(
        'Количество департаментов для синхронизации: ${departmentsToSync.length}');

    if (departmentsToSync.isEmpty) {
      print('Нет департаментов для синхронизации');
      return; // Если нет департаментов, завершаем функцию
    }

    for (var department in departmentsToSync) {
      final body = json.encode({
        'id': department.id,
        'name': department.name,
        'lastModified': department.lastModified.toIso8601String(),
        'synced': true,
        'newDepartment': department.newDepartment,
        'modified': department.modified,
      });

      print('Синхронизация департамента с ID: ${department.id}, данные: $body');

      try {
        final response = await client.post(url, headers: headers, body: body);
        if (response.statusCode == 200) {
          print('Департамент с ID ${department.id} успешно синхронизирован');
        } else {
          print(
              'Ошибка при синхронизации департамента с ID ${department.id}: ${response.statusCode}');
          print('Ответ от сервера: ${response.body}');
          throw Exception('Ошибка синхронизации департамента');
        }
      } catch (e) {
        print('Ошибка при синхронизации: $e');
      }
    }
  }

// Удаление департамента
  Future<void> deleteDepartment(int id) async {
    final response = await client.delete(Uri.parse('$departmentUrl/$id'));

    if (response.statusCode == 200 || response.statusCode == 204) {
      // Учитываем 204 как успешный код
      print('Департамент с ID $id успешно удален');
    } else if (response.statusCode == 404) {
      print('Департамент с ID $id не найден на сервере (404)');
      throw Exception('Department not found');
    } else {
      print(
          'Ошибка при удалении департамента с ID $id: ${response.statusCode}');
      print('Ответ от сервера: ${response.body}');
      throw Exception('Failed to delete department');
    }
  }

  // Логика получения департаментов, которые нужно синхронизировать
  Future<List<Department>> getUnsyncedDepartments() async {
    try {
      final allDepartments =
          await getDepartments(); // Получаем все департаменты
      return allDepartments
          .where((department) => !department.synced)
          .toList(); // Отбираем те, что не синхронизированы
    } catch (e) {
      throw Exception('Ошибка при загрузке департаментов: $e');
    }
  }
}
