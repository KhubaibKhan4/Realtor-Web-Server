package com.realtor.plugins.routes

import com.realtor.plugins.repository.*
import com.realtor.plugins.repository.house.HouseRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
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
    db: HousesRepository,
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

        val address = parameters["address"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val mls = parameters["mls"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val county = parameters["county"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val city = parameters["city"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val area = parameters["area"]?.toLongOrNull() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val neighborhood = parameters["neighborhood"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val zip = parameters["zip"]?.toLongOrNull() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val style = parameters["style"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val builtYear = parameters["builtYear"]?.toLongOrNull() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val taxes = parameters["taxes"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val description = parameters["description"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val dataSource = parameters["dataSource"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val priceHistoryDate = parameters["priceHistoryDate"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val priceHistoryDetail = parameters["priceHistoryDetail"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val restrictions = parameters["restrictions"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val housingOlderPersonsAct = parameters["housingOlderPersonsAct"] ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val foreclosure = parameters["foreclosure"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val views = parameters["views"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val shortSale = parameters["shortSale"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val newConstruction = parameters["newConstruction"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val adult = parameters["adult"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val leaseToOwn = parameters["leaseToOwn"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val noHoaFees = parameters["noHoaFees"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val furnished = parameters["furnished"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val pets = parameters["pets"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val primaryOnMain = parameters["primaryOnMain"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val airConditioning = parameters["airConditioning"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val sellerFinance = parameters["sellerFinance"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val green = parameters["green"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val fixedUpper = parameters["fixedUpper"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val horse = parameters["horse"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val golf = parameters["golf"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val fireplace = parameters["fireplace"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val deck = parameters["deck"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val garage = parameters["garage"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val basement = parameters["basement"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )
        val pool = parameters["pool"]?.toBoolean() ?: return@post call.respondText(
            text = "MISSING FIELD",
            status = HttpStatusCode.BadRequest
        )

        try {
            val houses = db.insert(
                title = title,
                price = price,
                type = type,
                size = size,
                rooms = rooms,
                address = address,
                mls = mls,
                county = county,
                city = city,
                area = area,
                neighborhood = neighborhood,
                zip = zip,
                style = style,
                builtYear = builtYear,
                taxes = taxes,
                description = description,
                dataSource = dataSource,
                priceHistoryDate = priceHistoryDate,
                priceHistoryDetail = priceHistoryDetail,
                restrictions = restrictions,
                housingOlderPersonsAct = housingOlderPersonsAct,
                foreclosure = foreclosure,
                views = views,
                short_Sale = shortSale,
                new_construction = newConstruction,
                adult = adult,
                leaseToOwn = leaseToOwn,
                noHoaFees = noHoaFees,
                furnished = furnished,
                pets = pets,
                primaryOnMain = primaryOnMain,
                aitConditioning = airConditioning,
                sellerFinance = sellerFinance,
                green = green,
                fixedUpper = fixedUpper,
                horse = horse,
                golf = golf,
                fireplace = fireplace,
                deck = deck,
                garage = garage,
                basement = basement,
                pool = pool
            )
            houses?.id?.let {
                call.respond(HttpStatusCode.OK, "Data Uploaded Successfully $houses")
            }

        } catch (e: Throwable) {
            call.respond(
                HttpStatusCode.Unauthorized,
                "Error While Pushing Data to Server: ${e.message}"
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

        val address = updateInfo["address"] ?: return@put call.respondText(
            text = "MISSING Address",
            status = HttpStatusCode.BadRequest
        )
        val mls = updateInfo["mls"] ?: return@put call.respondText(
            text = "MISSING MLS",
            status = HttpStatusCode.BadRequest
        )
        val county = updateInfo["county"] ?: return@put call.respondText(
            text = "MISSING County",
            status = HttpStatusCode.BadRequest
        )
        val city = updateInfo["city"] ?: return@put call.respondText(
            text = "MISSING City",
            status = HttpStatusCode.BadRequest
        )
        val area = updateInfo["area"]?.toLongOrNull() ?: return@put call.respondText(
            text = "MISSING Area",
            status = HttpStatusCode.BadRequest
        )
        val neighborhood = updateInfo["neighborhood"] ?: return@put call.respondText(
            text = "MISSING Neighborhood",
            status = HttpStatusCode.BadRequest
        )
        val zip = updateInfo["zip"]?.toLongOrNull() ?: return@put call.respondText(
            text = "MISSING ZIP",
            status = HttpStatusCode.BadRequest
        )
        val style = updateInfo["style"] ?: return@put call.respondText(
            text = "MISSING Style",
            status = HttpStatusCode.BadRequest
        )
        val builtYear = updateInfo["builtYear"]?.toLongOrNull() ?: return@put call.respondText(
            text = "MISSING Built Year",
            status = HttpStatusCode.BadRequest
        )
        val taxes = updateInfo["taxes"] ?: return@put call.respondText(
            text = "MISSING Taxes",
            status = HttpStatusCode.BadRequest
        )
        val description = updateInfo["description"] ?: return@put call.respondText(
            text = "MISSING Description",
            status = HttpStatusCode.BadRequest
        )
        val dataSource = updateInfo["dataSource"] ?: return@put call.respondText(
            text = "MISSING Data Source",
            status = HttpStatusCode.BadRequest
        )
        val priceHistoryDate = updateInfo["priceHistoryDate"] ?: return@put call.respondText(
            text = "MISSING Price History Date",
            status = HttpStatusCode.BadRequest
        )
        val priceHistoryDetail = updateInfo["priceHistoryDetail"] ?: return@put call.respondText(
            text = "MISSING Price History Detail",
            status = HttpStatusCode.BadRequest
        )
        val restrictions = updateInfo["restrictions"] ?: return@put call.respondText(
            text = "MISSING Restrictions",
            status = HttpStatusCode.BadRequest
        )
        val housingOlderPersonsAct = updateInfo["housingOlderPersonsAct"] ?: return@put call.respondText(
            text = "MISSING Housing Older Persons Act",
            status = HttpStatusCode.BadRequest
        )
        val foreclosure = updateInfo["foreclosure"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Foreclosure",
            status = HttpStatusCode.BadRequest
        )
        val views = updateInfo["views"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Views",
            status = HttpStatusCode.BadRequest
        )
        val shortSale = updateInfo["shortSale"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Short Sale",
            status = HttpStatusCode.BadRequest
        )
        val newConstruction = updateInfo["newConstruction"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING New Construction",
            status = HttpStatusCode.BadRequest
        )
        val adult = updateInfo["adult"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Adult",
            status = HttpStatusCode.BadRequest
        )
        val leaseToOwn = updateInfo["leaseToOwn"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Lease to Own",
            status = HttpStatusCode.BadRequest
        )
        val noHoaFees = updateInfo["noHoaFees"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING No HOA Fees",
            status = HttpStatusCode.BadRequest
        )
        val furnished = updateInfo["furnished"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Furnished",
            status = HttpStatusCode.BadRequest
        )
        val pets = updateInfo["pets"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Pets",
            status = HttpStatusCode.BadRequest
        )
        val primaryOnMain = updateInfo["primaryOnMain"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Primary on Main",
            status = HttpStatusCode.BadRequest
        )
        val airConditioning = updateInfo["airConditioning"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Air Conditioning",
            status = HttpStatusCode.BadRequest
        )
        val sellerFinance = updateInfo["sellerFinance"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Seller Finance",
            status = HttpStatusCode.BadRequest
        )
        val green = updateInfo["green"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Green",
            status = HttpStatusCode.BadRequest
        )
        val fixedUpper = updateInfo["fixedUpper"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Fixed Upper",
            status = HttpStatusCode.BadRequest
        )
        val horse = updateInfo["horse"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Horse",
            status = HttpStatusCode.BadRequest
        )
        val golf = updateInfo["golf"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Golf",
            status = HttpStatusCode.BadRequest
        )
        val fireplace = updateInfo["fireplace"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Fireplace",
            status = HttpStatusCode.BadRequest
        )
        val deck = updateInfo["deck"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Deck",
            status = HttpStatusCode.BadRequest
        )
        val garage = updateInfo["garage"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Garage",
            status = HttpStatusCode.BadRequest
        )
        val basement = updateInfo["basement"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Basement",
            status = HttpStatusCode.BadRequest
        )
        val pool = updateInfo["pool"]?.toBoolean() ?: return@put call.respondText(
            text = "MISSING Pool",
            status = HttpStatusCode.BadRequest
        )

        try {
            val result = id.toInt().let {
                db.updateHouseById(
                    id.toInt(), title, price, type, size, rooms,
                    address, mls, county, city, area, neighborhood, zip,
                    style, builtYear, taxes, description, dataSource, priceHistoryDate,
                    priceHistoryDetail, restrictions, housingOlderPersonsAct, foreclosure,
                    views, shortSale, newConstruction, adult, leaseToOwn, noHoaFees,
                    furnished, pets, primaryOnMain, airConditioning, sellerFinance, green,
                    fixedUpper, horse, golf, fireplace, deck, garage, basement, pool
                )
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
                text = "Error While Updating Data from Server ${e.message}",
                status = HttpStatusCode.BadRequest
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

    post("v1/images") {
        val parameter = call.receive<Parameters>()

        val imageUrl = parameter["imageUrl"] ?: return@post call.respondText(
            text = "IMAGE URL MISSING",
            status = HttpStatusCode.BadRequest
        )
        val description = parameter["description"] ?: return@post call.respondText(
            text = "DESCRIPTION MISSING",
            status = HttpStatusCode.BadRequest
        )

        try {
            val images = db.insert(imageUrl, description)
            images?.id?.let {
                call.respond(status = HttpStatusCode.OK, "Data Uploaded Successfully $images")
            }

        } catch (e: Throwable) {
            call.respondText(
                text = "ERROR WHILE PUSHING DATA TO SERVER ${e.message}",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
    get("v1/images") {
        try {
            val images = db.getAllImages()
            if (images?.isNotEmpty() == true) {
                call.respond(status = HttpStatusCode.OK, images)
            } else {
                call.respondText(
                    text = "NO Images Found",
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
    get("v1/images/{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            text = "Invalid ID Found",
            status = HttpStatusCode.BadRequest
        )
        try {
            val images = id.toInt().let {
                db.getImagesById(id.toInt())
            } ?: return@get call.respondText(
                text = "ERROR While FETCHING IMAGE BY ID",
                status = HttpStatusCode.BadRequest
            )
            images.let {
                call.respond(status = HttpStatusCode.OK, images)
            }
        } catch (e: Throwable) {
            call.respondText(
                text = "ERROR WHILE GETTING DATA FROM SERVER ${e.message}",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
    delete("v1/images/{id}") {
        val id = call.parameters["id"]
        try {
            val image = id?.toInt()?.let {
                db.deleteImagesById(id.toInt())
            } ?: return@delete call.respondText(
                text = "Invalid ID Found...",
                status = HttpStatusCode.BadRequest
            )
            if (image == 1) {
                call.respondText(
                    text = "Image with ID #$id Deleted Successfully"
                )
            } else {
                call.respondText(
                    text = "Error While Deleting Images...",
                    status = HttpStatusCode.BadRequest
                )
            }

        } catch (e: Throwable) {
            call.respondText(
                text = "ERROR WHILE DELETING DATA FROM SERVER ${e.message}",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
    put("v1/images/{id}") {
        val id = call.parameters["id"] ?: return@put call.respondText(
            text = "Invalid ID Found...",
            status = HttpStatusCode.BadRequest
        )
        val updateInfo = call.receive<Parameters>()
        val imageUrl = updateInfo["imageUrl"] ?: return@put call.respondText(
            text = "IMAGE URL MISSING",
            status = HttpStatusCode.BadRequest
        )
        val description = updateInfo["description"] ?: return@put call.respondText(
            text = "DESCRIPTION MISSING",
            status = HttpStatusCode.BadRequest
        )
        try {
            val images = id.toInt().let {
                db.updateImagesById(id.toInt(), imageUrl, description)
            }
            if (images == 1) {
                call.respondText(
                    text = "Images Data Updated Successfully...",
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
                text = "ERROR WHILE UPDATING DATA TO SERVER ${e.message}",
                status = HttpStatusCode.Unauthorized
            )
        }
    }

}
fun Route.houseRouting(
    houseRepository: HouseRepository
){
    authenticate {
        route("/house") {
            post("/create") {
                val parameters = call.receiveParameters()
                val title = parameters["title"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Title parameter missing")
                val imageUrl = parameters["imageUrl"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Image URL parameter missing")
                val address = parameters["address"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Address parameter missing")
                val type = parameters["type"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Type parameter missing")
                val size = parameters["size"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Size parameter missing")
                val rooms = parameters["rooms"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Rooms parameter missing")
                val categoryId = parameters["categoryId"]?.toIntOrNull() ?: return@post call.respond(HttpStatusCode.BadRequest, "Category ID parameter missing")

                val result = houseRepository.createHouse(title, imageUrl, address, type, size, rooms, categoryId)
                call.respond(HttpStatusCode.OK, result)
            }

            get("/{houseId}") {
                try {
                    val houseId = call.parameters["houseId"]?.toLongOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid house ID")
                    val house = houseRepository.getHouse(houseId) ?: return@get call.respond(HttpStatusCode.NotFound, "House not found")
                    call.respond(HttpStatusCode.OK, house)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Internal Server Error")
                }
            }

            delete("/{houseId}") {
                try {
                    val houseId = call.parameters["houseId"]?.toLongOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid house ID")
                    val result = houseRepository.deleteHouse(houseId)
                    call.respond(HttpStatusCode.OK, result)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Internal Server Error")
                }
            }
        }
    }
}