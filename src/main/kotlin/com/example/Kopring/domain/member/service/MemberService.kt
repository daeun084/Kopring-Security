package com.example.Kopring.domain.member.service

import com.example.Kopring.domain.member.dto.MemberRequestDto
import com.example.Kopring.domain.member.entity.Member
import com.example.Kopring.domain.member.entity.MemberRole
import com.example.Kopring.domain.member.repository.MemberRepository
import com.example.Kopring.domain.member.repository.MemberRoleRepository
import com.example.Kopring.global.enums.Role
import com.example.Kopring.global.exception.InvalidInputException
import org.springframework.stereotype.Service

@Service
class MemberService (
        private val memberRepository: MemberRepository,
        private val memberRoleRepository: MemberRoleRepository
) {
    fun signup(memberRequestDto: MemberRequestDto): String{
        //id 중복 검사
        var member: Member? = memberRepository.findByLoginId(memberRequestDto.loginId)
        if(member != null)
            throw InvalidInputException("이미 등록된 ID입니다.")

        //member 저장
        member = memberRequestDto.toEntity()
        memberRepository.save(member)

        //member role 저장
        val memberRole: MemberRole = MemberRole(null, Role.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }
}