package com.realtor.plugins.routes

import com.realtor.API_VERSION
import com.realtor.plugins.repository.CategoriesRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val CATEGORY_CREATE = "$API_VERSION/category"

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(CATEGORY_CREATE)
class CategoryCreateRoute


fun Route.category(
    db: CategoriesRepository
) {
    post(CATEGORY_CREATE) {
        val parameters = call.receive<Parameters>()
        val name = parameters["name"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.Unauthorized
        )
        val priority = parameters["priority"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.Unauthorized
        )

        try {
            val category = db.insert(name, priority.toInt())
            category?.id?.let {
                call.respond(status = HttpStatusCode.OK, category)

            }


        } catch (e: Throwable) {
            call.respondText("${e.message}")
        }
    }

    get(CATEGORY_CREATE) {
        try {
            val categoryList = db.getAllCategories()
            if (categoryList.isNotEmpty()) {
                call.respond(categoryList)
            } else {
                call.respondText("No Category Found!!")
            }
        } catch (e: Throwable) {
            call.respond(status = HttpStatusCode.BadRequest, e.message.toString())
        }
    }
    get("v1/category/{id}") {
        val parameter = call.parameters["id"]
        try {
            val category = parameter?.toInt()?.let { categoryId ->
                db.getCategoryById(id = categoryId)
            } ?: return@get call.respondText(
                text = "Invalid Id",
                status = HttpStatusCode.BadRequest
            )

            category.id.let {
                call.respond(status = HttpStatusCode.OK, category)
            }
        } catch (e: Throwable) {
            call.respond(status = HttpStatusCode.BadRequest, "Problems While Fetching Category")
        }
    }
    delete("v1/category/{id}") {
        val parameter = call.parameters["id"]
        try {
            val category = parameter?.toInt()?.let { categoryId ->
                db.deleteCategoryById(categoryId)
            } ?: return@delete call.respondText(
                text = "No Id Found",
                status = HttpStatusCode.BadRequest
            )

            if (category == 1) {
                call.respondText(
                    text = "Deleted Successfully",
                    status = HttpStatusCode.OK
                )
            } else {
                call.respondText("Id Not Found", status = HttpStatusCode.BadRequest)
            }

        } catch (e: Throwable) {
            call.respond(status = HttpStatusCode.BadRequest, e.message.toString())
        }
    }

}