import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {
    private val idPattern = Pattern.compile("^[a-zA-Z0-9]{6,10}$")
    private val passwordPattern =
        Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$")

    val isSignUpButtonEnabled = MutableLiveData<Boolean>(false)
    val idErrorMessage = MutableLiveData<String?>(null)
    val passwordErrorMessage = MutableLiveData<String?>(null)

    fun validateId(id: String) {
        idErrorMessage.value =
            if (idPattern.matcher(id).matches()) null else "ID는 영문, 숫자를 포함하여 6~10글자여야 합니다."
        updateButtonState()
    }

    fun validatePassword(password: String) {
        passwordErrorMessage.value =
            if (passwordPattern.matcher(password)
                    .matches()
            ) {
                null
            } else {
                "비밀번호는 영문, 숫자, 특수문자를 포함하여 6~12글자여야 합니다."
            }
        updateButtonState()
    }

    private fun updateButtonState() {
        val isIdValid = idErrorMessage.value == null
        val isPwValid = passwordErrorMessage.value == null
        isSignUpButtonEnabled.value = isIdValid && isPwValid
    }
}
