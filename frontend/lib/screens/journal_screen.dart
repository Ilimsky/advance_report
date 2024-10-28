import 'package:flutter/material.dart';

class JournalScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final items = List.generate(20, (index) => 'Запись $index');

    return ListView.builder(
      itemCount: items.length,
      itemBuilder: (context, index) {
        return ListTile(
          title: Text(items[index]),
        );
      },
    );
  }
}
