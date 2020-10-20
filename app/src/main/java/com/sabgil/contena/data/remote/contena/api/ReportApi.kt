package com.sabgil.contena.data.remote.contena.api

import com.sabgil.contena.data.remote.contena.dto.PostReportRequest
import com.sabgil.contena.data.remote.contena.dto.PostReportResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportApi {

    @POST(value = "report")
    fun postReport(
        @Body postReportRequest: PostReportRequest
    ): Single<PostReportResponse>
}