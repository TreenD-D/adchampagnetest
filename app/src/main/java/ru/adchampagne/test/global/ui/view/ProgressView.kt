package ru.adchampagne.test.global.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import ru.adchampagne.test.R
import pro.appcraft.lib.utils.common.setGone
import pro.appcraft.lib.utils.common.setVisible

private const val MIN_SHOW_TIME_MS = 500
private const val MIN_DELAY_MS = 500

class ProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var startTime: Long = -1
    private var postedHide = false
    private var postedShow = false
    private var dismissed = false

    private val progressBar: ProgressBar
    private val textView: AppCompatTextView

    private val delayedHide: () -> Unit = {
        postedHide = false
        startTime = -1
        post {
            visibility = View.GONE
            findViewById<View>(R.id.progress_content).setGone()
        }
    }

    private val delayedShow = {
        postedShow = false
        if (!dismissed) {
            startTime = System.currentTimeMillis()
            findViewById<View>(R.id.progress_content).setVisible()
        }
    }

    init {
        View.inflate(context, R.layout.view_progress, this)

        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.textView)

        initAttributes(context, attrs)

        findViewById<View>(R.id.progress_content).visibility = visibility
    }

    private fun initAttributes(context: Context, attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }

        val a = context.obtainStyledAttributes(attrs, R.styleable.ProgressView)
        val text = a.getString(R.styleable.ProgressView_ps_text)
        val textColor = a.getColor(R.styleable.ProgressView_ps_text_color, 0)
        val progressColor = a.getColor(R.styleable.ProgressView_ps_progress_color, 0)
        val backgroundColor = a.getColor(R.styleable.ProgressView_ps_background, 0)
        a.recycle()

        setProgressText(text)
        setProgressTextColor(textColor)
        setProgressColor(progressColor)
        setProgressBackground(backgroundColor)
    }

    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        removeCallbacks()
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks()
    }

    private fun removeCallbacks() {
        removeCallbacks(delayedHide)
        removeCallbacks(delayedShow)
    }

    fun setProgressText(text: CharSequence?) {
        textView.text = text
        textView.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
    }

    fun setProgressText(@StringRes textRes: Int) {
        setProgressText(context.getString(textRes))
    }

    fun setProgressTextColor(color: Int) {
        textView.setTextColor(color)
    }

    fun setProgressColor(color: Int) {
        progressBar.indeterminateTintList = ColorStateList.valueOf(color)
    }

    fun setProgressBackground(backgroundColor: Int) {
        findViewById<View>(R.id.progress_content).setBackgroundColor(backgroundColor)
    }

    @JvmOverloads
    fun hide(immediately: Boolean = false) {
        dismissed = true
        removeCallbacks(delayedShow)
        postedShow = false
        val diff = System.currentTimeMillis() - startTime

        if (immediately) {
            removeCallbacks(delayedHide)
            post(delayedHide)
        } else if (diff >= MIN_SHOW_TIME_MS || startTime == -1L || immediately) {
            post(delayedHide)
        } else if (!postedHide) {
            postDelayed(delayedHide, MIN_SHOW_TIME_MS - diff)
            postedHide = true
        }
    }

    @JvmOverloads
    fun show(immediately: Boolean = false) {
        visibility = View.VISIBLE
        startTime = -1
        dismissed = false
        removeCallbacks(delayedHide)
        postedHide = false
        if (immediately) {
            removeCallbacks(delayedShow)
            post(delayedShow)
        } else if (!postedShow) {
            postDelayed(delayedShow, MIN_DELAY_MS.toLong())
            postedShow = true
        }
    }

    fun toggleVisibility(show: Boolean, immediately: Boolean = false) {
        if (show) {
            show(immediately)
        } else {
            hide(immediately)
        }
    }
}
