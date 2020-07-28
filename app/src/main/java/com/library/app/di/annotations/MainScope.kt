package com.library.app.di.annotations

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 * Used on Instances which should be active when MainActivity is active
 */
@kotlin.annotation.Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class MainScope