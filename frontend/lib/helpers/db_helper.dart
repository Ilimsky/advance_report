import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
import '../models/department.dart';

class DatabaseHelper {
  static final DatabaseHelper instance = DatabaseHelper._init();
  static Database? _database;

  DatabaseHelper._init();

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDB('advance_report.db');
    return _database!;
  }

  Future<Database> _initDB(String filePath) async {
    final dbPath = await getDatabasesPath();
    final path = join(dbPath, filePath);

    print('Database path: $path'); // Вывод пути в консоль

    return await openDatabase(
      path,
      version: 1,
      onCreate: _createDB,
    );
  }

  Future _createDB(Database db, int version) async {
    await db.execute('''
    CREATE TABLE departments (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      name TEXT NOT NULL
    )
    ''');
  }

  Future<Department> create(Department department) async {
    final db = await instance.database;
    final id = await db.insert('departments', department.toMap());
    return Department(id: id, name: department.name);
  }

  Future<List<Department>> readAllDepartments() async {
    final db = await instance.database;
    final result = await db.query('departments');

    return result.map((json) => Department.fromMap(json)).toList();
  }

  Future<int> update(Department department) async {
    final db = await instance.database;

    return db.update(
      'departments',
      department.toMap(),
      where: 'id = ?',
      whereArgs: [department.id],
    );
  }

  Future<int> delete(int id) async {
    final db = await instance.database;

    return await db.delete(
      'departments',
      where: 'id = ?',
      whereArgs: [id],
    );
  }

  Future close() async {
    final db = await instance.database;

    db.close();
  }

  Future<bool> isDatabaseConnected() async {
    try {
      final db = await instance.database;
      return db.isOpen;
    } catch (e) {
      return false; // Если произошла ошибка, возвращаем false
    }
  }
}