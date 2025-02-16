import 'package:dio/dio.dart';
import 'models/account.dart';
import 'models/department.dart';
import 'models/employee.dart';
import 'models/job.dart';
import 'models/report.dart';

class ApiService {
  final Dio _dio = Dio(BaseOptions(baseUrl: 'http://localhost:8070/api'));

  Future<List<Department>> fetchDepartments() async {
    try {
      print('Fetching departments...');
      final response = await _dio.get('/departments');
      print('Departments fetched: ${response.data}');
      return (response.data as List)
          .map((json) => Department.fromJson(json))
          .toList();
    } catch (e) {
      print('Error fetching departments: $e');
      rethrow;
    }
  }

  Future<Department> createDepartment(String name) async {
    try {
      print('Creating department: $name');
      final response = await _dio.post('/departments', data: {'name': name});
      print('Department created: ${response.data}');
      return Department.fromJson(response.data);
    } catch (e) {
      print('Error creating department: $e');
      rethrow;
    }
  }

  Future<Department> updateDepartment(int id, String newName) async {
    try {
      print('Updating department $id with new name: $newName');
      final response =
          await _dio.put('/departments/$id', data: {'name': newName});
      print('Department updated: ${response.data}');
      return Department.fromJson(response.data);
    } catch (e) {
      print('Error updating department: $e');
      rethrow;
    }
  }

  Future<void> deleteDepartment(int id) async {
    try {
      print('Deleting department with id: $id');
      await _dio.delete('/departments/$id');
      print('Department deleted.');
    } catch (e) {
      print('Error deleting department: $e');
      rethrow;
    }
  }

  Future<List<Report>> fetchAllReports() async {
    try {
      print('Fetching all reports...');
      final response = await _dio.get(
        '/reports',
        options: Options(
          validateStatus: (status) => status! < 500, // Не выбрасывать исключение для статусов < 500
        ),
      );

      if (response.statusCode == 200) {
        print('Reports fetched: ${response.data}');
        return (response.data as List)
            .map((json) => Report.fromJson(json))
            .toList();
      } else {
        print('Server returned status code: ${response.statusCode}');
        print('Response data: ${response.data}');
        throw Exception('Failed to load reports');
      }
    } catch (e) {
      if (e is DioException) {
        print('Dio error: ${e.message}');
        print('Response data: ${e.response?.data}');
      } else {
        print('Unexpected error: $e');
      }
      rethrow;
    }
  }

  Future<List<Report>> fetchReportsByDepartment(int departmentId) async {
    try {
      print('Fetching reports for department ID: $departmentId');
      final response = await _dio.get('/reports/department/$departmentId');
      print('Reports fetched: ${response.data}');
      return (response.data as List)
          .map((json) => Report.fromJson(json))
          .toList();
    } catch (e) {
      print('Error fetching reports by department: $e');
      rethrow;
    }
  }

