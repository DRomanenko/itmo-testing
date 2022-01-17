package com.github.dromanenko.backend.base

import io.qameta.allure.Allure
import io.qameta.allure.model.Status
import io.qameta.allure.model.StatusDetails
import io.qameta.allure.model.StepResult

object AllureHelper {
    fun <T> step(name: String, description: String = "", action: () -> T): T {
        var result: T? = null
        val id = name.lowercase().replace("\\s+".toRegex(), "-")
        val stepRes = StepResult().withName(name).withDescription(description)
        Allure.getLifecycle().startStep(id, stepRes)
        Allure.getLifecycle().updateStep(id) {
            try {
                result = action()
            } catch (e: Exception) {
                val details = StatusDetails().withMessage(e.message).withTrace(e.stackTraceToString())
                it.withStatus(Status.FAILED).withStatusDetails(details)
                throw e
            }
            it.withStatus(Status.PASSED)
        }
        Allure.getLifecycle().stopStep(id)
        return result!!
    }
}