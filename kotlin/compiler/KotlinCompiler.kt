import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStream
import java.nio.charset.StandardCharsets

class KotlinCompiler {
    companion object {
        private const val KOTLIN_COMPILER = "kotlinc"
        private const val KOTLIN_RUNTIME = "kotlin"
    }

    fun compile(sourceCode: String): CompilerResult {
        val sourceFile = createTempFile(suffix = ".kt")
        sourceFile.writeText(sourceCode)

        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()

        val exitCode = executeCompiler(sourceFile, outputStream, errorStream)
        sourceFile.delete()

        return if (exitCode == 0) {
            CompilerResult.Success(outputStream.toString(StandardCharsets.UTF_8))
        } else {
            CompilerResult.Error(errorStream.toString(StandardCharsets.UTF_8))
        }
    }

    fun run(className: String, classpath: String, args: Array<String>): RunnerResult {
        val command = arrayOf(KOTLIN_RUNTIME, "-classpath", classpath, className) + args
        val process = ProcessBuilder(*command).start()

        val outputStream = ByteArrayOutputStream()
        val errorStream = ByteArrayOutputStream()

        val exitCode = process.waitFor()
        process.inputStream.copyTo(outputStream)
        process.errorStream.copyTo(errorStream)

        return if (exitCode == 0) {
            RunnerResult.Success(outputStream.toString(StandardCharsets.UTF_8))
        } else {
            RunnerResult.Error(errorStream.toString(StandardCharsets.UTF_8))
        }
    }

    private fun executeCompiler(sourceFile: File, outputStream: OutputStream, errorStream: OutputStream): Int {
        val command = arrayOf(KOTLIN_COMPILER, "-d", createTempDir().absolutePath, sourceFile.absolutePath)
        val process = ProcessBuilder(*command).start()

        process.inputStream.copyTo(outputStream)
        process.errorStream.copyTo(errorStream)

        return process.waitFor()
    }
}

sealed class CompilerResult {
    data class Success(val output: String) : CompilerResult()
    data class Error(val message: String) : CompilerResult()
}

sealed class RunnerResult {
    data class Success(val output: String) : RunnerResult()
    data class Error(val message: String) : RunnerResult()
}


/*
This KotlinCompiler class provides two main functions:

compile: takes a string of Kotlin source code as input and returns a CompilerResult object indicating whether the compilation was successful or not.
run: takes the name of a Kotlin class, the classpath, and an array of arguments, and returns a RunnerResult object indicating whether the execution was successful or not.

*/