package com.example.Kopring.global.authority

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean

class JwtAuthenticationFilter(
        private val jwtTokenProvider: JwtTokenProvider
): GenericFilterBean() {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        //token 정보 추출 (accessToken)
        val token = resolveToken(request as HttpServletRequest)

        //token이 유효하다면
        if(token != null && jwtTokenProvider.validateToken(token)){
            //토큰에 있는 내부 정보 추출
            val authentication = jwtTokenProvider.getAuthentication(token)
            //security context holder에 정보 기록
            SecurityContextHolder.getContext().authentication = authentication
        }

        chain?.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String?{
        //request에 있는 header 문자열 추출
        val bearerToken = request.getHeader("Authorization")

        //해당 request가 bearer token이 맞다면
        return if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            //header 뒤에 있는 키값 추출
            bearerToken.substring(7)
        } else {
            null
        }
    }
}