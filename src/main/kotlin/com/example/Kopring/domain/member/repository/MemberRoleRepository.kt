package com.example.Kopring.domain.member.repository

import com.example.Kopring.domain.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRoleRepository : JpaRepository<MemberRole, Long>{
}