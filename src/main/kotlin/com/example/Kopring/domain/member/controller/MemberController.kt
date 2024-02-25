package com.example.Kopring.domain.member.controller

import com.example.Kopring.domain.member.dto.MemberRequestDto
import com.example.Kopring.domain.member.service.MemberService
import com.example.Kopring.global.response.BaseResponse
import jakarta.validation.Valid
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
}