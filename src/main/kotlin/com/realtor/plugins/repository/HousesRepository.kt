package com.realtor.plugins.repository

import com.realtor.plugins.dao.HousesDao
import com.realtor.plugins.data.model.Houses
import com.realtor.plugins.data.model.Images
import com.realtor.plugins.data.table.HousesTable
import com.realtor.plugins.data.table.ImagesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class HousesRepository : HousesDao {
    override suspend fun insert(
        title: String,
        price: String,
        type: String,
        size: String,
        rooms: String,
        address: String,
        mls: String,
        county: String,
        city: String,
        area: Long,
        neighborhood: String,
        zip: Long,
        style: String,
        builtYear: Long,
        taxes: String,
        description: String,
        dataSource: String,
        priceHistoryDate: String,
        priceHistoryDetail: String,
        restrictions: String,
        housingOlderPersonsAct: String,
        foreclosure: Boolean,
        views: Boolean,
        short_Sale: Boolean,
        new_construction: Boolean,
        adult: Boolean,
        leaseToOwn: Boolean,
        noHoaFees: Boolean,
        furnished: Boolean,
        pets: Boolean,
        primaryOnMain: Boolean,
        aitConditioning: Boolean,
        sellerFinance: Boolean,
        green: Boolean,
        fixedUpper: Boolean,
        horse: Boolean,
        golf: Boolean,
        fireplace: Boolean,
        deck: Boolean,
        garage: Boolean,
        basement: Boolean,
        pool: Boolean
    ): Houses? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = HousesTable.insert { house ->
                house[HousesTable.title] = title
                house[HousesTable.price] = price
                house[HousesTable.type] = type
                house[HousesTable.size] = size
                house[HousesTable.rooms] = rooms
                house[HousesTable.address] = address
                house[HousesTable.mls] = mls
                house[HousesTable.county] = county
                house[HousesTable.city] = city
                house[HousesTable.area] = area
                house[HousesTable.neighborhood] = neighborhood
                house[HousesTable.zip] = zip
                house[HousesTable.style] = style
                house[HousesTable.builtYear] = builtYear
                house[HousesTable.taxes] = taxes
                house[HousesTable.description] = description
                house[HousesTable.dataSource] = dataSource
                house[HousesTable.priceHistoryDate] = priceHistoryDate
                house[HousesTable.priceHistoryDetail] = priceHistoryDetail
                house[HousesTable.restrictions] = restrictions
                house[HousesTable.housingOlderPersonsAct] = housingOlderPersonsAct
                house[HousesTable.foreclosure] = foreclosure
                house[HousesTable.views] = views
                house[HousesTable.shortSale] = short_Sale
                house[HousesTable.newConstruction] = new_construction
                house[HousesTable.adult] = adult
                house[HousesTable.leaseToOwn] = leaseToOwn
                house[HousesTable.noHoaFees] = noHoaFees
                house[HousesTable.furnished] = furnished
                house[HousesTable.pets] = pets
                house[HousesTable.primaryOnMain] = primaryOnMain
                house[HousesTable.airConditioning] = aitConditioning
                house[HousesTable.sellerFinance] = sellerFinance
                house[HousesTable.green] = green
                house[HousesTable.fixedUpper] = fixedUpper
                house[HousesTable.horse] = horse
                house[HousesTable.golf] = golf
                house[HousesTable.fireplace] = fireplace
                house[HousesTable.deck] = deck
                house[HousesTable.garage] = garage
                house[HousesTable.basement] = basement
                house[HousesTable.pool] = pool
            }
        }
        return statement?.resultedValues?.get(0)?.let { rowToResult(it) }
    }

    override suspend fun getHouses(): List<Houses>? {
        return DatabaseFactory.dbQuery {
            HousesTable.selectAll()
                .mapNotNull {
                    rowToResult(it)
                }
        }
    }

    override suspend fun getHousesById(id: Int): Houses? {
        return DatabaseFactory.dbQuery {
            HousesTable.select {
                HousesTable.id.eq(id)
            }.map {
                rowToResult(it)
            }.singleOrNull()
        }
    }

    override suspend fun deleteHouseById(id: Int): Int? {
        return DatabaseFactory.dbQuery {
            HousesTable.deleteWhere {
                HousesTable.id.eq(id)
            }
        }
    }

    override suspend fun updateHouseById(
        id: Int,
        title: String,
        price: String,
        type: String,
        size: String,
        rooms: String
    ): Int? {
        return DatabaseFactory.dbQuery {
            HousesTable.update({ HousesTable.id.eq(id) }) { house ->
                house[HousesTable.id] = id
                house[HousesTable.title] = title
                house[HousesTable.price] = price
                house[HousesTable.type] = type
                house[HousesTable.size] = size
                house[HousesTable.rooms] = rooms
            }
        }
    }

    fun rowToResult(row: ResultRow): Houses? {
        if (row == null) {
            return null
        } else {
            return Houses(
                id = row[HousesTable.id],
                title = row[HousesTable.title],
                price = row[HousesTable.price],
                type = row[HousesTable.type],
                size = row[HousesTable.size],
                rooms = row[HousesTable.rooms],
            )
        }
    }
}