package hu.frontrider.windowutil.util

import hu.frontrider.windowutil.graphics.Shader
import hu.frontrider.windowutil.input.InputManager
import hu.frontrider.windowutil.input.KeyboardHandler
import org.lwjgl.Version
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11.*


@Suppress("MemberVisibilityCanBePrivate", "CanBeParameter")
class Window(val width: Int,
             val height: Int,
             val title: String,
             val keyboardHandler: KeyboardHandler = KeyboardHandler(),
             val inputManager: InputManager = InputManager(keyboardHandler),
             val runConstantly: Boolean = false,
             private val shaders: ArrayList<Shader> = ArrayList(),
             posX: Int = -1,
             posY: Int = -1,
             val frameManager: FPSManager = FPSManager(),
             fullScreen: Boolean = false
             ) {

    private var window: Long = 0
    private var draw = true

    init {
        glfwSetErrorCallback({ _: Int, description: Long ->
            throw IllegalStateException(GLFWErrorCallback.getDescription(description))
        })

        println("LWJGL Version " + Version.getVersion() + " is used.")


        if (!glfwInit()) {
            throw IllegalStateException("Failed to initialise GLFW!")
        }
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)

        window = if (fullScreen) {
            glfwCreateWindow(width, height, title, glfwGetPrimaryMonitor(), 0)
        } else {
            glfwCreateWindow(width, height, title, 0, 0)
        }

        if (window == 0L) {
            throw IllegalStateException("Failed to create window")
        }

        glfwSetKeyCallback(window, keyboardHandler)

        if (!fullScreen) {
            val videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!

            var xpos = posX
            if (posX < 0)
                xpos = (videoMode.width() - width) / 2
            var ypos = posY
            if (posY < 0)
                ypos = (videoMode.height() - height) / 2
            glfwSetWindowPos(window, xpos, ypos)
        }
        glfwShowWindow(window)
        glfwMakeContextCurrent(window)
        createCapabilities()

    }

    fun enable2DTextureMode() {
        glEnable(GL_TEXTURE_2D)
    }

    fun shouldClose(): Boolean {
        return glfwWindowShouldClose(window)
    }

    fun setClearColor(r: Float, g: Float, b: Float, a: Float) {
        glClearColor(r, g, b, a)
    }

    fun setClearColor(r: Float, g: Float, b: Float) {
        setClearColor(r, g, b, 1f)
    }

    fun loop(renderCallback: (Window) -> Unit) {
        while (!shouldClose()) {
            inputManager.handleInput()
            render(renderCallback)
        }
        shutdown()
    }

    fun shutdown() {
        glfwTerminate()
    }

    /**
     * Sets up everything to do the drawing, than swaps the buffers
     * @param renderCallback a function to handle the drawing

     * */
    fun render(renderCallback: (Window) -> Unit) {
        if (draw) {
            shaders.forEach { it.bind() }
            glClear(GL_COLOR_BUFFER_BIT)
            renderCallback.invoke(this)
            glfwSwapBuffers(window)
            if (!runConstantly)
                draw = false
            frameManager.render()
        }
    }

    fun markDirty() {
        if (!runConstantly)
            draw = true
    }

    fun addShader(shader: Shader) {
        shaders.add(shader)
    }

    fun close() {
        glfwSetWindowShouldClose(window, true)
    }
}

