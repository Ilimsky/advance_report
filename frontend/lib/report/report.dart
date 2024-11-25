class Report {
  final int id;
  final String sequenceNumber;

  Report({
    required this.id,
    required this.sequenceNumber,
  });

  factory Report.fromJson(Map<String, dynamic> json) {
    return Report(
      id: json['id'],
      sequenceNumber: json['sequenceNumber'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'sequenceNumber': sequenceNumber,
    };
  }
}