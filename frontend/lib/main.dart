import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:sqflite/sqflite.dart';
import 'package:sqflite_common_ffi/sqflite_ffi.dart';
import 'screens/data_input_screen.dart';
import 'screens/journal_screen.dart';
import 'screens/reference_screen.dart';
import 'widgets/app_bar.dart';

void main() {
  databaseFactory = databaseFactoryFfi;
  try {
    final sqlite3 = DynamicLibrary.open('sqlite3.dll');
    print('SQLite loaded successfully.');
  } catch (e) {
    print('Error loading SQLite: $e');
  }
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Авансовый отчет',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: HomeScreen(),
    );
  }
}

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        appBar: CustomAppBar(), // Использование кастомного AppBar
        body: TabBarView(
          children: [
            DataInputScreen(),
            JournalScreen(),
            ReferenceScreen(),
          ],
        ),
      ),
    );
  }
}
