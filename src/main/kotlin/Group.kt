import kotlinx.serialization.*

@Serializable
data class Group(
    var groupName: String,//номер группы
    var table: TimeTable // расписание
)
{
    override fun toString() = "\n$groupName\n$table\n"
}