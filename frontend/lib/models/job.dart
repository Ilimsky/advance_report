class Job {
  final int id;
  final String name;

  Job({required this.id, required this.name});

  factory Job.fromJson(Map<String, dynamic> json) {
    return Job(id: json['id'], name: json['name']);
  }
}
