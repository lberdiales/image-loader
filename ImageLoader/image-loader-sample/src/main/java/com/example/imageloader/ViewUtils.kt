package com.example.imageloader

import android.graphics.PorterDuff
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * Objeto con funciones utilitarias para las vistas en eneral
 */
object ViewUtils {

    /**
     * Asigna el recurso de texto si el valor no es cero
     */
    fun TextView.setTextResourceIfNotZero(@StringRes textResource: Int) {
        if (textResource != 0) setText(textResource)
    }

    /**
     * Asigna el color si el valor no es cero
     */
    fun TextView.setTextColorResourceIfNotZero(@ColorRes colorResource: Int) {
        if (colorResource != 0) setTextColor(ContextCompat.getColor(context, colorResource))
    }

    /**
     * Asigna el recurso de imagen si el valor no es cero
     */
    fun ImageView.setImageResourceIfNotZero(@DrawableRes imageResource: Int) {
        if (imageResource != 0) setImageResource(imageResource)
    }

    /**
     * Asigna el color de tinte si el valor no es cero
     */
    fun ImageView.setTintColorResourceIfNotZero(
        @ColorRes colorResource: Int,
        mode: PorterDuff.Mode = PorterDuff.Mode.SRC_ATOP
    ) {
        if (colorResource != 0) setColorFilter(ContextCompat.getColor(context, colorResource), mode)
    }
}
