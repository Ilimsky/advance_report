import 'package:flutter/material.dart';
import 'package:frontend/report/api_service_report.dart';
import 'package:frontend/report/report.dart';
import '../department.dart';
import '../api_service.dart';
import 'package:http/http.dart' as http;

class InputDataScreen extends StatefulWidget {
  final Map<String, int> branchSelectionCount;
  final Function(String) onBranchSelected;

  InputDataScreen({
    Key? key,
    required this.branchSelectionCount,
    required this.onBranchSelected,
  }) : super(key: key);

  @override
  _InputDataScreenState createState() => _InputDataScreenState();
}

class _InputDataScreenState extends State<InputDataScreen> {
  late ApiService apiService;
  late ApiServiceReport apiServiceReport; // Экземпляр для работы с отчетами
  List<Department> departments = [];
  Department? selectedDepartment;

  // Список для отображения "Авансовых отчетов" с порядком и нумерацией
  List<String> advanceReports = [];

  @override
  void initState() {
    super.initState();
    apiService = ApiService(client: http.Client());
    apiServiceReport = ApiServiceReport(client: http.Client()); // Инициализируем ApiServiceReport
    _loadDepartments();
  }

  void _loadDepartments() async {
    try {
      List<Department> loadedDepartments = await apiService.getDepartments();
      setState(() {
        departments = loadedDepartments;
      });
    } catch (e) {
      print('Error loading departments: $e');
    }
  }

  void _updateAdvanceReports(String branchName) async {
    try {
      // Формируем sequenceNumber только с названием филиала
      String sequenceFormatted = "0/${branchName.split('-').last}";

      // Создаём новый отчёт
      final newReport = Report(
        id: 0,
        sequenceNumber: sequenceFormatted,
      );

      // Отправляем отчёт на сервер
      Report createdReport = await apiServiceReport.createReport(newReport);

      // Добавляем sequenceNumber из ответа сервера в список (если успешно)
      if (!advanceReports.contains(createdReport.sequenceNumber)) {
        setState(() {
          advanceReports.add(createdReport.sequenceNumber);
        });
        widget.onBranchSelected(createdReport.sequenceNumber);
      }

      print('Отчет создан: ${createdReport.sequenceNumber}');
    } catch (e) {
      print('Error updating advance reports: $e');
    }
  }


  @override
  Widget build(BuildContext context) {
    return Center(
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            DropdownButton<Department>(
              hint: Text('Филиал'),
              value: selectedDepartment,
              items: departments.map((Department department) {
                return DropdownMenuItem<Department>(
                  value: department,
                  child: Text(department.name),
                );
              }).toList(),
              onChanged: (Department? newValue) {
                if (newValue != null) {
                  setState(() {
                    selectedDepartment = newValue;
                  });
                  _updateAdvanceReports(newValue.name);
                }
              },
            ),
            if (selectedDepartment != null)
              Text('Филиал: ${selectedDepartment!.name}'),
            const SizedBox(height: 20),
          ],
        ),
      ),
    );
  }
}
