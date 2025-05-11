import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../providers/ReportProvider.dart';
import 'reports_table.dart';
import 'reports_search.dart';

class ReportsScreen extends StatefulWidget {
  @override
  _ReportsScreenState createState() => _ReportsScreenState();
}

class _ReportsScreenState extends State<ReportsScreen> {
  final TextEditingController _searchController = TextEditingController();
  String _searchQuery = '';

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      Provider.of<ReportProvider>(context, listen: false).fetchAllReports();
    });
  }

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Отчеты')),
      body: Column(
        children: [
          SearchReportsField(
            controller: _searchController,
            onChanged: (value) => setState(() => _searchQuery = value),
          ),
          Expanded(
            child: Consumer<ReportProvider>(
                builder: (context, reportProvider, child) {
              if (reportProvider.isLoading) {
                return Center(child: CircularProgressIndicator());
              }
              return ReportsTable(
                searchQuery: _searchQuery,
              );
            }),
          ),
        ],
      ),
    );
  }
}