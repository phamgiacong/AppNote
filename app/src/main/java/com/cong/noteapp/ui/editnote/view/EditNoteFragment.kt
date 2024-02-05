package com.cong.noteapp.ui.editnote.view

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cong.noteapp.MainViewModel
import com.cong.noteapp.R
import com.cong.noteapp.base.BaseFragment
import com.cong.noteapp.data.model.NoteModel
import com.cong.noteapp.databinding.DialogAskSaveBinding
import com.cong.noteapp.databinding.FragmentEditNoteBinding
import com.cong.noteapp.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNoteFragment : BaseFragment<FragmentEditNoteBinding>() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()

    private var id: Int = 0
    private lateinit var title: String
    private lateinit var content: String
    private lateinit var date: String
    private var background: Int = 0

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditNoteBinding.inflate(layoutInflater)

    override fun initData() {

    }

    override fun bindView() {

        binding.layoutEdit.background.setTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.primary_layout_icon_no
            )
        )
        binding.edtTitle.doAfterTextChanged {
            if ((it?.isNotEmpty() == true)) {
                binding.layoutEdit.background.setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.primary_layout_icon
                    )
                )
                return@doAfterTextChanged
            }
            binding.layoutEdit.background.setTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.primary_layout_icon_no
                )
            )
        }
    }

    override fun onClickListener() {

        binding.layoutEdit.setOnClickListener {
            if (binding.edtTitle.length() > 0) {
                dialogAsk(getString(R.string.save_changes), getString(R.string.save))
                return@setOnClickListener
            }

        }

        binding.layoutDelete.setOnClickListener {
            dialogAsk(getString(R.string.text_content_dialog_delete), getString(R.string.confirm))
        }

        binding.layoutBack.setOnClickListener {
            if (binding.edtTitle.text.toString() != title || binding.edtContent.text.toString() != content) {
                dialogAsk(getString(R.string.text_content_dialog_cancel), getString(R.string.keep))
                return@setOnClickListener
            }
            findNavController().popBackStack()

        }

    }

    override fun observeData() {
        mainViewModel.sendNote.observe(viewLifecycleOwner) {
            binding.edtTitle.setText(it.title)
            binding.edtContent.setText(it.content)
            binding.tvDate.text = it.date

            title = it.title
            content = it.content
            id = it.id
            date = it.date
            background = it.background
        }
    }

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

            when (bindingDialog.tvButtonSave.text) {
                getString(R.string.save) -> updateNote()
                getString(R.string.confirm) -> deleteNote()
            }

            dialog.dismiss()
            findNavController().popBackStack()

        }

        dialog.window?.let {
            it.attributes.windowAnimations = R.style.DialogAnimation
        }

        dialog.show()

    }

    private fun deleteNote() {
        val note = NoteModel(
            id,
            title,
            content,
            date,
            background
        )
        homeViewModel.deleteNote(note)
    }

    private fun updateNote() {
        val note = NoteModel(
            id,
            binding.edtTitle.text.toString(),
            binding.edtContent.text.toString(),
            date,
            background
        )
        homeViewModel.updateNote(note)
    }
}