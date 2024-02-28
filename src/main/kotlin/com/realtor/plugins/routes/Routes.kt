package com.realtor.plugins.routes

import com.realtor.plugins.repository.CategoriesRepository
import com.realtor.plugins.repository.ContactRepository
import com.realtor.plugins.repository.HousesRepository
import com.realtor.plugins.repository.ImagesRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.category(
    db: CategoriesRepository
) {
    post("v1/category") {
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
                call.respond(status = HttpStatusCode.OK, "Uploaded to Server Successfully $category")
            }


        } catch (e: Throwable) {
            call.respondText("Get Error While Posting Data to Server: ${e.message}")
        }
    }

    get("v1/category") {
        try {
            val categoryList = db.getAllCategories()
            if (categoryList.isNotEmpty()) {
                call.respond(categoryList)
            } else {
                call.respondText("No Category Found!!", status = HttpStatusCode.OK)
            }
        } catch (e: Throwable) {
            call.respond(status = HttpStatusCode.BadRequest, "Getting Categories Error " + e.message.toString())
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

    put("v1/category/{id}") {
        val id = call.parameters["id"] ?: return@put call.respondText(
            text = "Id Not Found!!",
            status = HttpStatusCode.BadRequest
        )

        val updateInfo = call.receive<Parameters>()
        val name = updateInfo["name"] ?: return@put call.respondText(
            text = "Missing Field",
            status = HttpStatusCode.Unauthorized
        )
        val priority = updateInfo["priority"] ?: return@put call.respondText(
            text = "Missing Field",
            status = HttpStatusCode.Unauthorized
        )

        try {
            val result = id.toInt().let { categoryId ->
                db.updateCategory(id.toInt(), name, priority)
            }
            if (result == 1) {
                call.respondText("Update SuccessFully....", status = HttpStatusCode.OK)
            } else {
                call.respondText("Something Went Wrong", status = HttpStatusCode.BadRequest)
            }

        } catch (e: Throwable) {
            call.respond(status = HttpStatusCode.BadRequest, e.message.toString())
        }

    }
    get("/") {
        try {
            call.respondText(
                "Welcome to Realtor Web, Please check your Endpoints to get Others Details",
                status = HttpStatusCode.OK
            )
        } catch (e: Throwable) {
            call.respond(status = HttpStatusCode.BadRequest, e.message.toString())
        }
    }
}

fun Route.houses(
    db: HousesRepository
) {
    post("v1/house") {
        val parameters = call.receive<Parameters>()

        val title = parameters["title"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val price = parameters["price"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val type = parameters["type"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val size = parameters["size"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val rooms = parameters["rooms"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )

        try {
            val houses = db.insert(title, price, type, size, rooms)
            houses?.id?.let {
                call.respond(status = HttpStatusCode.OK, "Data Uploaded Successfully $houses")
            }

        } catch (e: Throwable) {
            call.respondText(
                text = "Error While Pushing Data to Server",
                status = HttpStatusCode.Unauthorized
            )
        }
    }

    get("v1/house") {
        try {
            val housesList = db.getHouses()
            if (housesList?.isNotEmpty() == true) {
                call.respond(housesList)
            } else {
                call.respondText(
                    text = "No Houses Found. Please Retry Soon",
                    status = HttpStatusCode.OK
                )
            }
        } catch (e: Throwable) {
            call.respondText(
                text = "Error While Getting Lists from Server ${e.message}",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
    get("v1/house/{id}") {
        val id = call.parameters["id"]
        try {
            val houses = id?.toInt()?.let { houseId ->
                db.getHousesById(houseId)
            } ?: return@get call.respondText(
                text = "Id is Invalid",
                status = HttpStatusCode.BadRequest
            )
            houses.let { houseDetail ->
                call.respond(status = HttpStatusCode.OK, houses)
            }

        } catch (e: Throwable) {
            call.respondText(
                text = "Error While Getting Houses Detail from Server ${e.message}",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
    delete("v1/house/{id}") {
        val id = call.parameters["id"]
        try {
            val house = id?.toInt()?.let { houseId ->
                db.deleteHouseById(houseId)
            } ?: return@delete call.respondText(
                text = "Id is Invalid",
                status = HttpStatusCode.BadRequest
            )
            if (house == 1) {
                call.respondText(text = "House #$id Deleted Successfully", status = HttpStatusCode.OK)
            } else {
                call.respondText("Id Not Found", status = HttpStatusCode.BadRequest)
            }


        } catch (e: Throwable) {
            call.respondText(
                text = "Error While Deleting House from Server ${e.message}",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
    put("v1/house/{id}") {
        val id = call.parameters["id"] ?: return@put call.respondText(
            text = "Invalid Id Found",
            status = HttpStatusCode.BadRequest
        )
        val updateInfo = call.receive<Parameters>()
        val title = updateInfo["title"] ?: return@put call.respondText(
            text = "MISSING Title",
            status = HttpStatusCode.BadRequest
        )
        val price = updateInfo["price"] ?: return@put call.respondText(
            text = "MISSING Price",
            status = HttpStatusCode.BadRequest
        )
        val type = updateInfo["type"] ?: return@put call.respondText(
            text = "MISSING Type",
            status = HttpStatusCode.BadRequest
        )
        val size = updateInfo["size"] ?: return@put call.respondText(
            text = "MISSING Size",
            status = HttpStatusCode.BadRequest
        )
        val rooms = updateInfo["rooms"] ?: return@put call.respondText(
            text = "MISSING Rooms",
            status = HttpStatusCode.BadRequest
        )
        try {
            val result = id.toInt().let {
                db.updateHouseById(id.toInt(), title, price, type, size, rooms)
            }
            if (result == 1) {
                call.respondText(
                    text = "Data Updated Successfully",
                    status = HttpStatusCode.OK
                )
            } else {
                call.respondText(
                    text = "Error While Updating Data..",
                    status = HttpStatusCode.OK
                )
            }

        } catch (e: Throwable) {
            call.respondText(
                text = "Error While Updating Data from Server ${e.message}", status = HttpStatusCode.BadRequest
            )
        }
    }


}

fun Route.contact(
    db: ContactRepository
) {

    post("v1/contact") {
        val parameters = call.receive<Parameters>()
        val name = parameters["name"] ?: return@post call.respondText(
            text = "NAME MISSING",
            status = HttpStatusCode.BadRequest
        )
        val email = parameters["email"] ?: return@post call.respondText(
            text = "EMAIL MISSING",
            status = HttpStatusCode.BadRequest
        )
        val message = parameters["message"] ?: return@post call.respondText(
            text = "MESSAGE MISSING",
            status = HttpStatusCode.BadRequest
        )
        try {
            val contact = db.insert(name = name, email = email, message = message)
            contact?.id?.let {
                call.respondText(
                    text = "Message Stored Successfully $contact",
                    status = HttpStatusCode.OK
                )
            }

        } catch (e: Throwable) {
            call.respondText(
                text = "ERROR WHILE PUSHING DATA TO SERVER ${e.message}",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
    get("v1/contact") {
        try {
            val contact = db.getAllContacts()
            if (contact?.isNotEmpty() == true) {
                call.respond(contact)
            } else {
                call.respondText(
                    text = "No Contact Found...",
                    status = HttpStatusCode.OK
                )
            }

        } catch (e: Throwable) {
            call.respondText(
                text = "ERROR WHILE GETTING DATA FROM SERVER ${e.message}",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
    get("v1/contact/{id}") {
        val id = call.parameters["id"]
        try {
            val contact = id?.toInt()?.let { contactID ->
                db.getContactById(contactID)
            } ?: return@get call.respondText(
                text = "No Id Found",
                status = HttpStatusCode.BadRequest
            )
            contact.let {
                call.respond(status = HttpStatusCode.OK, contact)
            }

        } catch (e: Throwable) {
            call.respondText(
                text = "ERROR WHILE GETTING DATA From SERVER ${e.message}",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
    delete("v1/contact/{id}") {
        val id = call.parameters["id"]
        try {
            val contact = id?.toInt()?.let {
                db.deleteContactById(it)
            } ?: call.respondText(
                text = "Invalid ID Found",
                status = HttpStatusCode.OK
            )
            if (contact == 1) {
                call.respondText(
                    text = "Deleted Contact Successfully",
                    status = HttpStatusCode.OK
                )
            } else {
                call.respondText(
                    text = "Error While Deleting Contact",
                    status = HttpStatusCode.BadRequest
                )
            }

        } catch (e: Throwable) {
            call.respondText(
                text = "ERROR WHILE GETTING DATA From SERVER ${e.message}",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
    put("v1/contact/{id}") {
        val id = call.parameters["id"] ?: return@put call.respondText(
            text = "Invalid Id Found...",
            status = HttpStatusCode.BadRequest
        )
        val updateInfo = call.receive<Parameters>()
        val name = updateInfo["name"] ?: return@put call.respondText(
            text = "Name Missing",
            status = HttpStatusCode.BadRequest
        )
        val email = updateInfo["email"] ?: return@put call.respondText(
            text = "Email Missing",
            status = HttpStatusCode.BadRequest
        )
        val message = updateInfo["message"] ?: return@put call.respondText(
            text = "Message Missing",
            status = HttpStatusCode.BadRequest
        )
        try {
            val contact = id.toInt().let {
                db.updateContactById(id.toInt(), name = name, email = email, message = message)
            }
            if (contact == 1) {
                call.respondText(
                    text = "Contact Updated Successfully $contact",
                    status = HttpStatusCode.OK
                )
            } else {
                call.respondText(
                    text = "Error While Updating Data..",
                    status = HttpStatusCode.OK
                )
            }

        } catch (e: Throwable) {
            call.respondText(
                text = "ERROR WHILE UPDATING DATA From SERVER ${e.message}",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
}

fun Route.images(
    db: ImagesRepository
) {


}