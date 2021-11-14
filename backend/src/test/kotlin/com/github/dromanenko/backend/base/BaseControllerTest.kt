package com.github.dromanenko.backend.base

import com.github.dromanenko.backend.base.configuration.MockitoMockTestConfiguration
import com.github.dromanenko.backend.base.configuration.SpringMockTestConfiguration
import org.mockito.Mockito
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.context.annotation.Import
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl

@AutoConfigureRestDocs(outputDir = "target/snippets")
@Import(MockitoMockTestConfiguration::class, SpringMockTestConfiguration::class)
abstract class BaseControllerTest<T : Any> {
    @Autowired
    protected lateinit var service: T

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @AfterEach
    fun resetMocks() = Mockito.reset(service)

    protected fun ResultActionsDsl.createDocs() {
        this.andDo {
            handle(
                document(
                    "{ClassName}/{method_name}",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())
                )
            )
        }
    }
}