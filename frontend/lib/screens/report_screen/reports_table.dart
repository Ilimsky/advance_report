import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../providers/ReportProvider.dart';
import '../../providers/DepartmentProvider.dart';
import '../../providers/JobProvider.dart';
import '../../providers/EmployeeProvider.dart';
import '../../providers/AccountProvider.dart';
import 'reports_table_columns.dart';
import 'reports_table_rows.dart';
import 'reports_table_sort.dart';
import 'reports_table_filter.dart';

class ReportsTable extends StatefulWidget {
  final String searchQuery;

  const ReportsTable({required this.searchQuery});

  @override
  _ReportsTableState createState() => _ReportsTableState();
}

class _ReportsTableState extends State<ReportsTable> {
  int _sortColumnIndex = 0;
  bool _isAscending = true;

  @override
  Widget build(BuildContext context) {
    final reportProvider = Provider.of<ReportProvider>(context);
    final departmentProvider = Provider.of<DepartmentProvider>(context);
    final jobProvider = Provider.of<JobProvider>(context);
    final employeeProvider = Provider.of<EmployeeProvider>(context);
    final accountProvider = Provider.of<AccountProvider>(context);

    final filteredReports = filterReports(
      context: context,
      reports: reportProvider.reports,
      searchQuery: widget.searchQuery,
      departmentProvider: departmentProvider,
      jobProvider: jobProvider,
      employeeProvider: employeeProvider,
      accountProvider: accountProvider,
    );

    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      child: SingleChildScrollView(
        child: DataTable(
          sortColumnIndex: _sortColumnIndex,
          sortAscending: _isAscending,
          headingRowHeight: 40,
          dataRowMinHeight: 30,
          dataRowMaxHeight: 40,
          columnSpacing: 1,
          columns: buildTableColumns(
            context: context,
            onSort: (i, asc) => handleSort(
                i,
                asc,
                reportProvider,
                    (index, ascending) {
                  setState(() {
                    _sortColumnIndex = index;
                    _isAscending = ascending;
                  });
                }
            ),
            jobProvider: jobProvider,
            employeeProvider: employeeProvider,
            accountProvider: accountProvider,
          ),
          rows: buildTableRows(
            context: context,
            reports: filteredReports,
            departmentProvider: departmentProvider,
            jobProvider: jobProvider,
            employeeProvider: employeeProvider,
            accountProvider: accountProvider,
          ),
        ),
      ),
    );
  }
}