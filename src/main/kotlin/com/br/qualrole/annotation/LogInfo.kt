package com.br.qualrole.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LogInfo(
    val logParameters: Boolean = false,
    val logResult: Boolean = true
)