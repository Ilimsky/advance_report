import 'package:flutter/material.dart';
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
  List<Department> departments = [];
  Department? selectedDepartment;

  // Список для отображения "Авансовых отчетов" с порядком и нумерацией
  List<String> advanceReports = [];

  @override
  void initState() {
    super.initState();
    apiService = ApiService(client: http.Client());
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

  void _updateAdvanceReports(String branchName) {
    // Увеличиваем счетчик выбора филиала
    int currentCount = widget.branchSelectionCount.update(
      branchName,
          (count) => count + 1,
      ifAbsent: () => 1,
    );

    // Формируем строку для списка
    String reportEntry = "$currentCount/$branchName";

    // Лог для проверки значений перед добавлением
    print('InputDataScreen - Adding to advanceReports: $reportEntry');

    // Добавляем запись в список "Авансовых отчетов"
    setState(() {
      advanceReports.add(reportEntry);  // Добавляем строку отчета
    });

    // Вызываем коллбэк для передачи информации о выборе
    widget.onBranchSelected(reportEntry);  // Убедитесь, что только строка передается

    // Лог после обновления списка
    print('InputDataScreen - Updated advanceReports: $advanceReports');
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
            // Text(
            //   'Advance Reports:',
            //   style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
            // ),
            // Expanded(
            //   child: ListView.builder(
            //     itemCount: advanceReports.length,
            //     itemBuilder: (context, index) {
            //       return ListTile(
            //         title: Text(advanceReports[index]),
            //       );
            //     },
            //   ),
            // ),
          ],
        ),
      ),
    );
  }
}
