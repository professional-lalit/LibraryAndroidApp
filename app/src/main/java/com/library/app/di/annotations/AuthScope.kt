package com.library.app.di.annotations

/**
 * Created by Lalit N. Hajare, Software Engineer on 3/6/20
 * Used on Instances which should be active while on boarding process is active
 * like 'Signup', 'Login', 'Forgot Password'
 */
@kotlin.annotation.Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class AuthScope