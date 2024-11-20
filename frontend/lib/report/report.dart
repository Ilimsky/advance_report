class Report {
  final int id;
  String sequenceNumber;
  final DateTime lastModified;
  final bool synced;
  final bool newReport;
  final bool modified;

  Report({
    required this.id,
    required this.sequenceNumber,
    required this.lastModified,
    required this.synced,
    required this.newReport,
    required this.modified,
  });

  // Метод для создания объекта из JSON
  factory Report.fromJson(Map<String, dynamic> json) {
    return Report(
      id: json['id'],
      sequenceNumber: json['sequenceNumber'],
      lastModified: DateTime.parse(json['lastModified']),
      synced: json['synced'],
      newReport: json['newReport'],
      modified: json['modified'],
    );
  }

  // Метод для преобразования объекта в JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'sequenceNumber': sequenceNumber,
      'lastModified': lastModified.toIso8601String(),
      'synced': synced,
      'newReport': newReport,
      'modified': modified,
    };
  }
}
