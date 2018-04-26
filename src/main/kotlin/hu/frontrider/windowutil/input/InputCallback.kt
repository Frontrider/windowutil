package hu.frontrider.windowutil.input

data class InputHolder(
        val key:Int,
        val action:InputMethod,
        val callback:inputCallback,
        val priority:Int =0
)

data class MouseInputHolder(
        val key:Int,
        val action:InputMethod,
        val callback:mouseInputCallback,
        val priority:Int =0
)
typealias inputCallback =()->Boolean
typealias mouseInputCallback =(Double,Double)->Boolean

enum class InputMethod {
    PRESS,RELEASE, NONE,HOLD,
    MOUSE_PRESS,MOUSE_RELEASE,MOUSE_SCROLL,
    MOUSE_MOVED,MOUSE_ENTERED,MOUSE_LEFT
}
