package com.example.imageloader

import android.view.View
import android.widget.LinearLayout

/**
 * Objeto con funciones utilitarias para las Notificaciones
 */
object NotificationUtils {

    /**
     * Esta funcion remueve la vista de su padre si es un LinearLayout
     */
    fun View.removeSelfFromParent() {
        if (parent is LinearLayout) removeSelfFromLinearLayout()
    }

    /**
     * Esta funcion remueve la vista de su padre si es un LinearLayout
     */
    private fun View.removeSelfFromLinearLayout() {
        (parent as LinearLayout).removeView(this)
    }

    /**
     * Esta agrega la Notificaion a un LinearLayout en la posicion especificada por
     * @see position Dado que la notificacion deberia ocupar el ancho del padre solo se considera
     * para LinearLayout Verticales. Para agregar y quitar Notification a ConstraintLayout se debe ahcer manualmente
     * ya que es mas estable el comportamiento cuando se conocen las posiciones de las vistas y los amrgenes en un
     * ConstrainLayout
     *
     * @param position la posicion donde se agrega la Notificacion (arriba o abajo del target)
     * @param parent el LinearLayout al cual se agregara la Notificacion
     * @param view el target sobre el cual se agrega la notificacion, ya sea arriba de o abajo de
     * @param left margen izquierdo
     * @param top margen superior
     * @param right margen superior
     * @param bottom margen inferior
     */
    fun View.applyAnchorInLinearLayout(
        position: Notification.AnchorPosition,
        parent: LinearLayout,
        view: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        if (parent.orientation == LinearLayout.VERTICAL) {
            parent.addView(
                this,
                when (position) {
                    Notification.AnchorPosition.ABOVE -> parent.indexOfChild(view)
                    Notification.AnchorPosition.BELOW -> parent.indexOfChild(view) + 1
                },
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginEnd = right
                    marginStart = left
                    topMargin = top
                    bottomMargin = bottom
                }
            )
        }
    }
}
