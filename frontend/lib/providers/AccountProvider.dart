import 'package:flutter/cupertino.dart';

import '../models/Account.dart';
import '../api/ApiService.dart';

class AccountProvider extends ChangeNotifier {
  List<Account> _accounts = [];
  bool _isLoading = false;

  List<Account> get accounts => _accounts;
  bool get isLoading => _isLoading;

  AccountProvider() {
    fetchAccounts();
  }

  void fetchAccounts() async {
    _isLoading = true;
    notifyListeners();

    _accounts = await ApiService().fetchAccounts();
    _isLoading = false;
    notifyListeners();
  }

  Future<void> createAccount(String name) async {
    final newAccount = await ApiService().createAccount(name);
    _accounts.add(newAccount);
    notifyListeners();
  }

  Future<void> updateAccount(int id, String name) async {
    final updatedAccount = await ApiService().updateAccount(id, name);
    int index = _accounts.indexWhere((account) => account.id == id);
    if (index != -1) {
      _accounts[index] = updatedAccount;
      notifyListeners();
    }
  }

  Future<void> deleteAccount(int id) async {
    await ApiService().deleteAccount(id);
    _accounts.removeWhere((account) => account.id == id);
    notifyListeners();
  }
}