  // Future<Report> createReport(
  //     int departmentId, int jobId, int employeeId, int accountId) async {
  //   try {
  //     final response =
  //         await _dio.post('/reports/$departmentId/$jobId/$employeeId/$accountId', data: {
  //       'departmentId': departmentId,
  //       'jobId': jobId,
  //       'employeeId': employeeId,
  //       'accountId': accountId,
  //     });
  //     return Report.fromJson(response.data);
  //   } catch (e) {
  //     rethrow;
  //   }
  // }

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
      print(
          'Updating report $reportId with number: $reportNumber and department ID: $departmentId');
      final response = await _dio.put('/reports/$reportId', data: {
        'reportNumber': reportNumber,
        'departmentIdentifier': departmentId
      });
      print('Report updated: ${response.data}');
      return Report.fromJson(response.data);
    } catch (e) {
      print('Error updating report: $e');
      rethrow;
    }
  }

  Future<void> deleteReport(int reportId) async {
    try {
      print('Deleting report with ID: $reportId');
      await _dio.delete('/reports/$reportId');
      print('Report deleted.');
    } catch (e) {
      print('Error deleting report: $e');
      rethrow;
    }
  }

  Future<List<Job>> fetchJobs() async {
    try {
      print('Fetching jobs...');
      final response = await _dio.get('/jobs');
      print('Jobs fetched: ${response.data}');
      return (response.data as List).map((json) => Job.fromJson(json)).toList();
    } catch (e) {
      print('Error fetching jobs: $e');
      rethrow;
    }
  }

  Future<Job> createJob(String name) async {
    try {
      print('Creating job: $name');
      final response = await _dio.post('/jobs', data: {'name': name});
      print('Job created: ${response.data}');
      return Job.fromJson(response.data);
    } catch (e) {
      print('Error creating job: $e');
      rethrow;
    }
  }

  Future<Job> updateJob(int id, String newName) async {
    try {
      print('Updating job $id with new name: $newName');
      final response = await _dio.put('/jobs/$id', data: {'name': newName});
      print('Job updated: ${response.data}');
      return Job.fromJson(response.data);
    } catch (e) {
      print('Error updating job: $e');
      rethrow;
    }
  }

  Future<void> deleteJob(int id) async {
    try {
      print('Deleting job with ID: $id');
      await _dio.delete('/jobs/$id');
      print('Job deleted.');
    } catch (e) {
      print('Error deleting job: $e');
      rethrow;
    }
  }

  Future<List<Employee>> fetchEmployees() async {
    try {
      print('Fetching employees...');
      final response = await _dio.get('/employees');
      print('Employees fetched: ${response.data}');
      return (response.data as List)
          .map((json) => Employee.fromJson(json))
          .toList();
    } catch (e) {
      print('Error fetching employees: $e');
      rethrow;
    }
  }

  Future<Employee> createEmployee(String name) async {
    try {
      print('Creating employee: $name');
      final response = await _dio.post('/employees', data: {'name': name});
      print('Employee created: ${response.data}');
      return Employee.fromJson(response.data);
    } catch (e) {
      print('Error creating employee: $e');
      rethrow;
    }
  }

  Future<Employee> updateEmployee(int id, String newName) async {
    try {
      print('Updating employee $id with new name: $newName');
      final response =
          await _dio.put('/employees/$id', data: {'name': newName});
      print('Employee updated: ${response.data}');
      return Employee.fromJson(response.data);
    } catch (e) {
      print('Error updating employee: $e');
      rethrow;
    }
  }

  Future<void> deleteEmployee(int id) async {
    try {
      print('Deleting employee with ID: $id');
      await _dio.delete('/employees/$id');
      print('Employee deleted.');
    } catch (e) {
      print('Error deleting employee: $e');
      rethrow;
    }
  }

  Future<List<Account>> fetchAccounts() async {
    try {
      print('Fetching accounts...');
      final response = await _dio.get('/accounts');
      print('Accounts fetched: ${response.data}');
      return (response.data as List)
          .map((json) => Account.fromJson(json))
          .toList();
    } catch (e) {
      print('Error fetching accounts: $e');
      rethrow;
    }
  }

  Future<Account> createAccount(String name) async {
    try {
      print('Creating account: $name');
      final response = await _dio.post('/accounts', data: {'name': name});
      print('Account created: ${response.data}');
      return Account.fromJson(response.data);
    } catch (e) {
      print('Error creating account: $e');
      rethrow;
    }
  }

  Future<Account> updateAccount(int id, String newName) async {
    try {
      print('Updating account $id with new name: $newName');
      final response =
      await _dio.put('/accounts/$id', data: {'name': newName});
      print('Account updated: ${response.data}');
      return Account.fromJson(response.data);
    } catch (e) {
      print('Error updating account: $e');
      rethrow;
    }
  }

  Future<void> deleteAccount(int id) async {
    try {
      print('Deleting account with ID: $id');
      await _dio.delete('/accounts/$id');
      print('Account deleted.');
    } catch (e) {
      print('Error deleting account: $e');
      rethrow;
    }
  }
}
