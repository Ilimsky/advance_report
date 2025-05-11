import 'package:flutter/material.dart';
import 'package:frontend/providers/DepartmentProvider.dart';
import 'package:frontend/providers/EmployeeProvider.dart';
import 'package:frontend/providers/BindingProvider.dart';
import 'package:frontend/screens/CreateReportScreen.dart';
import 'package:frontend/screens/report_screen/reports_screen.dart';
import 'package:provider/provider.dart';

import 'providers/AccountProvider.dart';
import 'providers/JobProvider.dart';
import 'screens/reference_screen/reference_screen.dart';
import 'providers/ReportProvider.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => BindingProvider()),
        ChangeNotifierProvider(create: (_) => DepartmentProvider()),
        ChangeNotifierProvider(create: (_) => ReportProvider()),
        ChangeNotifierProvider(create: (_) => JobProvider()),
        ChangeNotifierProvider(create: (_) => EmployeeProvider()),
        ChangeNotifierProvider(create: (_) => AccountProvider()),
      ],
      child: MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Авансовые отчеты',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: HomeScreen(),
    );
  }
}

class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _selectedIndex = 0;

  final List<Widget> _screens = [
    ReportsScreen(), // Список отчетов
    CreateReportScreen(), // Создание отчетов
    ReferenceScreen(), // Справочник (филиалы)
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Управление отчетами')),
      body: _screens[_selectedIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _selectedIndex,
        onTap: (index) => setState(() => _selectedIndex = index),
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.list), label: 'Список отчетов'),
          BottomNavigationBarItem(icon: Icon(Icons.add), label: 'Создание отчетов'),
          BottomNavigationBarItem(icon: Icon(Icons.book), label: 'Справочник'),
        ],
      ),
    );
  }
}