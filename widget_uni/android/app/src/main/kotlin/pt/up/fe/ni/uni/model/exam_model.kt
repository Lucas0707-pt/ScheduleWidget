package pt.up.fe.ni.uni.model

class ExamModel {
    private val exam_name : String
    private val date : String //2022-07-01
    private val starting_time : String

    constructor(ocorr_nome : String, data_string : String, hora_inicio : String) {
        exam_name = ocorr_nome
        date = data_string
        starting_time = hora_inicio
    }

    fun getExamName() : String{
        return exam_name
    }

    fun getYear() : Int{
        return date.substring(0,4).toInt()
    }

    fun getMonth() : Int{
        return date.substring(5,7).toInt()
    }

    fun getDay() : Int{
        return date.substring(8,10).toInt()
    }

    fun getStartingTime() : String{
        return starting_time
    }

    fun getStartingTimeSeconds() : Int{
        return starting_time.substring(0,2).toInt()*3600 + starting_time.substring(3,5).toInt()*60
    }


    fun getUnixTime() : Long {
        val date = date.split("/")
        val time = starting_time.split(":")
        return (date[2].toLong() * 365 + date[1].toLong() * 30 + date[0].toLong() * 1) * 86400 + time[0].toLong() * 3600 + time[1].toLong() * 60
    }


}