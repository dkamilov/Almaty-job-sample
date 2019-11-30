package com.android.damir.jobfind.viewmodels

import android.app.Application
import android.app.SearchManager
import android.content.Intent
import android.provider.SearchRecentSuggestions
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.damir.jobfind.MySuggestionsProvider
import com.android.damir.jobfind.network.HHVacancy
import com.android.damir.jobfind.paging.AlmatyJobPageDataSourceFactory
import com.android.damir.jobfind.paging.AlmatyJobPagedListProvider
import kotlinx.coroutines.*

class AlmatyJobViewModel(application: Application) : AndroidViewModel(application){

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    lateinit var almatyJobPagedList: LiveData<PagedList<HHVacancy?>>

    // Делает запрос к JobApiNetwork без строки названия запроса
    private fun searchEmptyVacancies(){
        val factory = AlmatyJobPageDataSourceFactory(viewModelScope, "")
        almatyJobPagedList = AlmatyJobPagedListProvider(factory).provide()
    }

    // Делает запрос к JopApiNetwork со строкой названия вакансии
    private fun searchVacanciesWithQuery(text: String){
        val factory = AlmatyJobPageDataSourceFactory(viewModelScope, text)
        almatyJobPagedList = AlmatyJobPagedListProvider(factory).provide()
    }

    /**
     * Получает текст поиска из SearchView если Intent запустивший активность == ACTION_SEARCH
     * и делает запрос с текстом запроса или без текста
     */
    fun handleIntent(intent: Intent?){
        if(Intent.ACTION_SEARCH == intent?.action){
            intent.getStringExtra(SearchManager.QUERY)?.also {
                SearchRecentSuggestions(getApplication(), MySuggestionsProvider.AUTHORITY, MySuggestionsProvider.MODE)
                    .saveRecentQuery(it, null)
                searchVacanciesWithQuery(it)
            }
        }else{
            searchEmptyVacancies()
        }
    }

    fun clearSuggestionsHistory(){
        SearchRecentSuggestions(getApplication(), MySuggestionsProvider.AUTHORITY, MySuggestionsProvider.MODE)
            .clearHistory()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}