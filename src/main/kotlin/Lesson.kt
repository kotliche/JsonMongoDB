import kotlinx.serialization.*

@Serializable
data class Lesson(
    var teacher: String?, //имя преподавателя
    var name: String,//название предмета
    var audience: String,// аудитория
)
{
    override fun toString() = when {
       teacher == null -> "\n: $audience, Name: $name"
        else ->"\n Classroom: $audience,Name: $name,Teacher:$teacher"
    }
}