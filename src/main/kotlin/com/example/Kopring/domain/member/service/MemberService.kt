package com.example.Kopring.domain.member.service

import com.example.Kopring.domain.member.dto.MemberLoginDto
import com.example.Kopring.domain.member.dto.MemberRequestDto
import com.example.Kopring.domain.member.dto.MemberResponseDto
import com.example.Kopring.domain.member.entity.Member
import com.example.Kopring.domain.member.entity.MemberRole
import com.example.Kopring.domain.member.repository.MemberRepository
import com.example.Kopring.domain.member.repository.MemberRoleRepository
import com.example.Kopring.global.authority.JwtTokenProvider
import com.example.Kopring.global.authority.TokenInfo
import com.example.Kopring.global.enums.Role
import com.example.Kopring.global.exception.InvalidInputException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Service
class MemberService (
        private val memberRepository: MemberRepository,
        private val memberRoleRepository: MemberRoleRepository,
        private val authenticationManagerBuilder: AuthenticationManagerBuilder,
        private val jwtTokenProvider: JwtTokenProvider
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

    fun login(memberLoginDto: MemberLoginDto): TokenInfo{
        val authenticationToken = UsernamePasswordAuthenticationToken(memberLoginDto.loginId, memberLoginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }

    fun getMyInfo(id: Long): MemberResponseDto {
        val member : Member = memberRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "존재하지 않는 유저입니다")
        return member.toDto()
    }
}