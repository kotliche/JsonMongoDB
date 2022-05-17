import kotlinx.serialization.*

@Serializable
class Day(
    var dayOfWeek: String, // название дня недели
    var lessons: MutableList<Lesson?> // занятие (пара)
)
{
    override fun toString() = "\n$dayOfWeek\n$lessons\n"
}