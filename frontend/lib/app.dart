import 'package:flutter/material.dart';
import 'package:frontend/screens/reference_screen.dart';

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Авансовый отчет',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: ReferenceScreen(), // используем ReferenceScreen вместо DeptListPage
    );
  }
}