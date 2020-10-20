package com.sabgil.contena.data.repository

import com.sabgil.contena.data.remote.contena.dto.PostReportRequest
import com.sabgil.contena.domain.model.ReportResult
import io.reactivex.Single
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val repository: ReportRepository
) : ReportRepository {

    override fun postReport(postReportRequest: PostReportRequest): Single<ReportResult> {
        TODO("Not yet implemented")
    }
}