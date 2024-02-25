package com.example.Kopring.domain.member.dto

import com.example.Kopring.domain.member.entity.Member
import com.example.Kopring.global.annotation.ValidEnum
import com.example.Kopring.global.enums.Gender
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class MemberRequestDto(
        var id: Long?,

        @field:NotBlank
        @JsonProperty("loginId")
        private val _loginId: String?,

        @field:NotBlank
        @field:Pattern(
                regexp="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
                message = " , , 8~20 " )
        @JsonProperty("password")
        private val _password: String?,

        @field:NotBlank
        @JsonProperty("name")
        private val _name: String?,

        @field:NotBlank
        @field:Pattern(
                regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
                message = " (YYYY-MM-DD) ")
        @JsonProperty("birthDate")
        private val _birthDate: String?, //LocalDate

        @field:NotBlank
        @field:ValidEnum(enumClass = Gender::class,
                message = "MAN WOMAN ")
        @JsonProperty("gender")
        private val _gender: String?, //Gender

        @field:NotBlank
        @field:Email
        @JsonProperty("email")
        private val _email: String?
){
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
    val name: String
        get() = _name!!
    val birthDate: LocalDate
        get() = _birthDate!!.toLocalDate()
    val gender: Gender
        get() = Gender.valueOf(_gender!!)
    val email: String
        get() = _email!!

    private fun String.toLocalDate(): LocalDate =
            LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    fun toEntity(): Member =
            Member(id, loginId, password, name, birthDate, gender, email)
}

