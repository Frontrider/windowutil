package hu.frontrider.windowutil.input

data class InputHolder(
        val key:Int,
        val action:InputMethod,
        val callback:inputCallback,
        val priority:Int =0
)

typealias inputCallback =()->Boolean

enum class InputMethod {
    PRESS,RELEASE, NONE,HOLD
}