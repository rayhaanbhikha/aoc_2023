import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import java.io.File
import java.lang.Exception
import kotlin.io.path.Path

object InputLoader {
    fun load(day: Int, fresh: Boolean = false): List<String> {
        val file = File("./src/main/resources/day${day}_input.txt")
        if (fresh || !file.exists()) {
            loadFile(day, file)
            return load(5)
        }
        return file.readLines()
    }

    fun loadAsString(day: Int): String {
        val file = File("./src/main/resources/day${day}_input.txt")
        if (!file.exists()) {
            loadFile(day, file)
            return loadAsString(5)
        }
        return file.readText()
    }

    private fun loadFile(day: Int, file: File) {
        println("Loading Input from adventofcode.com")
        val input = AOCService.loadInput(day)
        file.writeText(input)
    }

    fun loadExample(day: Int): List<String> {
        val file = File("./src/main/resources/day${day}_input_example.txt")
        if (!file.exists()) {
            throw Exception("Example Input day $day missing")
        }
        return file.readLines()
    }

    fun loadExampleAsString(day: Int): String {
        val file = File("./src/main/resources/day${day}_input_example.txt")
        if (!file.exists()) {
            throw Exception("Example Input day $day missing")
        }
        return file.readText()
    }
}


object AOCService {

    private val client = HttpClient()

    fun loadInput(day: Int): String {
        val sessionToken = loadToken()
        return runBlocking {
            makeRequest(day, sessionToken)
        }
    }

    private suspend fun makeRequest(day: Int, token: String): String {
        val response = client.request("https://adventofcode.com/2023/day/${day}/input") {
            cookie("session", token)
            headers {
                append(HttpHeaders.Cookie, "session=${token};")
            }
        }
        return response.body<String>()
    }

    private fun loadToken(): String {
        val homeDir = System.getProperty("user.home")
        val sessionTokenFile = Path(homeDir, ".aoc-session").toFile()
        if (!sessionTokenFile.exists()) {
            throw Exception("set aoc session token in ~/.aoc-session")
        }
        return sessionTokenFile.readText().trim()
    }
}