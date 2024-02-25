package com.example.Kopring.global.authority

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Date

//30분 인증 유효 시간
const val EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 30
@Component
class JwtTokenProvider {
    @Value("\${jwt.secret}")
    lateinit var secretKey: String

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    //토큰 생성
    fun createToken(authentication: Authentication): TokenInfo{
        //authentication 내부에 든 권한들을 ","을 기준으로 나누어 string으로 저장
        val authorities: String = authentication
                .authorities
                .joinToString(",", transform = GrantedAuthority::getAuthority)

        val now = Date()
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS)

        //access token
        val accessToken = Jwts
                .builder()
                .setSubject(authentication.name)
                .claim("auth", authorities) //"auth"라는 이름으로 권한들을 토큰에 담아줌
                .setIssuedAt(now) //발행된 시간
                .setExpiration(accessExpiration) //유효기간
                .signWith(key, SignatureAlgorithm.HS256) //키 생성에 사용한 알고리즘 명시
                .compact()

        return TokenInfo("Bearer", accessToken)
    }


    //토큰 정보 추출
    fun getAuthentication(token: String) : Authentication { //access token
        //claim 추출
        val claims : Claims = getClaims(token)

        //claim 내부에 있는 string type의 authorities 추출
        val auth = claims["auth"] ?: throw RuntimeException("잘못된 토큰입니다.")

        //권한 정보 추출
        val authorities: Collection<GrantedAuthority> = (auth as String) //string type으로 캐스팅
                .split(",")
                .map{ SimpleGrantedAuthority(it)} //","을 기준으로 잘라 Collection에 담아줌

        val principal : UserDetails = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }


    //토큰 검증
    fun validateToken(token: String): Boolean {
        try {
            getClaims(token)
            return true
        } catch (e: Exception) {
            when (e) {
                is SecurityException -> {}  // 잘못된 JWT Token
                is MalformedJwtException -> {}  // 잘못된 JWT Token
                is ExpiredJwtException -> {}    // 만료된 JWT Token
                is UnsupportedJwtException -> {}
                is IllegalArgumentException -> {}
                else -> {}  // else
            }
            println(e.message)
        }
        return false
    }

    private fun getClaims(token: String): Claims =
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .body

}