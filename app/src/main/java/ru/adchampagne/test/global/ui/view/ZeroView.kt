package ru.adchampagne.test.global.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import ru.adchampagne.test.R
import pro.appcraft.lib.utils.common.setVisible

class ZeroView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val container: View
    private val titleTextView: AppCompatTextView
    private val descriptionTextView: AppCompatTextView
    private val actionButton: AppCompatTextView
    private val imageViewPlaceholder: AppCompatImageView

    private var actionCallback: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.view_zero, this)

        container = findViewById(R.id.layoutContainer)
        titleTextView = findViewById(R.id.titleTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        imageViewPlaceholder = findViewById(R.id.imageViewPlaceholder)
        actionButton = findViewById(R.id.actionButton)
        actionButton.setOnClickListener {
            if (actionCallback != null) {
                actionCallback?.invoke()
            }
        }

        val a = context.obtainStyledAttributes(attrs, R.styleable.ZeroView)
        val title = a.getString(R.styleable.ZeroView_zero_title)
        val description = a.getString(R.styleable.ZeroView_zero_description)
        val action = a.getString(R.styleable.ZeroView_zero_action)
        val background = a.getColor(
            R.styleable.ZeroView_zero_background,
            ContextCompat.getColor(getContext(), android.R.color.white)
        )
        val titleColor = a.getColor(
            R.styleable.ZeroView_zero_title_color,
            ContextCompat.getColor(getContext(), android.R.color.black)
        )
        val descriptionColor = a.getColor(
            R.styleable.ZeroView_zero_description_color,
            ContextCompat.getColor(getContext(), android.R.color.black)
        )
        val actionColor = a.getColor(
            R.styleable.ZeroView_zero_action_color,
            ContextCompat.getColor(getContext(), R.color.appPrimary)
        )
        val actionTextColor = a.getColor(
            R.styleable.ZeroView_zero_action_text_color,
            ContextCompat.getColor(getContext(), R.color.colorTextWhite)
        )
        a.recycle()

        setTitle(title)
        setDescription(description)
        setAction(action)
        setTitleColor(titleColor)
        setDescriptionColor(descriptionColor)
        setActionColor(actionColor)
        setActionTextColor(actionTextColor)
        setContainerBackground(background)
    }

    fun setContainerBackground(background: Int) {
        container.setBackgroundColor(background)
    }

    fun setActionColor(actionColor: Int) {
        actionButton.setTextColor(ColorStateList.valueOf(actionColor))
    }

    private fun setActionTextColor(actionTextColor: Int) {
        actionButton.setTextColor(actionTextColor)
    }

    fun setDescriptionColor(descriptionColor: Int) {
        descriptionTextView.setTextColor(descriptionColor)
    }

    fun setTitleColor(titleColor: Int) {
        titleTextView.setTextColor(titleColor)
    }

    fun setTitle(title: CharSequence?) {
        titleTextView.text = title
        titleTextView.visibility = if (TextUtils.isEmpty(title)) View.GONE else View.VISIBLE
    }

    fun setTitle(@StringRes titleRes: Int) {
        setTitle(context.getString(titleRes))
    }

    fun setDescription(description: CharSequence?) {
        descriptionTextView.text = description
        descriptionTextView.visibility =
            if (TextUtils.isEmpty(description)) View.GONE else View.VISIBLE
    }

    fun setDescription(@StringRes descriptionRes: Int) {
        setDescription(context.getString(descriptionRes))
    }

    fun setAction(action: CharSequence?) {
        actionButton.text = action
        actionButton.visibility = if (TextUtils.isEmpty(action)) View.INVISIBLE else View.VISIBLE
    }

    fun setAction(@StringRes actionRes: Int) {
        setAction(context.getString(actionRes))
    }

    fun setActionCallback(callback: (() -> Unit)?) {
        actionCallback = callback
    }

    fun setImageResource(@DrawableRes imageResId: Int) {
        imageViewPlaceholder.setImageResource(imageResId)
    }

    fun setImageVisibility(visible: Boolean) {
        imageViewPlaceholder.setVisible(visible)
    }

    @Synchronized
    fun hide() {
        visibility = View.GONE
    }

    @Synchronized
    fun show() {
        visibility = View.VISIBLE
    }
}
