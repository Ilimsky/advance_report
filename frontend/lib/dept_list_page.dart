import 'package:flutter/material.dart';
import '../helpers/db_helper.dart';
import '../models/dept.dart';
import 'dept_form.dart';

class DeptListPage extends StatefulWidget {
  const DeptListPage({super.key});

  @override
  DeptListPageState createState() => DeptListPageState();
}

class DeptListPageState extends State<DeptListPage> {
  late DatabaseHelper dbHelper;
  late Future<List<Dept>> depts;

  @override
  void initState() {
    super.initState();
    dbHelper = DatabaseHelper.instance;
    _refreshDeptList();
  }

  void _refreshDeptList() {
    setState(() {
      depts = dbHelper.readAllDepts();
    });
  }

  void _openForm(Dept? dept) async {
    final result = await showDialog(
      context: context,
      builder: (_) => DeptForm(dept: dept),
    );
    if (result == true) _refreshDeptList();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.deepPurple,
        title: const Text('Dept List'),
      ),
      body: FutureBuilder<List<Dept>>(
        future: depts,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('No depts found'));
          }

          return ListView.builder(
            itemCount: snapshot.data!.length,
            itemBuilder: (context, index) {
              final dept = snapshot.data![index];

              return ListTile(
                title: Text(dept.name),
                trailing: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    IconButton(
                      icon: const Icon(Icons.edit),
                      onPressed: () => _openForm(dept),
                    ),
                    IconButton(
                      icon: const Icon(Icons.delete),
                      onPressed: () async {
                        await dbHelper.delete(dept.id!);
                        _refreshDeptList();
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