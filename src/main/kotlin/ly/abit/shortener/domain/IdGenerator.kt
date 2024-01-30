package ly.abit.shortener.domain

import java.util.UUID


class IdGenerator {
    fun generateUniqueId(): ShortId {
        val id = UUID.randomUUID().toString().replace("-", "")
        return ShortId(id)
    }
}
