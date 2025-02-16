class Report {
  final int id;
  final int reportNumber;
  final int departmentId;
  final int jobId;
  final int employeeId;
  final int accountId;

  // Новые поля
  final String dateReceived;
  final String amountIssued;
  final String dateApproved;
  final String purpose;
  final String recognizedAmount;
  final String comments;

  Report({
    required this.id,
    required this.reportNumber,
    required this.departmentId,
    required this.jobId,
    required this.employeeId,
    required this.accountId,
    required this.dateReceived,
    required this.amountIssued,
    required this.dateApproved,
    required this.purpose,
    required this.recognizedAmount,
    required this.comments,
  });

  factory Report.fromJson(Map<String, dynamic> json) {
    return Report(
      id: json['id'],
      reportNumber: json['reportNumber'],
      departmentId: json['departmentId'],
      jobId: json['jobId'],
      employeeId: json['employeeId'],
      accountId: json['accountId'],
      dateReceived: json['dateReceived'],
      amountIssued: json['amountIssued'],
      dateApproved: json['dateApproved'],
      purpose: json['purpose'],
      recognizedAmount: json['recognizedAmount'],
      comments: json['comments'],
    );
  }
}