import 'package:flutter/cupertino.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

import '../../models/Report.dart';
import '../../models/Department.dart';
import '../../models/Job.dart';
import '../../models/Employee.dart';
import '../../models/Account.dart';
import '../../providers/DepartmentProvider.dart';
import '../../providers/JobProvider.dart';
import '../../providers/EmployeeProvider.dart';
import '../../providers/AccountProvider.dart';

List<Report> filterReports({
  required BuildContext context,
  required List<Report> reports,
  required String searchQuery,
  required DepartmentProvider departmentProvider,
  required JobProvider jobProvider,
  required EmployeeProvider employeeProvider,
  required AccountProvider accountProvider,
}) {
  if (searchQuery.isEmpty) return reports;

  final dateFormat = DateFormat('dd.MM.yyyy');

  return reports.where((report) {
    final department = departmentProvider.departments.firstWhere(
          (d) => d.id == report.departmentId,
      orElse: () => Department(id: 0, name: ''),
    );
    final job = jobProvider.jobs.firstWhere(
          (j) => j.id == report.jobId,
      orElse: () => Job(id: 0, name: ''),
    );
    final employee = employeeProvider.employees.firstWhere(
          (e) => e.id == report.employeeId,
      orElse: () => Employee(id: 0, name: ''),
    );
    final account = accountProvider.accounts.firstWhere(
          (a) => a.id == report.accountId,
      orElse: () => Account(id: 0, name: ''),
    );

    final dateReceivedStr = dateFormat.format(report.dateReceived);
    final dateApprovedStr = report.dateApproved != null ? dateFormat.format(report.dateApproved!) : '';

    final query = searchQuery.toLowerCase();
    return report.reportNumber.toString().contains(query) ||
        dateReceivedStr.toLowerCase().contains(query) ||
        dateApprovedStr.toLowerCase().contains(query) ||
        report.amountIssued.toLowerCase().contains(query) ||
        report.recognizedAmount.toLowerCase().contains(query) ||
        report.purpose.toLowerCase().contains(query) ||
        report.comments.toLowerCase().contains(query) ||
        department.name.toLowerCase().contains(query) ||
        job.name.toLowerCase().contains(query) ||
        employee.name.toLowerCase().contains(query) ||
        account.name.toLowerCase().contains(query);
  }).toList();
}