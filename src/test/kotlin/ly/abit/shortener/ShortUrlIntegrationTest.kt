package ly.abit.shortener

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
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

        val createResponse = testClient.postShortLink(originalUrl)
        val findResponse = testClient.getShortLink(createResponse.data.shortId)

        findResponse.data.url shouldBe originalUrl
    }

    test("동일한 URL로 여러 번 단축 URL을 생성하면 항상 같은 shortId를 반환 받는다") {
        val originalUrl = "https://example.com"

        val firstResponse = testClient.postShortLink(originalUrl)
        val secondResponse = testClient.postShortLink(originalUrl)
        val thirdResponse = testClient.postShortLink(originalUrl)

        firstResponse.data.shortId shouldBe secondResponse.data.shortId
        secondResponse.data.shortId shouldBe thirdResponse.data.shortId
    }

    test("존재하지 않는 shortId로 원본 URL을 조회할 수 없다") {
        val nonExistentShortId = "nonExistent"

        testClient.get()
            .uri("/short-links/$nonExistentShortId")
            .exchange()
            .expectStatus().isNotFound
    }
})
