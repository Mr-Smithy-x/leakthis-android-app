package nyc.vonley.leakthis.di.modules.glide

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import nyc.vonley.leakthis.di.annotations.SharedPreferenceStorage
import nyc.vonley.leakthis.di.modules.LocalStorageModule
import nyc.vonley.leakthis.util.SharedPreferenceManager
import nyc.vonley.leakthis.util.SharedPreferenceManagerImpl
import java.io.InputStream
import java.nio.ByteBuffer
import javax.inject.Inject

@GlideModule
class MyGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        Log.e("REGISTERING COMPONENTS", "OK LOL")
        super.registerComponents(context, glide, registry)
        registry.prepend(String::class.java, ByteBuffer::class.java, Base64ModelLoaderFactory())
        registry.prepend(String::class.java, InputStream::class.java, HeaderModelLoadFactory.Factory(LocalStorageModule.provideSharePreferenceManager(context)))
    }


}