package hu.frontrider.windowutil.graphics

import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import kotlin.system.exitProcess

class Shader(fileName: String,private val bindingCallback:(Shader)->Unit = {}) {

    private val program: Int = glCreateProgram()
    private val vertexShader = glCreateShader(GL_VERTEX_SHADER)
    private val fragmentShader = glCreateShader(GL_FRAGMENT_SHADER)
    private val uniforms = HashMap<String, Any>()

    init {
        glShaderSource(vertexShader, readFile("${fileName}_vertex.glsl"))
        glCompileShader(vertexShader)
        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) != 1) {
            error(glGetShaderInfoLog(vertexShader))
            exitProcess(1)
        }

        glShaderSource(fragmentShader, readFile("${fileName}_fragment.glsl"))
        glCompileShader(fragmentShader)
        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) != 1) {
            error(glGetShaderInfoLog(fragmentShader))
            exitProcess(1)
        }

        glAttachShader(program, vertexShader)
        glAttachShader(program, fragmentShader)

        glBindAttribLocation(program, 0, "verticies")
        glBindAttribLocation(program, 1, "textures")

        glLinkProgram(program)
        if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
            error(glGetProgramInfoLog(program))
            exitProcess(1)
        }
        glValidateProgram(program)
        if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
            error(glGetProgramInfoLog(program))
            exitProcess(1)
        }
    }

    fun addUniform(name: String, value: Any) {
        uniforms[name] = value
    }


    fun bind() {
        glUseProgram(program)
        bindingCallback.invoke(this)
    }

    fun setUniform(name: String, value: Int) {
        val location = glGetUniformLocation(program, name)
        if (location != -1) {
            glUniform1i(location, value)
        }
    }

    fun setUniform(name: String, value: Matrix4f) {
        val location = glGetUniformLocation(program, name)
        val buffer = BufferUtils.createFloatBuffer(16)
        value.get(buffer)
        if (location != -1) {
            glUniformMatrix4fv(location, false, buffer)
        }
    }

    private fun readFile(fileName: String): String {
        val string = StringBuilder()
        val reader: BufferedReader
        try {
            reader = BufferedReader(FileReader(File("./shader/$fileName")))
            var line = reader.readLine()
            while (line != null) {
                string.append(line)
                string.append("\n")
                line = reader.readLine()
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return string.toString()
    }
}