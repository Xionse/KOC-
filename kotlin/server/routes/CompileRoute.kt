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

/*
This CompileRoute class defines a route handler for the /compile URL that accepts a POST request with the Kotlin source code as the request body. It calls the compile function of the KotlinCompiler class to compile the source code, and returns a JSON response indicating whether the compilation was successful or not, along with the compiler output if the compilation was successful.

The toJson extension function on CompilerResult is used to convert the CompilerResult object to a JSON string.

*/