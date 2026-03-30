package com.rappi.nitro.base.viewModels.models

import androidx.annotation.StringRes

sealed class CustomToastEvent {

    abstract val iconRes: Int?
    abstract var backgroundColor: Int?
    abstract var gravity: Int?
    abstract var duration: Int?

    data class MessageByString @JvmOverloads constructor(
        val message: String,
        override val iconRes: Int?,
        override var backgroundColor: Int? = null,
        override var gravity: Int? = null,
        override var duration: Int? = null
    ) : CustomToastEvent()

    data class MessageByResource @JvmOverloads constructor(
        @StringRes val messageRes: Int,
        override val iconRes: Int?,
        override var backgroundColor: Int? = null,
        override var gravity: Int? = null,
        override var duration: Int? = null
    ) : CustomToastEvent()
}
