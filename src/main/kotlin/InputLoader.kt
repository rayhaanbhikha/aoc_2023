import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException
import java.lang.Exception
import kotlin.io.path.Path

object InputLoader {
    fun load(day: Int): List<String> {
        val file = File("./src/main/resources/day${day}_input.txt")
        if (!file.exists()) {
            println("Loading Input from adventofcode.com")
            val input = AOCService.loadInput(day)
            println(input)
            file.writeText(input)
            return file.readLines()
        }
        return file.readLines()
    }

    fun loadExample(day: Int): List<String> {
        val file = File("./src/main/resources/day${day}_input_example.txt")
        if (!file.exists()) {
            throw Exception("Example Input day $day missing")
        }
        return file.readLines()
    }
}


object AOCService {

    private val client = OkHttpClient()

    fun loadInput(day: Int): String {
        val sessionToken = loadToken()

        val request = Request.Builder()
            .url("https://adventofcode.com/2023/day/${day}/input")
            .addHeader("Cookie", "session=${sessionToken};")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            return response.body?.string() ?: ""
        }
    }

    private fun loadToken(): String {
        val homeDir = System.getProperty("user.home")
        val sessionTokenFile = Path(homeDir, ".aoc-session").toFile()
        if (!sessionTokenFile.exists()) {
            throw Exception("set aoc session token in ~/.aoc-session")
        }
        val text = sessionTokenFile.readText()
        return text
    }
}