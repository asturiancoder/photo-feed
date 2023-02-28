package com.asturiancoder.photofeed.util

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "UUID")
class Uuid {
    var uuidString: String = uuidGenerator.randomUuidString().uppercase()
        private set

    companion object {
        private val uuidGenerator = UuidGeneratorFactory.create()

        fun from(uuidString: String): Uuid? {
            if (uuidGenerator.isValidUuidValue(uuidString)) {
                return Uuid().apply { this.uuidString = uuidString.uppercase() }
            }

            return null
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Uuid) return false

        return this.uuidString.uppercase() == other.uuidString.uppercase()
    }

    override fun hashCode(): Int {
        return uuidString.hashCode()
    }
}

internal interface UuidGenerator {
    fun randomUuidString(): String
    fun isValidUuidValue(value: String): Boolean
}

internal expect object UuidGeneratorFactory {
    fun create(): UuidGenerator
}