package com.legion1900.doer.common

import androidx.annotation.DrawableRes

sealed interface DoerImage {

    class NetworkImage(val url: String) : DoerImage
    class ResourceImage(@DrawableRes val resId: Int) : DoerImage
}

val DoerImage.coilModel: Any
    get() = when (this) {
        is DoerImage.NetworkImage -> this.url
        is DoerImage.ResourceImage -> this.resId
    }
