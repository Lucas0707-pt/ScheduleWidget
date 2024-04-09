package pt.up.fe.ni.uni.controller
import pt.up.fe.ni.uni.model.ExamModel


class ExamController{

    private val model : ExamModel
    private val exam_name : String
    private val day : String
    private val starting_time : String

    constructor(ocorr_nome : String, data_string : String, hora_inicio : String){
        exam_name = convertNameToInitials(ocorr_nome)
        day = data_string
        starting_time = hora_inicio
        model = ExamModel(exam_name, day, starting_time)
    }

    fun convertNameToInitials(ocorr_nome : String) : String {
        var initials = ""
        var aux = ocorr_nome.split(" ")
        for(i in 0 until aux.size){
            if (aux[i][0].isUpperCase()){
                initials += aux[i][0]
            }
        }
        return initials

    }

    fun getModel() : ExamModel {
        return model
    }

}