package com.example.Kopring.global.response

import com.example.Kopring.global.enums.ResultCode

data class BaseResponse<T> (
    val resultCode: String = ResultCode.SUCCESS.name,
    val data: T? = null,
    val message: String = ResultCode.SUCCESS.msg
    )