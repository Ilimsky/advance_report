import 'package:flutter/material.dart';
import '../../models/report.dart';

import '../../providers/job_provider.dart';
import '../../providers/employee_provider.dart';
import '../../providers/account_provider.dart';
import 'package:provider/provider.dart';

class ReportEditDialog extends StatelessWidget {
  final Report report;
  final Function(Report) onSave;

  const ReportEditDialog({
    required this.report,
    required this.onSave,
  });

  @override
  Widget build(BuildContext context) {
    final _formKey = GlobalKey<FormState>();

    final TextEditingController _dateReceivedController = TextEditingController(text: report.dateReceived);
    final TextEditingController _amountIssuedController = TextEditingController(text: report.amountIssued);
    final TextEditingController _dateApprovedController = TextEditingController(text: report.dateApproved ?? '');
    final TextEditingController _purposeController = TextEditingController(text: report.purpose);
    final TextEditingController _recognizedAmountController = TextEditingController(text: report.recognizedAmount ?? '');
    final TextEditingController _commentsController = TextEditingController(text: report.comments);

    // Получаем провайдеры для выпадающих списков
    final jobProvider = Provider.of<JobProvider>(context);
    final employeeProvider = Provider.of<EmployeeProvider>(context);
    final accountProvider = Provider.of<AccountProvider>(context);

    // Выбранные значения (по умолчанию — текущие значения отчета)
    int? selectedJobId = report.jobId;
    int? selectedEmployeeId = report.employeeId;
    int? selectedAccountId = report.accountId;

    return AlertDialog(
      title: Text('Редактировать отчет'),
      content: Form(
        key: _formKey,
        child: SingleChildScrollView(
          child: Column(
            children: [
              // Поле для даты получения д/с
              TextFormField(
                controller: _dateReceivedController,
                decoration: InputDecoration(labelText: 'Дата получения д/с'),
              ),
              // Поле для выданной суммы
              TextFormField(
                controller: _amountIssuedController,
                decoration: InputDecoration(labelText: 'Выданная сумма'),
                keyboardType: TextInputType.number,
              ),
              // Поле для даты утверждения а/о
              TextFormField(
                controller: _dateApprovedController,
                decoration: InputDecoration(labelText: 'Дата утверждения а/о'),
              ),
              // Поле для назначения
              TextFormField(
                controller: _purposeController,
                decoration: InputDecoration(labelText: 'Назначение'),
              ),
              // Поле для признанной суммы по а/о
              TextFormField(
                controller: _recognizedAmountController,
                decoration: InputDecoration(labelText: 'Признанная сумма по а/о'),
                keyboardType: TextInputType.number,
              ),
              // Поле для комментариев
              TextFormField(
                controller: _commentsController,
                decoration: InputDecoration(labelText: 'Комментарии'),
              ),
              // Выпадающий список для выбора должности
              DropdownButtonFormField<int>(
                value: selectedJobId,
                decoration: InputDecoration(labelText: 'Должность'),
                items: jobProvider.jobs.map((job) {
                  return DropdownMenuItem(
                    value: job.id,
                    child: Text(job.name),
                  );
                }).toList(),
                onChanged: (value) {
                  selectedJobId = value;
                },
              ),
              // Выпадающий список для выбора сотрудника
              DropdownButtonFormField<int>(
                value: selectedEmployeeId,
                decoration: InputDecoration(labelText: 'Сотрудник'),
                items: employeeProvider.employees.map((employee) {
                  return DropdownMenuItem(
                    value: employee.id,
                    child: Text(employee.name),
                  );
                }).toList(),
                onChanged: (value) {
                  selectedEmployeeId = value;
                },
              ),
              // Выпадающий список для выбора счета
              DropdownButtonFormField<int>(
                value: selectedAccountId,
                decoration: InputDecoration(labelText: 'Счет'),
                items: accountProvider.accounts.map((account) {
                  return DropdownMenuItem(
                    value: account.id,
                    child: Text(account.name),
                  );
                }).toList(),
                onChanged: (value) {
                  selectedAccountId = value;
                },
              ),
            ],
          ),
        ),
      ),
      actions: [
        TextButton(
          onPressed: () {
            Navigator.pop(context); // Закрыть диалог
          },
          child: Text('Отмена'),
        ),
        TextButton(
          onPressed: () {
            if (_formKey.currentState!.validate()) {
              final updatedReport = Report(
                id: report.id,
                reportNumber: report.reportNumber, // Номер отчета остается неизменным
                departmentId: report.departmentId, // Филиал остается неизменным
                jobId: selectedJobId ?? report.jobId,
                employeeId: selectedEmployeeId ?? report.employeeId,
                accountId: selectedAccountId ?? report.accountId,
                dateReceived: _dateReceivedController.text,
                amountIssued: _amountIssuedController.text,
                dateApproved: _dateApprovedController.text.isEmpty ? null : _dateApprovedController.text,
                purpose: _purposeController.text,
                recognizedAmount: _recognizedAmountController.text.isEmpty ? null : _recognizedAmountController.text,
                comments: _commentsController.text,
              );
              onSave(updatedReport);
              Navigator.pop(context); // Закрыть диалог
            }
          },
          child: Text('Сохранить'),
        ),
      ],
    );
  }
}