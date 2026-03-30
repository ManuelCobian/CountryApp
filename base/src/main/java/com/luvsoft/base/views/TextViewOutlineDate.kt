package com.luvsoft.base.views

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.appcompat.content.res.AppCompatResources
import com.luvsoft.base.R
import com.luvsoft.base.databinding.ViewTextviewOutlineTagBinding
import java.util.Calendar

class TextViewOutlineDate @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var attrMaxLength: Int = -1

    private var attrHint: String = ""

    private var attrFocusable: Boolean = true

    private var attrDrawableRight: Drawable? = null

    val binding: ViewTextviewOutlineTagBinding =
        ViewTextviewOutlineTagBinding.inflate(
            LayoutInflater.from(this.context),
            this,
            true
        )

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.InputOutlineBox,
            defStyleAttr,
            0
        ).apply {
            try {
                attrHint = getString(R.styleable.InputOutlineBox_hintLayout) ?: ""
                attrFocusable = getBoolean(R.styleable.InputOutlineBox_focusable, true)
                attrMaxLength = getInteger(R.styleable.InputOutlineBox_maxLength, -1)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    attrDrawableRight = getDrawable(R.styleable.InputOutlineBox_drawableRight)
                } else {
                    val drawableRightId =
                        getResourceId(R.styleable.InputOutlineBox_drawableRight, -1)
                    if (drawableRightId != -1)
                        attrDrawableRight = AppCompatResources.getDrawable(context, drawableRightId)
                }
            } finally {
                recycle()
            }
        }
        initInput()
    }

    private fun initInput() {
        setHint(attrHint)
        setDrawableRight(attrDrawableRight)
        setMaxLength(attrMaxLength)
        setDatapickerFragment()
    }

    open fun setDatapickerFragment() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // Formatea la fecha en "dd/MM/yyyy"
                val formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Solo muestra meses y años
        datePickerDialog.datePicker.findViewById<View>(
            Resources.getSystem().getIdentifier("day", "id", "android")
        )?.visibility = View.GONE

        // Mostrar el DatePickerDialog al hacer clic en el TextView
        binding.textViewDateValue.setOnClickListener {
            datePickerDialog.show()
        }
    }

    fun setFocusable() {
        binding.textViewDateValue.isFocusable = attrFocusable
    }

    fun setHint(hint: String) {
        binding.textViewDateValue.hint = hint
    }

    fun setHintRequired(hint: SpannableStringBuilder) {
        binding.textViewDateValue.hint = hint
    }

    fun setMaxLength(length: Int) {
        if (length > 0) binding.textViewDateValue.filters += InputFilter.LengthFilter(length)
    }

    fun setDrawableRight(drawableRight: Drawable?) {
        if (drawableRight != null)
            binding.textViewDateValue.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                drawableRight,
                null
            )
    }

    fun setText(text: String?) {
        binding.textViewDateValue.setText(text)
    }

    fun setError(error: String?) {
        binding.textViewDateValue.error = error
    }

    fun getEditText(): TextView = binding.textViewDateValue

    fun getText(): String = binding.textViewDateValue.text.toString()

    companion object {

        fun TextViewOutlineDate.setTextValue(value: String?) {
            if (getText() != value) {
                setText(value)
            }
        }

        fun TextViewOutlineDate.getTextValue() = getText()
    }
}