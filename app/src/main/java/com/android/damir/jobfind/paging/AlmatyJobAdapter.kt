package com.android.damir.jobfind.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.damir.jobfind.R
import com.android.damir.jobfind.convertSalary
import com.android.damir.jobfind.network.HHVacancy
import kotlinx.android.synthetic.main.almaty_job_item.view.*

class AlmatyJobAdapter(var context: Context) : PagedListAdapter<HHVacancy, AlmatyJobAdapter.AlmatyJobViewHolder>(DiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlmatyJobViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.almaty_job_item, parent, false)
        return AlmatyJobViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlmatyJobViewHolder, position: Int) {
        val vacancy = getItem(position)
        holder.bind(vacancy, context)
    }

    class AlmatyJobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(vacancy: HHVacancy?, context: Context){
            itemView.also{
                it.name.text = vacancy?.name
                it.address.text = vacancy?.address?.raw
                it.company.text = vacancy?.employer?.name
                it.salary.text =  convertSalary(vacancy?.salary, context)
            }
        }
    }

}

object DiffItemCallback: DiffUtil.ItemCallback<HHVacancy>() {

    override fun areItemsTheSame(oldItem: HHVacancy, newItem: HHVacancy): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: HHVacancy, newItem: HHVacancy): Boolean {
        return oldItem == newItem
    }
}