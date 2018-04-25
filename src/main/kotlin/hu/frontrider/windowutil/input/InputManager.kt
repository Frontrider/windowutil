package hu.frontrider.windowutil.input

import org.lwjgl.glfw.GLFW

class InputManager(
        private val keyboardHandler: KeyboardHandler,
        private var keys: ArrayList<InputHolder> = ArrayList()) {

    private var sorted = false

    fun handleInput() {
        GLFW.glfwPollEvents()
        if(sorted)
            sortCallbacks()
        for (input in keys) {
            if (keyboardHandler.isValid(input)) {
                if (input.callback.invoke())
                    keyboardHandler.invalidate(input.key)
            }
        }
    }

    fun addKeyCallback(key: Int, action: InputMethod, callback: inputCallback, priority: Int = 0) {
        keys.add(InputHolder(key, action, callback, priority))
        sorted = false
    }

    fun addKeyCallback(inputHolder: InputHolder) {
        keys.add(inputHolder)
        sorted = false
    }

    private fun sortCallbacks() {
        keys.sortBy {
            it.priority
        }
        sorted = true
    }

}