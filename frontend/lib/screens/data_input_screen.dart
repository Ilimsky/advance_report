import 'package:flutter/material.dart';

class DataInputScreen extends StatelessWidget {
  final TextEditingController _controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        children: [
          TextField(
            controller: _controller,
            decoration: InputDecoration(
              labelText: 'Введите данные',
              border: OutlineInputBorder(),
            ),
          ),
          SizedBox(height: 20),
          ElevatedButton(
            onPressed: () {
              // Обработка ввода данных
              final input = _controller.text;
              print('Введенные данные: $input'); // Для демонстрации
              _controller.clear(); // Очистка поля ввода
            },
            child: Text('Сохранить'),
          ),
        ],
      ),
    );
  }
}
