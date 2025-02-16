import 'package:flutter/material.dart';
import '../api_service.dart';
import '../models/report.dart';

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

  // Future<void> createReport(int departmentId, int jobId, int employeeId, int accountId) async {
  //   final newReport = await ApiService().createReport(departmentId, jobId, employeeId, accountId);
  //   _reports.add(newReport);
  //   notifyListeners();
  // }

  Future<void> createReport({
    required int departmentId,
    required int jobId,
    required int employeeId,
    required int accountId,
    required String dateReceived,
    required String amountIssued,
    required String dateApproved,
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

  Future<void> updateReport(int reportId, int reportNumber, int departmentId) async {
    final updatedReport = await ApiService().updateReport(reportId, reportNumber, departmentId);
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


}
