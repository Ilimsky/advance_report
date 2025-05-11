import 'package:flutter/material.dart';
import 'package:frontend/screens/report_screen/reports_dialogs.dart';
import 'package:frontend/screens/report_screen/reports_print.dart';
import 'package:provider/provider.dart';
import 'package:intl/intl.dart';

import '../../models/Report.dart';
import '../../models/Department.dart';
import '../../models/Job.dart';
import '../../models/Employee.dart';
import '../../models/Account.dart';
import '../../providers/DepartmentProvider.dart';
import '../../providers/JobProvider.dart';
import '../../providers/EmployeeProvider.dart';
import '../../providers/AccountProvider.dart';

List<DataRow> buildTableRows({
  required BuildContext context,
  required List<Report> reports,
  required DepartmentProvider departmentProvider,
  required JobProvider jobProvider,
  required EmployeeProvider employeeProvider,
  required AccountProvider accountProvider,
}) {
  final dateFormat = DateFormat('dd.MM.yyyy');

  return reports.map((report) {
    final department = departmentProvider.departments.firstWhere(
          (d) => d.id == report.departmentId,
      orElse: () => Department(id: 0, name: 'Неизвестно'),
    );
    final job = jobProvider.jobs.firstWhere(
          (j) => j.id == report.jobId,
      orElse: () => Job(id: 0, name: 'Неизвестно'),
    );
    final employee = employeeProvider.employees.firstWhere(
          (e) => e.id == report.employeeId,
      orElse: () => Employee(id: 0, name: 'Неизвестно'),
    );
    final account = accountProvider.accounts.firstWhere(
          (a) => a.id == report.accountId,
      orElse: () => Account(id: 0, name: 'Неизвестно'),
    );

    return DataRow(cells: [
      _buildDataCell('${report.reportNumber}/${department.name}', 40, 1),
      _buildDataCell(dateFormat.format(report.dateReceived), 80, 1),
      _buildDataCell(report.amountIssued, 90, 1),
      _buildDataCell(
          report.dateApproved != null ? dateFormat.format(report.dateApproved!) : '',
          80,
          1
      ),
      _buildDataCell(job.name, 100, 2),
      _buildDataCell(employee.name, 120, 1),
      _buildDataCell(report.purpose, 200, 3),
      _buildDataCell(report.recognizedAmount, 90, 1),
      _buildDataCell(account.name, 60, 1),
      _buildDataCell(report.comments, 200, 3),
      _buildActionsCell(context, report, department, job, employee, account),
    ]);
  }).toList();
}

DataCell _buildDataCell(String text, double width, int maxLines) {
  return DataCell(Container(
    width: width,
    child: Text(
      text,
      style: TextStyle(fontSize: 12),
      overflow: TextOverflow.ellipsis,
      maxLines: maxLines,
      textAlign: TextAlign.center,
      softWrap: true,
    ),
  ));
}

DataCell _buildActionsCell(
    BuildContext context,
    Report report,
    Department department,
    Job job,
    Employee employee,
    Account account,
    ) {
  return DataCell(
    Container(
      width: 60,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          IconButton(
            icon: Icon(Icons.edit, size: 16),
            padding: EdgeInsets.zero,
            constraints: BoxConstraints(),
            onPressed: () => showEditReportDialog(context, report),
          ),
          IconButton(
            icon: Icon(Icons.delete, size: 16),
            padding: EdgeInsets.zero,
            constraints: BoxConstraints(),
            onPressed: () => showDeleteReportDialog(context, report.id),
          ),
          IconButton(
            icon: Icon(Icons.print, size: 16),
            padding: EdgeInsets.zero,
            constraints: BoxConstraints(),
            onPressed: () => printReport(report, department, job, employee, account),
          ),
        ],
      ),
    ),
  );
}