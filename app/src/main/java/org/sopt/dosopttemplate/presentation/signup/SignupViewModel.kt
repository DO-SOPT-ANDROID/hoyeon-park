package org.sopt.dosopttemplate.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.ServicePool
import org.sopt.dosopttemplate.data.auth.RequestSignUpDto
import org.sopt.dosopttemplate.data.model.SignupState
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {
    private val idPattern = Pattern.compile("^[a-zA-Z0-9]{6,10}$")
    private val passwordPattern =
        Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$")

    private val _isSignUpButtonEnabled = MutableStateFlow(false)
    val isSignUpButtonEnabled: StateFlow<Boolean> = _isSignUpButtonEnabled

    private val _idErrorMessage = MutableStateFlow<String?>(null)
    val idErrorMessage: StateFlow<String?> = _idErrorMessage

    private val _passwordErrorMessage = MutableStateFlow<String?>(null)
    val passwordErrorMessage: StateFlow<String?> = _passwordErrorMessage

    private val _signUpStatus = MutableStateFlow<SignupState?>(null)
    val signUpStatus = _signUpStatus.asStateFlow()

    fun validateId(id: String) {
        _idErrorMessage.value =
            if (idPattern.matcher(id).matches()) null else "ID는 영문, 숫자를 포함하여 6~10글자여야 합니다."
        updateButtonState()
    }

    fun validatePassword(password: String) {
        _passwordErrorMessage.value =
            if (passwordPattern.matcher(password).matches()) {
                null
            } else {
                "비밀번호는 영문, 숫자, 특수문자를 포함하여 6~12글자여야 합니다."
            }
        updateButtonState()
    }

    fun signup(username: String, password: String, nickname: String) {
        viewModelScope.launch {
            runCatching {
                ServicePool.authService.signup(RequestSignUpDto(username, password, nickname))
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    _signUpStatus.value = SignupState.Success
                } else {
                    _signUpStatus.value = SignupState.Error
                }
            }.onFailure {
                _signUpStatus.value = SignupState.Error
            }
        }
    }

    private fun updateButtonState() {
        viewModelScope.launch {
            val isIdValid = _idErrorMessage.value == null
            val isPwValid = _passwordErrorMessage.value == null
            _isSignUpButtonEnabled.value = isIdValid && isPwValid
        }
    }
}
