package ly.abit.shortener

import ly.abit.shortener.domain.ShortId
import ly.abit.shortener.domain.ShortLink
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ShortenUrlService(
    private val repository: ShortenUrlRepository
) {
    fun shorten(url: String): ShortenUrlData {
        val shortLink = ShortLink(url = url)
        val saved = repository.save(shortLink)
        return ShortenUrlData.from(saved)
    }

    fun findLink(id: String): ShortenUrlData {
        val shortLink = repository.findById(ShortId(id)).orElseThrow {
            ClassNotFoundException("다음의 id로 short link 조회할 수 없습니다. id: $id")
        }

        return ShortenUrlData.from(shortLink)
    }
}

data class ShortenUrlData(
    val shortId: String,
    val url: String,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(shortLink: ShortLink): ShortenUrlData {
            return ShortenUrlData(shortLink.shortId.toString(), shortLink.url, shortLink.createdAt)
        }
    }
}