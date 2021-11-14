package com.github.dromanenko.backend.interceptor

import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HeadersInterceptor(private val allowedHosts: String) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        fun setHeaderIfNotExist(header: String, value: String) {
            if (!response.containsHeader(header))
                response.addHeader(header, value)
        }
        setHeaderIfNotExist("Access-Control-Allow-Origin", allowedHosts)
        setHeaderIfNotExist("Access-Control-Allow-Credentials", "true")
        setHeaderIfNotExist("Cache-Control", "no-store")
        setHeaderIfNotExist("Access-Control-Allow-Methods", "GET,POST")
        setHeaderIfNotExist("Access-Control-Allow-Headers", "Content-Type")
        return true
    }
}