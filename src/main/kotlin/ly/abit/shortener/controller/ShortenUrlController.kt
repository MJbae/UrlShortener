package ly.abit.shortener.controller

import ly.abit.shortener.ShortenUrlData
import ly.abit.shortener.ShortenUrlService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/short-links")
class ShortenUrlController(
    private val service: ShortenUrlService
) {
    @PostMapping
    fun create(@RequestBody request: ShortenUrlRequest): ShortenUrlResponse {
        val dto = service.shorten(request.url)
        return ShortenUrlResponse(dto)
    }

    @GetMapping("/{id}")
    fun find(@PathVariable id: String): ShortenUrlResponse {
        val dto = service.findLink(id)
        return ShortenUrlResponse(dto)
    }
}

data class ShortenUrlRequest(
    val url: String
)

data class ShortenUrlResponse(
    val data: ShortenUrlData
)

