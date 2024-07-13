package com.hamza.test.data.network.api

import com.hamza.test.data.model.response.ProblemsResponse
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiService @Inject constructor() {

    interface Service {
        @GET(MOKE_API)
        suspend fun getMedCategories2(): Response<ProblemsResponse>
    }

    companion object {
        const val API_URL = "https://run.mocky.io/"
        const val VERSION = "v3/"
        const val MOKE_API = "${VERSION}2118d740-9aab-4f04-abad-67cb4402d408"
    }
}


