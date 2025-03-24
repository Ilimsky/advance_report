import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../models/account.dart';
import '../../models/department.dart';
import '../../models/employee.dart';
import '../../models/job.dart';
import '../../models/report.dart';
import '../../providers/account_provider.dart';
import '../../providers/department_provider.dart';
import '../../providers/employee_provider.dart';
import '../../providers/job_provider.dart';

class ReportTable extends StatelessWidget {
  final List<Report> reports;
  final Function(Report) onEdit;
  final Function(int) onDelete;
  final Function(Report, Department, Job, Employee, Account) onPrint;

  const ReportTable({
    required this.reports,
    required this.onEdit,
    required this.onDelete,
    required this.onPrint,
  });

  @override
  Widget build(BuildContext context) {
    final departmentProvider = Provider.of<DepartmentProvider>(context);
    final jobProvider = Provider.of<JobProvider>(context);
    final employeeProvider = Provider.of<EmployeeProvider>(context);
    final accountProvider = Provider.of<AccountProvider>(context);

    final smallTextStyle = TextStyle(fontSize: 12);
    final headerTextStyle = TextStyle(fontSize: 12, fontWeight: FontWeight.bold);

    return Column(
      children: [
        // Заголовки столбцов
        Table(
          columnWidths: {
            0: FlexColumnWidth(1),
            1: FlexColumnWidth(2),
            2: FlexColumnWidth(2),
            3: FlexColumnWidth(2),
            4: FlexColumnWidth(2),
            5: FlexColumnWidth(2),
            6: FlexColumnWidth(2),
            7: FlexColumnWidth(2),
            8: FlexColumnWidth(2),
            9: FlexColumnWidth(2),
            10: FlexColumnWidth(2),
          },
          children: [
            TableRow(
              children: [
                TableCell(child: Center(child: Text('№', style: headerTextStyle))),
                TableCell(child: Center(child: Text('Дата получения д/с', style: headerTextStyle))),
                TableCell(child: Center(child: Text('Выданная сумма', style: headerTextStyle))),
                TableCell(child: Center(child: Text('Дата утверждения а/о', style: headerTextStyle))),
                TableCell(child: Center(child: Text('Должность', style: headerTextStyle))),
                TableCell(child: Center(child: Text('Сотрудник', style: headerTextStyle))),
                TableCell(child: Center(child: Text('Назначение', style: headerTextStyle))),
                TableCell(child: Center(child: Text('Признанная сумма по а/о', style: headerTextStyle))),
                TableCell(child: Center(child: Text('Счет', style: headerTextStyle))),
                TableCell(child: Center(child: Text('Комментарии', style: headerTextStyle))),
                TableCell(child: Center(child: Text('Действия', style: headerTextStyle))),
              ],
            ),
          ],
        ),
        Expanded(
          child: ListView.builder(
            itemCount: reports.length,
            itemBuilder: (context, index) {
              final report = reports[index];
              final department = departmentProvider.departments.firstWhere(
                    (dept) => dept.id == report.departmentId,
                orElse: () => Department(id: 0, name: 'Неизвестно'),
              );
              final job = jobProvider.jobs.firstWhere(
                    (job) => job.id == report.jobId,
                orElse: () => Job(id: 0, name: 'Неизвестно'),
              );
              final employee = employeeProvider.employees.firstWhere(
                    (employee) => employee.id == report.employeeId,
                orElse: () => Employee(id: 0, name: 'Неизвестно'),
              );
              final account = accountProvider.accounts.firstWhere(
                    (account) => account.id == report.accountId,
                orElse: () => Account(id: 0, name: 'Неизвестно'),
              );

              return Table(
                columnWidths: {
                  0: FlexColumnWidth(1),
                  1: FlexColumnWidth(2),
                  2: FlexColumnWidth(2),
                  3: FlexColumnWidth(2),
                  4: FlexColumnWidth(2),
                  5: FlexColumnWidth(2),
                  6: FlexColumnWidth(2),
                  7: FlexColumnWidth(2),
                  8: FlexColumnWidth(2),
                  9: FlexColumnWidth(2),
                  10: FlexColumnWidth(2),
                },
                children: [
                  TableRow(
                    children: [
                      // Номер отчета и отдел
                      TableCell(
                        child: Center(
                          child: Text(
                            '${report.reportNumber}/${department.name}',
                            style: smallTextStyle,
                          ),
                        ),
                      ),
                      // Дата получения д/с
                      TableCell(
                        child: Center(
                          child: Text(
                            report.dateReceived,
                            style: smallTextStyle,
                          ),
                        ),
                      ),
                      // Выданная сумма
                      TableCell(
                        child: Center(
                          child: Text(
                            report.amountIssued,
                            style: smallTextStyle,
                          ),
                        ),
                      ),
                      // Дата утверждения а/о
                      TableCell(
                        child: Center(
                          child: Text(
                            report.dateApproved ?? 'Н/Д',
                            style: smallTextStyle,
                          ),
                        ),
                      ),
                      // Должность
                      TableCell(
                        child: Center(
                          child: Text(
                            job.name,
                            style: smallTextStyle,
                          ),
                        ),
                      ),
                      // Сотрудник
                      TableCell(
                        child: Center(
                          child: Text(
                            employee.name,
                            style: smallTextStyle,
                          ),
                        ),
                      ),
                      // Назначение
                      TableCell(
                        child: Center(
                          child: Text(
                            report.purpose,
                            style: smallTextStyle,
                          ),
                        ),
                      ),
                      // Признанная сумма по а/о
                      TableCell(
                        child: Center(
                          child: Text(
                            report.recognizedAmount ?? 'Н/Д',
                            style: smallTextStyle,
                          ),
                        ),
                      ),
                      // Счет
                      TableCell(
                        child: Center(
                          child: Text(
                            account.name,
                            style: smallTextStyle,
                          ),
                        ),
                      ),
                      // Комментарии
                      TableCell(
                        child: Center(
                          child: Text(
                            report.comments,
                            style: smallTextStyle,
                          ),
                        ),
                      ),
                      // Действия (иконки)
                      TableCell(
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            IconButton(
                              icon: Icon(Icons.edit, size: 20),
                              onPressed: () => onEdit(report),
                            ),
                            IconButton(
                              icon: Icon(Icons.delete, size: 20),
                              onPressed: () => _showDeleteConfirmationDialog(context, report.id, onDelete),
                            ),
                            IconButton(
                              icon: Icon(Icons.print, size: 20),
                              onPressed: () => onPrint(report, department, job, employee, account),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ],
              );
            },
          ),
        ),
      ],
    );
  }

  void _showDeleteConfirmationDialog(BuildContext context, int reportId, Function(int) onDelete) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('Удалить отчет?'),
        content: Text('Вы уверены, что хотите удалить этот отчет?'),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.pop(context); // Закрыть диалог
            },
            child: Text('Отменить'),
          ),
          TextButton(
            onPressed: () {
              onDelete(reportId); // Вызов функции удаления
              Navigator.pop(context); // Закрыть диалог
            },
            child: Text('Удалить', style: TextStyle(color: Colors.red)),
          ),
        ],
      ),
    );
  }
}