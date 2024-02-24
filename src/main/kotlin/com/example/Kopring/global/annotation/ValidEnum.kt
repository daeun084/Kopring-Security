package com.example.Kopring.global.annotation

import com.fasterxml.jackson.databind.util.EnumValues
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [ValidEnumValidator::class]) //validator
annotation class ValidEnum(
        val message: String = "Invalid enum value",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = [],
        val enumClass: KClass<out Enum<*>>
)

class ValidEnumValidator : ConstraintValidator<ValidEnum, Any> {
    private lateinit var enumValues: Array<out Enum<*>>
    override fun initialize(annotation: ValidEnum) {
        enumValues = annotation.enumClass.java.enumConstants
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
        //value : 사용자로부터 들어오는 값
        if(value == null){
            return true
        }
        //any : {}안에 있는 조건 중 하나라도 만족하면 true 반환
        return enumValues.any{it.name == value.toString()}
    }
}