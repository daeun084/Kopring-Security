package com.example.Kopring.domain.member.dto

data class MemberResponseDto(
        val id: Long,
        val loginId: String,
        val name: String,
        val birthDate: String,
        val gender: String,
        val email: String,
)
