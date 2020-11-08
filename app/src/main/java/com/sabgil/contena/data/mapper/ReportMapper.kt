package com.sabgil.contena.data.mapper

import com.sabgil.contena.data.remote.dto.PostReportResponse
import com.sabgil.contena.domain.model.ReportResult

interface ReportMapper {

    fun toReportResult(from: PostReportResponse): ReportResult
}