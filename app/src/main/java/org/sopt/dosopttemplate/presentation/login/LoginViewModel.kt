package org.sopt.dosopttemplate.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.ServicePool.authService
import org.sopt.dosopttemplate.data.auth.RequestLoginDto
import org.sopt.dosopttemplate.data.model.LoginState

class LoginViewModel : ViewModel() {

    // UI State
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Loading)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(id: String, password: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authService.login(RequestLoginDto(id, password))
            }.onSuccess {
                _loginState.value = LoginState.Success(it.body()!!)
            }.onFailure {
                _loginState.value = LoginState.Error
            }
        }
    }
}
