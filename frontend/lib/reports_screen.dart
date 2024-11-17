import 'package:flutter/material.dart';

class ReportsScreen extends StatelessWidget {
  final List<String> advanceReports;

  ReportsScreen({Key? key, required this.advanceReports}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    print('ReportsScreen - Current advanceReports: $advanceReports');

    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const SizedBox(height: 10),
          Expanded(
            child: advanceReports.isEmpty
                ? Center(
              child: Text('No reports available.'),
            )
                : ListView.builder(
              itemCount: advanceReports.length,
              itemBuilder: (context, index) {
                print('ReportsScreen - Rendering report item: ${advanceReports[index]}');
                return ListTile(
                  title: Text(advanceReports[index]),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      IconButton(
                        icon: Icon(Icons.edit),
                        onPressed: () {
                          // Handle edit action
                          print('Edit report: ${advanceReports[index]}');
                        },
                      ),
                      IconButton(
                        icon: Icon(Icons.delete),
                        onPressed: () {
                          // Handle delete action
                          print('Delete report: ${advanceReports[index]}');
                        },
                      ),
                      IconButton(
                        icon: Icon(Icons.print),
                        onPressed: () {
                          // Handle print action
                          print('Print report: ${advanceReports[index]}');
                        },
                      ),
                    ],
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
