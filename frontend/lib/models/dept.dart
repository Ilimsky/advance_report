class Dept {
  final int? id;
  final String name;

  Dept({this.id, required this.name});

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'name': name,
    };
  }

  factory Dept.fromMap(Map<String, dynamic> map) {
    return Dept(
      id: map['id'],
      name: map['name'],
    );
  }
}