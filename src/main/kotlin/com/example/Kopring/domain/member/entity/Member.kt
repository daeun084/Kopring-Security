package com.example.Kopring.domain.member.entity

import com.example.Kopring.domain.member.dto.MemberResponseDto
import com.example.Kopring.global.enums.Gender
import com.example.Kopring.global.enums.Role
import jakarta.persistence.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
@Table(
        uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
)
class Member(
        @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

        @Column(nullable = false, length = 30, updatable = false)
    var loginId: String,

        @Column(nullable = false, length = 100)
    val password: String,

        @Column(nullable = false, length = 10)
    val name: String,

        @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val birthDate: LocalDate,

        @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    val gender: Gender,

        @Column(nullable = false, length = 30)
    val email: String,
){
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val memberRole: List<MemberRole>? = null

    fun toDto(): MemberResponseDto =
            MemberResponseDto(id!!, loginId, name, birthDate.formatDate(), gender.desc, email)

    private fun LocalDate.formatDate(): String =
            this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

}

@Entity
class MemberRole(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @Column(nullable = false, length = 30)
        @Enumerated(EnumType.STRING)
        var role: Role, //권한

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(foreignKey = ForeignKey(name = "fk_member_role_member_id"))
        var member: Member
)

