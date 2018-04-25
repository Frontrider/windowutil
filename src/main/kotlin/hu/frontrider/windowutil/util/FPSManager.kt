package hu.frontrider.windowutil.util


@Suppress("MemberVisibilityCanBePrivate", "unused")
class FPSManager {
    private var oldTime = 0.0f

    private var frameTime = 0.0
    private var frames = 0
    private var currentFramesPerSecond = 0

    fun render() {
        frameTime += deltaTime
        frames++
        if (frameTime / 1000000000 > 1) {
            currentFramesPerSecond = frames
            frames = 0
            frameTime = 0.0
        }
    }

    val framesPerSecond: Int
        get() = currentFramesPerSecond

    val deltaTime: Float
        get() {
            val newTime = System.nanoTime().toFloat()
            val delta = newTime - oldTime
            oldTime = newTime
            return delta
        }
}
