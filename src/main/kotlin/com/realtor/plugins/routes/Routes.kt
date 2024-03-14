package com.realtor.plugins.routes

import com.realtor.domain.local.DatabaseFactory
import com.realtor.plugins.data.model.house.HouseWithImages
import com.realtor.plugins.data.table.category.CategoriesTable
import com.realtor.plugins.repository.category.CategoriesRepository
import com.realtor.plugins.repository.contact.ContactRepository
import com.realtor.plugins.repository.house.HousesRepository
import com.realtor.plugins.repository.images.ImagesRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.exposed.sql.select

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
            val category = db.insert(name, priority.toLong())
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
            val categoryId = parameter?.toLongOrNull()
            if (categoryId == null) {
                return@get call.respondText(
                    text = "Invalid Id",
                    status = HttpStatusCode.BadRequest
                )
            }

            val category = db.getCategoryById(id = categoryId)
            if (category == null) {
                return@get call.respondText(
                    text = "Category not found",
                    status = HttpStatusCode.NotFound
                )
            }

            val houseList = withContext(Dispatchers.IO) {
                HousesRepository().getHousesListByCategoryId(categoryId)
            }
            if (houseList.isNullOrEmpty()) {
                return@get call.respondText(
                    text = "No houses found for this category",
                    status = HttpStatusCode.NotFound
                )
            }

            val housesWithImages = houseList.map { house ->
                val images = ImagesRepository().getImagesListBYHouseId(house.id)
                JsonObject(
                    mapOf(
                        "id" to JsonPrimitive(house.id),
                        "categoryId" to JsonPrimitive(house.categoryId),
                        "categoryTitle" to JsonPrimitive(house.categoryTitle),
                        "title" to JsonPrimitive(house.title),
                        "price" to JsonPrimitive(house.price),
                        "type" to JsonPrimitive(house.type),
                        "size" to JsonPrimitive(house.size),
                        "rooms" to JsonPrimitive(house.rooms),
                        "address" to JsonPrimitive(house.address),
                        "mls" to JsonPrimitive(house.mls),
                        "county" to JsonPrimitive(house.county),
                        "city" to JsonPrimitive(house.city),
                        "area" to JsonPrimitive(house.area),
                        "neighborhood" to JsonPrimitive(house.neighborhood),
                        "zip" to JsonPrimitive(house.zip),
                        "style" to JsonPrimitive(house.style),
                        "builtYear" to JsonPrimitive(house.builtYear),
                        "taxes" to JsonPrimitive(house.taxes),
                        "description" to JsonPrimitive(house.description),
                        "dataSource" to JsonPrimitive(house.dataSource),
                        "priceHistoryDate" to JsonPrimitive(house.priceHistoryDate),
                        "priceHistoryDetail" to JsonPrimitive(house.priceHistoryDetail),
                        "restrictions" to JsonPrimitive(house.restrictions),
                        "housingOlderPersonsAct" to JsonPrimitive(house.housingOlderPersonsAct),
                        "foreclosure" to JsonPrimitive(house.foreclosure),
                        "views" to JsonPrimitive(house.views),
                        "short_Sale" to JsonPrimitive(house.short_Sale),
                        "new_construction" to JsonPrimitive(house.new_construction),
                        "adult" to JsonPrimitive(house.adult),
                        "leaseToOwn" to JsonPrimitive(house.leaseToOwn),
                        "noHoaFees" to JsonPrimitive(house.noHoaFees),
                        "furnished" to JsonPrimitive(house.furnished),
                        "pets" to JsonPrimitive(house.pets),
                        "primaryOnMain" to JsonPrimitive(house.primaryOnMain),
                        "aitConditioning" to JsonPrimitive(house.aitConditioning),
                        "sellerFinance" to JsonPrimitive(house.sellerFinance),
                        "green" to JsonPrimitive(house.green),
                        "fixedUpper" to JsonPrimitive(house.fixedUpper),
                        "horse" to JsonPrimitive(house.horse),
                        "golf" to JsonPrimitive(house.golf),
                        "fireplace" to JsonPrimitive(house.fireplace),
                        "deck" to JsonPrimitive(house.deck),
                        "garage" to JsonPrimitive(house.garage),
                        "basement" to JsonPrimitive(house.basement),
                        "pool" to JsonPrimitive(house.pool),
                        // Add other house properties here
                        "images" to JsonArray(
                            images.map { image ->
                                JsonObject(
                                    mapOf(
                                        "id" to JsonPrimitive(image.id),
                                        "houseId" to JsonPrimitive(image.houseId),
                                        "imageUrl" to JsonPrimitive(image.imageUrl),
                                        "description" to JsonPrimitive(image.description)
                                        // Add other image properties here
                                    )
                                )
                            }
                        )
                    )
                )
            }

            val categoryJson = JsonObject(
                mapOf(
                    "category" to JsonObject(
                        mapOf(
                            "id" to JsonPrimitive(category.id),
                            "name" to JsonPrimitive(category.name),
                            "priority" to JsonPrimitive(category.priority),
                            "houses" to JsonArray(housesWithImages)
                        )
                    )
                )
            )

            call.respond(status = HttpStatusCode.OK, categoryJson)
        } catch (e: Throwable) {
            call.respond(status = HttpStatusCode.BadRequest, "Problems While Fetching Category")
        }
    }
    delete("v1/category/{id}") {
        val parameter = call.parameters["id"]
        try {
            val category = parameter?.toLongOrNull()?.let { categoryId ->
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
                db.updateCategory(id.toLong(), name, priority.toLong())
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
        val categoryIdString = parameters["categoryId"]
        val categoryId = categoryIdString?.toLongOrNull()

        if (categoryId == null) {
            return@post call.respondText(
                text = "MISSING OR INVALID categoryId",
                status = HttpStatusCode.BadRequest
            )
        }

        try {
            val categoryTitle = DatabaseFactory.dbQuery {
                CategoriesTable.select { CategoriesTable.id.eq(categoryId) }
                    .singleOrNull()?.get(CategoriesTable.name)
                    ?: throw IllegalArgumentException("Category with id $categoryId not found")
            }
            val houses = db.insert(
                categoryId = categoryId,
                categoryTitle = categoryTitle,
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
            val houses = id?.toLongOrNull()?.let { houseId ->
                db.getHousesById(houseId)
            } ?: return@get call.respondText(
                text = "Id is Invalid",
                status = HttpStatusCode.BadRequest
            )
            val imagesList = ImagesRepository().getImagesListBYHouseId(houses.id)
            val houseWithImages = HouseWithImages(houses, imagesList)
            houses.let { houseDetail ->
                call.respond(status = HttpStatusCode.OK, houseWithImages)
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
            val house = id?.toLongOrNull()?.let { houseId ->
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
                    id.toLong(), title, price, type, size, rooms,
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
    get("v1/house/filter") {
        try {
            val parameters = call.request.queryParameters
            val name = parameters["name"]
            val city = parameters["city"]
            val location = parameters["location"]
            val area = parameters["area"]?.toLongOrNull()
            val category = parameters["category"]?.toLongOrNull()
            val beds = parameters["beds"]?.toLongOrNull()
            val baths = parameters["baths"]?.toLongOrNull()
            val minPrice = parameters["minPrice"]?.toLongOrNull()
            val maxPrice = parameters["maxPrice"]?.toLongOrNull()

            val filteredHouses = db.getFilteredHouses(
                categoryId = category,
                name = name,
                city = city?.lowercase(),
                location = location,
                area = area,
                beds = beds,
                baths = baths,
                minPrice = minPrice,
                maxPrice = maxPrice
            )
            if (filteredHouses != null) {
                call.respond(HttpStatusCode.OK, filteredHouses)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError)
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
            val contact = id?.toLongOrNull()?.let { contactID ->
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
            val contact = id?.toLongOrNull()?.let {
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
                db.updateContactById(id.toLong(), name = name, email = email, message = message)
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
        val houseId = parameter["houseId"]?.toLongOrNull() ?: return@post call.respondText(
            text = "HOUSE ID MISSING OR INVALID",
            status = HttpStatusCode.BadRequest
        )

        try {
            val images = db.insert(houseId, imageUrl, description)
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
                db.getImagesById(id.toLong())
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
                db.deleteImagesById(id.toLong())
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
        val houseId = updateInfo["houseId"]?.toLongOrNull() ?: return@put call.respondText(
            text = "houseId MISSING",
            status = HttpStatusCode.BadRequest
        )
        try {
            val images = id.toInt().let {
                db.updateImagesById(id.toLong(), houseId, imageUrl, description)
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