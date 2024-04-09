package pt.up.fe.ni.uni.viewer
import pt.up.fe.ni.uni.model.DateModel
import pt.up.fe.ni.uni.model.ExamsScheduleModel
import pt.up.fe.ni.uni.model.ExamModel
import pt.up.fe.ni.uni.viewer.ExamViewer


class ExamsScheduleViewer {

    private val exams_schedule_model : ExamsScheduleModel
    private val date_model : DateModel
    
    constructor(examsScheduleModel : ExamsScheduleModel, dateModel : DateModel) {
        exams_schedule_model = examsScheduleModel
        date_model = dateModel
    }

    fun sortExams() : MutableList<ExamModel>  {
        val year = date_model.getYear()
        val month = date_model.getMonth()
        val day = date_model.getDay()
        val time_in_seconds = date_model.getTimeInSeconds()
        val exams_list = exams_schedule_model.getExamsList()
        var upcoming_exams_list : MutableList<ExamModel> = mutableListOf()

        for (exam in exams_list) {

            if (exam.getYear() > year){
                upcoming_exams_list.add(exam)
            }
            else if (exam.getYear() == year && exam.getMonth() > month){
                upcoming_exams_list.add(exam)
            }
            else if (exam.getYear() == year && exam.getMonth() == month && exam.getDay() > day){
                upcoming_exams_list.add(exam)
            }
            else if (exam.getYear() == year && exam.getMonth() == month && exam.getDay() == day && exam.getStartingTimeSeconds() > time_in_seconds){
                upcoming_exams_list.add(exam)
            }
        }
        val upcoming_exams_list_sorted = upcoming_exams_list.sortedWith(compareBy({it.getYear()}, {it.getMonth()}, {it.getDay()}, {it.getStartingTimeSeconds()}))
        return upcoming_exams_list_sorted.toMutableList()
    }

    fun getFirstExam() : String {
        val exams_list = sortExams()
        if (exams_list.isEmpty()) {
            return "Sem Exames Marcados"
        }
        else {
            val exam_viewer = ExamViewer(exams_list[0])
            return exam_viewer.getString()
        }
    }

    fun getSecondExam() : String {
        val exams_list = sortExams()
        if (exams_list.size < 2) {
            return ""
        }
        else {
            val exam_viewer = ExamViewer(exams_list[1])
            return exam_viewer.getString()
        }
    
    }

}