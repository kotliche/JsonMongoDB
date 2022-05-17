import org.apache.poi.xssf.usermodel.*
import org.litote.kmongo.*

fun main() {
    val file = "./resource/18.xlsx" // путь к файлу Excel
    val file1 = "./resource/21.xlsx"
    val excel = XSSFWorkbook(file)
    val excel1 = XSSFWorkbook(file1)
    val controller = Controller(excel)
    val controller1 = Controller(excel1)
    val mongoDB = mongoDatabase.getCollection<Group>().apply { drop() }
    mongoDB.insertMany(controller.groups)
    mongoDB.insertMany(controller1.groups)
    prettyPrintCursor(mongoDB.find())

}