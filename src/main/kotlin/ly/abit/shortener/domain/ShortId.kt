package ly.abit.shortener.domain

import jakarta.persistence.Embeddable

@Embeddable
data class ShortId(
    private val value: String
){
    override fun toString(): String {
        return value
    }
}
