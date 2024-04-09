package pt.up.fe.ni.uni.controller

import pt.up.fe.ni.uni.model.ScheduleModel
import org.json.*



class ScheduleController {

    private var model : ScheduleModel

    init{
        model = ScheduleModel()
    }

    //{"horario": [{"dia": 3, "hora_inicio": 30600, "ucurr_sigla": "CPD", "aula_duracao": 2, "sala_sigla": "B107"}]}

    fun processSchedule(scheduleJSON : String) {
        val json_string = JSONTokener(scheduleJSON).nextValue() as JSONObject
        val schedule_list = json_string.getJSONArray("horario")
        
        for (i in 0 until schedule_list.length()) {
            val day = schedule_list.getJSONObject(i).getString("dia").toInt()
            val class_name = schedule_list.getJSONObject(i).getString("ucurr_sigla")
            val room = schedule_list.getJSONObject(i).getString("sala_sigla")
            val starting_time = schedule_list.getJSONObject(i).getString("hora_inicio").toInt()
            val duration = schedule_list.getJSONObject(i).getString("aula_duracao").toInt()
            val class_controller = ClassController(day, class_name, room, starting_time, duration)
            model.addClass(class_controller.getModel())
        }
    }

    fun getModel() : ScheduleModel {
        return model
    }
}