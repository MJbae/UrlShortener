package ly.abit.shortener.domain

import jakarta.persistence.Embeddable
import java.net.URI

@Embeddable
data class OriginalUrl(
    private val url: String
) {
    init {
        validate(url)
    }

    private fun validate(url: String) {
        try {
            URI(url).toURL()
        } catch (e: Exception) {
            throw IllegalArgumentException("url 형식이 올바르지 않습니다, url: $url", e)
        }
    }

    override fun toString(): String {
        return url
    }
}
