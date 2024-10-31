import 'package:flutter/material.dart';
import '../helpers/db_helper.dart';
import '../models/department.dart';
import 'department_form.dart';

class DepartmentListPage extends StatefulWidget {
  const DepartmentListPage({super.key});

  @override
  DepartmentListPageState createState() => DepartmentListPageState();
}

class DepartmentListPageState extends State<DepartmentListPage> {
  late DatabaseHelper dbHelper;
  late Future<List<Department>> departments;

  @override
  void initState() {
    super.initState();
    dbHelper = DatabaseHelper.instance;
    _refreshDepartmentList();
  }

  void _refreshDepartmentList() {
    setState(() {
      departments = dbHelper.readAllDepartments();
    });
  }

  void _openForm(Department? department) async {
    final result = await showDialog(
      context: context,
      builder: (_) => DepartmentForm(department: department),
    );
    if (result == true) _refreshDepartmentList();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.deepPurple,
        title: const Text('Department List'),
      ),
      body: FutureBuilder<List<Department>>(
        future: departments,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('No departments found'));
          }

          return ListView.builder(
            itemCount: snapshot.data!.length,
            itemBuilder: (context, index) {
              final department = snapshot.data![index];

              return ListTile(
                title: Text(department.name),
                trailing: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    IconButton(
                      icon: const Icon(Icons.edit),
                      onPressed: () => _openForm(department),
                    ),
                    IconButton(
                      icon: const Icon(Icons.delete),
                      onPressed: () async {
                        await dbHelper.delete(department.id!);
                        _refreshDepartmentList();
                      },
                    ),
                  ],
                ),
              );
            },
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.add),
        onPressed: () => _openForm(null),
      ),
    );
  }
}