package com.cong.noteapp.ui.searchnote.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cong.noteapp.MainViewModel
import com.cong.noteapp.R
import com.cong.noteapp.base.BaseFragment
import com.cong.noteapp.data.model.NoteModel
import com.cong.noteapp.databinding.FragmentSearchNoteBinding
import com.cong.noteapp.ui.home.adapter.HomeAdapter
import com.cong.noteapp.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SearchNoteFragment : BaseFragment<FragmentSearchNoteBinding>(), HomeAdapter.OnClick {

    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchNoteBinding.inflate(layoutInflater)

    override fun initData() {

    }

    override fun bindView() {
        binding.edtSearch.doAfterTextChanged {

        }
    }

    override fun onClickListener() {
        binding.layoutBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imgClose.setOnClickListener {
            binding.edtSearch.setText("")
        }
    }

    override fun observeData() {
        binding.edtSearch.doAfterTextChanged { editable ->
            val text = editable.toString().lowercase(Locale.getDefault())

            if (text.isEmpty()) {
                binding.layoutNoNote.visibility = View.VISIBLE
                binding.rcvNote.visibility = View.GONE
                return@doAfterTextChanged
            }

            homeViewModel.note.observe(viewLifecycleOwner) { notes ->
                binding.rcvNote.adapter = HomeAdapter(
                    notes.filter {
                        it.title.lowercase(Locale.getDefault())
                            .contains(text)
                    },
                    requireContext(),
                    this
                )
                binding.layoutNoNote.visibility = View.GONE
                binding.rcvNote.visibility = View.VISIBLE
            }
        }

    }

    override fun edit(note: NoteModel) {
        mainViewModel.sendNote.postValue(note)
        findNavController().navigate(R.id.action_searchNoteFragment_to_editNoteFragment)
    }
}