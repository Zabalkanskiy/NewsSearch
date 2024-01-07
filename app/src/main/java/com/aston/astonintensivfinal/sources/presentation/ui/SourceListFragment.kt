package com.aston.astonintensivfinal.sources.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aston.astonintensivfinal.data.headlinesmodel.Article
import com.aston.astonintensivfinal.databinding.SourcesListBinding
import com.aston.astonintensivfinal.sources.dagger.DaggerSourcesComponent
import com.aston.astonintensivfinal.sources.presentation.recycler.SourceListAdapter
import com.aston.astonintensivfinal.sources.presentation.viewmodel.SourceListViewModel
import com.aston.astonintensivfinal.sources.presentation.viewmodel.SourceListViewModelFactory
import com.aston.astonintensivfinal.sources.presentation.viewmodel.model.SourceNewsVM
import com.aston.astonintensivfinal.utils.Utils
import javax.inject.Inject

class SourceListFragment : Fragment() {
    lateinit var sourcesAdapter: SourceListAdapter
    private var _binding: SourcesListBinding? = null
    private val binding get() = _binding!!

    lateinit var sourceListViewModel: SourceListViewModel

    @Inject lateinit var sourceListViewModelFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerSourcesComponent.builder().build().inject(this)

        sourceListViewModel = ViewModelProvider(this, sourceListViewModelFactory).get(SourceListViewModel::class.java)
        
        sourcesAdapter = SourceListAdapter { sourceNews: SourceNewsVM, position : Int ->

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SourcesListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sourceListRecyclerView.adapter = sourcesAdapter
        binding.sourceListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.sourceListSearchButton.setOnClickListener {  }
        binding.sourceListFilterButton.setOnClickListener {  }

        sourceListViewModel.getSourcesData(apiKey = Utils.NEWSAPIKEY, page = 1, pageSize = 20)
        sourceListViewModel.getListSourceNews().observe(this, {
            sourcesAdapter.submitList(it)
            binding.sourceListRecyclerView.visibility = View.VISIBLE
            binding.sourceListProgressBar.visibility = View.INVISIBLE
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun newInstance(): SourceListFragment {

            return SourceListFragment()
        }

    }
}