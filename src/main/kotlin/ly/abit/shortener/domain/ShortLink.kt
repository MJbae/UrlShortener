package ly.abit.shortener.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class ShortLink(
    @Embedded
    @AttributeOverride(name = "url", column = Column(name = "original_url", unique = true))
    private val url: OriginalUrl
) {
    @Id
    @Embedded
    @AttributeOverride(name = "id", column = Column(name = "short_id", unique = true, length = 50))
    private val shortId: ShortId = IdGenerator().generateUniqueId()

    @Version
    private var version: Int = 0

    val createdAt: LocalDateTime = LocalDateTime.now()

    fun originalUrl(): String{
        return url.toString()
    }

    fun id(): String{
        return shortId.toString()
    }
}
