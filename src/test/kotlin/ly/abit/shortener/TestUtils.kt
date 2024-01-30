package ly.abit.shortener

import ly.abit.shortener.controller.ShortenUrlRequest
import ly.abit.shortener.controller.ShortenUrlResponse
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient


fun WebTestClient.postShortLink(url: String): ShortenUrlResponse {
    return this.post()
        .uri("/short-links")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(ShortenUrlRequest(url))
        .exchange()
        .expectStatus().is2xxSuccessful
        .expectBody(ShortenUrlResponse::class.java)
        .returnResult().responseBody!!
}

fun WebTestClient.getShortLink(shortId: String): ShortenUrlResponse {
    return this.get()
        .uri("/short-links/$shortId")
        .exchange()
        .expectStatus().is2xxSuccessful
        .expectBody(ShortenUrlResponse::class.java)
        .returnResult().responseBody!!
}