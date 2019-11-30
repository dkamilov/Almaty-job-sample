package com.android.damir.jobfind

import android.content.SearchRecentSuggestionsProvider

class MySuggestionsProvider: SearchRecentSuggestionsProvider() {

    init{
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object{
        const val AUTHORITY = "com.android.damir.jobfind.MySuggestionsProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}