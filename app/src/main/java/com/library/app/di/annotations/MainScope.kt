package com.library.app.di.annotations

import javax.inject.Scope

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 * Used on Instances which should be active when MainActivity is active
 */
@Scope
@kotlin.annotation.Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class MainScope