package com.example.Kopring.global.config

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUser(
        //userDetails의 user를 상속받는 custom User 생성
        val userId: Long,
        userName: String,
        password: String,
        authorities: Collection<GrantedAuthority>
) : User(userName, password, authorities)
