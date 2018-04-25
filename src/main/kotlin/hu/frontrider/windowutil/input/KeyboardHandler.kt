package hu.frontrider.windowutil.input

import hu.frontrider.windowutil.input.InputMethod.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWKeyCallback

class KeyboardHandler : GLFWKeyCallback() {

    override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {

        if (keys[key] == RELEASE) {
            keys[key] = NONE
        }
        when (action) {
            GLFW_PRESS -> {
                keys[key] = PRESS
            }
            GLFW_REPEAT -> {
                keys[key] = HOLD
            }
            GLFW_RELEASE -> {
                keys[key] = RELEASE
            }
        }
    }

    private val keys = Array(65536, { NONE })

    fun isValid(input: InputHolder): Boolean {

        return keys[input.key] == input.action
    }
    fun invalidate(key:Int){
        keys[key] = NONE
    }

}
