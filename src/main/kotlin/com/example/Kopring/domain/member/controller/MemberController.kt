package com.example.Kopring.domain.member.controller

import com.example.Kopring.domain.member.dto.MemberLoginDto
import com.example.Kopring.domain.member.dto.MemberRequestDto
import com.example.Kopring.domain.member.dto.MemberResponseDto
import com.example.Kopring.domain.member.service.MemberService
import com.example.Kopring.global.authority.TokenInfo
import com.example.Kopring.global.config.CustomUser
import com.example.Kopring.global.response.BaseResponse
import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/member")
@RestController
class MemberController (
        private val memberService: MemberService
){
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid memberRequestDto: MemberRequestDto): BaseResponse<Unit>{
        val resultMsg: String = memberService.signup(memberRequestDto)
        return BaseResponse(message = resultMsg)
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid memberLoginDto: MemberLoginDto): BaseResponse<TokenInfo>{
        val tokenInfo = memberService.login(memberLoginDto)
        return BaseResponse(data = tokenInfo)
    }

    @GetMapping("/info")
    fun getMyInfo():BaseResponse<MemberResponseDto>{
        //contextHolder에 저장된 userId 추출
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId

        val response = memberService.getMyInfo(userId)
        return BaseResponse(data = response)
    }

    @PutMapping("/info")
    fun saveMyInfo(@RequestBody memberRequestDto: MemberRequestDto): BaseResponse<Unit>{
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        memberRequestDto.id = userId
        val resultMsg : String = memberService.saveMyInfo(memberRequestDto)
        return BaseResponse(message = resultMsg)
    }
}