package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.model.User
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.*


/**
 * created By DORO 2020/10/10
 */

interface EventAPI {


    @GET("event")
    suspend fun getEventList(
        @QueryMap parameters: HashMap<String, Any>
    ) : ApiResponse<EventListResponse>

    @GET("event/{id}")
    suspend fun getEvent(
        @Path("id") eventId : Int
    ) : ApiResponse<EventResponse>
}

@Parcelize
data class EventListResponse(
    @SerializedName("event")
    val events : List<Event>
) : Parcelable

@Parcelize
data class EventResponse(
    @SerializedName("event")
    val event : Event
) : Parcelable