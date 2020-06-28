package com.library.app.screens.onboarding.login

import android.content.Context
import com.library.app.R
import com.library.app.common.Constants.emailPattern
import com.library.app.common.Constants.invalidPwdPattern
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Created by Lalit Hajare, Software Engineer on 3/6/20
 * This class validation the fields on Login screen
 * The verification class for this class is com.library.app.screens.onboarding.login.LoginValidationUsecaseTest
 */
class LoginValidationUsecase @Inject constructor(val mContext: Context) {

    private val EMAIL_PATTERN: Pattern = Pattern.compile(emailPattern)
    private val INVALID_PASSWORD_PATTERN: Pattern = Pattern.compile(invalidPwdPattern)

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && EMAIL_PATTERN.matcher(email).matches()
    }

    private fun hasSpecialChar(password: String): Boolean {
        return password.isNotEmpty() && !INVALID_PASSWORD_PATTERN.matcher(password).matches()
    }

    /**
     * Returns Boolean value whether the credentials are in proper format before sending to server
     * Also, invokes the callback function passed in this functions, returning appropriate message
     */
    fun validateCredentials(email: String, password: String, cb: (String) -> Unit): Boolean {
        if (email.isEmpty()) {
            cb.invoke(mContext.getString(R.string.plz_enter_email))
            return false
        } else if (!isValidEmail(email)) {
            cb.invoke(mContext.getString(R.string.plz_enter_valid_email))
            return false
        } else if (password.isEmpty()) {
            cb.invoke(mContext.getString(R.string.plz_enter_pwd))
            return false
        } else if (password.length < 6) {
            cb.invoke(mContext.getString(R.string.pwd_shd_contain_6_char))
            return false
        } else if (!hasSpecialChar(password)) {
            cb.invoke(mContext.getString(R.string.pwd_shd_have_1_sp_char))
            return false
        }
        return true
    }

}