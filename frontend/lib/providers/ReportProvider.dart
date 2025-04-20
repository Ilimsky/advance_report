import 'package:flutter/cupertino.dart';
import 'package:intl/intl.dart';

import '../api/ApiService.dart';
import '../models/Report.dart';

class ReportProvider extends ChangeNotifier {
  List<Report> _reports = [];
  bool _isLoading = false;

  List<Report> get reports => _reports;
  bool get isLoading => _isLoading;

  void fetchReportsByDepartment(int departmentId) async {
    _isLoading = true;
    notifyListeners();

    _reports = await ApiService().fetchReportsByDepartment(departmentId);
    _isLoading = false;
    notifyListeners();
  }

  void fetchAllReports() async {
    _isLoading = true;
    notifyListeners();

    _reports = await ApiService().fetchAllReports();
    _isLoading = false;
    notifyListeners();
  }

  Future<void> createReport({
    required int departmentId,
    required int jobId,
    required int employeeId,
    required int accountId,
    required DateTime dateReceived,
    required String amountIssued,
    required DateTime dateApproved,
    required String purpose,
    required String recognizedAmount,
    required String comments,
  }) async {
    final newReport = await ApiService().createReport(
      departmentId: departmentId,
      jobId: jobId,
      employeeId: employeeId,
      accountId: accountId,
      dateReceived: dateReceived,
      amountIssued: amountIssued,
      dateApproved: dateApproved,
      purpose: purpose,
      recognizedAmount: recognizedAmount,
      comments: comments,
    );
    _reports.add(newReport);
    notifyListeners();
  }

  Future<void> updateReport(
      int reportId, {
        required int reportNumber, // Добавляем этот параметр
        required int departmentId,
        required int jobId,
        required int employeeId,
        required int accountId,
        required DateTime dateReceived,
        required String amountIssued,
        required DateTime dateApproved,
        required String purpose,
        required String recognizedAmount,
        required String comments,
      }) async {
    final updatedReport = await ApiService().updateReport(
      reportId,
      reportNumber: reportNumber, // Передаем номер отчета
      departmentId: departmentId,
      jobId: jobId,
      employeeId: employeeId,
      accountId: accountId,
      dateReceived: dateReceived,
      amountIssued: amountIssued,
      dateApproved: dateApproved,
      purpose: purpose,
      recognizedAmount: recognizedAmount,
      comments: comments,
    );

    int index = _reports.indexWhere((report) => report.id == reportId);
    if (index != -1) {
      _reports[index] = updatedReport;
      notifyListeners();
    }
  }

  Future<void> deleteReport(int reportId) async {
    await ApiService().deleteReport(reportId);
    _reports.removeWhere((report) => report.id == reportId);
    notifyListeners();
  }

  void sortReports(Comparator<Report> comparator) {
    _reports.sort(comparator);
    notifyListeners();
  }
}