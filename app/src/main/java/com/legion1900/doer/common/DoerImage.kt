package com.legion1900.doer.common

import androidx.annotation.DrawableRes
import com.legion1900.doer.R

sealed interface DoerImage

class NetworkImage(val url: String) : DoerImage
class ResourceImage(val res: Resource) : DoerImage {

    enum class Resource(@DrawableRes val resId: Int) {
        BALOON(R.drawable.baloon),
        FOREST(R.drawable.forest),
        COAST(R.drawable.coast),
        MOUNTAINS(R.drawable.mountains),
        NIGHT_SKY(R.drawable.night_sky),
    }
}

val DoerImage.coilModel: Any
    get() = when (this) {
        is NetworkImage -> this.url
        is ResourceImage -> this.res.resId
    }
