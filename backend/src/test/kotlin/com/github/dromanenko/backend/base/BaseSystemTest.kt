package com.github.dromanenko.backend.base

import com.github.dromanenko.backend.response.RecipesResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.util.LinkedMultiValueMap
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Suppress("SameParameterValue")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [BaseSystemTest.Initializer::class])
abstract class BaseSystemTest {
    object Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        private lateinit var postgreSQLContainer: PostgreSQLContainer<Nothing>

        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:14.1")
                .apply {
                    withDatabaseName("integration-tests")
                    withExposedPorts(30009)
                    withUsername("admin")
                    withPassword("admin")
                }
            postgreSQLContainer.start()
            TestPropertyValues.of(
                "spring.datasource.url=" + postgreSQLContainer.jdbcUrl,
                "spring.datasource.username=" + postgreSQLContainer.username,
                "spring.datasource.password=" + postgreSQLContainer.password
            ).applyTo(configurableApplicationContext.environment)
        }
    }

    @LocalServerPort
    protected var port: Int = 0

    @Autowired
    protected lateinit var restTemplateBuilder: RestTemplateBuilder


    private inline fun <reified T, P> post(method: String, httpEntity: HttpEntity<P>) =
        restTemplateBuilder.build().postForEntity("http://localhost:$port/$method", httpEntity, T::class.java)

    private inline fun <reified T> get(
        method: String,
        cookie: String? = null,
        params: Map<String, String> = mapOf(),
    ) =
        restTemplateBuilder.build().exchange(
            "http://localhost:$port/$method?" + params.entries.joinToString("&") { it.key + "=" + it.value },
            HttpMethod.GET,
            HttpEntity<Any>(HttpHeaders().apply { if (cookie != null) set(HttpHeaders.COOKIE, cookie) }),
            T::class.java
        )

    protected fun login(login: String, password: String, redirectTo: String) =
        auth("login", login, password, redirectTo)

    protected fun register(login: String, password: String, redirectTo: String) =
        auth("register", login, password, redirectTo)

    protected fun unlogin(cookie: String) =
        post<String, Any>("unlogin", HttpEntity(
            HttpHeaders().apply {
                set(HttpHeaders.COOKIE, cookie)
            }
        ))

    protected fun auth(method: String, login: String, password: String, redirectTo: String) =
        post<String, LinkedMultiValueMap<String, String>>(
            method,
            HttpEntity(LinkedMultiValueMap<String, String>()
                .apply {
                    add("login", login)
                    add("password", password)
                    add("redirectTo", redirectTo)
                },
                HttpHeaders().apply { contentType = MediaType.APPLICATION_FORM_URLENCODED }
            )
        )

    protected fun currentUser(cookie: String? = null) = get<String>("current-user", cookie)

    protected fun addRecipe(name: String, description: String, cookie: String? = null) =
        post<String, Any>("add-recipe", HttpEntity(
            """
                {
                    "name":"$name",
                    "description":"$description"
                }
            """.trimIndent(),
            HttpHeaders().apply {
                if (cookie != null) set(HttpHeaders.COOKIE, cookie)
                contentType = MediaType.APPLICATION_JSON
            }
        ))

    protected fun getRecipes(user: String? = null, cookie: String? = null) =
        get<RecipesResponse>("recipes", cookie, if (user != null) mapOf("user" to user) else mapOf())
}