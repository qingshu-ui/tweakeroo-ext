package com.github.shu.tweakerooExt.utils

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PostInit(
    val dependencies: Array<KClass<*>> = [],
)
