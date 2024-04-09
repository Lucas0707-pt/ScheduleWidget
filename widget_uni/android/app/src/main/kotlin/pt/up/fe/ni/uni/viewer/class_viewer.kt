package pt.up.fe.ni.uni.viewer
import pt.up.fe.ni.uni.model.ClassModel

class ClassViewer {

    private val model : ClassModel
    
    constructor(classModel : ClassModel) {
        model = classModel
    }

    fun getString() : String {
        val class_name = model.getClassName()
        val room = model.getRoom()
        val starting_time = model.getStartingTime()
        val ending_time = model.getEndingTime()

        return "$class_name" + "   " + "$room" + "\n\n" + "$starting_time" + "-" + "$ending_time"
    }

}