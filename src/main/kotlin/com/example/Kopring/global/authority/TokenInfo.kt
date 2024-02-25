package com.example.Kopring.global.authority

data class TokenInfo (
        //jwt 권한 인증 타입
    val grantType: String,
    val accessToken: String
)