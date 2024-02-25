package com.example.Kopring.domain.member.controller

import com.example.Kopring.domain.member.dto.MemberLoginDto
import com.example.Kopring.domain.member.dto.MemberRequestDto
import com.example.Kopring.domain.member.dto.MemberResponseDto
import com.example.Kopring.domain.member.service.MemberService
import com.example.Kopring.global.authority.TokenInfo
import com.example.Kopring.global.response.BaseResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
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

    @GetMapping("/info/{id}")
    fun getMyInfo(@PathVariable id: Long):BaseResponse<MemberResponseDto>{
        val response = memberService.getMyInfo(id)
        return BaseResponse(data = response)
    }
}