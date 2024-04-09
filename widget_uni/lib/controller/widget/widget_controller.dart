import 'dart:convert';
import 'dart:ffi';
import 'package:flutter/services.dart';
import 'package:home_widget/home_widget.dart';
import 'package:http/http.dart' as http;
import 'dart:io';
import '../local_storage/app_shared_preferences.dart';
import 'package:tuple/tuple.dart';




import 'package:intl/intl.dart';


const loginUrl =
    'https://sigarra.up.pt/feup/pt/mob_val_geral.autentica';

const teacherScheduleUrl =
    'https://sigarra.up.pt/feup/pt/mob_hor_geral.docente';

const studentScheduleUrl =
    'https://sigarra.up.pt/feup/pt/mob_hor_geral.estudante';

const studentExamsUrl =
    'https://sigarra.up.pt/feup/pt/mob_fest_geral.exames';

class User {
  final String code;
  final String type;
  const User(this.code, this.type);
  factory User.fromJson(Map<String, dynamic> json) {
    return User(json['codigo'], json['tipo']);
  }
}

Future<User> login(_Session session, String username, String password) async {
  var data = await session.post(loginUrl,
      {'pv_login': username, 'pv_password': password});
  if (data["authenticated"]) {
    return User.fromJson(data);
  } else {
    return null;
  }
}

Future<dynamic> getUserSchedule(_Session session, User user,
    String dataIni, String dataFim) async {
  var url = (user.type == "F"? teacherScheduleUrl : studentScheduleUrl)
      + "?pv_codigo=${user.code}&pv_semana_ini=$dataIni&pv_semana_fim=$dataFim";
  return await session.get(url);
}

Future<dynamic> getUserExams(_Session session, User user) async {
  var url = studentExamsUrl
      + "?pv_codigo=${user.code}";
  return await session.get(url);
}

// Allows passing cookies between HTTP requests.
class _Session {
  Map<String, String> _headers = {}; // stores the cookie

  // Posts data to a url and retrieves the deserialized JSON response.
  // Uses a previously retrieved cookie in the request header, if existent.
  // Saves the cookie retrieved in the response header, if provided.
  Future<dynamic> post(String url, dynamic data) async {
    var response = await http.post(Uri.parse(url), body: data, headers: _headers);
    if (response.statusCode == 200) {
      _updateCookie(response);
      return json.decode(response.body);
    }
    else {
      throw Exception('Failed to access $url');
    }
  }

  // Retrieves the (deserialized) JSON response from a url.
  // Uses a previously retrieved cookie in the request header, if existent.
  Future<dynamic> get(String url) async {
    var response = await http.get(Uri.parse(url), headers: _headers);
    if (response.statusCode == 200) {
      _updateCookie(response);
      return json.decode(response.body);
    }
    else {
      throw Exception('Failed to access $url');
    }
  }

  // Saves in "headers" the cookie retrieved in the response header.
  void _updateCookie(http.Response response) {
    String rawCookie = response.headers['set-cookie'];
    if (rawCookie != null) {
      var sanitizedCookie = rawCookie.replaceAll(",", ";"); //needed with SIGARRA
      _headers['cookie'] = sanitizedCookie;
    }
  }
}

class WidgetController{

  String schedule;
  String exams;
  static bool was_updated = false;

  WidgetController() {
    this.startData();
  }

  void startData(){
    this.schedule = "";
    this.exams = "";
  }

  bool isUpdated(){
    return was_updated;
  }

  String cleanScheduleJSON(Map<String,dynamic> scheduleJSON){
    var scheduleArray = scheduleJSON['horario'];
    var newScheduleArray = [];
    for(var i = 0; i < scheduleArray.length; i++){
      var scheduleObject = scheduleArray[i];
      var newScheduleObject = {};
      newScheduleObject['dia'] = scheduleObject['dia'].toString();
      newScheduleObject['hora_inicio'] = scheduleObject['hora_inicio'].toString();
      newScheduleObject['ucurr_sigla'] = scheduleObject['ucurr_sigla'];
      newScheduleObject['aula_duracao'] = scheduleObject['aula_duracao'].toString();
      newScheduleObject['sala_sigla'] = scheduleObject['sala_sigla'];
      newScheduleArray.add(newScheduleObject);
    }
    final schedule = {};
    schedule['horario'] = newScheduleArray;
    return json.encode(schedule);
  }

  String cleanExamsJSON(List<dynamic> examsJSON){
    var newExamsArray = [];
    for(var i = 0; i < examsJSON.length; i++){
      var examObject = examsJSON[i];
      var newExamObject = {};
      newExamObject['ocorr_nome'] = examObject['ocorr_nome'];
      newExamObject['data'] = examObject['data'];
      newExamObject['hora_inicio'] = examObject['hora_inicio'];
      newExamsArray.add(newExamObject);
    }
    final exams = {};
    exams['exames'] = newExamsArray;
    return json.encode(exams);

  }

  void fetchData(String username, String password) async {
    var date = DateTime.now();

    final String beginWeek = date.year.toString().padLeft(4, '0') +
        date.month.toString().padLeft(2, '0') +
        date.day.toString().padLeft(2, '0');
    date = date.add(Duration(days: 6));

    final String endWeek = date.year.toString().padLeft(4, '0') +
        date.month.toString().padLeft(2, '0') +
        date.day.toString().padLeft(2, '0');
    var session = _Session();
    var userData = await login(session, username, password);
    var scheduleData = await getUserSchedule(session, userData, beginWeek, endWeek);
    var examsData = await getUserExams(session, userData);
    this.schedule = cleanScheduleJSON(scheduleData);
    this.exams = cleanExamsJSON(examsData);
    updateAppWidget();

  }

  Future<void> updateAppWidget() async {
    await HomeWidget.saveWidgetData<String>('schedule', this.schedule);
    await HomeWidget.saveWidgetData<String>('exams', this.exams);
    await HomeWidget.updateWidget(name: 'AppWidgetProvider', iOSName: 'AppWidgetProvider');
    was_updated = true;
  }
}
