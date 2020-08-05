package com.library.app.di.annotations

import javax.inject.Scope

/**
 * Created by Lalit Hajare, Software Engineer on 16/6/20
 */
@Scope
@kotlin.annotation.Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class SignupScope