package ly.abit.shortener.domain

import jakarta.persistence.*
import java.net.URI
import java.time.LocalDateTime

@Entity
class ShortLink(url: String) {

    val url: String = validateUrl(url)

    @Id
    @Embedded
    @AttributeOverride(name = "id", column = Column(name = "shortId", unique = true))
    val shortId: ShortId = IdGenerator().generateUniqueId()

    val createdAt: LocalDateTime = LocalDateTime.now()

    private fun validateUrl(url: String): String {
        URI(url).toURL()
        return url
    }
}
