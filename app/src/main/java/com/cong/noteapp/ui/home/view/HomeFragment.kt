package com.cong.noteapp.ui.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cong.noteapp.MainViewModel
import com.cong.noteapp.R
import com.cong.noteapp.base.BaseFragment
import com.cong.noteapp.data.model.NoteModel
import com.cong.noteapp.databinding.FragmentHomeBinding
import com.cong.noteapp.ui.home.adapter.HomeAdapter
import com.cong.noteapp.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeAdapter.OnClick {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(layoutInflater)

    override fun initData() {

    }

    override fun bindView() {

        //setup RecyclerView
        binding.rcvNote.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rcvNote.setHasFixedSize(true)

    }

    override fun onClickListener() {
        binding.layoutAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
        binding.layoutSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchNoteFragment)
        }
        binding.layoutSetting.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }
    }

    override fun observeData() {
        homeViewModel.note.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.layoutNoNote.visibility = View.GONE
                binding.rcvNote.visibility = View.VISIBLE
                binding.rcvNote.adapter = HomeAdapter(it, requireContext(), this)
                return@observe
            }
            binding.layoutNoNote.visibility = View.VISIBLE
            binding.rcvNote.visibility = View.GONE

        }
    }

    override fun edit(note: NoteModel) {
        mainViewModel.sendNote.postValue(note)
        findNavController().navigate(R.id.action_homeFragment_to_editNoteFragment)
    }
}