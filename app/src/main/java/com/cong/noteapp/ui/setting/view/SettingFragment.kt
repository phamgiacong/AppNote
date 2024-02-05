package com.cong.noteapp.ui.setting.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cong.noteapp.MainActivity
import com.cong.noteapp.MainViewModel
import com.cong.noteapp.R
import com.cong.noteapp.base.BaseFragment
import com.cong.noteapp.data.model.LanguageModel
import com.cong.noteapp.databinding.DialogBottomLanguageBinding
import com.cong.noteapp.databinding.FragmentSettingBinding
import com.cong.noteapp.ui.setting.adapter.LanguageAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class SettingFragment : BaseFragment<FragmentSettingBinding>(),
    LanguageAdapter.OnClickItemLanguage {

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var dialog: BottomSheetDialog

    private lateinit var languages: List<LanguageModel>

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingBinding.inflate(layoutInflater)

    override fun initData() {
        languages = listOf(
            LanguageModel("1", R.drawable.england, getString(R.string.en), "en"),
            LanguageModel("2", R.drawable.japan, getString(R.string.ja), "ja"),
            LanguageModel("3", R.drawable.laos, getString(R.string.lo), "lo"),
            LanguageModel("4", R.drawable.thailand, getString(R.string.th), "th"),
            LanguageModel("5", R.drawable.vietnam, getString(R.string.vi), "vi"),
            LanguageModel("6", R.drawable.china, getString(R.string.zh), "zh"),
        )
    }

    override fun bindView() {

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            mainViewModel.checkedDarkMode.postValue(isChecked)
        }

        showViewLanguage(checkLanguage())

    }

    override fun onClickListener() {
        binding.layoutBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.layoutLanguage.setOnClickListener {
            setDialogBottom()
        }
    }

    override fun observeData() {
        mainViewModel.checkedDarkMode.observe(viewLifecycleOwner) {
            binding.switchDarkMode.isChecked = it

            if (it) {
                binding.imgDarkMode.setImageResource(R.drawable.nightlight)
                (requireActivity() as MainActivity).setDarkMode()
            } else {
                binding.imgDarkMode.setImageResource(R.drawable.sunny)
                (requireActivity() as MainActivity).setLightMode()
            }

            val editor = requireActivity().getSharedPreferences("changeMode", Context.MODE_PRIVATE)
            editor.edit().putBoolean("darkMode", it).apply()

        }

        mainViewModel.language.observe(viewLifecycleOwner) {

            showViewLanguage(it)

        }
    }

    private fun setDialogBottom() {
        dialog = BottomSheetDialog(requireContext(), R.style.DialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val bindingDialog = DialogBottomLanguageBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        bindingDialog.rcvLanguage.adapter = LanguageAdapter(languages, this, checkLanguage())

        dialog.window?.let {
            it.attributes.windowAnimations = R.style.DialogAnimation
        }

        dialog.show()
    }

    override fun setLanguage(language: LanguageModel) {
        (requireActivity() as MainActivity).saveLanguage(language.keyword ?: "en")
        mainViewModel.language.postValue(language)
        dialog.dismiss()
    }

    private fun showViewLanguage(language: LanguageModel) {
        binding.imgLanguage.setImageResource(language.image ?: 0)

        binding.tvLanguage.text = when (language.keyword) {
            "en" -> {
                "English"
            }

            "ja" -> {
                "日本語"
            }

            "lo" -> {
                "ລາວ"
            }

            "th" -> {
                "ไทย"
            }

            "vi" -> {
                "Tiếng Việt"
            }

            "zh" -> {
                "中文"
            }

            else -> {
                binding.imgLanguage.setImageResource(R.drawable.earth)
                "Language"
            }
        }
    }

    private fun checkLanguage(): LanguageModel {
        val editor = requireActivity().getSharedPreferences("changeMode", Context.MODE_PRIVATE)
        val language = editor.getString("language", "")

        var currentLanguage = LanguageModel()

        if (languages.isNotEmpty()) {
            for (item in languages) {
                if (item.keyword == language.toString()) {
                    currentLanguage = item
                }
            }
        }
        return currentLanguage
    }
}