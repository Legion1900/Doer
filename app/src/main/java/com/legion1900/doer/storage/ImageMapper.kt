package com.legion1900.doer.storage

import com.legion1900.doer.common.DoerImage
import com.legion1900.doer.common.NetworkImage
import com.legion1900.doer.common.ResourceImage

class ImageMapper {

    private val idResourceMap by lazy {
        ResourceImage.Resource.entries.associateBy { it.hashCode().toString() }
    }

    fun mapToStorageImage(image: DoerImage): LocalNoteImage {
        return LocalNoteImage(getIdentifier(image), getSource(image))
    }

    fun mapLocalImage(image: LocalNoteImage): DoerImage {
        return when (image.source) {
            LocalNoteImage.Source.NETWORK -> NetworkImage(image.identifier)
            LocalNoteImage.Source.LOCAL -> {
                val resource = idResourceMap[image.identifier] ?: ResourceImage.Resource.entries[0]
                ResourceImage(resource)
            }
        }
    }

    private fun getSource(image: DoerImage): LocalNoteImage.Source {
        return when (image) {
            is NetworkImage -> LocalNoteImage.Source.NETWORK
            is ResourceImage -> LocalNoteImage.Source.LOCAL
        }
    }

    private fun getIdentifier(image: DoerImage): String {
        return when (image) {
            is NetworkImage -> image.url
            is ResourceImage -> image.res.hashCode().toString()
        }
    }
}
