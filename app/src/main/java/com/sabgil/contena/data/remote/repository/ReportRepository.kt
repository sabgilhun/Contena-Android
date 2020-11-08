package com.sabgil.contena.data.remote.repository

import com.sabgil.contena.data.remote.dto.PostReportRequest
import com.sabgil.contena.domain.model.ReportResult
import io.reactivex.Single

interface ReportRepository {

    fun postReport(postReportRequest: PostReportRequest): Single<ReportResult>
}