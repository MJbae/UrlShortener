package ly.abit.shortener

import ly.abit.shortener.domain.OriginalUrl
import ly.abit.shortener.domain.ShortId
import ly.abit.shortener.domain.ShortLink
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ShortenUrlRepository : JpaRepository<ShortLink, ShortId> {
    fun findByUrl(url: OriginalUrl): Optional<ShortLink>
}
