import '../../models/Report.dart';
import '../../providers/ReportProvider.dart';

void handleSort(
    int columnIndex,
    bool ascending,
    ReportProvider reportProvider,
    Function(int, bool) updateState,
    ) {
  switch (columnIndex) {
    case 0:
      _sort<num>((r) => r.reportNumber, columnIndex, ascending, reportProvider, updateState);
      break;
    case 1:
      _sort<DateTime>((r) => r.dateReceived, columnIndex, ascending, reportProvider, updateState);
      break;
    case 2:
      _sort<String>((r) => r.amountIssued, columnIndex, ascending, reportProvider, updateState);
      break;
  // Добавьте другие case для каждого столбца
  }
}

void _sort<T>(
    Comparable<T> Function(Report r) getField,
    int columnIndex,
    bool ascending,
    ReportProvider reportProvider,
    Function(int, bool) updateState,
    ) {
  reportProvider.reports.sort((a, b) {
    final aValue = getField(a);
    final bValue = getField(b);
    return ascending
        ? Comparable.compare(aValue, bValue)
        : Comparable.compare(bValue, aValue);
  });

  updateState(columnIndex, ascending);
}