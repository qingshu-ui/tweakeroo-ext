package com.github.shu.tweakerooExt.utils

import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PreInit(
    val dependencies: Array<KClass<*>> = [],
)
