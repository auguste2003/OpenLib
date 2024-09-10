package com.example.openlib.data.remote

import com.example.openlib.data.local.Book

data class SearchResponse(val numFound :Int,
                          val docs: List<Book>)
