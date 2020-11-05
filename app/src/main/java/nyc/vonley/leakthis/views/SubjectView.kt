package nyc.vonley.leakthis.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import nyc.vonley.leakthis.R

/**
 * TODO: document your custom view class.
 */
class SubjectView : FrameLayout {

    private var color: Int? = null
    var valueView: AppCompatTextView? = null
    var subjectView: AppCompatTextView? = null
    private lateinit var textPaint: TextPaint
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f

    /**
     * The text to draw
     */
    var text: String? = null
    var subject: String? = null

    /**
     * In the example view, this drawable is drawn above the text.
     */
    var exampleDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.SubjectView, defStyle, 0
        )
        subject = a.getString(
            R.styleable.SubjectView_subject
        )
        text = a.getString(
            R.styleable.SubjectView_android_text
        )
        color = a.getColor(R.styleable.SubjectView_android_textColor, resources.getColor(R.color.black))
        a.recycle()
        inflate(context, R.layout.subject_view_layout, this)
        subjectView = findViewById<AppCompatTextView>(R.id.custom_subject_view_key)
        valueView = findViewById<AppCompatTextView>(R.id.custom_subject_view_value)
        subjectView?.text = subject
        valueView?.text = text
        subjectView?.setTextColor(color!!)
        valueView?.setTextColor(color!!)
    }

}