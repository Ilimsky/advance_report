import 'package:pdf/pdf.dart';
import 'package:pdf/widgets.dart' as pw;
import 'package:printing/printing.dart';
import '../../models/account.dart';
import '../../models/department.dart';
import '../../models/employee.dart';
import '../../models/job.dart';
import '../../models/report.dart';

import 'package:flutter/services.dart';

class ReportPdfGenerator {
  static Future<void> generateAndOpenPdf(Report report, Department department, Job job, Employee employee, Account account) async {
    // Загружаем шрифт с поддержкой кириллицы
    final fontData = await rootBundle.load("assets/fonts/Roboto-Regular.ttf");
    final ttf = pw.Font.ttf(fontData);

    final pdf = pw.Document();

    pdf.addPage(
      pw.Page(
        build: (pw.Context context) => pw.Column(
          crossAxisAlignment: pw.CrossAxisAlignment.start,
          children: [
            pw.Text(
              'Отчет №${report.reportNumber}',
              style: pw.TextStyle(font: ttf, fontSize: 20, fontWeight: pw.FontWeight.bold),
            ),
            pw.SizedBox(height: 10),
            pw.Text('Филиал: ${department.name}', style: pw.TextStyle(font: ttf)),
            pw.Text('Дата получения д/с: ${report.dateReceived}', style: pw.TextStyle(font: ttf)),
            pw.Text('Выданная сумма: ${report.amountIssued}', style: pw.TextStyle(font: ttf)),
            pw.Text('Дата утверждения а/о: ${report.dateApproved ?? "Н/Д"}', style: pw.TextStyle(font: ttf)),
            pw.Text('Должность: ${job.name}', style: pw.TextStyle(font: ttf)),
            pw.Text('Сотрудник: ${employee.name}', style: pw.TextStyle(font: ttf)),
            pw.Text('Назначение: ${report.purpose}', style: pw.TextStyle(font: ttf)),
            pw.Text('Признанная сумма по а/о: ${report.recognizedAmount ?? "Н/Д"}', style: pw.TextStyle(font: ttf)),
            pw.Text('Счет: ${account.name}', style: pw.TextStyle(font: ttf)),
            pw.Text('Комментарии: ${report.comments}', style: pw.TextStyle(font: ttf)),
          ],
        ),
      ),
    );

    await Printing.layoutPdf(
      onLayout: (PdfPageFormat format) async => pdf.save(),
    );
  }
}