package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network.responses

import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.Quote

data class QuotesResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)