import 'package:flutter/material.dart';
import '../models/department.dart';
import '../helpers/db_helper.dart';

class DepartmentForm extends StatefulWidget {
  final Department? department;

  const DepartmentForm({super.key, this.department});

  @override
  _DepartmentFormState createState() => _DepartmentFormState();
}

class _DepartmentFormState extends State<DepartmentForm> {
  final nameController = TextEditingController();

  @override
  void initState() {
    super.initState();
    if (widget.department != null) {
      nameController.text = widget.department!.name;
    }
  }

  @override
  void dispose() {
    nameController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text(widget.department == null ? 'Add Department' : 'Edit Department'),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          TextField(
            controller: nameController,
            decoration: const InputDecoration(labelText: 'Name'),
          ),
        ],
      ),
      actions: [
        TextButton(
          onPressed: () => Navigator.of(context).pop(),
          child: const Text('Cancel'),
        ),
        TextButton(
          onPressed: () async {
            final name = nameController.text;

            if (name.isNotEmpty) {
              if (widget.department == null) {
                await DatabaseHelper.instance.create(Department(name: name));
              } else {
                await DatabaseHelper.instance.update(Department(
                  id: widget.department!.id,
                  name: name,
                ));
              }
              Navigator.of(context).pop(true);
            }
          },
          child: const Text('Save'),
        ),
      ],
    );
  }
}