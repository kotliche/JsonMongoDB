import com.mongodb.ExplainVerbosity
import com.mongodb.client.FindIterable
import org.json.JSONObject
import org.litote.kmongo.KMongo
import org.litote.kmongo.json

val client = KMongo
    .createClient("mongodb://root:xgsLKNvu6xT5@192.168.42.209:27017")
val mongoDatabase = client.getDatabase("test")

fun prettyPrintJson(json: String) =
    println(
        JSONObject(json)
            .toString(4)
    )

fun prettyPrintCursor(cursor: Iterable<*>) =
    prettyPrintJson("{ result: ${cursor.json} }")

        //fun prettyPrintExplain(cursor: FindIterable<*>) =
    //prettyPrintJson(cursor.explain(ExplainVerbosity.EXECUTION_STATS).json)