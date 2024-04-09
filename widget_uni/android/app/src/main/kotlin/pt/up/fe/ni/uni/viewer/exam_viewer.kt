package pt.up.fe.ni.uni.viewer
import pt.up.fe.ni.uni.model.ExamModel

class ExamViewer {

    private val model : ExamModel
    
    constructor(examModel : ExamModel) {
        model = examModel
    }

    fun getString() : String {
        val exam_name = model.getExamName()
        val day = model.getDay()
        val month = model.getMonth()
        val starting_time = model.getStartingTime()

        return "$exam_name" + "  " + "$day" + "/" + "$month" + "  " + "$starting_time"
    }

}