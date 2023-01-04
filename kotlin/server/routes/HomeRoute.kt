import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.home() {
    get("/") {
        call.respondHtml(HTML_TEMPLATE)
    }
}

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
/* 
This HomeRoute class defines a route handler for the root URL (/) that returns an HTML response containing the content of the HTML_TEMPLATE constant.

The HTML_TEMPLATE constant contains an HTML template for the homepage of the online Kotlin compiler. It includes links to the main CSS stylesheet and JavaScript file, and includes div elements for the code editor and compiler output.


*/