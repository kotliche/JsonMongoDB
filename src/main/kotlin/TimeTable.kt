import kotlinx.serialization.*

@Serializable
data class TimeTable(
    val evenWeek: MutableList<Day>, //нижняя неделя
    val oddWeek: MutableList<Day> //верхняя неделя
) {
    override fun toString() = "Нижняя неделя:\n$evenWeek\nВерхняя неделя:\n$oddWeek\n"
}