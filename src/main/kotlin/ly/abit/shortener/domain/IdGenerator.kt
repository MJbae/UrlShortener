package ly.abit.shortener.domain

import java.util.UUID


class IdGenerator {
    fun generateUniqueId(): ShortId {
        val id = UUID.randomUUID().toString()
            .replace("-", "")
            .substring(startIndex = 2, endIndex = 12)

        return ShortId(id)
    }
}
