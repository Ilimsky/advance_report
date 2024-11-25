import 'package:flutter/material.dart';
import 'package:frontend/report/api_service_report.dart';
import 'package:frontend/report/report.dart';
import 'package:http/http.dart' as http;

class ReportsScreen extends StatefulWidget {
  final List<String> advanceReports;

  ReportsScreen({Key? key, required this.advanceReports}) : super(key: key);

  @override
  _ReportsScreenState createState() => _ReportsScreenState();
}

class _ReportsScreenState extends State<ReportsScreen> {
  late List<String> processedReports;
  late ApiServiceReport apiServiceReport;
  List<Report> reports = [];
  bool isSyncing = false; // Для отображения прогресса

  @override
  void initState() {
    super.initState();
    // Инициализация processedReports из advanceReports
    processedReports = widget.advanceReports
        .map((report) => report.replaceAll('/ФРЛ-', '/'))
        .toList();
    apiServiceReport = ApiServiceReport(client: http.Client()); // Передаем экземпляр client
    _loadReports(); // Загружаем отчеты
    _autoSyncReports(); // Запускаем автоматическую синхронизацию
  }

  void _loadReports() async {
    try {
      // print('Загрузка отчетов...');
      List<Report> loadedReports = await apiServiceReport.getReports();
      // print('Загружено ${loadedReports.length} отчетов');
      setState(() {
        reports = loadedReports; // Обновляем список отчетов
        processedReports = loadedReports.map((report) => report.sequenceNumber).cast<String>().toList(); // Обновляем processedReports
      });
    } catch (e) {
      print('Ошибка при загрузке отчетов: $e');
      setState(() {
        reports = []; // Отображаем пустой список
        processedReports = []; // Обновляем processedReports
      });
    }
  }


  Future<void> _syncReports() async {
    try {
      setState(() {
        isSyncing = true; // Начинаем отображение прогресса
      });

      // print('Начало синхронизации с сервером...');
      await apiServiceReport.syncReports(); // Синхронизация данных через apiService
      // print('Синхронизация завершена успешно.');

      // Обновляем список после синхронизации
      _loadReports(); // Перезагружаем данные после синхронизации
      setState(() {
        processedReports = widget.advanceReports
            .map((report) => report.replaceAll('/ФРЛ-', '/'))
            .toList(); // Обновляем список обработанных отчетов
      });

    } catch (e) {
      print('Ошибка при синхронизации данных: $e');
    } finally {
      setState(() {
        isSyncing = false; // Завершаем отображение прогресса
      });
    }
  }


  void _autoSyncReports() {
    WidgetsBinding.instance.addPostFrameCallback((_) {
      _syncReports(); // Синхронизируем сразу после загрузки экрана
    });
  }

  @override
  Widget build(BuildContext context) {
    // print('ReportsScreen - Current advanceReports: ${widget.advanceReports}');
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(
                'Reports',
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
              ),
              if (isSyncing)
                CircularProgressIndicator() // Отображаем индикатор во время синхронизации
              else
                IconButton(
                  icon: Icon(Icons.sync),
                  onPressed: _syncReports, // Синхронизация по запросу
                ),
            ],
          ),
          const SizedBox(height: 10),
          Expanded(
            child: processedReports.isEmpty
                ? Center(
              child: Text('No reports available.'),
            )
                : ListView.builder(
              itemCount: processedReports.length,
              itemBuilder: (context, index) {
                // print('ReportsScreen - Rendering report item: ${processedReports[index]}');
                return ListTile(
                  title: Text(processedReports[index]),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      IconButton(
                        icon: Icon(Icons.edit),
                        onPressed: () {
                          final report = reports[index];
                          _editReport(report);
                        },
                      ),
                      IconButton(
                        icon: Icon(Icons.delete),
                        onPressed: () {
                          final report = reports[index];
                          _deleteReport(report);
                        },
                      ),

                      IconButton(
                        icon: Icon(Icons.print),
                        onPressed: () {
                          print('Print report: ${processedReports[index]}');
                        },
                      ),
                    ],
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }

  void _editReport(Report report) async {
    TextEditingController _controller = TextEditingController(text: report.sequenceNumber);

    await showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: Text('Edit Report'),
          content: TextField(
            controller: _controller,
            decoration: InputDecoration(labelText: 'Sequence Number'),
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(); // Закрываем диалог
              },
              child: Text('Cancel'),
            ),
            TextButton(
              onPressed: () async {
                Navigator.of(context).pop(); // Закрываем диалог
                final updatedReport = Report(
                  id: report.id,
                  sequenceNumber: _controller.text,
                );
                try {
                  await apiServiceReport.updateReport(updatedReport);
                  _loadReports(); // Перезагружаем список отчетов
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(content: Text('Report updated successfully')),
                  );
                } catch (e) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(content: Text('Failed to update report: $e')),
                  );
                }
              },
              child: Text('Save'),
            ),
          ],
        );
      },
    );
  }

  void _deleteReport(Report report) async {
    bool? confirm = await showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: Text('Delete Report'),
          content: Text('Are you sure you want to delete this report?'),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(false); // Отмена
              },
              child: Text('Cancel'),
            ),
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(true); // Подтверждение
              },
              child: Text('Delete'),
            ),
          ],
        );
      },
    );

    if (confirm == true) {
      try {
        await apiServiceReport.deleteReport(report.id);
        _loadReports(); // Перезагружаем список отчетов
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Report deleted successfully')),
        );
      } catch (e) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Failed to delete report: $e')),
        );
      }
    }
  }


}
