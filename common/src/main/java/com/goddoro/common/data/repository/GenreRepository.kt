package com.goddoro.common.data.repository

import com.goddoro.common.data.model.Genre

interface GenreRepository {

    suspend fun listGenre() : List<Genre>
}