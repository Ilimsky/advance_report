class Department {
  final int id;
  String name;
  final DateTime lastModified;
  final bool synced;
  final bool newDepartment;
  final bool modified;

  Department({
    required this.id,
    required this.name,
    required this.lastModified,
    required this.synced,
    required this.newDepartment,
    required this.modified,
  });

  // Метод для создания объекта из JSON
  factory Department.fromJson(Map<String, dynamic> json) {
    return Department(
      id: json['id'],
      name: json['name'],
      lastModified: DateTime.parse(json['lastModified']),
      synced: json['synced'],
      newDepartment: json['newDepartment'],
      modified: json['modified'],
    );
  }

  // Метод для преобразования объекта в JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'lastModified': lastModified.toIso8601String(),
      'synced': synced,
      'newDepartment': newDepartment,
      'modified': modified,
    };
  }
}
