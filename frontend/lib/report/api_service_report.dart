import 'dart:convert';
import 'package:frontend/report/report.dart';
import 'package:http/http.dart' as http;

class ApiServiceReport {
  final String reportsUrl = 'http://localhost:8081/reports';  // Укажите свой URL бэкенда
  final String reportUrl = 'http://localhost:8081/report';  // Укажите свой URL бэкенда
  final http.Client client; // HTTP клиент для отправки запросов

  ApiServiceReport({required this.client}); // Инициализация с переданным клиентом

  // Получение всех отчетов
  Future<List<Report>> getReports() async {
    final response = await client.get(Uri.parse(reportsUrl));

    if (response.statusCode == 200) {
      // Декодируем тело ответа как строку UTF-8
      String responseBody = utf8.decode(response.bodyBytes);

      List<dynamic> data = json.decode(responseBody);
      return data.map((reportJson) => Report.fromJson(reportJson)).toList();
    } else {
      throw Exception('Failed to load reports');
    }
  }


  // Создание нового отчета
  Future<Report> createReport(Report report) async {
    try {
      final response = await client.post(
        Uri.parse(reportUrl),
        headers: <String, String>{
          'Content-Type': 'application/json',
        },
        body: json.encode(report.toJson()),
      );

      if (response.statusCode == 200 || response.statusCode == 201) {
        // Если ответ успешен, создаем объект отчета
        final responseData = json.decode(response.body);
        print('Ответ от сервера: ${response.body}');
        return Report.fromJson(responseData); // Возвращаем созданный отчет
      } else {
        // Выводим подробности о неудачном ответе
        print('Неудачный ответ от сервера: ${response.statusCode}');
        print('Тело ответа: ${response.body}');
        throw Exception('Failed to create report');
      }
    } catch (e) {
      // Ошибка при отправке запроса
      print('Ошибка при отправке запроса: $e');
      throw Exception('Failed to create report');
    }
  }

  // Обновление отчета
  Future<Report> updateReport(Report report) async {
    final response = await client.put(
      Uri.parse('$reportUrl/${report.id}'),
      headers: <String, String>{
        'Content-Type': 'application/json',
      },
      body: json.encode(report.toJson()),
    );

    if (response.statusCode == 200) {
      return Report.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed to update report');
    }
  }

  // Синхронизация отчетов
  Future<void> syncReports() async {
    final url = Uri.parse('$reportsUrl'); // Убедитесь, что URL правильный
    final headers = {'Content-Type': 'application/json'};

    print('Запуск синхронизации. URL: $url');

    List<Report> reportsToSync = await getUnsyncedReports();
    print('Количество отчетов для синхронизации: ${reportsToSync.length}');

    if (reportsToSync.isEmpty) {
      print('Нет отчетов для синхронизации');
      return; // Если нет отчетов, завершаем функцию
    }

    for (var report in reportsToSync) {
      final body = json.encode({
        'id': report.id,
        'sequenceNumber': report.sequenceNumber,
        'lastModified': report.lastModified.toIso8601String(),
        'synced': true,
        'newReport': report.newReport,
        'modified': report.modified,
      });

      print('Синхронизация отчета с ID: ${report.id}, данные: $body');

      try {
        final response = await client.post(url, headers: headers, body: body);
        if (response.statusCode == 200) {
          print('Департамент с ID ${report.id} успешно синхронизирован');
        } else {
          print('Ошибка при синхронизации отчета с ID ${report.id}: ${response.statusCode}');
          print('Ответ от сервера: ${response.body}');
          throw Exception('Ошибка синхронизации отчета');
        }
      } catch (e) {
        print('Ошибка при синхронизации: $e');
      }
    }
  }

// Удаление отчета
  Future<void> deleteReport(int id) async {
    final response = await client.delete(Uri.parse('$reportUrl/$id'));

    if (response.statusCode == 200 || response.statusCode == 204) { // Учитываем 204 как успешный код
      print('Департамент с ID $id успешно удален');
    } else if (response.statusCode == 404) {
      print('Департамент с ID $id не найден на сервере (404)');
      throw Exception('Report not found');
    } else {
      print('Ошибка при удалении отчета с ID $id: ${response.statusCode}');
      print('Ответ от сервера: ${response.body}');
      throw Exception('Failed to delete report');
    }
  }

  // Логика получения отчетов, которые нужно синхронизировать
  Future<List<Report>> getUnsyncedReports() async {
    try {
      final allReports = await getReports();  // Получаем все отчеты
      return allReports.where((report) => !report.synced).toList(); // Отбираем те, что не синхронизированы
    } catch (e) {
      throw Exception('Ошибка при загрузке отчетов: $e');
    }
  }
}
