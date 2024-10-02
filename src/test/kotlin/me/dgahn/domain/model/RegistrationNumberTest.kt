package me.dgahn.domain.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RegistrationNumberTest {
    @Test
    fun `유효한 사업자등록번호는 RegistrationNumber 객체를 생성한다`() {
        // Given
        val validNumber = "123-45-67890"

        // When
        val registrationNumber = RegistrationNumber.from(validNumber)

        // Then
        validNumber shouldBe registrationNumber.value
    }

    @Test
    fun `유효하지 않은 사업자등록번호는 IllegalArgumentException 예외를 발생시킨다`() {
        // Given
        val invalidNumber = "1234567890"

        // When & Then
        shouldThrow<IllegalArgumentException> {
            RegistrationNumber.from(invalidNumber)
        }
    }

    @Test
    fun `길이가 올바르지 않은 사업자등록번호는 IllegalArgumentException 예외를 발생시킨다`() {
        // Given
        val shortNumber = "12-34-5678"
        val longNumber = "1234-56-78901"

        // When & Then
        shouldThrow<IllegalArgumentException> {
            RegistrationNumber.from(shortNumber)
        }

        shouldThrow<IllegalArgumentException> {
            RegistrationNumber.from(longNumber)
        }
    }

    @Test
    fun `잘못된 문자가 포함된 사업자등록번호는 IllegalArgumentException 예외를 발생시킨다`() {
        // Given
        val alphabetNumber = "abc-def-ghijk"

        // When & Then
        shouldThrow<IllegalArgumentException> {
            RegistrationNumber.from(alphabetNumber)
        }
    }
}
