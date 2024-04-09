package pt.up.fe.ni.uni.controller

import pt.up.fe.ni.uni.model.ExamsScheduleModel
import org.json.*



class ExamsScheduleController {

    private var model : ExamsScheduleModel

    init{
        model = ExamsScheduleModel()
    }

    //{"exames": [{"ocorr_nome": "Engenharia de Software", "data": "2022-06-20" , "hora_inicio": "08:30"}]}

    fun processExamsSchedule(examsJSON : String) {
        val json_string = JSONTokener(examsJSON).nextValue() as JSONObject
        val exams_list = json_string.getJSONArray("exames")
        
        for (i in 0 until exams_list.length()) {
            val exam = exams_list.getJSONObject(i)
            val exam_name = exam.getString("ocorr_nome")
            val exam_date = exam.getString("data")
            val exam_starting_time = exam.getString("hora_inicio")
            val exam_controller = ExamController(exam_name, exam_date, exam_starting_time)
            model.addExam(exam_controller.getModel())
        }
    }

    fun getModel() : ExamsScheduleModel {
        return model
    }
}