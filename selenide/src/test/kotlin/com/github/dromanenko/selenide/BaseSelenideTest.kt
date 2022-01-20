package com.github.dromanenko.selenide

import com.github.dromanenko.selenide.utils.BrowserDisplayNameGenerator
import io.qameta.allure.Allure
import io.qameta.allure.util.ResultsUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayNameGeneration

@DisplayNameGeneration(BrowserDisplayNameGenerator::class)
abstract class BaseSelenideTest {
    @BeforeEach
    fun setUpHistoryId() {
        Allure.getLifecycle().updateTestCase {
            it.historyId = ResultsUtils.md5(it.name)
        }
    }
}