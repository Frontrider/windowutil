package hu.frontrider.windowutil.graphics

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import java.nio.FloatBuffer
import java.nio.IntBuffer

@SuppressWarnings("WeakerAccess", "unused")
class Model(vertices: Array<Float>,
            texCords: Array<Float>,
            indices: Array<Int>) {

    private var drawcount: Int = indices.size
    private val vertex_id: Int = glGenBuffers()

    private val texture_id: Int
    private val indices_id :Int

    init {

        glBindBuffer(GL_ARRAY_BUFFER, vertex_id)
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW)

        texture_id = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, texture_id)
        glBufferData(GL_ARRAY_BUFFER, createBuffer(texCords), GL_STATIC_DRAW)

        indices_id = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,indices_id)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,createBuffer(indices), GL_STATIC_DRAW)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    fun getRadius(){

    }

    fun render() {
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)

        glBindBuffer(GL_ARRAY_BUFFER, vertex_id)
        glVertexAttribPointer(0,3, GL_FLOAT,false, 0, 0)


        glBindBuffer(GL_ARRAY_BUFFER, texture_id)
        glVertexAttribPointer(1,2, GL_FLOAT,false, 0, 0)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,indices_id)

        glDrawElements(GL_TRIANGLES,drawcount, GL_UNSIGNED_INT,0)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
    }

    private fun createBuffer(data: Array<Float>): FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data.toFloatArray())
        buffer.flip()
        return buffer
    }

    private fun createBuffer(data: Array<Int>): IntBuffer {
        val buffer = BufferUtils.createIntBuffer(data.size)
        buffer.put(data.toIntArray())
        buffer.flip()
        return buffer
    }
}