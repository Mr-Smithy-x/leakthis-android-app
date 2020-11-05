package nyc.vonley.leakthis.util

import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.Spanned
import android.util.Log
import android.view.View
import org.xml.sax.Attributes

class LeakThisHtmlTagHandler private constructor() : HtmlParser.TagHandler {


    override fun handleTag(
        opening: Boolean,
        tag: String?,
        output: Editable?,
        attributes: Attributes?
    ): Boolean {

        return false
    }

    companion object {
        fun create(string: String): Spanned? {
            return HtmlParser.buildSpannedText(string, LeakThisHtmlTagHandler())
        }

        fun create(view: View, string: String): Spanned? {
            return HtmlParser.buildSpannedText(
                string,
                URLImageParser(view, object : URLImageParser.URLImageParserCallBack {
                    override fun onLoaded(url: String?, drawable: Drawable?) {
                        Log.e("LeakThisHtmlTagHandler:url", "URL: $url")
                    }
                }),
                LeakThisHtmlTagHandler()
            )
        }
    }


}
