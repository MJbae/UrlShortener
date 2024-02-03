package ly.abit.shortener.domain

import de.mkammerer.snowflakeid.SnowflakeIdGenerator
import java.util.*


class IdGenerator {
    private val generatorId = 0
    private val generator: SnowflakeIdGenerator = SnowflakeIdGenerator.createDefault(generatorId)
    fun snowflake(): Long {
        return generator.next()
    }

    fun generateUniqueId(): ShortId {
        val id = UUID.randomUUID().toString()
            .replace("-", "")
            .substring(startIndex = 2, endIndex = 12)

        return ShortId(id)
    }
}

class UuidGenerator {
    fun snowflake(): Long {
        val generatorId = 0
        val generator: SnowflakeIdGenerator = SnowflakeIdGenerator.createDefault(generatorId)
        return generator.next()
    }

    fun v4(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }

    fun v3(name: String): String {
        return UUID.nameUUIDFromBytes(name.toByteArray()).toString().replace("-", "")
    }

//    fun v5(name: String): String {
//        throw UnsupportedOperationException("Direct version 5 UUID generation is not supported")
//    }
//
//    fun v2(): String {
//        throw UnsupportedOperationException("Direct version 2 UUID generation is not supported")
//    }
//
//    fun v1(): String {
//        throw UnsupportedOperationException("Direct version 1 UUID generation is not supported")
//    }
}