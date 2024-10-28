import 'package:flutter/material.dart';

class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  @override
  Widget build(BuildContext context) {
    return AppBar(
      title: Text('Авансовый отчет'),
      bottom: TabBar(
        tabs: [
          Tab(text: 'Ввод данных'),
          Tab(text: 'Журнал'),
          Tab(text: 'Справочник'),
        ],
      ),
    );
  }

  @override
  Size get preferredSize => Size.fromHeight(kToolbarHeight + 48.0); // Высота AppBar с TabBar
}
