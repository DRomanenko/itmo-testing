package com.github.dromanenko.selenide.utils

import com.codeborne.selenide.Configuration
import org.junit.jupiter.api.DisplayNameGenerator
import java.lang.reflect.Method

class BrowserDisplayNameGenerator : DisplayNameGenerator.Standard() {
    override fun generateDisplayNameForMethod(testClass: Class<*>?, testMethod: Method?) =
        "${super.generateDisplayNameForMethod(testClass, testMethod).removeSuffix("()")} (${Configuration.browser})"
}