package com.br.qualrole.config

import com.br.qualrole.annotation.LogInfo
import mu.KLogger
import mu.KLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class LogConfig(val log: KLogger = KLogging().logger) {

    @Pointcut("execution(public * com.br.qualrole..*(..)) && @annotation(com.br.qualrole.annotation.LogInfo)")
    fun loggableMethods() {
    }

    @Around("loggableMethods() && @annotation(logInfo)")
    fun logMethodExecution(joinPoint: ProceedingJoinPoint, logInfo: LogInfo): Any? {
        val className = joinPoint.signature.declaringTypeName.split(".").last()
        val methodName = joinPoint.signature.name
        val parameters = joinPoint.args.joinToString(", ") { it.toString() }

        val message = "c=$className m=$methodName"

        if (logInfo.logParameters) log.info { "$message p=$parameters" }
        else log.info { message }

        val result = joinPoint.proceed()

        if (logInfo.logResult) log.info { "$message r=$result" }

        return result
    }
}