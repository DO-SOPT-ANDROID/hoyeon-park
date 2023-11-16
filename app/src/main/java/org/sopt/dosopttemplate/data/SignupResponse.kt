package org.sopt.dosopttemplate.data

sealed class SignupResponse {
    data class ResponseSignUpDto(
        val location: String
    )
}