package ly.abit.shortener.domain

import jakarta.persistence.Embeddable

@Embeddable
data class ShortId(
    private val id: String
) {
    init {
        validate(id)
    }

    private fun validate(id: String) {
        if (id.length < 3) {
            throw IllegalStateException("id는 세 자리 이상이어야 한다")
        }
    }

    override fun toString(): String {
        return id
    }
}
