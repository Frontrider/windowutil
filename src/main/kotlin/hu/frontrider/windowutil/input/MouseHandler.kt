package hu.frontrider.windowutil.input

import hu.frontrider.windowutil.input.InputMethod.NONE
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback
import org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback
import org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback
import org.lwjgl.glfw.GLFW.glfwSetScrollCallback




class MouseHandler(
        val window: Long,
        val click: ArrayList<InputHolder> = ArrayList()
) {
    val keys = HashMap<Int, InputMethod>()
    var cursorEntered = false

    var scrollX = 0.0
    var scrollY = 0.0
    var xPos =0.0
    var yPos = 0.0

    var mouseEnterValid = true
    var mouseScrollValid = true
    var mouseMoved = false

    fun invalidate(button:Int){
        keys[button] = NONE
    }

    fun init() {
        glfwSetMouseButtonCallback(window, { _, key: Int, action: Int, _ ->
            if (!keys.containsKey(key)) {
                keys[key] = NONE
            }

            if (keys[key] == InputMethod.RELEASE) {
                keys[key] = NONE
            }

            when (action) {
                GLFW.GLFW_PRESS -> {
                    keys[key] = InputMethod.MOUSE_PRESS
                }
                GLFW.GLFW_RELEASE -> {
                    keys[key] = InputMethod.MOUSE_RELEASE
                }
            }
        })

        glfwSetCursorPosCallback(window,{ _, xpos, ypos ->
            this.xPos = xpos
            this.yPos = ypos
            mouseMoved = true
        })

        glfwSetScrollCallback(window,{ _, xOffset :Double, yOffset :Double->
            scrollX =xOffset
            scrollY = yOffset
            mouseScrollValid = true
        })

        glfwSetCursorEnterCallback(window, { _, entered:Boolean->
            cursorEntered = entered
            mouseEnterValid = true
        })

    }

}