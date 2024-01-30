package ly.abit.shortener

import ly.abit.shortener.domain.ShortId
import ly.abit.shortener.domain.ShortLink
import org.springframework.data.jpa.repository.JpaRepository

interface ShortenUrlRepository : JpaRepository<ShortLink, ShortId>