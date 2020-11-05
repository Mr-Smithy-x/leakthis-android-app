package nyc.vonley.leakthis.di.modules.glide

import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import java.nio.ByteBuffer

class Base64ModelLoaderFactory:  ModelLoaderFactory<String, ByteBuffer> {
    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, ByteBuffer> {
        return Base64ModelLoader()
    }

    override fun teardown() {

    }
}