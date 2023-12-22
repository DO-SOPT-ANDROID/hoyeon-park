package org.sopt.dosopttemplate.data.model

sealed class SignupState {
    object Loading : SignupState()
    object Success : SignupState()
    object Error : SignupState()
}
