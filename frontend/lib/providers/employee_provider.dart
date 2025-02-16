import 'package:flutter/material.dart';
import '../api_service.dart';
import '../models/employee.dart';
import '../models/employee.dart';

class EmployeeProvider extends ChangeNotifier {
  List<Employee> _employees = [];
  bool _isLoading = false;

  List<Employee> get employees => _employees;
  bool get isLoading => _isLoading;

  EmployeeProvider() {
    fetchEmployees();
  }

  void fetchEmployees() async {
    _isLoading = true;
    notifyListeners();

    _employees = await ApiService().fetchEmployees();
    _isLoading = false;
    notifyListeners();
  }

  Future<void> createEmployee(String name) async {
    final newEmployee = await ApiService().createEmployee(name);
    _employees.add(newEmployee);
    notifyListeners();
  }

  Future<void> updateEmployee(int id, String name) async {
    final updatedEmployee = await ApiService().updateEmployee(id, name);
    int index = _employees.indexWhere((employee) => employee.id == id);
    if (index != -1) {
      _employees[index] = updatedEmployee;
      notifyListeners();
    }
  }

  Future<void> deleteEmployee(int id) async {
    await ApiService().deleteEmployee(id);
    _employees.removeWhere((employee) => employee.id == id);
    notifyListeners();
  }
}
