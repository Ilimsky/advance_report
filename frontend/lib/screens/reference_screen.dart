import 'package:flutter/material.dart';
import '../database_helper.dart';

class ReferenceScreen extends StatefulWidget {
  @override
  _ReferenceScreenState createState() => _ReferenceScreenState();
}

class _ReferenceScreenState extends State<ReferenceScreen> {
  final TextEditingController _branchController = TextEditingController();
  final DatabaseHelper _databaseHelper = DatabaseHelper();

  void _addBranch() async {
    final branch = _branchController.text;
    if (branch.isNotEmpty) {
      await _databaseHelper.insertDepartment(branch); // Сохранение в базу данных
      print('Добавлен филиал: $branch'); // Для демонстрации
      _branchController.clear(); // Очистка поля ввода
    }
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Row(
        children: [
          Expanded(
            child: TextField(
              controller: _branchController,
              decoration: InputDecoration(
                labelText: 'Филиал',
                border: OutlineInputBorder(),
              ),
            ),
          ),
          SizedBox(width: 10), // Отступ между полем и кнопкой
          ElevatedButton(
            onPressed: _addBranch,
            child: Text('Добавить'),
          ),
        ],
      ),
    );
  }
}
