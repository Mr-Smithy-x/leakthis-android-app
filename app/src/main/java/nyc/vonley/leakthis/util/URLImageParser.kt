package nyc.vonley.leakthis.util

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html.ImageGetter
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


/***
 * Construct the URLImageParser which will execute AsyncTask and refresh the container
 *
 * @param container
 * @param context
 * @param urlImageParserCallBack
 */
class URLImageParser(
    var container: View,
    private val urlImageParserCallBack: URLImageParserCallBack
) : ImageGetter, CoroutineScope {

    var glide = Glide.with(container.context).asBitmap()
    private val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    fun cleanup() {
        job.cancel()
    }

    override fun getDrawable(source: String): Drawable {
        val urlDrawable = URLDrawable()
        launch {
            try {
                val bitmap = glide.load(source).submit().get()
                withContext(Dispatchers.Main) {
                    val drawable: Drawable = BitmapDrawable(container.resources, bitmap)
                    //val multiplier =  .50 / drawable.intrinsicWidth
                    val width = (drawable.intrinsicWidth).toInt()
                    val height =(drawable.intrinsicHeight).toInt()
                    drawable.setBounds(0, 0, 100, 100)
                    urlDrawable.setBounds(0, 0, 100, 100)
                    drawable.invalidateSelf()
                    urlDrawable.drawable = drawable
                    urlDrawable.invalidateSelf()
                    container.invalidate()
                    urlImageParserCallBack.onLoaded(source, urlDrawable)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return urlDrawable
    }

    interface URLImageParserCallBack {
        fun onLoaded(url: String?, drawable: Drawable?)
    }


}