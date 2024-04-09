package pt.up.fe.ni.uni

import pt.up.fe.ni.uni.controller.DateController
import pt.up.fe.ni.uni.model.DateModel
import pt.up.fe.ni.uni.viewer.DateViewer

import pt.up.fe.ni.uni.controller.ScheduleController
import pt.up.fe.ni.uni.model.ScheduleModel
import pt.up.fe.ni.uni.viewer.ScheduleViewer

import pt.up.fe.ni.uni.controller.ExamsScheduleController
import pt.up.fe.ni.uni.model.ExamsScheduleModel
import pt.up.fe.ni.uni.viewer.ExamsScheduleViewer


import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.widget.RemoteViews
import es.antonborri.home_widget.HomeWidgetBackgroundIntent
import es.antonborri.home_widget.HomeWidgetLaunchIntent
import es.antonborri.home_widget.HomeWidgetProvider

class AppWidgetProvider : HomeWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray, widgetData: SharedPreferences) {
        appWidgetIds.forEach { widgetId ->
            val views = RemoteViews(context.packageName, R.layout.widget_layout).apply {

                // Open App on Widget Click
                val pendingIntent = HomeWidgetLaunchIntent.getActivity(context,
                        MainActivity::class.java)
                setOnClickPendingIntent(R.id.widget_root, pendingIntent)

                //date
                val date_controller = DateController()
                val date_viewer = DateViewer(date_controller.getModel())
                setTextViewText(R.id.tv_date, date_viewer.getString())

                //schedule
                val schedule = widgetData.getString("schedule", "").orEmpty()
                if (schedule != "") {
                    val schedule_controller = ScheduleController();
                    schedule_controller.processSchedule(schedule);
                    val schedule_viewer =
                        ScheduleViewer(schedule_controller.getModel(), date_controller.getModel())

                    if (schedule_controller.getModel().getClassesSize() >= 1) {
                        setTextViewText(R.id.tv_class1, schedule_viewer.getFirstClass())
                    }

                    if (schedule_controller.getModel().getClassesSize() >= 2) {
                        setTextViewText(R.id.tv_class2, schedule_viewer.getSecondClass())
                    }
                }


                //exams schedule
                val exams_schedule = widgetData.getString("exams", "").orEmpty()

                if (exams_schedule != "") {
                    val exams_schedule_controller = ExamsScheduleController();

                    exams_schedule_controller.processExamsSchedule(exams_schedule);

                    val exams_schedule_viewer = ExamsScheduleViewer(
                        exams_schedule_controller.getModel(),
                        date_controller.getModel()
                    )

                    if (exams_schedule_controller.getModel().getExamsSize() >= 1) {
                        setTextViewText(R.id.tv_exam1, exams_schedule_viewer.getFirstExam())
                    }

                    if (exams_schedule_controller.getModel().getExamsSize() >= 2) {
                        setTextViewText(R.id.tv_exam2, exams_schedule_viewer.getSecondExam())
                    }
                }
            }
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }
}