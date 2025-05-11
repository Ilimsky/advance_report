import 'package:flutter/material.dart';
import 'package:pdf/pdf.dart';
import 'package:printing/printing.dart';
import 'package:pdf/widgets.dart' as pw;
import 'package:intl/intl.dart';

import '../../models/Report.dart';
import '../../models/Department.dart';
import '../../models/Job.dart';
import '../../models/Employee.dart';
import '../../models/Account.dart';

Future<void> printReport(Report report, Department department, Job job,
    Employee employee, Account account) async {
  final pdf = pw.Document();
  final dateFormat = DateFormat('dd.MM.yyyy');

  pdf.addPage(
    pw.Page(
      build: (pw.Context context) {
        return pw.Column(
          crossAxisAlignment: pw.CrossAxisAlignment.start,
          children: [
            pw.Header(level: 0, child: pw.Text('Отчет №${report.reportNumber}/${department.name}')),
            pw.SizedBox(height: 20),
            pw.Table(
              border: pw.TableBorder.all(),
              children: [
                pw.TableRow(
                  children: [
                    pw.Padding(
                      padding: const pw.EdgeInsets.all(8.0),
                      child: pw.Text('Дата получения д/с'),
                    ),
                    pw.Padding(
                      padding: const pw.EdgeInsets.all(8.0),
                      child: pw.Text(dateFormat.format(report.dateReceived)),
                    ),
                  ],
                ),
                // ... other table rows ...
              ],
            ),
          ],
        );
      },
    ),
  );

  await Printing.layoutPdf(
    onLayout: (PdfPageFormat format) async => pdf.save(),
  );
}