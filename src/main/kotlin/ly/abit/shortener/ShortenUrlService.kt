package ly.abit.shortener

import ly.abit.shortener.domain.ShortId
import ly.abit.shortener.domain.ShortLink
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ShortenUrlService(
    private val repository: ShortenUrlRepository
) {
    @Transactional
    fun shorten(url: String): ShortenUrlData {
        val shortLink = ShortLink(url = url)
        val saved = repository.save(shortLink)
        return ShortenUrlData.from(saved)
    }

    @Transactional(readOnly = true)
    fun findLink(id: String): ShortenUrlData {
        val shortLink = repository.findById(ShortId(id)).orElseThrow {
            ClassNotFoundException("다음의 id로 short link 조회할 수 없습니다. id: $id")
        }

        return ShortenUrlData.from(shortLink)
    }

    @Transactional(readOnly = true)
    fun findOriginalLink(id: String): String {
        val shortLink = repository.findById(ShortId(id)).orElseThrow {
            ClassNotFoundException("다음의 id로 short link 조회할 수 없습니다. id: $id")
        }

        return shortLink.originalUrl()
    }
}

data class ShortenUrlData(
    val shortId: String,
    val url: String,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(shortLink: ShortLink): ShortenUrlData {
            return ShortenUrlData(shortLink.id(), shortLink.originalUrl(), shortLink.createdAt)
        }
    }
}