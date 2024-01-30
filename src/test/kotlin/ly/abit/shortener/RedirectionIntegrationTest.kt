package ly.abit.shortener

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedirectionIntegrationTest(
    private val testClient: WebTestClient,
    private val repository: ShortenUrlRepository
) : FunSpec({

    beforeTest {
        repository.deleteAll()
    }

    test("원본 URL 리다이렉션의 응답의 상태코드는 302 Found 이다") {
        val originalUrl = "https://example.com"
        val createResponse = testClient.post()
            .uri("/short-links")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ShortenUrlRequest(originalUrl))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(ShortenUrlResponse::class.java)
            .returnResult().responseBody!!

        testClient.get()
            .uri("/r/${createResponse.data.shortId}")
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.FOUND)
    }

    test("원본 URL 리다이렉션의 응답 헤더의 Location 필드에 원본 URL이 지정된다") {
        val originalUrl = "https://example.com"
        val createResponse = testClient.post()
            .uri("/short-links")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ShortenUrlRequest(originalUrl))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(ShortenUrlResponse::class.java)
            .returnResult().responseBody!!

        testClient.get()
            .uri("/r/${createResponse.data.shortId}")
            .exchange()
            .expectHeader().valueEquals("Location", originalUrl)
    }
})
