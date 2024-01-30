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
        URI(url).toURL()
    }

    override fun toString(): String {
        return url
    }
}
