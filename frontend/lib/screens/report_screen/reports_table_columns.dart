import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../providers/JobProvider.dart';
import '../../providers/EmployeeProvider.dart';
import '../../providers/AccountProvider.dart';
import 'reports_table_sort.dart';

List<DataColumn> buildTableColumns({
  required BuildContext context,
  required Function(int, bool) onSort,
  required JobProvider jobProvider,
  required EmployeeProvider employeeProvider,
  required AccountProvider accountProvider,
}) {
  return [
    DataColumn(
      label: _buildColumnLabel('Номер отчета', 40),
      onSort: (i, asc) => onSort(i, asc),
    ),
    DataColumn(
      label: _buildColumnLabel('Дата получения д/с', 70),
      onSort: (i, asc) => onSort(i, asc),
    ),
    DataColumn(
      label: _buildColumnLabel('Выданная сумма', 90),
      onSort: (i, asc) => onSort(i, asc),
    ),
    DataColumn(
      label: _buildColumnLabel('Дата утверждения а/о', 70),
      onSort: (i, asc) => onSort(i, asc),
    ),
    DataColumn(
      label: _buildColumnLabel('Должность', 100),
      onSort: (i, asc) => onSort(i, asc),
    ),
    DataColumn(
      label: _buildColumnLabel('Сотрудник', 120),
      onSort: (i, asc) => onSort(i, asc),
    ),
    DataColumn(
      label: _buildColumnLabel('Назначение', 200),
      onSort: (i, asc) => onSort(i, asc),
    ),
    DataColumn(
      label: _buildColumnLabel('Признанная сумма', 90),
      onSort: (i, asc) => onSort(i, asc),
    ),
    DataColumn(
      label: _buildColumnLabel('Счет', 60),
      onSort: (i, asc) => onSort(i, asc),
    ),
    DataColumn(
      label: _buildColumnLabel('Комментарии', 200),
      onSort: (i, asc) => onSort(i, asc),
    ),
    DataColumn(
      label: _buildColumnLabel('Действия', 60),
    ),
  ];
}

Widget _buildColumnLabel(String text, double width) {
  return Container(
    width: width,
    child: Text(
      text,
      style: TextStyle(fontSize: 10),
      softWrap: true,
      overflow: TextOverflow.visible,
      maxLines: 2,
      textAlign: TextAlign.center,
    ),
  );
}