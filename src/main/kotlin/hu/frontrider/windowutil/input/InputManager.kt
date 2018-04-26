package hu.frontrider.windowutil.input

import org.lwjgl.glfw.GLFW

class InputManager(
        private val keyboardHandler: KeyboardHandler,
        private var keys: ArrayList<InputHolder> = ArrayList(),
        private var mouseEvents: ArrayList<MouseInputHolder> = ArrayList()
) {

    private var sorted = false

    private lateinit var mouseHandler: MouseHandler

    fun setMouseHandler(mouseHandler: MouseHandler) {
        this.mouseHandler = mouseHandler;
    }

    fun handleInput() {
        GLFW.glfwPollEvents()
        if (!sorted)
            sortCallbacks()

        for (input in keys) {
            if (keyboardHandler.isValid(input)) {
                if (input.callback.invoke())
                    keyboardHandler.invalidate(input.key)
            } else {
                if (input.action == InputMethod.MOUSE_ENTERED && mouseHandler.cursorEntered && mouseHandler.mouseEnterValid) {
                    mouseHandler.mouseEnterValid = !input.callback.invoke()
                } else {
                    if (input.action == InputMethod.MOUSE_LEFT && !mouseHandler.cursorEntered && mouseHandler.mouseEnterValid) {
                        mouseHandler.mouseEnterValid = !input.callback.invoke()
                    }
                    if (mouseHandler.keys.containsKey(input.key) && input.action == mouseHandler.keys[input.key]) {
                        if (input.callback.invoke())
                            mouseHandler.invalidate(input.key)
                    }
                }
            }
        }

        for (mouse in mouseEvents) {
            if (mouseHandler.mouseScrollValid && mouse.action == InputMethod.MOUSE_SCROLL) {
                mouseHandler.mouseScrollValid = !mouse.callback.invoke(mouseHandler.scrollX, mouseHandler.scrollY)
            } else
                if (mouseHandler.mouseMoved && mouse.action == InputMethod.MOUSE_MOVED) {
                    mouseHandler.mouseMoved = !mouse.callback.invoke(mouseHandler.xPos, mouseHandler.yPos)
                }
        }
    }

    fun addInputCallback(key: Int, action: InputMethod, callback: inputCallback, priority: Int = 0) {
        keys.add(InputHolder(key, action, callback, priority))
        sorted = false
    }

    fun addInputCallback(key: Int, action: InputMethod, callback: mouseInputCallback, priority: Int = 0) {
        mouseEvents.add(MouseInputHolder(key, action, callback, priority))
        sorted = false
    }

    fun addInputCallback(inputHolder: InputHolder) {
        keys.add(inputHolder)
        sorted = false
    }


    fun addInputCallback(inputHolder: MouseInputHolder) {
        mouseEvents.add(inputHolder)
        sorted = false
    }

    private fun sortCallbacks() {
        keys.sortBy {
            it.priority
        }
        mouseEvents.sortBy {
            it.priority
        }
        sorted = true
    }

}