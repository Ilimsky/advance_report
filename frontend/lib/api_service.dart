import 'package:dio/dio.dart';
import 'models/account.dart';
import 'models/department.dart';
import 'models/employee.dart';
import 'models/job.dart';
import 'models/report.dart';

class ApiService {
  // final Dio _dio = Dio(BaseOptions(baseUrl: 'http://localhost:8070/api'));
  final Dio _dio = Dio(BaseOptions(baseUrl: 'https://advance-report.onrender.com/api'));

  Future<List<Department>> fetchDepartments() async {
    try {
      final response = await _dio.get('/departments');
      return (response.data as List)
          .map((json) => Department.fromJson(json))
          .toList();
    } catch (e) {
      rethrow;
    }
  }

  Future<Department> createDepartment(String name) async {
    try {
      final response = await _dio.post('/departments', data: {'name': name});
      return Department.fromJson(response.data);
    } catch (e) {
      rethrow;
    }
  }

  Future<Department> updateDepartment(int id, String newName) async {
    try {
      final response =
          await _dio.put('/departments/$id', data: {'name': newName});
      return Department.fromJson(response.data);
    } catch (e) {
      rethrow;
    }
  }

  Future<void> deleteDepartment(int id) async {
    try {
      await _dio.delete('/departments/$id');
    } catch (e) {
      rethrow;
    }
  }

  Future<List<Report>> fetchAllReports() async {
    try {
      final response = await _dio.get(
        '/reports',
        options: Options(
          validateStatus: (status) => status! < 500, // Не выбрасывать исключение для статусов < 500
        ),
      );

      if (response.statusCode == 200) {
        return (response.data as List)
            .map((json) => Report.fromJson(json))
            .toList();
      } else {
        throw Exception('Failed to load reports');
      }
    } catch (e) {
      if (e is DioException) {
      } else {
      }
      rethrow;
    }
  }

  Future<List<Report>> fetchReportsByDepartment(int departmentId) async {
    try {
      final response = await _dio.get('/reports/department/$departmentId');
      return (response.data as List)
          .map((json) => Report.fromJson(json))
          .toList();
    } catch (e) {
      rethrow;
    }
  }

  Future<Report> createReport({
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
    try {
      final response = await _dio.post(
        '/reports',
        data: {
          'departmentId': departmentId,
          'jobId': jobId,
          'employeeId': employeeId,
          'accountId': accountId,
          'dateReceived': dateReceived,
          'amountIssued': amountIssued,
          'dateApproved': dateApproved,
          'purpose': purpose,
          'recognizedAmount': recognizedAmount,
          'comments': comments,
        },
      );
      return Report.fromJson(response.data);
    } catch (e) {
      rethrow;
    }
  }

  Future<Report> updateReport(
      int reportId, int reportNumber, int departmentId) async {
    try {
      final response = await _dio.put('/reports/$reportId', data: {
        'reportNumber': reportNumber,
        'departmentIdentifier': departmentId
      });
      return Report.fromJson(response.data);
    } catch (e) {
      rethrow;
    }
  }

  Future<void> deleteReport(int reportId) async {
    try {
      await _dio.delete('/reports/$reportId');
    } catch (e) {
      rethrow;
    }
  }

  Future<List<Job>> fetchJobs() async {
    try {
      final response = await _dio.get('/jobs');
      return (response.data as List).map((json) => Job.fromJson(json)).toList();
    } catch (e) {
      rethrow;
    }
  }

  Future<Job> createJob(String name) async {
    try {
      final response = await _dio.post('/jobs', data: {'name': name});
      return Job.fromJson(response.data);
    } catch (e) {
      rethrow;
    }
  }

  Future<Job> updateJob(int id, String newName) async {
    try {
      final response = await _dio.put('/jobs/$id', data: {'name': newName});
      return Job.fromJson(response.data);
    } catch (e) {
      rethrow;
    }
  }

  Future<void> deleteJob(int id) async {
    try {
      await _dio.delete('/jobs/$id');
    } catch (e) {
      rethrow;
    }
  }

  Future<List<Employee>> fetchEmployees() async {
    try {
      final response = await _dio.get('/employees');
      return (response.data as List)
          .map((json) => Employee.fromJson(json))
          .toList();
    } catch (e) {
      rethrow;
    }
  }

  Future<Employee> createEmployee(String name) async {
    try {
      final response = await _dio.post('/employees', data: {'name': name});
      return Employee.fromJson(response.data);
    } catch (e) {
      rethrow;
    }
  }

  Future<Employee> updateEmployee(int id, String newName) async {
    try {
      final response =
          await _dio.put('/employees/$id', data: {'name': newName});
      return Employee.fromJson(response.data);
    } catch (e) {
      rethrow;
    }
  }

  Future<void> deleteEmployee(int id) async {
    try {
      await _dio.delete('/employees/$id');
    } catch (e) {
      rethrow;
    }
  }

  Future<List<Account>> fetchAccounts() async {
    try {
      final response = await _dio.get('/accounts');
      return (response.data as List)
          .map((json) => Account.fromJson(json))
          .toList();
    } catch (e) {
      rethrow;
    }
  }

  Future<Account> createAccount(String name) async {
    try {
      final response = await _dio.post('/accounts', data: {'name': name});
      return Account.fromJson(response.data);
    } catch (e) {
      rethrow;
    }
  }

  Future<Account> updateAccount(int id, String newName) async {
    try {
      final response =
      await _dio.put('/accounts/$id', data: {'name': newName});
      return Account.fromJson(response.data);
    } catch (e) {
      rethrow;
    }
  }

  Future<void> deleteAccount(int id) async {
    try {
      await _dio.delete('/accounts/$id');
    } catch (e) {
      rethrow;
    }
  }
}
