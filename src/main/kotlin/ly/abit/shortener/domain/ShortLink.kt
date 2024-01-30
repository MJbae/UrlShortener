package ly.abit.shortener.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class ShortLink(
    val url: String
){
    @Id
    @Embedded
    @AttributeOverride(name = "id", column = Column(name = "shortId", unique = true))
    val shortId: ShortId = IdGenerator().generateUniqueId()
    val createdAt: LocalDateTime = LocalDateTime.now()
}
