package pt.up.fe.ni.uni.model

class DateModel {
    private val time_in_seconds : Int
    private val day : String
    private val month : String
    private val year : String
    private val weekDay : String

    constructor(timeInSeconds : Int, dia : String, mes : String, ano : String, diadasemana : String) {
        time_in_seconds = timeInSeconds
        day = dia
        month = mes
        year = ano
        weekDay = diadasemana
    }

    fun getTimeInSeconds() : Int {
        return time_in_seconds
    }

    fun getDay() : Int {
        return day.toInt()
    }

    fun getMonth() : Int {
        return month.toInt()
    }

    fun getYear() : Int {
        return year.toInt()
    }

    fun getWeekDay() : String {
        return weekDay
    }

    fun getWeekDayNumber() : Int {
        when(weekDay){
            "SEG" -> return 2
            "TER" -> return 3
            "QUA" -> return 4
            "QUI" -> return 5
            "SEX" -> return 6
            "SAB" -> return 7
            "DOM" -> return 1
        }
        return 0;
    }
    
}