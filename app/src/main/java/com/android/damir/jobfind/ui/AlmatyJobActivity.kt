package com.android.damir.jobfind.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.damir.jobfind.R
import com.android.damir.jobfind.paging.AlmatyJobAdapter
import com.android.damir.jobfind.viewmodels.AlmatyJobViewModel
import kotlinx.android.synthetic.main.activity_almaty_job.*



class AlmatyJobActivity : AppCompatActivity(){


    private val TAG = "AlmatyJobActivity"
    private lateinit var viewModel: AlmatyJobViewModel
    private lateinit var adapter: AlmatyJobAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_almaty_job)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(AlmatyJobViewModel::class.java)

        val dividerItemDecoration = DividerItemDecoration(recycler_view.context, DividerItemDecoration.VERTICAL)
        adapter = AlmatyJobAdapter(this)

        recycler_view.addItemDecoration(dividerItemDecoration)
        recycler_view.adapter = adapter

        viewModel.handleIntent(intent)
    }

    override fun onResume() {

        //Подписка на список вакансий
        viewModel.almatyJobPagedList.observe(this, Observer {
            progress_bar.visibility = View.VISIBLE
            adapter.submitList(it)
            progress_bar.visibility = View.GONE
        })
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(true)
            isSubmitButtonEnabled = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clear_search_suggestions -> viewModel.clearSuggestionsHistory()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNewIntent(intent: Intent?) {
        viewModel.handleIntent(intent)
        super.onNewIntent(intent)
    }
}
