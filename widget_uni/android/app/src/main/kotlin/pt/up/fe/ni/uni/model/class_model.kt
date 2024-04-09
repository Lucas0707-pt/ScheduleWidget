package pt.up.fe.ni.uni.model

class ClassModel {
    private val day : Int
    private val class_name : String
    private val room : String
    private val starting_time_seconds : Int
    private val ending_time_seconds : Int
    private val starting_time : String
    private val ending_time : String

    constructor(dia : Int, ucurr_sigla : String, sala_sigla : String, hora_inicio_segundos : Int, hora_fim_segundos : Int, hora_inicio : String, hora_fim : String) {
        day = dia
        class_name = ucurr_sigla
        room = sala_sigla
        starting_time_seconds = hora_inicio_segundos
        ending_time_seconds = hora_fim_segundos
        starting_time = hora_inicio
        ending_time = hora_fim
    }

    fun getDay() : Int {
        return day
    }

    fun getClassName() : String {
        return class_name
    }

    fun getRoom() : String {
        return room
    }

    fun getStartingTimeSeconds() : Int {
        return starting_time_seconds
    }

    fun getEndingTimeSeconds() : Int {
        return ending_time_seconds
    }

    fun getStartingTime() : String {
        return starting_time
    }

    fun getEndingTime() : String {
        return ending_time
    }

}