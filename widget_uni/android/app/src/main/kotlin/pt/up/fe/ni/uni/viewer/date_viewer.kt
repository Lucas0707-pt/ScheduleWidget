package pt.up.fe.ni.uni.viewer
import pt.up.fe.ni.uni.model.DateModel

class DateViewer {

    private val model : DateModel
    
    constructor(dateModel : DateModel) {
        model = dateModel
    }

    fun getString() : String {
        val weekday = model.getWeekDay();
        val day = model.getDay();
        val month = model.getMonth();

        return "$weekday" + "\n\n" + "$day/$month"
    }

}