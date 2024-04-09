package pt.up.fe.ni.uni.model

class ScheduleModel {
    var classes_list : MutableList<ClassModel> = mutableListOf()

    fun addClass(c : ClassModel) {
        classes_list.add(c)
    }

    fun removeClass(c : ClassModel) {
        classes_list.remove(c)
    }

    fun removeClass(index : Int) {
        classes_list.removeAt(index)
    }

    fun getClassesList () : List<ClassModel> {
        return classes_list
    }

    fun getClass(index : Int) : ClassModel {
        return classes_list[index]
    }

    fun getClassesSize() : Int {
        return classes_list.size
    }
}