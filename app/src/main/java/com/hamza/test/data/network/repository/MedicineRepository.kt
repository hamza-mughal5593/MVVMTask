package com.mycomposeproj.data.repository

import androidx.lifecycle.LiveData
import com.hamza.test.data.network.api.ApiService
import com.hamza.test.data.model.response.ProblemsResponse
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class MedicineRepository @Inject constructor(private val apiService: ApiService.Service) {

    suspend fun getMedCategories2(): Response<ProblemsResponse> {
        return apiService.getMedCategories2()
    }
}