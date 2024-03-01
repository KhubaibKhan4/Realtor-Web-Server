package com.realtor.plugins.repository

import com.realtor.plugins.dao.house.HousesDao
import com.realtor.plugins.data.model.Houses
import com.realtor.plugins.data.table.HousesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class HousesRepository : HousesDao {
    override suspend fun insert(
        categoryId: Long,
        categoryTitle: String,
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
                house[HousesTable.categoryId] = categoryId
                house[HousesTable.categoryTitle] = categoryTitle
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

    override suspend fun getHousesById(id: Long): Houses? {
        return DatabaseFactory.dbQuery {
            HousesTable.select {
                HousesTable.id.eq(id)
            }.map {
                rowToResult(it)
            }.singleOrNull()
        }
    }

    override suspend fun getHouseByCategoryId(id: Long): Houses? {
        return DatabaseFactory.dbQuery {
            HousesTable.select {
                HousesTable.categoryId.eq(id)
            }.map {
                rowToResult(it)
            }.singleOrNull()
        }
    }

    override suspend fun deleteHouseByCategoryId(id: Long): Int? {
        return DatabaseFactory.dbQuery {
            HousesTable.deleteWhere {
                HousesTable.categoryId.eq(id)
            }
        }
    }

    override suspend fun deleteHouseById(id: Long): Int? {
        return DatabaseFactory.dbQuery {
            HousesTable.deleteWhere {
                HousesTable.id.eq(id)
            }
        }
    }

    override suspend fun updateHouseById(
        id: Long,
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
    ): Int? {
        return DatabaseFactory.dbQuery {
            HousesTable.update({ HousesTable.id.eq(id) }) { house ->
                house[HousesTable.categoryTitle] = categoryTitle
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
    }

    private fun rowToResult(row: ResultRow): Houses? {
        return row[HousesTable.id]?.let { id ->
            Houses(
                id = id,
                categoryId = row[HousesTable.categoryId],
                categoryTitle = row[HousesTable.categoryTitle],
                title = row[HousesTable.title],
                price = row[HousesTable.price],
                type = row[HousesTable.type],
                size = row[HousesTable.size],
                rooms = row[HousesTable.rooms],
                address = row[HousesTable.address],
                mls = row[HousesTable.mls],
                county = row[HousesTable.county],
                city = row[HousesTable.city],
                area = row[HousesTable.area],
                neighborhood = row[HousesTable.neighborhood],
                zip = row[HousesTable.zip],
                style = row[HousesTable.style],
                builtYear = row[HousesTable.builtYear],
                taxes = row[HousesTable.taxes],
                description = row[HousesTable.description],
                dataSource = row[HousesTable.dataSource],
                priceHistoryDate = row[HousesTable.priceHistoryDate],
                priceHistoryDetail = row[HousesTable.priceHistoryDetail],
                restrictions = row[HousesTable.restrictions],
                housingOlderPersonsAct = row[HousesTable.housingOlderPersonsAct],
                foreclosure = row[HousesTable.foreclosure],
                views = row[HousesTable.views],
                short_Sale = row[HousesTable.shortSale],
                new_construction = row[HousesTable.newConstruction],
                adult = row[HousesTable.adult],
                leaseToOwn = row[HousesTable.leaseToOwn],
                noHoaFees = row[HousesTable.noHoaFees],
                furnished = row[HousesTable.furnished],
                pets = row[HousesTable.pets],
                primaryOnMain = row[HousesTable.primaryOnMain],
                aitConditioning = row[HousesTable.airConditioning],
                sellerFinance = row[HousesTable.sellerFinance],
                green = row[HousesTable.green],
                fixedUpper = row[HousesTable.fixedUpper],
                horse = row[HousesTable.horse],
                golf = row[HousesTable.golf],
                fireplace = row[HousesTable.fireplace],
                deck = row[HousesTable.deck],
                garage = row[HousesTable.garage],
                basement = row[HousesTable.basement],
                pool = row[HousesTable.pool]
            )
        }
    }
}