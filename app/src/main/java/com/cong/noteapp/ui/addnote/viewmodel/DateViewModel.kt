package com.cong.noteapp.ui.addnote.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
class DateViewModel : ViewModel() {

    var day = MutableLiveData<String>()
    var month = 0
    var year = 0
    var date = MutableLiveData<String>()

    fun getAllDaysInMonth(year: Int, month: Int): List<String> {
        val yearMonth = YearMonth.of(year, month)

        val daysInMonth = arrayListOf<String>()

        for (day in 1..yearMonth.lengthOfMonth()) {
            daysInMonth.add(day.toString())
        }

        return daysInMonth

    }

}