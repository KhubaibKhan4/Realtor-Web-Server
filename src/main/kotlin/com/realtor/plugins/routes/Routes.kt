package com.realtor.plugins.routes

import com.realtor.API_VERSION
import io.ktor.server.locations.*

const val CATEGORY_CREATE = "$API_VERSION/category"
const val GET_CATEGORY_BY_ID = "$API_VERSION/category/{id}"

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(CATEGORY_CREATE)
class CategoryCreateRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(GET_CATEGORY_BY_ID)
class GetCategoryById
