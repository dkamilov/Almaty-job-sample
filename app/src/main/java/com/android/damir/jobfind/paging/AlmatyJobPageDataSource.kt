package com.android.damir.jobfind.paging

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.android.damir.jobfind.network.HHVacancy
import com.android.damir.jobfind.network.JobApiNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "AlmatyJobPageDataSource"
private val api = JobApiNetwork.apiResponse


class AlmatyJobPageDataSource(private val scope: CoroutineScope, private val text: String) : PageKeyedDataSource<Int, HHVacancy>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, HHVacancy>) {
        scope.launch {
            val items = api.getVacanciesAsync(0, text).await()
            callback.onResult(items.items, null, 1)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, HHVacancy>) {
        scope.launch {
            val items = api.getVacanciesAsync(params.key, text).await()
            callback.onResult(items.items, params.key.inc())
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, HHVacancy>) {
        scope.launch {
            val items = api.getVacanciesAsync(params.key, text).await()
            callback.onResult(items.items, params.key.dec())
        }
    }
}

class AlmatyJobPageDataSourceFactory(private val scope: CoroutineScope, private val text: String) : DataSource.Factory<Int, HHVacancy>(){
    override fun create(): DataSource<Int, HHVacancy> {
        return AlmatyJobPageDataSource(scope, text)
    }

}