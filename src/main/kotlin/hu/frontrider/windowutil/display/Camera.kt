package hu.frontrider.windowutil.display

import org.joml.Matrix4f
import org.joml.Vector3f

class Camera(width:Int,height:Int){
    private var position=Vector3f()
    private val projection:Matrix4f

    init {
        position = Vector3f();
        projection = Matrix4f().setOrtho2D(-width/2f,width/2f,-height/2f,height/2f)
    }

    fun setPosition(position:Vector3f){
        this.position.add(position)
    }

    fun getProjection():Matrix4f{

        val target = Matrix4f()
        val pos = Matrix4f().setTranslation(position)

        projection.mul(pos,target)

        return target
    }

    fun setScale(scale: Float) {
        projection.scale(scale)
    }
}