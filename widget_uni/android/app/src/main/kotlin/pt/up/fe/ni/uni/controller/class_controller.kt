package pt.up.fe.ni.uni.controller
import pt.up.fe.ni.uni.model.ClassModel


class ClassController{

    private val model : ClassModel
    private val day : Int
    private val class_name : String
    private val room : String
    private val starting_time : String
    private val ending_time : String

    constructor(dia : Int, ucurr_sigla : String, sala_sigla : String, hora_inicio : Int, aula_duracao : Int){
        day = dia
        class_name = ucurr_sigla
        room = sala_sigla
        starting_time = convert_seconds_to_time(hora_inicio)
        ending_time = convert_seconds_to_time(hora_inicio + aula_duracao*3600)
        model = ClassModel(day, class_name, room, hora_inicio, hora_inicio + aula_duracao*3600, starting_time, ending_time)
    }


    fun convert_seconds_to_time(seconds : Int) : String {
        var hours : Int = seconds / 3600
        var hours_str : String = hours.toString()
        var minutes : Int = (seconds % 3600) / 60
        var minutes_str : String = minutes.toString()

        if (hours < 10) {
            hours_str = "0" + hours
        }
        if (minutes < 10) {
            minutes_str = "0" + minutes
        }
        return hours_str + ":" + minutes_str
    }

    fun getModel() : ClassModel {
        return model
    }

}