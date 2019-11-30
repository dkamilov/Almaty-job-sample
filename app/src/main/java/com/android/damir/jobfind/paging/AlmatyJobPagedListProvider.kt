package com.android.damir.jobfind.paging

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.damir.jobfind.network.HHVacancy

class AlmatyJobPagedListProvider(private val factory: DataSource.Factory<Int, HHVacancy>){

    fun provide(): LiveData<PagedList<HHVacancy?>>{

        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()

        return LivePagedListBuilder(factory, config)
            .build()
    }
}