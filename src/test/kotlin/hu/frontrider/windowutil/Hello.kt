package hu.frontrider.windowutil

import hu.frontrider.windowutil.display.Camera
import hu.frontrider.windowutil.graphics.Shader
import hu.frontrider.windowutil.graphics.TexturedModel
import hu.frontrider.windowutil.input.InputMethod
import hu.frontrider.windowutil.util.Window
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.GL_TEXTURE_2D

fun main(args: Array<String>) {

    val window = Window(
            width = 640,
            height = 480,
            runConstantly = false,
            fullScreen = false,
            title = "render utils")

    window.enable(GL_TEXTURE_2D)
    window.setClearColor(1f, 0f, 1f)

    val vertices = arrayOf(
            -.5f, .5f, 0f,   //TOP LEFT
            .5f, .5f, 0f,    //TOP RIGHT
            .5f, -.5f, 0f,     //BOTTOM RIGHT
            -.5f, -.5f, 0f   //BOTTOM LEFT
    )

    val texture = arrayOf(
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f
    )

    val indices = arrayOf(
            0, 1, 2,
            2, 3, 0
    )
    val camera = Camera(640, 480)
    camera.setScale(80f)

    val shader = Shader("shader", { shader ->
        shader.setUniform("sampler", 0)
        shader.setUniform("projection", camera.getProjection())
    })

    window.addShader(shader)
    val model = TexturedModel(vertices, indices, texture, "./res/snowball.png")

    window.inputManager.addInputCallback(
            GLFW_KEY_F5,
            InputMethod.PRESS,
            {->
                window.markDirty()
                true
            }
    )

    window.inputManager.addInputCallback(
            GLFW_KEY_F5,
            InputMethod.PRESS,
            {->
                println("this one is not supposed to show up")
                true
            },
            100
    )

    window.inputManager.addInputCallback(
            GLFW_MOUSE_BUTTON_1,
            InputMethod.MOUSE_PRESS,
            {->
                println("mouse clicked")
                true
            }
    )

    window.inputManager.addInputCallback(
            GLFW_MOUSE_BUTTON_1,
            InputMethod.MOUSE_RELEASE,
            {->
                println("mouse released")
                true
            }
    )
    window.inputManager.addInputCallback(
            0,
            InputMethod.MOUSE_SCROLL,
            {x,y->
                println("mouse Scrolled")
                true
            }
    )

    window.inputManager.addInputCallback(
            0,
            InputMethod.MOUSE_ENTERED,
            {->
                println("mouse entered")
                true
            }
    )

    window.inputManager.addInputCallback(
            0,
            InputMethod.MOUSE_LEFT,
            {->
                println("mouse left")
                true
            }
    )
    window.inputManager.addInputCallback(
            0,
            InputMethod.MOUSE_MOVED,
            {x,y->
                println("mouse moved")
                true
            }
    )


    window.inputManager.addInputCallback(
            GLFW_MOUSE_BUTTON_1,
            InputMethod.MOUSE_PRESS,
            {->
                println("mouse clicked, not showing")
                true
            },
            100
    )

    window.inputManager.addInputCallback(
            GLFW_KEY_ESCAPE,
            InputMethod.PRESS,
            {->
                window.close()
                true
            }
    )


    window.loop(
            renderCallback = {
                model.render(0)
            }
    )
}

