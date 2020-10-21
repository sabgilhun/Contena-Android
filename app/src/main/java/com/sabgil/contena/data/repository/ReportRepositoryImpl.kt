package com.sabgil.contena.data.repository

import com.sabgil.contena.data.mapper.ReportMapper
import com.sabgil.contena.data.remote.contena.api.ReportApi
import com.sabgil.contena.data.remote.contena.dto.PostReportRequest
import com.sabgil.contena.domain.model.ReportResult
import io.reactivex.Single
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportApi: ReportApi,
    private val reportMapper: ReportMapper
) : ReportRepository {

    override fun postReport(postReportRequest: PostReportRequest): Single<ReportResult> =
        reportApi.postReport(postReportRequest).map(reportMapper::toReportResult)
}