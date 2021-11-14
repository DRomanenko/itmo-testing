package com.github.dromanenko.backend.configuration

import com.github.dromanenko.backend.interceptor.HeadersInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class HeadersConfiguration : WebMvcConfigurer {
    @Value("\${allowed.hosts}")
    private lateinit var allowedHosts: String

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(HeadersInterceptor(allowedHosts))
    }
}