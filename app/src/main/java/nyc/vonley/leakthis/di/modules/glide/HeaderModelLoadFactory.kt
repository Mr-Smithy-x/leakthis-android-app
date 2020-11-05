package nyc.vonley.leakthis.di.modules.glide

import android.util.Log
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import nyc.vonley.leakthis.BuildConfig
import nyc.vonley.leakthis.util.SharedPreferenceManager
import java.io.InputStream

class HeaderModelLoadFactory : BaseGlideUrlLoader<String> {
    private val loader: ModelLoader<GlideUrl, InputStream>
    private var modelCache: ModelCache<String, GlideUrl>? = null
    private var manager: SharedPreferenceManager? = null

    protected constructor(loader: ModelLoader<GlideUrl, InputStream>) : super(loader) {
        this.loader = loader
    }

    protected constructor(
        loader: ModelLoader<GlideUrl, InputStream>,
        modelCache: ModelCache<String, GlideUrl>?
    ) : super(loader, modelCache) {
        this.loader = loader
        this.modelCache = modelCache
    }

    override fun getHeaders(s: String, width: Int, height: Int, options: Options): Headers? {
        val builder = LazyHeaders.Builder()
        if(manager != null && manager!!.hasCookies()) {
            val string =  manager!!.getCookieString()
            if(BuildConfig.LOG) {
                Log.e("HeaderModelLoadFactory", "Cookie: ${string}")
            }
            return builder
                .addHeader("Cookie", string)
                .build()
        }else{
            Log.e("HeaderModelLoadFactory", "COOKIE NOT PRESENT")
        }
        return builder.build()
    }

    override fun getUrl(model: String, width: Int, height: Int, options: Options): String {
        return model
    }

    override fun handles(model: String): Boolean {
        return true
    }

    class Factory(private val manager: SharedPreferenceManager) :
        ModelLoaderFactory<String, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
            val headerModelLoadFactory = HeaderModelLoadFactory(
                multiFactory.build(
                    GlideUrl::class.java, InputStream::class.java
                )
            )
            headerModelLoadFactory.setManager(manager)
            return headerModelLoadFactory
        }

        override fun teardown() {}
    }

    private fun setManager(manager: SharedPreferenceManager) {
        this.manager = manager
    }
}