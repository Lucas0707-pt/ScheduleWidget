package pt.up.fe.ni.uni.model

class ExamsScheduleModel {
    var exams_list : MutableList<ExamModel> = mutableListOf()

    fun addExam(e : ExamModel) {
        exams_list.add(e)
    }

    fun removeExam(e : ExamModel) {
        exams_list.remove(e)
    }

    fun removeExam(index : Int) {
        exams_list.removeAt(index)
    }

    fun getExamsList () : List<ExamModel> {
        return exams_list
    }

    fun getExam(index : Int) : ExamModel {
        return exams_list[index]
    }

    fun getExamsSize() : Int {
        return exams_list.size
    }
}