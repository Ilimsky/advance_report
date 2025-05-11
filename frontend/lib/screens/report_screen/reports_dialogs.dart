import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

import '../../api/ApiService.dart';
import '../../models/Account.dart';
import '../../models/Department.dart';
import '../../models/Employee.dart';
import '../../models/Job.dart';
import '../../models/Report.dart';
import '../../providers/ReportProvider.dart';

void showEditReportDialog(BuildContext context, Report report) {
  final reportProvider = Provider.of<ReportProvider>(context, listen: false);

  // Контроллеры для текстовых полей
  final reportNumberController = TextEditingController(text: report.reportNumber.toString());
  final dateReceivedController = TextEditingController(text: DateFormat('yyyy-MM-dd').format(report.dateReceived));
  final dateApprovedController = TextEditingController(text: DateFormat('yyyy-MM-dd').format(report.dateApproved));
  final amountIssuedController = TextEditingController(text: report.amountIssued);
  final purposeController = TextEditingController(text: report.purpose);
  final recognizedAmountController = TextEditingController(text: report.recognizedAmount);
  final commentsController = TextEditingController(text: report.comments);

  // Значения для выпадающих списков
  int? selectedDepartmentId = report.departmentId;
  int? selectedJobId = report.jobId;
  int? selectedEmployeeId = report.employeeId;
  int? selectedAccountId = report.accountId;

  showDialog(
    context: context,
    builder: (context) => StatefulBuilder(
      builder: (context, setState) {
        return AlertDialog(
          title: Text('Редактировать отчет'),
          content: SingleChildScrollView(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                TextField(
                  controller: reportNumberController,
                  decoration: InputDecoration(labelText: 'Номер отчета'),
                  keyboardType: TextInputType.number,
                ),
                // Department dropdown
                FutureBuilder<List<Department>>(
                  future: ApiService().fetchDepartments(),
                  builder: (context, snapshot) {
                    if (!snapshot.hasData) return CircularProgressIndicator();
                    return DropdownButtonFormField<int>(
                      value: selectedDepartmentId,
                      items: snapshot.data!.map((department) {
                        return DropdownMenuItem<int>(
                          value: department.id,
                          child: Text(department.name),
                        );
                      }).toList(),
                      onChanged: (value) => setState(() => selectedDepartmentId = value),
                      decoration: InputDecoration(labelText: 'Отдел'),
                    );
                  },
                ),
                // Job dropdown
                FutureBuilder<List<Job>>(
                  future: ApiService().fetchJobs(),
                  builder: (context, snapshot) {
                    if (!snapshot.hasData) return CircularProgressIndicator();
                    return DropdownButtonFormField<int>(
                      value: selectedJobId,
                      items: snapshot.data!.map((job) {
                        return DropdownMenuItem<int>(
                          value: job.id,
                          child: Text(job.name),
                        );
                      }).toList(),
                      onChanged: (value) => setState(() => selectedJobId = value),
                      decoration: InputDecoration(labelText: 'Должность'),
                    );
                  },
                ),
                // Employee dropdown
                FutureBuilder<List<Employee>>(
                  future: ApiService().fetchEmployees(),
                  builder: (context, snapshot) {
                    if (!snapshot.hasData) return CircularProgressIndicator();
                    return DropdownButtonFormField<int>(
                      value: selectedEmployeeId,
                      items: snapshot.data!.map((employee) {
                        return DropdownMenuItem<int>(
                          value: employee.id,
                          child: Text(employee.name),
                        );
                      }).toList(),
                      onChanged: (value) => setState(() => selectedEmployeeId = value),
                      decoration: InputDecoration(labelText: 'Сотрудник'),
                    );
                  },
                ),
                // Account dropdown
                FutureBuilder<List<Account>>(
                  future: ApiService().fetchAccounts(),
                  builder: (context, snapshot) {
                    if (!snapshot.hasData) return CircularProgressIndicator();
                    return DropdownButtonFormField<int>(
                      value: selectedAccountId,
                      items: snapshot.data!.map((account) {
                        return DropdownMenuItem<int>(
                          value: account.id,
                          child: Text(account.name),
                        );
                      }).toList(),
                      onChanged: (value) => setState(() => selectedAccountId = value),
                      decoration: InputDecoration(labelText: 'Счет'),
                    );
                  },
                ),
                // Date fields
                TextField(
                  controller: dateReceivedController,
                  decoration: InputDecoration(
                    labelText: 'Дата получения',
                    suffixIcon: Icon(Icons.calendar_today),
                  ),
                  readOnly: true,
                  onTap: () async {
                    final date = await showDatePicker(
                      context: context,
                      initialDate: report.dateReceived,
                      firstDate: DateTime(2000),
                      lastDate: DateTime(2100),
                    );
                    if (date != null) {
                      dateReceivedController.text = DateFormat('yyyy-MM-dd').format(date);
                    }
                  },
                ),
                TextField(
                  controller: dateApprovedController,
                  decoration: InputDecoration(
                    labelText: 'Дата утверждения',
                    suffixIcon: Icon(Icons.calendar_today),
                  ),
                  readOnly: true,
                  onTap: () async {
                    final date = await showDatePicker(
                      context: context,
                      initialDate: report.dateApproved,
                      firstDate: DateTime(2000),
                      lastDate: DateTime(2100),
                    );
                    if (date != null) {
                      dateApprovedController.text = DateFormat('yyyy-MM-dd').format(date);
                    }
                  },
                ),
                // Other fields
                TextField(
                  controller: amountIssuedController,
                  decoration: InputDecoration(labelText: 'Выданная сумма'),
                  keyboardType: TextInputType.numberWithOptions(decimal: true),
                ),
                TextField(
                  controller: purposeController,
                  decoration: InputDecoration(labelText: 'Цель'),
                  maxLines: 2,
                ),
                TextField(
                  controller: recognizedAmountController,
                  decoration: InputDecoration(labelText: 'Признанная сумма'),
                  keyboardType: TextInputType.numberWithOptions(decimal: true),
                ),
                TextField(
                  controller: commentsController,
                  decoration: InputDecoration(labelText: 'Комментарии'),
                  maxLines: 3,
                ),
              ],
            ),
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: Text('Отмена'),
            ),
            TextButton(
              onPressed: () {
                reportProvider.updateReport(
                  report.id,
                  reportNumber: int.parse(reportNumberController.text),
                  departmentId: selectedDepartmentId!,
                  jobId: selectedJobId!,
                  employeeId: selectedEmployeeId!,
                  accountId: selectedAccountId!,
                  dateReceived: DateFormat('yyyy-MM-dd').parse(dateReceivedController.text),
                  amountIssued: amountIssuedController.text,
                  dateApproved: DateFormat('yyyy-MM-dd').parse(dateApprovedController.text),
                  purpose: purposeController.text,
                  recognizedAmount: recognizedAmountController.text,
                  comments: commentsController.text,
                );
                Navigator.pop(context);
              },
              child: Text('Сохранить'),
            ),
          ],
        );
      },
    ),
  );
}
void showDeleteReportDialog(BuildContext context, int reportId) {
  final reportProvider = Provider.of<ReportProvider>(context, listen: false);

  showDialog(
    context: context,
    builder: (context) => AlertDialog(
      title: Text('Удалить отчет'),
      content: Text('Вы уверены, что хотите удалить этот отчет?'),
      actions: [
        TextButton(
          onPressed: () => Navigator.pop(context),
          child: Text('Отмена'),
        ),
        TextButton(
          onPressed: () {
            reportProvider.deleteReport(reportId);
            Navigator.pop(context);
          },
          child: Text('Удалить', style: TextStyle(color: Colors.red)),
        ),
      ],
    ),
  );
}