import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.sessions.*

class Server(private val host: String, private val port: Int) {
    private val compiler = KotlinCompiler()

    fun start() {
        embeddedServer(Netty, port) {
            install(CallLogging)
            install(Sessions)
            routing {
                home()
                compile()
                save()
            }
        }.start(wait = true)
    }

    private fun Routing.home() {
        get("/") {
            call.respondHtml(HTML_TEMPLATE)
        }
    }

    private fun Routing.compile() {
        post("/compile") {
            val code = call.receiveText()
            val result = compiler.compile(code)
            call.respondText(result.toJson(), ContentType.Application.Json)
        }
    }

    private fun Routing.save() {
        post("/save") {
            val session = call.sessions.get<Session>() ?: Session()
            val code = call.receiveText()
            session.code = code
            call.sessions.set(session)
            call.respondText("Success")
        }

        get("/load") {
            val session = call.sessions.get<Session>()
            val code = session?.code ?: ""
            call.respondText(code, ContentType.Text.Plain)
        }
    }
}

data class Session(var code: String = "")

private const val HTML_TEMPLATE = """
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Kotlin Online Compiler</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
    <div id="editor"></div>
    <div id="output"></div>
    <script src="/js/main.js"></script>
</body>
</html>
"""

fun main() {
    val server = Server(host = "0.0.0.0", port = 8080)
    server.start()
}

private fun CompilerResult.toJson(): String {
    return when (this) {
        is CompilerResult.Success -> """{"success":true,"output":"${escapeJson(output)}"}"""
        is CompilerResult.Error -> """{"success":false,"message":"${escapeJson(message)}"}"""
    }
}

private fun escapeJson(str: String): String {
    return str.replace("\"", "\\\"").replace("\n", "\\n")
}


// This Server class creates a Ktor web server that listens for HTTP requests on the specified host and port.