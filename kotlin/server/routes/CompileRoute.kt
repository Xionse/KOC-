import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.compile() {
    post("/compile") {
        val code = call.receiveText()
        val result = compiler.compile(code)
        call.respondText(result.toJson(), ContentType.Application.Json)
    }
}

private val compiler = KotlinCompiler()

private fun CompilerResult.toJson(): String {
    return when (this) {
        is CompilerResult.Success -> """{"success":true,"output":"${escapeJson(output)}"}"""
        is CompilerResult.Error -> """{"success":false,"message":"${escapeJson(message)}"}"""
    }
}

private fun escapeJson(str: String): String {
    return str.replace("\"", "\\\"").replace("\n", "\\n")
}
