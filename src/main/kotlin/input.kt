import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException
import java.lang.Exception

object InputLoader {
    fun load(day: Int): List<String> {
        val file = File("./src/main/resources/day${day}_input.txt")
        if (!file.exists()) {
            println("Loading Input from adventofcode.com")
            val input = AOCService.loadInput()
            file.writeText(input)
            return file.readLines()
        }
        return file.readLines()
    }

    fun loadExample(day: Int): List<String> {
        val file = File("./src/main/resources/day${day}_example_input.txt")
        if (!file.exists()) {
            throw Exception("Example Input day $day missing")
        }
        return file.readLines()
    }
}


object AOCService {

    private val client = OkHttpClient()

    fun loadInput(): String {
        val sessionToken: String = System.getenv("AOC_SESSION_TOKEN")
            ?: throw Exception("Missing AOC_SESSION_TOKEN environment variable")

        val request = Request.Builder()
            .url("https://adventofcode.com/2023/day/3/input")
            .addHeader("Cookie", "session=${sessionToken};")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            return response.body?.string() ?: ""
        }
    }

}