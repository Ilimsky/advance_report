import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../models/report.dart';
import '../models/department.dart';
import '../models/job.dart';
import '../models/employee.dart';
import '../models/account.dart';
import '../providers/report_provider.dart';
import '../providers/department_provider.dart';
import '../providers/job_provider.dart';
import '../providers/employee_provider.dart';
import '../providers/account_provider.dart';

class ReportsScreen extends StatefulWidget {
  @override
  _ReportsScreenState createState() => _ReportsScreenState();
}

class _ReportsScreenState extends State<ReportsScreen> {
  @override
  void initState() {
    super.initState();
    // Загружаем отчеты при инициализации экрана
    WidgetsBinding.instance.addPostFrameCallback((_) {
      Provider.of<ReportProvider>(context, listen: false).fetchAllReports();
    });
  }

  void _showEditDialog(BuildContext context, Report report, ReportProvider reportProvider) {
    TextEditingController controller = TextEditingController(text: report.reportNumber.toString());

    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('Редактировать отчет'),
        content: TextField(
          controller: controller,
          decoration: InputDecoration(labelText: 'Новый номер отчета'),
          keyboardType: TextInputType.number,
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
              final newReportNumber = int.tryParse(controller.text) ?? report.reportNumber;
              reportProvider.updateReport(
                report.id,
                newReportNumber,
                report.departmentId,
              );
              Navigator.pop(context); // Закрыть диалог
            },
            child: Text('Сохранить'),
          ),
        ],
      ),
    );
  }

  void _showDeleteDialog(BuildContext context, int reportId, ReportProvider reportProvider) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('Удалить отчет'),
        content: Text('Вы уверены, что хотите удалить этот отчет?'),
        actions: [
          TextButton(
            onPressed: () {
              Navigator.pop(context); // Закрыть диалог
            },
            child: Text('Отмена'),
          ),
          TextButton(
            onPressed: () {
              reportProvider.deleteReport(reportId);
              Navigator.pop(context); // Закрыть диалог
            },
            child: Text('Удалить', style: TextStyle(color: Colors.red)),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    final reportProvider = Provider.of<ReportProvider>(context);
    final departmentProvider = Provider.of<DepartmentProvider>(context);
    final jobProvider = Provider.of<JobProvider>(context);
    final employeeProvider = Provider.of<EmployeeProvider>(context);
    final accountProvider = Provider.of<AccountProvider>(context);

    return Scaffold(
      appBar: AppBar(
        title: Text('Отчеты'),
      ),
      body: Column(
        children: [
          Expanded(
            child: reportProvider.isLoading
                ? Center(child: CircularProgressIndicator())
                : ListView.builder(
              itemCount: reportProvider.reports.length,
              itemBuilder: (context, index) {
                final report = reportProvider.reports[index];
                final department = departmentProvider.departments
                    .firstWhere(
                      (dept) => dept.id == report.departmentId,
                  orElse: () => Department(id: 0, name: 'Неизвестно'),
                );
                final job = jobProvider.jobs
                    .firstWhere(
                      (job) => job.id == report.jobId,
                  orElse: () => Job(id: 0, name: 'Неизвестно'),
                );
                final employee = employeeProvider.employees
                    .firstWhere(
                      (employee) => employee.id == report.employeeId,
                  orElse: () => Employee(id: 0, name: 'Неизвестно'),
                );
                final account = accountProvider.accounts
                    .firstWhere(
                      (account) => account.id == report.accountId,
                  orElse: () => Account(id: 0, name: 'Неизвестно'),
                );
                return ListTile(
                  title: Text('${report.reportNumber}/${department.name} '
                      '${report.dateReceived}'
                      '${report.amountIssued}'
                      '${job.name}  '
                      '${employee.name} '
                      '${report.purpose}'
                      '${account.name}'
                      '${report.comments}'),
                  subtitle: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      // Text('Дата получения: ${report.dateReceived}'),
                      // Text('Выданная сумма: ${report.amountIssued}'),
                      // Text('Назначение: ${report.purpose}'),
                      // Text('Комментарии: ${report.comments}'),
                    ],
                  ),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      IconButton(
                        icon: Icon(Icons.edit),
                        onPressed: () => _showEditDialog(context, report, reportProvider),
                      ),
                      IconButton(
                        icon: Icon(Icons.delete),
                        onPressed: () => _showDeleteDialog(context, report.id, reportProvider),
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