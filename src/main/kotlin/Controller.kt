import org.apache.poi.xssf.usermodel.*

class Controller(tFile: XSSFWorkbook) {
    private val tableGroup = tFile.getSheetAt(0)//получаем лист из данного файла он нулевой (номер)
    private fun schedule(table: XSSFSheet): MutableList<Group> {
        var thisGroup: Group
        val grouup: MutableList<Group> = mutableListOf() // создаем пустой список куда будем вносить полностью расписание
        var i = 0
        while(table.getRow(16 * i + 0)?.getCell(0) != null) { // проверка на нахождение строки с названием группы
            thisGroup = Group(groupName=table.getRow(16 * i + 0).getCell(0).toString().substringAfter(": "), sheduleDop(i,table))// вытаскиваем группу с его индексом и буквой, также закидываем и расписание благодаря прописанной функции
            grouup.add(thisGroup)
            i++
        }
        return grouup //возвращаем расписание групп
    }

    private fun sheduleDop(id: Int, table: XSSFSheet): TimeTable
    {
        val sheduleTable = TimeTable(mutableListOf(), mutableListOf()) //создаем пустую коллекцию для вверхней и нижней недели
        var lessonT: Lesson? // основная база где хранятся имена преподователей, предметов и аудиторий

        var row = 16 * id + 4 //начальная строка
        var step = 1 // начальный столбец
        val toRow = row + 12 // конечная строка
        val toStep = 5 // последний столбец

        var upWeekDay: Day //верхняя неделя
        var lowWeekDay: Day //нижняя неделя

        while (row < toRow)
        {
            upWeekDay=Day(table.getRow(row).getCell(0).toString(), mutableListOf()) // вставляем в конструктор класса Day день недели
            lowWeekDay=Day(table.getRow(row).getCell(0).toString(), mutableListOf())//и пустой список где в него будут вставляться занятия
            while (step <= toStep)// от 1 до 5 пар
            {
                if (table.getRow(row).getCell(step).toString().filter { !it.isWhitespace() } !== "") { // смотрит есть ли пробелы c помощью ссылочного равенства
                    val one = table.getRow(row).getCell(step).toString().split("ст.пр.")// разделяем на части
                    val two = table.getRow(row).getCell(step).toString().split("доц.")
                    val three = table.getRow(row).getCell(step).toString().split(" проф.")

                    val teachIndex :String = when {
                        one.size != 1 -> "ст.пр."
                        two.size != 1 -> "доц."
                        three.size != 1 -> " проф."
                        else -> " а.Сз1"
                    }

                    lessonT = if((one.size != 1) || (three.size != 1) || (two.size!=1)){ //находим нужные типы преподователей
                        val indexed1=table.getRow(row).getCell(step).toString()
                        Lesson(
                            indexed1.substringAfter(teachIndex).substringBefore(" а."),//преподаватель (между after и before)
                            indexed1.substringAfter(".").substringBefore(teachIndex).substringBefore("- 1").substringBefore("- 2"),//предмет
                            indexed1.substringAfter("а."),//аудитория
                        )
                    } else{
                        val indexed2=table.getRow(row).getCell(step).toString()
                        Lesson(
                            teacher = null,//преподаватель
                            indexed2.substringBefore(" а.Сз1"), //предмет
                            indexed2.substringAfter("а."),//аудитория
                        )
                    }

                } else lessonT = null

                if(row%16 <= 9) { //также от Пнд до Сбт при меньше девяти это вверхняя неделя
                    upWeekDay.lessons.add(lessonT) //закидываем информацию о паре в вверхнюю неделю (в список)
                }else { //
                    lowWeekDay.lessons.add(lessonT)// аналогично только в нижнюю
                }
                step++ //переход к следующей паре
            }
            sheduleTable.evenWeek.add(upWeekDay) // закидываем информацию о вверхней неделе группы (в список)
            sheduleTable.oddWeek.add(lowWeekDay) //аналогично
            row++// переходим к следующему дню
            step = 1//начинаем с Пнд
        }
        return sheduleTable // возвращаем таблицу с четной и нечетной недели группы
    }
    val groups = schedule(tableGroup) // забрасываем готовое расписание всех групп
}