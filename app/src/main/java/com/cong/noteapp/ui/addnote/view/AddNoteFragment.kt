package com.cong.noteapp.ui.addnote.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cong.noteapp.R
import com.cong.noteapp.base.BaseFragment
import com.cong.noteapp.data.model.NoteModel
import com.cong.noteapp.databinding.DialogAskSaveBinding
import com.cong.noteapp.databinding.DialogNotificationBinding
import com.cong.noteapp.databinding.FragmentAddNoteBinding
import com.cong.noteapp.ui.addnote.viewdialog.adapter.DaysInMonthAdapter
import com.cong.noteapp.ui.addnote.viewdialog.adapter.DaysOfWeekAdapter
import com.cong.noteapp.ui.addnote.viewmodel.DateViewModel
import com.cong.noteapp.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(), DaysInMonthAdapter.OnClick {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val dateViewModel: DateViewModel by viewModels()
    private lateinit var daysOfWeek: List<String>
    private lateinit var monthsInYear: List<String>
    private val today = LocalDate.now()

    private var year = 0
    private var month = 0

    private var enabled = false
    private var enabledTime = false

    private var enabledLayoutCalendar = false
    private var enabledLayoutTime = false

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddNoteBinding.inflate(inflater, container, false)

    override fun initData() {

        year = today.year
        month = today.monthValue

        daysOfWeek = listOf(
            getString(R.string.sun),
            getString(R.string.mon),
            getString(R.string.tue),
            getString(R.string.web),
            getString(R.string.thu),
            getString(R.string.fri),
            getString(R.string.sat)
        )

        monthsInYear = listOf(
            getString(R.string.january),
            getString(R.string.february),
            getString(R.string.march),
            getString(R.string.april),
            getString(R.string.may),
            getString(R.string.june),
            getString(R.string.july),
            getString(R.string.august),
            getString(R.string.september),
            getString(R.string.october),
            getString(R.string.november),
            getString(R.string.december)
        )
    }

    override fun bindView() {

        binding.layoutSave.background.setTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.primary_layout_icon_no
            )
        )
        binding.edtTitle.doAfterTextChanged {
            if (it?.isNotEmpty() == true) {
                binding.layoutSave.background.setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.primary_layout_icon
                    )
                )
                return@doAfterTextChanged
            }
            binding.layoutSave.background.setTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.primary_layout_icon_no
                )
            )

        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClickListener() {

        binding.layoutSave.setOnClickListener {
            if (binding.edtTitle.length() > 0) {
                dialogAsk(getString(R.string.save_changes), getString(R.string.save))
                return@setOnClickListener
            }
        }

        binding.layoutBack.setOnClickListener {
            if (binding.edtTitle.length() > 0 || binding.edtContent.length() > 0) {
                dialogAsk(getString(R.string.text_content_dialog_cancel), getString(R.string.keep))
                return@setOnClickListener
            }
            findNavController().popBackStack()

        }

        binding.layoutNotification.setOnClickListener {
            showDialogNotification()
        }

    }

    @SuppressLint("SetTextI18n")
    override fun observeData() {
        dateViewModel.date.observe(viewLifecycleOwner) { date ->
            if (date.isNotEmpty()) {
                if (date == "${convertMonthNumberToName(month)} ${today.dayOfMonth}, $year") {
                    binding.tvNotification.text = getString(R.string.today)
                    return@observe
                }
                binding.tvNotification.text = date
                return@observe
            }
            binding.tvNotification.text = getString(R.string.add_a_notification)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dialogAsk(contentDialog: String, textButton: String) {
        val dialog = Dialog(requireContext(), R.style.DialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        val bindingDialog = DialogAskSaveBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        bindingDialog.tvContentDialog.text = contentDialog

        bindingDialog.tvButtonSave.text = textButton

        bindingDialog.tvButtonDiscard.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.tvButtonSave.setOnClickListener {

            if (bindingDialog.tvButtonSave.text == getString(R.string.save)) {
                insertData()
            }
            dialog.dismiss()
            findNavController().popBackStack()

        }

        dialog.window?.let {
            it.attributes.windowAnimations = R.style.DialogAnimation
        }

        dialog.show()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertData() {
        val now = LocalDate.now()
        val note = NoteModel(
            0,
            binding.edtTitle.text.toString(),
            binding.edtContent.text.toString(),
            "${now.dayOfMonth}/${now.monthValue}/${now.year}",
            homeViewModel.randomColor()
        )
        homeViewModel.insertNote(note)
    }

    private fun showDialogNotification() {
        val dialog = Dialog(requireContext(), R.style.DialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val bindingDialog = DialogNotificationBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        bindViewDialog(bindingDialog)
        setOnClickListenerDialog(bindingDialog)
        observeDialog(bindingDialog)

        dialog.window?.let {
            it.attributes.windowAnimations = R.style.DialogNotificationAnimation
            it.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            it.setGravity(Gravity.TOP)
        }

        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun bindViewDialog(bindingDialog: DialogNotificationBinding) {

        //bind view date
        if (enabled) {
            showCalendar(bindingDialog)
        } else {
            resetCalendar()
            hideCalendar(bindingDialog)
        }

        bindingDialog.switchDate.isChecked = enabled

        //set switch date
        bindingDialog.switchDate.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enabled = true
                addCalendar()
                showCalendar(bindingDialog)
                enabledLayoutCalendar = true
                return@setOnCheckedChangeListener
            }

            enabled = false
            bindingDialog.switchTime.isChecked = false
            resetCalendar()
            hideCalendar(bindingDialog)
            enabledLayoutCalendar = false
        }

        bindingDialog.rcvDaysOfWeek.adapter = DaysOfWeekAdapter(daysOfWeek)

        showDateToView(bindingDialog)

        //bind view time
        if (enabledTime) {
            showTimer(bindingDialog)
        } else {
            hideTimer(bindingDialog)
        }

        bindingDialog.numberPickerHour.minValue = 0
        bindingDialog.numberPickerHour.maxValue = 23
        bindingDialog.numberPickerHour.setFormatter { value -> String.format("%02d", value) }

        bindingDialog.numberPickerMinutes.minValue = 0
        bindingDialog.numberPickerMinutes.maxValue = 59
        bindingDialog.numberPickerMinutes.setFormatter { value -> String.format("%02d", value) }

        bindingDialog.switchTime.isChecked = enabledTime

        //set switch time
        bindingDialog.switchTime.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (!enabled) {
                    enabled = true
                    addCalendar()
                    bindingDialog.switchDate.isChecked = enabled
                    bindingDialog.tvDate.visibility = View.VISIBLE
                    enabledLayoutCalendar = false
                }
                bindingDialog.layoutCalendar.visibility = View.GONE
                enabledTime = true
                showTimer(bindingDialog)
                enabledLayoutTime = true
                return@setOnCheckedChangeListener
            }
            enabledTime = false
            hideTimer(bindingDialog)
            enabledLayoutCalendar = false
        }

        if (enabled || enabledTime) {
            bindingDialog.layoutCalendar.visibility = View.GONE
            bindingDialog.layoutTimer.visibility = View.GONE
            enabledLayoutCalendar = false
            enabledLayoutTime = false
        }
    }

    private fun setOnClickListenerDialog(bindingDialog: DialogNotificationBinding) {
        bindingDialog.imgUp.setOnClickListener {
            month -= 1
            if (month < 1) {
                year -= 1
                month = 12
            }
            showDateToView(bindingDialog)
        }

        bindingDialog.imgDown.setOnClickListener {
            month += 1
            if (month > 12) {
                year += 1
                month = 1
            }
            showDateToView(bindingDialog)
        }

        //click layout date
        bindingDialog.layoutDate.setOnClickListener {
            if (!bindingDialog.switchDate.isChecked) {
                return@setOnClickListener
            }
            if (!enabledLayoutCalendar) {
                bindingDialog.layoutCalendar.visibility = View.VISIBLE
                enabledLayoutCalendar = true
                if (enabledLayoutTime) {
                    bindingDialog.layoutTimer.visibility = View.GONE
                    enabledLayoutTime = false
                }
            } else {
                enabledLayoutCalendar = false
                bindingDialog.layoutCalendar.visibility = View.GONE
            }

        }

        //click layout time
        bindingDialog.layoutTime.setOnClickListener {
            if (!bindingDialog.switchTime.isChecked) {
                return@setOnClickListener
            }
            if (!enabledLayoutTime) {
                bindingDialog.layoutTimer.visibility = View.VISIBLE
                enabledLayoutTime = true
                if (enabledLayoutCalendar) {
                    bindingDialog.layoutCalendar.visibility = View.GONE
                    enabledLayoutCalendar = false
                }
            } else {
                enabledLayoutTime = false
                bindingDialog.layoutTimer.visibility = View.GONE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeDialog(bindingDialog: DialogNotificationBinding) {
        dateViewModel.date.observe(viewLifecycleOwner) {
            if (it == "${convertMonthNumberToName(month)} ${today.dayOfMonth}, $year") {
                bindingDialog.tvDate.text = getString(R.string.today)
                return@observe
            }
            bindingDialog.tvDate.text = it
        }
    }

    @SuppressLint("SetTextI18n")
    fun showDateToView(bindingDialog: DialogNotificationBinding) {
        val daysOfWeekNew = mutableListOf<String>()
        val firstDayOfMonth = LocalDate.of(year, month, 1)
        var dayOfWeek = firstDayOfMonth.dayOfWeek.value

        if (dayOfWeek == 7) {
            dayOfWeek = 0
        }
        for (i in 0 until dayOfWeek) {
            daysOfWeekNew.add("")
        }

        val mutableDaysInMonth =
            daysOfWeekNew + dateViewModel.getAllDaysInMonth(year, month)

        bindingDialog.rcvDaysInMonth.adapter =
            DaysInMonthAdapter(
                mutableDaysInMonth,
                this,
                requireContext(),
                dayOfWeek,
                year,
                month,
                dateViewModel,
                this
            )

        bindingDialog.tvMonthYear.text = "${convertMonthNumberToName(month)} $year"
    }

    private fun convertMonthNumberToName(month: Int): String {

        return if (month in 1..monthsInYear.size) {
            monthsInYear[month - 1]
        } else {
            "Invalid month"
        }
    }

    private fun resetCalendar() {
        dateViewModel.day.postValue("")
        dateViewModel.date.postValue("")
        dateViewModel.year = 0
        dateViewModel.month = 0
    }

    private fun addCalendar() {
        dateViewModel.day.postValue("${today.dayOfMonth}")
        dateViewModel.year = year
        dateViewModel.month = month
        dateViewModel.date.postValue("${convertMonthNumberToName(month)} ${today.dayOfMonth}, $year")
    }

    private fun showCalendar(bindingDialog: DialogNotificationBinding) {
        bindingDialog.layoutCalendar.visibility = View.VISIBLE
        bindingDialog.tvDate.visibility = View.VISIBLE
    }

    private fun hideCalendar(bindingDialog: DialogNotificationBinding) {
        bindingDialog.tvDate.visibility = View.GONE
        bindingDialog.layoutCalendar.visibility = View.GONE
    }

    private fun showTimer(bindingDialog: DialogNotificationBinding) {
        bindingDialog.layoutTimer.visibility = View.VISIBLE
        bindingDialog.tvTime.visibility = View.VISIBLE
    }

    private fun hideTimer(bindingDialog: DialogNotificationBinding) {
        bindingDialog.tvTime.visibility = View.GONE
        bindingDialog.layoutTimer.visibility = View.GONE
    }

    override fun selectDayInMonth(date: String) {
        dateViewModel.day.postValue(date)
        dateViewModel.date.postValue("${convertMonthNumberToName(month)} $date, $year")
        dateViewModel.year = year
        dateViewModel.month = month
    }

}