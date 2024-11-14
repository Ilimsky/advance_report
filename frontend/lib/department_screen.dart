import 'dart:convert';
import 'package:flutter/material.dart';
import '../department.dart';
import '../api_service.dart';
import 'package:http/http.dart' as http;

class DepartmentScreen extends StatefulWidget {
  @override
  _DepartmentScreenState createState() => _DepartmentScreenState();
}

class _DepartmentScreenState extends State<DepartmentScreen> {
  late ApiService apiService;
  List<Department> departments = []; // Список департаментов

  @override
  void initState() {
    super.initState();
    apiService = ApiService(client: http.Client()); // Передаем экземпляр client
    _loadDepartments(); // Загружаем департаменты
  }

  void _loadDepartments() async {
    try {
      print('Загрузка департаментов...');
      List<Department> loadedDepartments = await apiService.getDepartments();
      print('Загружено ${loadedDepartments.length} департаментов');
      setState(() {
        departments = loadedDepartments; // Обновляем список департаментов
      });
    } catch (e) {
      print('Ошибка при загрузке департаментов: $e');
      setState(() {
        departments = []; // Отображаем пустой список
      });
    }
  }

  // Синхронизация департаментов
  void _syncDepartments() async {
    try {
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Синхронизация'),
            content: CircularProgressIndicator(),
          );
        },
      );

      print('Начало синхронизации с сервером...');
      await apiService
          .syncDepartments(); // Синхронизация данных через apiService
      print('Синхронизация завершена успешно.');

      _loadDepartments(); // Перезагружаем данные после синхронизации
      Navigator.of(context).pop(); // Закрываем диалог
    } catch (e) {
      print('Ошибка при синхронизации данных: $e');
      Navigator.of(context).pop(); // Закрываем диалог при ошибке
    }
  }

  // Функция для удаления департамента
  // Метод для удаления департамента

  Widget _buildDepartmentList(List<Department> departments) {
    return ListView.builder(
      itemCount: departments.length,
      itemBuilder: (context, index) {
        final department = departments[index];
        return ListTile(
          title: Text(department.name),
          subtitle: Text('Last modified: ${department.lastModified}'),
          trailing: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              IconButton(
                icon: Icon(Icons.edit),
                onPressed: () async {
                  // Вызовем метод редактирования
                  await _editDepartment(department);
                },
              ),
              IconButton(
                icon: Icon(Icons.delete),
                onPressed: () async {
                  bool? confirmDelete = await showDialog(
                    context: context,
                    builder: (BuildContext context) {
                      return AlertDialog(
                        title: Text('Удалить департамент?'),
                        content: Text(
                            'Вы уверены, что хотите удалить ${department.name}?'),
                        actions: <Widget>[
                          TextButton(
                            child: Text('Отмена'),
                            onPressed: () => Navigator.of(context).pop(false),
                          ),
                          TextButton(
                            child: Text('Удалить'),
                            onPressed: () => Navigator.of(context).pop(true),
                          ),
                        ],
                      );
                    },
                  );

                  if (confirmDelete == true) {
                    try {
                      await apiService.deleteDepartment(department.id);
                      _loadDepartments(); // Обновляем список после удаления
                    } catch (e) {
                      print('Ошибка при удалении департамента: $e');
                    }
                  }
                },
              ),
            ],
          ),
        );
      },
    );
  }

  // Метод для редактирования департамента
  Future<void> _editDepartment(Department department) async {
    TextEditingController nameController = TextEditingController(text: department.name);

    bool? confirmEdit = await showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Редактировать департамент'),
          content: TextField(
            controller: nameController,
            decoration: InputDecoration(labelText: 'Название департамента'),
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Отмена'),
              onPressed: () => Navigator.of(context).pop(false),
            ),
            TextButton(
              child: Text('Сохранить'),
              onPressed: () {
                Navigator.of(context).pop(true);
              },
            ),
          ],
        );
      },
    );

    if (confirmEdit == true) {
      String updatedName = nameController.text;

      // Обновляем департамент на сервере
      department.name = updatedName;

      try {
        await apiService.updateDepartment(department);  // Обновляем департамент
        _loadDepartments();  // Перезагружаем список департаментов после обновления
      } catch (e) {
        print('Ошибка при редактировании департамента: $e');
      }
    }
  }

  void _addDepartment() async {
    TextEditingController nameController = TextEditingController(); // Контроллер для текстового поля

    bool? confirmAdd = await showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Добавить новый департамент'),
          content: TextField(
            controller: nameController, // Привязка к контроллеру
            decoration: InputDecoration(labelText: 'Название департамента'),
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Отмена'),
              onPressed: () => Navigator.of(context).pop(false),
            ),
            TextButton(
              child: Text('Сохранить'),
              onPressed: () {
                Navigator.of(context).pop(true);
              },
            ),
          ],
        );
      },
    );

    if (confirmAdd == true) {
      String departmentName = nameController.text.trim(); // Получаем название департамента

      if (departmentName.isEmpty) {
        print('Название департамента не может быть пустым');
        return; // Если поле пустое, отменяем добавление
      }

      // Создаем новый департамент с введенным именем
      Department newDepartment = Department(
        id: 0,  // id 0 для нового департамента
        name: departmentName,  // Передаем имя как строку без преобразования
        lastModified: DateTime.now(),
        synced: false,
        newDepartment: true,
        modified: false,
      );

      try {
        // Отправляем новый департамент на сервер
        Department createdDepartment = await apiService.createDepartment(newDepartment);

        // Обновляем UI с новым департаментом
        setState(() {
          departments.add(createdDepartment); // Добавляем новый департамент в список
        });

        print('Департамент успешно добавлен: ${createdDepartment.name}, ID: ${createdDepartment.id}');
      } catch (e) {
        print('Ошибка при добавлении департамента: $e');
      }
    }
  }









  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Departments'),
        actions: [
          IconButton(
            icon: Icon(Icons.add),
            onPressed: _addDepartment, // Метод для добавления нового департамента
          ),
          IconButton(
            icon: Icon(Icons.sync),
            onPressed: _syncDepartments, // Синхронизация при нажатии
          ),
        ],
      ),
      body: FutureBuilder<List<Department>>(
        future: apiService.getDepartments(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(
                child:
                    CircularProgressIndicator()); // Показываем индикатор загрузки
          } else if (snapshot.hasError) {
            return Center(child: Text('Ошибка: ${snapshot.error}'));
          } else if (snapshot.hasData && snapshot.data!.isEmpty) {
            return Center(child: Text('Нет департаментов'));
          } else if (snapshot.hasData) {
            return _buildDepartmentList(snapshot.data!);
          } else {
            return Center(child: Text('Нет данных'));
          }
        },
      ),
    );
  }
}
