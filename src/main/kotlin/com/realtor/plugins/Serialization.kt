package com.realtor.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

@OptIn(KtorExperimentalLocationsAPI::class)
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(
            json = Json {
                prettyPrint = true
            }
        )
    }
    install(Locations) {

    }
}
