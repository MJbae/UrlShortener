package ly.abit.shortener.controller

import jakarta.servlet.http.HttpServletResponse
import ly.abit.shortener.ShortenUrlService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/r")
class RedirectController(
    private val service: ShortenUrlService
) {
    @GetMapping("/{shortId}")
    fun redirectToOriginalUrl(@PathVariable shortId: String, response: HttpServletResponse) {
        val originalUrl = service.findOriginalLink(shortId)
        response.sendRedirect(originalUrl)
    }
}