package com.realtor.plugins

import com.realtor.plugins.repository.CategoriesRepository
import com.realtor.plugins.repository.DatabaseFactory
import com.realtor.plugins.routes.category
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
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
