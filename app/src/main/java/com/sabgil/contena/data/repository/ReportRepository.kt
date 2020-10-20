package com.sabgil.contena.data.repository

import com.sabgil.contena.data.remote.contena.dto.PostReportRequest
import com.sabgil.contena.domain.model.ReportResult
import io.reactivex.Single

interface ReportRepository {

    fun postReport(postReportRequest: PostReportRequest): Single<ReportResult>
}