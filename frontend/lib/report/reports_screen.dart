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

  @override
  void initState() {
    super.initState();
    // Инициализация processedReports из advanceReports
    processedReports = widget.advanceReports
        .map((report) => report.replaceAll('/ФРЛ-', '/'))
        .toList();
    apiServiceReport = ApiServiceReport(client: http.Client()); // Передаем экземпляр client
    _loadReports(); // Загружаем отчеты
  }

  void _loadReports() async {
    try {
      print('Загрузка отчетов...');
      List<Report> loadedReports = await apiServiceReport.getReports();
      print('Загружено ${loadedReports.length} отчетов');
      setState(() {
        reports = loadedReports; // Обновляем список отчетов
      });
    } catch (e) {
      print('Ошибка при загрузке отчетов: $e');
      setState(() {
        reports = []; // Отображаем пустой список
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    print('ReportsScreen - Current advanceReports: ${widget.advanceReports}');
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const SizedBox(height: 10),
          Expanded(
            child: processedReports.isEmpty
                ? Center(
              child: Text('No reports available.'),
            )
                : ListView.builder(
              itemCount: processedReports.length,
              itemBuilder: (context, index) {
                print('ReportsScreen - Rendering report item: ${processedReports[index]}');
                return ListTile(
                  title: Text(processedReports[index]),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      IconButton(
                        icon: Icon(Icons.edit),
                        onPressed: () {
                          print('Edit report: ${processedReports[index]}');
                        },
                      ),
                      IconButton(
                        icon: Icon(Icons.delete),
                        onPressed: () {
                          print('Delete report: ${processedReports[index]}');
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
}
