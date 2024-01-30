package ly.abit.shortener

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import ly.abit.shortener.domain.OriginalUrl

class OriginalUrlTest : FunSpec({

    test("올바른 URL 형식으로만 OriginalUrl 인스턴스를 생성할 수 있다") {
        val validUrl = "https://www.example.com"

        shouldNotThrow<IllegalArgumentException> {
            OriginalUrl(validUrl)
        }
    }

    test("빈 문자열의 URL로 OriginalUrl 인스턴스를 생성할 수 없다") {
        val invalidUrl = ""

        shouldThrow<IllegalArgumentException> {
            OriginalUrl(invalidUrl)
        }
    }

    test("잘못된 프로토콜 형식의 URL로 OriginalUrl 인스턴스를 생성할 수 없다") {
        val invalidUrl = "htp://example.com"

        shouldThrow<IllegalArgumentException> {
            OriginalUrl(invalidUrl)
        }
    }

    test("불환전한 형식으로 URL로 OriginalUrl 인스턴스를 생성할 수 없다") {
        val invalidUrl = "www.example"

        shouldThrow<IllegalArgumentException> {
            OriginalUrl(invalidUrl)
        }
    }
})