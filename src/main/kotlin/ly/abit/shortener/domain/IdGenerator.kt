package ly.abit.shortener.domain

import de.mkammerer.snowflakeid.SnowflakeIdGenerator
import java.util.*


import java.math.BigInteger

class IdGenerator {
    private val generatorId = 0
    private val generator: SnowflakeIdGenerator = SnowflakeIdGenerator.createDefault(generatorId)
    private val base62Chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"

    fun generateUniqueId(): ShortId {
        val snowflakeId = generator.next()
        val base62Id = encodeBase62(snowflakeId)

        return ShortId(base62Id)
    }

    private fun encodeBase62(number: Long): String {
        var num = BigInteger.valueOf(number)
        val base = BigInteger.valueOf(62)
        val encoded = StringBuilder()

        while (num > BigInteger.ZERO) {
            val remainder = num.mod(base).toInt()
            encoded.insert(0, base62Chars[remainder])
            num = num.divide(base)
        }

        return encoded.toString()
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