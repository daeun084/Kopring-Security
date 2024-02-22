package com.example.Kopring.domain.member.service

import com.example.Kopring.domain.member.dto.MemberRequestDto
import com.example.Kopring.domain.member.entity.Member
import com.example.Kopring.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService (
        private val memberRepository: MemberRepository
) {
    fun signup(memberRequestDto: MemberRequestDto): String{
        //id 중복 검사
        var member: Member? = memberRepository.findByLoginId(memberRequestDto.loginId)
        if(member != null)
            return "이미 등록된 ID입니다."

        member = Member(
                null,
                memberRequestDto.loginId,
                memberRequestDto.password,
                memberRequestDto.name,
                memberRequestDto.birthDate,
                memberRequestDto.gender,
                memberRequestDto.email
        )

        memberRepository.save(member)
        return "회원가입이 완료되었습니다."
    }
}