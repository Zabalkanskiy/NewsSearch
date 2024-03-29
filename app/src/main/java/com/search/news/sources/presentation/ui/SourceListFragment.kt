package com.search.news.sources.presentation.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.search.news.SearchApplication
import com.search.news.R
import com.search.news.common.mviState.FilterState
import com.search.news.common.presentation.ui.FilterFragment
import com.search.news.core.hasNetwork
import com.search.news.databinding.SourcesListBinding
import com.search.news.headlines.presentation.ui.INTERNAL_ERROR_FRAGMENT
import com.search.news.headlines.presentation.ui.NETWORK_ERROR_FRAGMENT
import com.search.news.common.presentation.ui.InternalErrorFragment
import com.search.news.common.presentation.ui.NoInternetConnectionFragment
import com.search.news.common.presentation.viewModel.MainViewModel
import com.search.news.core.data.retrofit.NEWSAPIKEY
import com.search.news.headlines.presentation.ui.FILTER_FRAGMENT
import com.search.news.sources.dagger.DaggerSourcesComponent
import com.search.news.sources.presentation.recycler.SourceListAdapter
import com.search.news.sources.presentation.viewmodel.SourceListViewModel
import com.search.news.sources.presentation.viewmodel.model.sourseList.SourceNewsVM
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val ONESOURCELISTFRAGMENT ="ONESOURCELISTFRAGMENT"
class SourceListFragment : Fragment() {
    lateinit var sourcesAdapter: SourceListAdapter

    lateinit var menuHost: MenuHost

    private var _binding: SourcesListBinding? = null
    private val binding get() = _binding!!

    lateinit var sourceListViewModel: SourceListViewModel

    private val searchJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + searchJob)

    @Inject lateinit var sourceListViewModelFactory: ViewModelProvider.Factory
    lateinit var mainViewModel: MainViewModel

    lateinit var filterState: FilterState


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerSourcesComponent.factory().create(SearchApplication.getAstonApplicationContext.appComponent).inject(this)

        sourceListViewModel = ViewModelProvider(this, sourceListViewModelFactory).get(SourceListViewModel::class.java)


        sourcesAdapter = SourceListAdapter { sourceNews: SourceNewsVM, position : Int ->
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.lottie_view_fragment_container, OneSourceLIstFragment.newInstance(idSource = sourceNews.id ?: "", nameSource = sourceNews.name ?: ""),ONESOURCELISTFRAGMENT)
                .addToBackStack(ONESOURCELISTFRAGMENT)
                .commit()

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

    @androidx.annotation.OptIn(com.google.android.material.badge.ExperimentalBadgeUtils::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity(), sourceListViewModelFactory).get(MainViewModel::class.java)



        binding.sourceListRecyclerView.adapter = sourcesAdapter
        binding.sourceListRecyclerView.layoutManager = LinearLayoutManager(context)

        mainViewModel.getStateShared.observe(viewLifecycleOwner){
            binding.sourceListRecyclerView.visibility = View.INVISIBLE
            binding.sourceListProgressBar.visibility = View.VISIBLE
            sourcesAdapter.submitList(null)
            binding.sourceListRecyclerView.layoutManager?.scrollToPosition(0)
            sourceListViewModel.clearData()
            val isNetwork = hasNetwork(requireContext())

            sourceListViewModel.getSourcesData(
                apiKey = NEWSAPIKEY,
                page = 1,
                pageSize = 20,
                isOnline = isNetwork,
                filterState = it
            )

        }


        filterState = mainViewModel.getStateShared.value as FilterState
        val needBage:Boolean = sourceListViewModel.hasBage(filterState)
        if (needBage){
            val badgeDrawable = BadgeDrawable.create(requireContext()) // Create a new BadgeDrawable
            badgeDrawable.backgroundColor = Color.RED
            BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.oneSourceListToolbar, R.id.top_bar_menu)

        }


        menuHost = requireActivity()




        sourceListViewModel.getListSourceNews().observe(viewLifecycleOwner) {
            sourcesAdapter.submitList(it)
            binding.sourceListRecyclerView.visibility = View.VISIBLE
            binding.sourceListProgressBar.visibility = View.INVISIBLE
        }


        sourceListViewModel.getInternalErrorResponse().observe(viewLifecycleOwner) {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.lottie_view_fragment_container,
                    InternalErrorFragment.newInstance(),
                    INTERNAL_ERROR_FRAGMENT
                )
                .addToBackStack(INTERNAL_ERROR_FRAGMENT)
                .commit()
        }

        sourceListViewModel.getNetworkErrorResponce().observe(viewLifecycleOwner) {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.lottie_view_fragment_container,
                    NoInternetConnectionFragment.newInstance(),
                    NETWORK_ERROR_FRAGMENT
                )
                .addToBackStack(NETWORK_ERROR_FRAGMENT)
                .commit()
        }

        val searchItem = binding.oneSourceListToolbar.menu.findItem(R.id.top_bar_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchItem.setOnMenuItemClickListener {

            searchView.requestFocus()
            true
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private var searchQuery = ""

            override fun onQueryTextSubmit(query: String): Boolean {

                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                searchQuery = newText
                uiScope.launch {
                    flow {
                        emit(searchQuery)
                    }
                        .debounce(300) // Устанавливаем задержку
                        .collect { query ->

                            handleSearchQuery(query)
                        }
                }

                return false
            }
        })



        binding.oneSourceListToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.top_bar_menu -> {
                    parentFragmentManager.beginTransaction()
                        //need add news data to fragment
                        .replace(
                            R.id.lottie_view_fragment_container,
                            FilterFragment.newInstance(),
                            FILTER_FRAGMENT
                        )
                        .addToBackStack(FILTER_FRAGMENT)
                        .commit()

                    true
                }

                else -> false
            }
        }



    }
    fun handleSearchQuery(query: String){
        sourceListViewModel.findSearchSources(query)

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