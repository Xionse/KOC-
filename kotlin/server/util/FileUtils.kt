import java.io.File
import java.io.OutputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

class FileUtils {
    companion object {
        fun createTempDir(): File {
            return Files.createTempDirectory("koc_").toFile()
        }

        fun createTempFile(prefix: String = "koc_", suffix: String): File {
            return Files.createTempFile(prefix, suffix).toFile()
        }

        fun writeText(file: File, text: String) {
            file.writeText(text)
        }

        fun writeBytes(file: File, bytes: ByteArray) {
            file.writeBytes(bytes)
        }

        fun readText(file: File): String {
            return file.readText(StandardCharsets.UTF_8)
        }

        fun readBytes(file: File): ByteArray {
            return file.readBytes()
        }

        fun delete(file: File) {
            file.delete()
        }

        fun deleteRecursively(file: File) {
            file.deleteRecursively()
        }

        fun copy(source: File, destination: File) {
            source.copyTo(destination)
        }

        fun copyRecursively(source: File, destination: File) {
            source.copyRecursively(destination)
        }

        fun move(source: File, destination: File) {
            source.renameTo(destination)
        }

        fun createLink(source: File, destination: File) {
            Files.createLink(destination.toPath(), source.toPath())
        }

        fun createSymbolicLink(source: File, destination: File) {
            Files.createSymbolicLink(destination.toPath(), source.toPath())
        }
    }
}


/*
This FileUtils class provides a variety of utility functions for working with files and directories in Kotlin. Some of the functions include:

createTempDir: creates a new temporary directory.
createTempFile: creates a new temporary file.
writeText: writes a string to a file.
writeBytes: writes a byte array to a file.
readText: reads a file as a string.
readBytes: reads a file as a byte array.
delete: deletes a file.
deleteRecursively: deletes a file or directory, including all its contents.
copy: copies a file.
copyRecursively: copies a file 

*/