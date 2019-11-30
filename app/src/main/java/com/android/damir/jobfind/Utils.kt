package com.android.damir.jobfind

import android.content.Context
import com.android.damir.jobfind.network.Salary

fun convertSalary(salary: Salary?, context: Context): String{
    if(salary?.from != null && salary.to != null){
        return context.resources.getString(R.string.salary, salary.from, salary.to, salary.currency)
    }else if(salary?.to == null && salary?.from != null){
        return context.resources.getString(R.string.salary_from, salary.from, salary.currency)
    }else if(salary?.from == null && salary?.to != null){
        return context.resources.getString(R.string.salary_to, salary.to, salary.currency)
    }

    return ""
}