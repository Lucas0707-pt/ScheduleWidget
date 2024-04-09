package pt.up.fe.ni.uni.viewer
import pt.up.fe.ni.uni.model.DateModel
import pt.up.fe.ni.uni.model.ScheduleModel
import pt.up.fe.ni.uni.model.ClassModel
import pt.up.fe.ni.uni.viewer.ClassViewer


class ScheduleViewer {

    private val schedule_model : ScheduleModel
    private val date_model : DateModel
    
    constructor(scheduleModel : ScheduleModel, dateModel : DateModel) {
        schedule_model = scheduleModel
        date_model = dateModel
    }

    fun sortClasses() : MutableList<ClassModel> {
        val weekDay = date_model.getWeekDayNumber()
        val time_in_seconds = date_model.getTimeInSeconds()
        val classes_list = schedule_model.getClassesList()
        var ordered_classes : MutableList<ClassModel> = mutableListOf()
        var upcoming_classes_list_otherDays : MutableList<ClassModel> = mutableListOf()

        for (i in classes_list.indices) {
            if (classes_list[i].getDay() == weekDay  && classes_list[i].getEndingTimeSeconds() > time_in_seconds) {
                ordered_classes.add(classes_list[i])
            }
        }
        ordered_classes.sortBy { it.getStartingTimeSeconds() }

        var next_weekDay = (weekDay + 1) % 7
        while (true) {
            upcoming_classes_list_otherDays = mutableListOf()
            for (i in classes_list.indices) {
                if (classes_list[i].getDay() == next_weekDay) {
                    ordered_classes.add(classes_list[i])
                }
            }
            upcoming_classes_list_otherDays.sortBy { it.getStartingTimeSeconds() }
            for (i in upcoming_classes_list_otherDays.indices) {
                ordered_classes.add(upcoming_classes_list_otherDays[i])
            }
            next_weekDay = (next_weekDay + 1) % 7
            if (next_weekDay == weekDay) {
                break
            }
        } 

        return ordered_classes
    }

    fun getFirstClass() : String {
        val classes_list = sortClasses()
        if (classes_list.isEmpty()) {
            return ""
        }
        val class_viewer = ClassViewer(classes_list[0])
        return class_viewer.getString()
    }

    fun getSecondClass() : String {
        val classes_list = sortClasses()
        if (classes_list.size < 2) {
            return ""
        }
        val class_viewer = ClassViewer(classes_list[1])
        return class_viewer.getString()
    }

}