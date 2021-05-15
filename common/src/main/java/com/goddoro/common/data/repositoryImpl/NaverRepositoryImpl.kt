package com.goddoro.common.data.repositoryImpl

import com.goddoro.common.common.NAVER_CLIENT_ID
import com.goddoro.common.common.NAVER_CLIENT_SECRET
import com.goddoro.common.common.filterValueNotNull
import com.goddoro.common.data.api.AddressResponse
import com.goddoro.common.data.api.LocationResponse
import com.goddoro.common.data.api.NaverAPI
import com.goddoro.common.data.repository.NaverRepository


/**
 * created By DORO 4/25/21
 */

class NaverRepositoryImpl ( val naverApi : NaverAPI) : NaverRepository {
    override suspend fun getAddressFromLocation(latitude: Double, longitude: Double): AddressResponse {


        val parameters = hashMapOf(
            "output" to "json",
            "coords" to "$latitude,$longitude",
            "orders" to "roadaddr"
        ).filterValueNotNull()

        return naverApi.getAddress(parameters)
    }

    override suspend fun getLocationFromAddress(address: String): LocationResponse {

        val parameters = hashMapOf(
            "query" to address
        ).filterValueNotNull()

        return naverApi.getLocation(parameters)
    }


}