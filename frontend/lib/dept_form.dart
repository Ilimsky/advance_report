import 'package:flutter/material.dart';
import '../models/dept.dart';
import '../helpers/db_helper.dart';

class DeptForm extends StatefulWidget {
  final Dept? dept;

  const DeptForm({super.key, this.dept});

  @override
  _DeptFormState createState() => _DeptFormState();
}

class _DeptFormState extends State<DeptForm> {
  final nameController = TextEditingController();

  @override
  void initState() {
    super.initState();
    if (widget.dept != null) {
      nameController.text = widget.dept!.name;
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
      title: Text(widget.dept == null ? 'Add Dept' : 'Edit Dept'),
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
              if (widget.dept == null) {
                await DatabaseHelper.instance.create(Dept(name: name));
              } else {
                await DatabaseHelper.instance.update(Dept(
                  id: widget.dept!.id,
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