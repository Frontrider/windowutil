package hu.frontrider.windowutil.graphics

@Suppress("MemberVisibilityCanBePrivate")
class TexturedModel(vertices: Array<Float>,
                    indices: Array<Int>,
                    texCords: Array<Float>,
                    filePath:String) {

    val texture = Texture(filePath)
    val model = Model(vertices, texCords, indices)

    fun render(sampler:Int){
        texture.bind(sampler)
        model.render()
    }
}