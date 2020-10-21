package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.contena.dto.PostReportResponse
import com.sabgil.contena.domain.model.ReportResult
import javax.inject.Inject

class ReportMapperImpl @Inject constructor() : ReportMapper {

    override fun toReportResult(from: PostReportResponse): ReportResult {
        TODO("Not yet implemented")
    }
}