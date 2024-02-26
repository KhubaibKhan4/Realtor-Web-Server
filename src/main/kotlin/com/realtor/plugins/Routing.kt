package com.realtor.plugins

import com.realtor.API_VERSION
import com.realtor.plugins.repository.CategoriesRepository
import com.realtor.plugins.repository.DatabaseFactory
import com.realtor.plugins.repository.HousesRepository
import com.realtor.plugins.routes.category
import com.realtor.plugins.routes.houses
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    DatabaseFactory.init()
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    val db = CategoriesRepository()
    val housesDb = HousesRepository()
    routing {
        category(db)
        houses(housesDb)
    }
}
