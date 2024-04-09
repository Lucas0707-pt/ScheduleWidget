package pt.up.fe.ni.uni.controller
import pt.up.fe.ni.uni.model.DateModel

class DateController {

    private val model : DateModel

    init{
        val time_in_seconds = getTimeInSeconds()
        val day = getDay()
        val month = getMonth()
        val year = getYear()
        val weekDay = getWeekDay()
        model = DateModel(time_in_seconds, day, month, year, weekDay)
    }

    fun getTimeInSeconds() : Int {
        var dateInstance = java.util.Calendar.getInstance()
        var date = dateInstance.getTime()
        var hoursFormat = java.text.SimpleDateFormat("HH")
        var hours_str = hoursFormat.format(date)
        var minutesFormat = java.text.SimpleDateFormat("mm")
        var minutes_str = minutesFormat.format(date)
        var secondsFormat = java.text.SimpleDateFormat("ss")
        var seconds_str = secondsFormat.format(date)
        var hours = Integer.parseInt(hours_str)
        var minutes = Integer.parseInt(minutes_str)
        var seconds = Integer.parseInt(seconds_str)
        var timeInSeconds = hours*3600 + minutes*60 + seconds
        return timeInSeconds
    }


    fun getDay() : String {
        var dateInstance = java.util.Calendar.getInstance()
        var date = dateInstance.getTime()
        var dateFormat = java.text.SimpleDateFormat("dd")
        var dateString = dateFormat.format(date)
        return dateString
    }

    fun getMonth() : String{
        var dateInstance = java.util.Calendar.getInstance()
        var date = dateInstance.getTime()
        var dateFormat = java.text.SimpleDateFormat("MM")
        var dateString = dateFormat.format(date)
        return dateString
    }

    fun getYear() : String{
        var dateInstance = java.util.Calendar.getInstance()
        var date = dateInstance.getTime()
        var dateFormat = java.text.SimpleDateFormat("yyyy")
        var dateString = dateFormat.format(date)
        return dateString
    }

    fun weekDayPortuguese(dayOfWeek : String) : String {
        when(dayOfWeek){
            "Mon" -> return "SEG"
            "Tue" -> return "TER"
            "Wed" -> return "QUA"
            "Thu" -> return "QUI"
            "Fri" -> return "SEX"
            "Sat" -> return "SAB"
            "Sun" -> return "DOM"
        }
        return ""
    }

    fun getWeekDay() : String {
        var dateInstance = java.util.Calendar.getInstance()
        var date = dateInstance.getTime()
        var dateFormat = java.text.SimpleDateFormat("EEE")
        var dateString = dateFormat.format(date)
        return this.weekDayPortuguese(dateString)
    }

    public fun getModel() : DateModel {
        return model
    }
}