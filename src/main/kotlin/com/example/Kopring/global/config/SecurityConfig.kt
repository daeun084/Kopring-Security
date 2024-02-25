package com.example.Kopring.global.config

import com.example.Kopring.global.authority.JwtAuthenticationFilter
import com.example.Kopring.global.authority.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig (
        private val jwtTokenProvider: JwtTokenProvider
){
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain{
        http
                .httpBasic{it.disable()}
                .csrf{it.disable()}
                .sessionManagement{it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)} //세션 사용 X
                .authorizeHttpRequests{
                    it.requestMatchers("/api/member/signup").anonymous()
                            .anyRequest().permitAll()
                    //해당 api를 호출하는 사람은 인증되지 않은 사람이어야 하며
                    //그 외의 요청은 권한 없이 모두가 요청 가능함을 명시
                }
                .addFilterBefore( //앞에 있는 필터가 뒤에 있는 필터보다 먼저 실행되어야 함을 명시
                        JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter::class.java
                )
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()

}