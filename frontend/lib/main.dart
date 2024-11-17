import 'package:flutter/material.dart';
import 'department_screen.dart';
import 'input_data_screen.dart';
import 'reports_screen.dart'; // Подключаем экран отчетов

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Department App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MainScreen(),
    );
  }
}

class MainScreen extends StatefulWidget {
  @override
  _MainScreenState createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> with SingleTickerProviderStateMixin {
  late TabController _tabController;
  Map<String, int> branchSelectionCount = {};
  List<String> advanceReports = []; // Храним список отчетов

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 3, vsync: this); // 3 вкладки
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  void _updateReports(String branch) {
    setState(() {
      // Увеличиваем счетчик для выбранного филиала
      int count = branchSelectionCount[branch] ?? 0;
      advanceReports.add("$branch");
      branchSelectionCount[branch] = count + 1;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Авансовые отчеты'),
        bottom: TabBar(
          controller: _tabController,
          tabs: [
            Tab(text: 'Авансовые отчеты'),
            Tab(text: 'Ввод данных'),
            Tab(text: 'Справочник'), // Новая вкладка
          ],
        ),
      ),
      body: TabBarView(
        controller: _tabController,
        children: [
          ReportsScreen(advanceReports: advanceReports), // Новая вкладка
          InputDataScreen(
            branchSelectionCount: branchSelectionCount,
            onBranchSelected: _updateReports, // Передаем callback
          ),
          DepartmentScreen(),
        ],
      ),
    );
  }
}
