package ly.abit.shortener

import io.kotest.core.spec.style.FunSpec
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldNotThrow
import ly.abit.shortener.domain.ShortId

class ShortIdTest : FunSpec({

    test("세 자리 이상의 ID로만 ShortId 인스턴스를 생성할 수 있다") {
        val validId = "123"

        shouldNotThrow<IllegalStateException> {
            ShortId(validId)
        }
    }

    test("두 자리 이하의 ID로는 ShortId 인스턴스를 생성할 수 없다") {
        val invalidId = "12"

        shouldThrow<IllegalStateException> {
            ShortId(invalidId)
        }
    }

    test("빈 문자열로는 ShortId 인스턴스를 생성할 수 없다") {
        val emptyId = ""

        shouldThrow<IllegalStateException> {
            ShortId(emptyId)
        }
    }
})
