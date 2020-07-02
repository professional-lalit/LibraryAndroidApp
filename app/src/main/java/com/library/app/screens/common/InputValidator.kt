package com.library.app.screens.common

import com.library.app.common.Constants
import java.util.regex.Pattern

/**
 * Created by Lalit Hajare, Software Engineer on 2/7/20
 */
abstract class InputValidator {

    protected val EMAIL_PATTERN: Pattern = Pattern.compile(Constants.emailPattern)
    protected val INVALID_PASSWORD_PATTERN: Pattern = Pattern.compile(Constants.invalidPwdPattern)

    enum class ValidationCode {
        EMPTY_NAME_ERROR, FULL_NAME_ERROR, EMPTY_EMAIL_ERROR, INVALID_EMAIL_ERROR, EMPTY_PASSWORD_ERROR,
        INVALID_PASSWORD_LENGTH_ERROR, SPCHR_PASSWORD_ERROR, UNMATCHING_PASSWORDS_ERROR,
        NEW_PASSWORD_EMPTY, NEW_PASSWORD_LENGTH_ERROR, NEW_PASSWORD_SPCHR_ERROR,
        VALID
    }

    protected fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && EMAIL_PATTERN.matcher(email).matches()
    }

    protected fun hasSpecialChar(password: String): Boolean {
        return password.isNotEmpty() && !INVALID_PASSWORD_PATTERN.matcher(password).matches()
    }

    protected var name: String = ""
    protected var email: String = ""
    protected var password: String = ""
    protected var confirmedPassword: String = ""
    protected var oldPassword: String = ""
    protected var newPassword: String = ""

    class SchemeNotSetException : Exception() {
        override val message: String?
            get() = "scheme not set, you should call scheme() to set scheme before build()"
    }

    class Builder {
        private var mValidator: InputValidator? = null
        private var name: String = ""
        private var email: String = ""
        private var scheme: ValidatorFactory.ValidationScheme? = null
        private var password: String = ""
        private var confirmedPassword: String = ""
        private var oldPassword: String = ""
        private var newPassword: String = ""

        fun name(name: String): Builder {
            this.name = name
            return this
        }

        fun email(email: String): Builder {
            this.email = email
            return this
        }

        fun password(password: String): Builder {
            this.password = password
            return this
        }

        fun confirmedPassword(confirmedPassword: String): Builder {
            this.confirmedPassword = confirmedPassword
            return this
        }

        fun oldPassword(oldPasswpord: String): Builder {
            this.oldPassword = oldPasswpord
            return this
        }

        fun newPassword(newPasswpord: String): Builder {
            this.newPassword = newPasswpord
            return this
        }

        fun scheme(scheme: ValidatorFactory.ValidationScheme): Builder {
            this.scheme = scheme
            return this
        }

        fun build(): InputValidator {
            if (this.scheme == null) {
                throw SchemeNotSetException()
            } else {
                mValidator = ValidatorFactory().getValidator(this.scheme!!)
                mValidator!!.name = this.name
                mValidator!!.email = this.email
                mValidator!!.password = this.password
                mValidator!!.confirmedPassword = this.confirmedPassword
                mValidator!!.newPassword = this.newPassword
                mValidator!!.oldPassword = this.oldPassword
                return mValidator!!
            }
        }
    }

    abstract fun isValid(): ValidationCode
}