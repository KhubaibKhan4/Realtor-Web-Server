package com.realtor.plugins

import com.realtor.plugins.repository.*
import com.realtor.plugins.repository.category.CategoriesRepository
import com.realtor.plugins.repository.contact.ContactRepository
import com.realtor.plugins.repository.house.HousesRepository
import com.realtor.plugins.repository.images.ImagesRepository
import com.realtor.plugins.routes.category
import com.realtor.plugins.routes.contact
import com.realtor.plugins.routes.houses
import com.realtor.plugins.routes.images
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
    val contactDb = ContactRepository()
    val imagesDb = ImagesRepository()
    routing {
        category(db)
        houses(housesDb)
        contact(contactDb)
        images(imagesDb)
    }
}