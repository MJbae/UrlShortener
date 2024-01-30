package ly.abit.shortener

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import ly.abit.shortener.controller.ShortenUrlRequest
import ly.abit.shortener.controller.ShortenUrlResponse
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShortUrlIntegrationTest(
    private val testClient: WebTestClient,
    private val repository: ShortenUrlRepository
) : FunSpec({

    beforeTest {
        repository.deleteAll()
    }

    test("단축 URL 생성에 따라 반환 받은 shortId로 원본 URL을 조회할 수 있다") {
        val originalUrl = "https://example.com"
        val createResponse = testClient.post()
            .uri("/short-links")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ShortenUrlRequest(originalUrl))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(ShortenUrlResponse::class.java)
            .returnResult().responseBody!!

        val findResponse = testClient.get()
            .uri("/short-links/${createResponse.data.shortId}")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(ShortenUrlResponse::class.java)
            .returnResult().responseBody!!

        findResponse.data.url shouldBe originalUrl
    }

    test("동일한 URL로 여러 번 단축 URL을 생성하면 항상 같은 shortId를 반환 받는다") {
        val originalUrl = "https://example.com"
        val fistResponse = testClient.post()
            .uri("/short-links")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ShortenUrlRequest(originalUrl))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(ShortenUrlResponse::class.java)
            .returnResult().responseBody!!
        val secondResponse = testClient.post()
            .uri("/short-links")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ShortenUrlRequest(originalUrl))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(ShortenUrlResponse::class.java)
            .returnResult().responseBody!!
        val thirdResponse = testClient.post()
            .uri("/short-links")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ShortenUrlRequest(originalUrl))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(ShortenUrlResponse::class.java)
            .returnResult().responseBody!!

        fistResponse.data.shortId shouldBe secondResponse.data.shortId
        secondResponse.data.shortId shouldBe thirdResponse.data.shortId
    }

    test("URL 형식에 맞지 않는 URL의 경우 400 Bad Request 상태 코드를 반환한다") {
        val invalidUrl = "invalid-url"
        testClient.post()
            .uri("/short-links")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ShortenUrlRequest(invalidUrl))
            .exchange()
            .expectStatus().isBadRequest
    }

    test("존재하지 않는 shortId로 단축 url을 조회하면 404 Not Found 상태 코드를 반환한다") {
        val nonExistentShortId = "nonexistent"
        testClient.get()
            .uri("/short-links/$nonExistentShortId")
            .exchange()
            .expectStatus().isNotFound
    }

    test("빈 URL로 단축 URL 생성을 요청하면 400 Bad Request 상태 코드를 반환한다") {
        val emptyUrl = ""
        testClient.post()
            .uri("/short-links")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ShortenUrlRequest(emptyUrl))
            .exchange()
            .expectStatus().isBadRequest
    }
})
