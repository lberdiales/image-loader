package com.example.imageloader

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.example.imageloader.NotificationUtils.applyAnchorInLinearLayout
import com.example.imageloader.NotificationUtils.removeSelfFromParent
import com.example.imageloader.ViewUtils.setImageResourceIfNotZero
import com.example.imageloader.ViewUtils.setTextResourceIfNotZero
import com.example.imageloader.databinding.PayModDesignSystemOneLineNotificationBinding
import com.example.imageloader.databinding.PayModDesignSystemTwoLineNotificationBinding
import com.google.android.material.textview.MaterialTextView
import java.util.concurrent.TimeUnit

/**
 * Una notificacion compuesta de una a dos etiquetas de texto TextView y un icono al final ImageView, la vista
 * se puede crear a traves de un Builder @see Notification.Builder. Se puede configurar para que desaparezca
 * sola mientras exista dentro de un LinearLayout. De otra forma se tiene que implementar el listener
 * @see OnAutoDismissListener para remover la vista manualmente
 */
class Notification @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    /**
     * Enum que representa la posiciones donde se puede insertar una Notificacion automaticamente dentro
     * de un LinearLayout
     */
    enum class AnchorPosition {
        ABOVE,
        BELOW
    }

    /**
     * Enum que representa el tipo de Notificaion que se crea, este tipo define elc olor del fondo
     */
    enum class NotificationType {
        NEUTRAL,
        ALERT,
        ERROR
    }

    /**
     * Enum que representa el numero de lineas de texto que se agregan a la notificacion
     */
    enum class NotificationTextNumber {
        ONE,
        TWO
    }

    @StringRes
    private var labelTextResource: Int = 0

    @StringRes
    private var bottomLabelTextResource: Int = 0

    @DrawableRes
    private var startIconResource = R.drawable.ic_play_24dp

    @DrawableRes
    private var endIconResource = R.drawable.ic_pause_24dp

    private val autoDismiss: Boolean
        get() = autoDismissSeconds > 0

    private var autoDismissSeconds = 0

    private var endIconEnabled = false

    private var startIconEnabled = false

    private var notificationType: NotificationType = NotificationType.NEUTRAL

    private var notificationTextNumber: NotificationTextNumber = NotificationTextNumber.ONE

    lateinit var labelTextView: MaterialTextView

    lateinit var endIconImageView: AppCompatImageView

    lateinit var startIconImageView: AppCompatImageView

    var bottomLabelTextView: MaterialTextView? = null

    private var onAutoDismissListener: OnAutoDismissListener? = null

    private lateinit var binding: ViewBinding

    /**
     * Asigna el numero de etiquetas de texto en la notifiacion
     */
    fun setTextNumber(textNumber: NotificationTextNumber) {
        notificationTextNumber = textNumber
    }

    /**
     * Asigna el tipo de notificacion, controla el color del fondo
     */
    fun setNotificationType(type: NotificationType) {
        notificationType = type
        setupBackground()
        setupViews()
    }

    fun setLabelText(text: CharSequence) {
        labelTextView.text = text
    }

    fun setBottomLabelText(text: CharSequence) {
        bottomLabelTextView?.text = text
    }

    fun setLabelTextResource(@StringRes textResource: Int) {
        labelTextView.setTextResourceIfNotZero(textResource)
    }

    fun setBottomLabelTextResource(@StringRes textResource: Int) {
        bottomLabelTextView?.setTextResourceIfNotZero(textResource)
    }

    fun setEndIconResource(@DrawableRes imageResource: Int) {
        endIconImageView.setImageResourceIfNotZero(imageResource)
    }

    fun setStartIconResource(@DrawableRes imageResource: Int) {
        startIconImageView.setImageResourceIfNotZero(imageResource)
    }

    fun setEndIconClickListener(listener: OnClickListener) {
        endIconImageView.setOnClickListener(listener)
    }

    /**
     * Asigna un listener que implementa
     * @see OnAutoDismissListener, se ejecutara cuando expire el timer para remover automaticamente
     * la notificacion de su padre
     */
    fun setOnAutoDismissListener(listener: OnAutoDismissListener) {
        onAutoDismissListener = listener
    }

    init {
        attrs?.let {
            initAttrs(it)
            initViews()
            setupViews()
            setupAutoDismiss()
        }
    }

    /**
     * Configura la notificacion para que se remueva automaticamente del padre despues de la duracion
     * @see autoDismissSeconds
     * @see removeSelfFromParent solo funciona si el padre es LinearLayout vertical, de otra forma se
     * tendra que removar la notificacion manualmente de su padre escuchando
     * @see onAutoDismissListener
     */
    private fun setupAutoDismiss() {
        if (autoDismiss) {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    onAutoDismissListener?.onAutoDismiss(this)
                    removeSelfFromParent()
                },
                TimeUnit.SECONDS.toMillis(autoDismissSeconds.toLong())
            )
        }
    }

    private fun initViews() {
        binding = getLayoutBinding()
        if (binding is PayModDesignSystemOneLineNotificationBinding) extractViews(binding as PayModDesignSystemOneLineNotificationBinding)
        if (binding is PayModDesignSystemTwoLineNotificationBinding) extractViews(binding as PayModDesignSystemTwoLineNotificationBinding)
        setupMinHeight()
        setupBackground()
    }

    private fun setupViews() {
        setupStartIcon()
        setupEndIcon()
        setupTexts()
    }

    private fun setupMinHeight() {
        minHeight = resources.getDimension(
            when (notificationTextNumber) {
                NotificationTextNumber.ONE -> R.dimen.pay_mod_design_system_notification_one_line_height
                NotificationTextNumber.TWO -> R.dimen.pay_mod_design_system_notification_two_line_height
            }
        ).toInt()
    }

    private fun setupTexts() {
        setLabelTextResource(labelTextResource)
        if (notificationTextNumber == NotificationTextNumber.TWO) {
            bottomLabelTextView?.let { setBottomLabelTextColor(it) }
            setBottomLabelTextResource(bottomLabelTextResource)
        }
    }

    private fun setBottomLabelTextColor(textView: TextView) {
        textView.setTextColor(
            ContextCompat.getColor(
                context,
                when (notificationType) {
                    NotificationType.NEUTRAL -> R.color.pay_mod_design_system_color_grays_onWhite_contentB
                    NotificationType.ALERT -> R.color.pay_mod_design_system_color_grays_onWhite_contentA
                    NotificationType.ERROR -> R.color.pay_mod_design_system_color_grays_onWhite_contentA
                }
            )
        )
    }

    private fun setupIconsEnabled(isStartIconEnabled: Boolean, isEndIconEnabled: Boolean) {
        startIconEnabled = isStartIconEnabled
        endIconEnabled = isEndIconEnabled
    }

    private fun setupStartIcon() {
        if (startIconEnabled) {
            startIconImageView.visibility = View.VISIBLE
            startIconImageView.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    when (notificationType) {
                        NotificationType.NEUTRAL -> R.color.pay_mod_design_system_color_grays_onWhite_contentA
                        NotificationType.ALERT -> R.color.pay_mod_design_system_color_status_alert
                        NotificationType.ERROR -> R.color.pay_mod_design_system_color_status_error
                    }
                )
            )
            setStartIconResource(startIconResource)
        }
    }

    private fun setupEndIcon() {
        if (endIconEnabled) {
            endIconImageView.visibility = View.VISIBLE
            endIconImageView.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.pay_mod_design_system_color_grays_onWhite_contentA
                )
            )
            setEndIconResource(endIconResource)
        }
    }

    private fun getLayoutBinding(): ViewBinding =
        when (notificationTextNumber) {
            NotificationTextNumber.ONE -> PayModDesignSystemOneLineNotificationBinding.inflate(
                LayoutInflater.from(context), this
            )
            NotificationTextNumber.TWO -> PayModDesignSystemTwoLineNotificationBinding.inflate(
                LayoutInflater.from(context), this
            )
        }

    private fun extractViews(binding: PayModDesignSystemOneLineNotificationBinding) {
        labelTextView = binding.textViewLabel
        endIconImageView = binding.imageViewEndIcon
        startIconImageView = binding.imageViewStartIcon
    }

    private fun extractViews(binding: PayModDesignSystemTwoLineNotificationBinding) {
        labelTextView = binding.textViewLabel
        bottomLabelTextView = binding.textViewBottomLabel
        endIconImageView = binding.imageViewEndIcon
        startIconImageView = binding.imageViewStartIcon
    }

    private fun setupBackground() {
        val backgroundDrawable = GradientDrawable().apply {
            cornerRadius =
                resources.getDimension(R.dimen.pay_mod_design_system_spacing_large)
            color =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        when (notificationType) {
                            NotificationType.NEUTRAL -> R.color.pay_mod_design_system_color_background_elevatedOnWhite
                            NotificationType.ALERT -> R.color.pay_mod_design_system_color_pastel_onWhite_paleYellow
                            NotificationType.ERROR -> R.color.pay_mod_design_system_color_pastel_onWhite_paleRed
                        }
                    )
                )
        }
        background = backgroundDrawable
    }

    @Suppress("ComplexMethod")
    @SuppressLint("CustomViewStyleable")
    private fun initAttrs(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.pay_mod_design_system_notification
        )

        val size = typedArray.indexCount
        (0 until size).asSequence()
            .map { typedArray.getIndex(it) }
            .forEach {
                when (it) {
                    R.styleable.pay_mod_design_system_notification_pay_mod_design_system_notification_bottom_label ->
                        bottomLabelTextResource =
                            typedArray.getResourceId(it, 0)
                    R.styleable.pay_mod_design_system_notification_pay_mod_design_system_notification_label ->
                        labelTextResource =
                            typedArray.getResourceId(it, 0)
                    R.styleable.pay_mod_design_system_notification_pay_mod_design_system_notification_end_icon ->
                        endIconResource =
                            typedArray.getResourceId(it, endIconResource)
                    R.styleable.pay_mod_design_system_notification_pay_mod_design_system_notification_start_icon ->
                        startIconResource =
                            typedArray.getResourceId(it, startIconResource)
                    R.styleable.pay_mod_design_system_notification_pay_mod_design_system_notification_type ->
                        notificationType =
                            NotificationType.values()[typedArray.getInteger(it, 0)]
                    R.styleable.pay_mod_design_system_notification_pay_mod_design_system_notification_text_number ->
                        notificationTextNumber =
                            NotificationTextNumber.values()[typedArray.getInteger(it, 0)]
                    R.styleable.pay_mod_design_system_notification_pay_mod_design_system_notification_end_icon_enabled ->
                        endIconEnabled =
                            typedArray.getBoolean(it, false)
                    R.styleable.pay_mod_design_system_notification_pay_mod_design_system_notification_start_icon_enabled ->
                        startIconEnabled =
                            typedArray.getBoolean(it, false)
                    R.styleable.pay_mod_design_system_notification_pay_mod_design_system_notification_auto_dismiss_seconds ->
                        autoDismissSeconds =
                            typedArray.getInteger(it, 0)
                }
            }
        typedArray.recycle()
    }

    /**
     * El Builder de notificacion se utiliza para configurar todas las opciones de la vista
     */
    class Builder {
        @StringRes
        private var labelTextResource: Int = 0

        @StringRes
        private var bottomLabelTextResource: Int = 0
        private var labelText: CharSequence = ""
        private var bottomLabelText: CharSequence = ""
        private var endIconEnabled = false
        private var endIconResource = 0
        private var startIconEnabled = false
        private var startIconResource = 0
        private var textNumber: NotificationTextNumber = NotificationTextNumber.ONE
        private var notificationType: NotificationType = NotificationType.NEUTRAL
        private var viewAnchor: View? = null
        private var notificationPosition: AnchorPosition = AnchorPosition.BELOW
        private var leftMargin: Int = 0
        private var topMargin: Int = 0
        private var rightMargin: Int = 0
        private var bottomMargin: Int = 0
        private var isAnchored = false
        private var autoDismissSeconds: Int = 0
        private var endIconClickListener: OnClickListener? = null
        private var linearLayoutParent: LinearLayout? = null
        private var autoDismissListener: OnAutoDismissListener? = null

        /**
         * Aplica el texto de la etiqueta superior
         */
        fun labelText(text: CharSequence): Builder {
            labelText = text
            return this
        }

        /**
         * Aplica el recurso de texto de la etiqueta superior
         */
        fun labelTextResource(textResource: Int): Builder =
            apply { labelTextResource = textResource }

        /**
         * Aplica el texto de la etiqueta inferior
         */
        fun bottomLabelText(text: CharSequence): Builder =
            apply { bottomLabelText = text }

        /**
         * Aplica el recurso de texto de la etiqueta inferior
         */
        fun bottomLabelTextResource(textResource: Int): Builder =
            apply { bottomLabelTextResource = textResource }

        /**
         * Aplica el recurso del icono del final
         */
        fun endIconResource(@DrawableRes iconResource: Int): Builder =
            apply {
                endIconEnabled = true
                endIconResource = iconResource
            }

        /**
         * Aplica el recurso del icono del inicio
         */
        fun startIconResource(@DrawableRes iconResource: Int): Builder =
            apply {
                startIconEnabled = true
                startIconResource = iconResource
            }

        /**
         * Aplica el numero de etiquetas para la notificacion
         */
        fun notificationTextNumber(number: NotificationTextNumber): Builder =
            apply { textNumber = number }

        /**
         * Aplica el tipo de fondo de la notificacion
         */
        fun notificationType(type: NotificationType): Builder =
            apply { notificationType = type }

        /**
         * Aplica la funcion de remover la notificacion automaticamente de su padre despues de
         * @param afterSeconds
         * La notificacion solo se remueve automaticamente si el padre es un LinearLayout Vertical,
         * de otra forma hay que implementar el listener de autodismiss
         * @see OnAutoDismissListener para quitar la notificacion manualmente
         */
        fun autoDismiss(afterSeconds: Int): Builder =
            apply { autoDismissSeconds = afterSeconds }

        /**
         * Aplica un listener de click al icono del final
         */
        fun endIconClickListener(listener: OnClickListener): Builder =
            apply { endIconClickListener = listener }

        /**
         * Se puede agregar la notificacion automaticamente usando esta funcion. Solo aplica para
         * LinearLayout Vertical
         */
        fun setAnchor(
            position: AnchorPosition,
            parent: LinearLayout,
            view: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int
        ): Builder =
            apply {
                isAnchored = true
                linearLayoutParent = parent
                viewAnchor = view
                notificationPosition = position
                leftMargin = left
                topMargin = top
                rightMargin = right
                bottomMargin = bottom
            }

        /**
         * Aplica un listener para cuadno ha transcurrido el tiempo de autoDismiss
         */
        fun autoDismissListener(listener: OnAutoDismissListener): Builder =
            apply { autoDismissListener = listener }

        /**
         * Construye la Notificacion con la configuracion del Builder definida antes de llamar build()
         */
        fun build(context: Context): Notification =
            Notification(context).also {
                it.id = View.generateViewId()
                it.setTextNumber(textNumber)
                it.setupIconsEnabled(
                    isStartIconEnabled = startIconEnabled,
                    isEndIconEnabled = endIconEnabled
                )
                it.autoDismissSeconds = autoDismissSeconds
                it.setupAutoDismiss()
                it.initViews()
                it.setNotificationType(notificationType)
                if (labelText.isNotEmpty()) it.setLabelText(labelText)
                if (labelTextResource != 0) it.setLabelTextResource(labelTextResource)
                if (bottomLabelText.isNotEmpty()) it.setBottomLabelText(bottomLabelText)
                if (bottomLabelTextResource != 0) it.setBottomLabelTextResource(
                    bottomLabelTextResource
                )
                if (startIconResource != 0) it.setStartIconResource(startIconResource)
                if (endIconResource != 0) it.setEndIconResource(endIconResource)
                endIconClickListener?.let { listener -> it.setEndIconClickListener(listener) }
                autoDismissListener?.let { listener -> it.setOnAutoDismissListener(listener) }
                if (isAnchored) applyAnchor(it)
            }

        private fun applyAnchor(view: View) {
            if (viewAnchor != null && linearLayoutParent != null) view.applyAnchorInLinearLayout(
                notificationPosition,
                linearLayoutParent!!,
                viewAnchor!!,
                leftMargin,
                topMargin,
                rightMargin,
                bottomMargin
            )
        }
    }

    interface OnAutoDismissListener {
        fun onAutoDismiss(notification: Notification)
    }
}
