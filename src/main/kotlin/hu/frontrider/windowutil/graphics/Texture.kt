package hu.frontrider.windowutil.graphics

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_TEXTURE0
import org.lwjgl.opengl.GL13.glActiveTexture
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


class Texture(filename: String) {

    var id: Int
    var width: Int
    var height: Int

    init {
        val image: BufferedImage = ImageIO.read(File(filename))

        width = image.width
        height = image.height

        val pixels_raw = image.getRGB(0, 0, width, height, null, 0, width)!!

        val pixels = BufferUtils.createByteBuffer(width * height * 4)

        for (i in 0 until width) {
            for (j in 0 until height) {
                val pixel = pixels_raw[i * width + j]
                pixels.put(((pixel shr 16) and 0xFF).toByte())  //red
                pixels.put(((pixel shr 8) and 0xFF).toByte())   //green
                pixels.put((pixel and 0xFF).toByte())           //blue
                pixels.put(((pixel shr 24) and 0xFF).toByte())  //alpha
            }
        }
        pixels.flip()

        id = glGenTextures()

        glBindTexture(GL_TEXTURE_2D, id)

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST.toFloat())
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST.toFloat())
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels)
    }

    fun bind(sampler: Int) {
        if (sampler in 0..31) {
            glActiveTexture(GL_TEXTURE0 + sampler)
            glBindTexture(GL_TEXTURE_2D, id)
        }
    }
}