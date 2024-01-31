package com.aston.astonintensivfinal.saved.presentation.ui

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
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.common.mviState.FilterState
import com.aston.astonintensivfinal.common.presentation.ui.FilterFragment
import com.aston.astonintensivfinal.common.presentation.ui.InternalErrorFragment
import com.aston.astonintensivfinal.common.presentation.viewModel.MainViewModel
import com.aston.astonintensivfinal.core.recycler.NewsAdapter
import com.aston.astonintensivfinal.core.recycler.modelRecycler.NewsInterface
import com.aston.astonintensivfinal.databinding.SavedListNewsBinding
import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.fullNewsPresenter.FullNewsPresenterModel
import com.aston.astonintensivfinal.headlines.presentation.ui.FILTER_FRAGMENT
import com.aston.astonintensivfinal.headlines.presentation.ui.FULL_NEWS_FRAGMENT
import com.aston.astonintensivfinal.headlines.presentation.ui.HeadlinesFullNewsFragment
import com.aston.astonintensivfinal.headlines.presentation.ui.INTERNAL_ERROR_FRAGMENT
import com.aston.astonintensivfinal.saved.dagger.DaggerSavedListNewsComponent
import com.aston.astonintensivfinal.saved.presentation.repository.SavedListViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavedListFragment : Fragment() {
    private var _binding: SavedListNewsBinding? = null
    private val binding get() = _binding!!

    lateinit var newsAdapter: NewsAdapter

    private val searchJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + searchJob)


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var savedListViewModel: SavedListViewModel
    lateinit var mainViewModel: MainViewModel

    lateinit var filterState: FilterState
    lateinit var menuHost: MenuHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerSavedListNewsComponent.builder().build().inject(this)


        newsAdapter = NewsAdapter(onClickAction = { article: NewsInterface, position: Int ->

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.lottie_view_fragment_container,
                        HeadlinesFullNewsFragment.newInstance(

                            FullNewsPresenterModel(
                                urlToImage = article.urlToImage ?: "",
                                title = article.title ?: "",
                                content = article.content ?: "",
                                publishedAt = article.publishedAt ?: "",
                                source = article.source ?: "",
                                description = article.description ?: "",
                                url = article.url ?: "",
                                idSource = article.idSource ?: ""
                            )
                        )
                    )

                    .addToBackStack(FULL_NEWS_FRAGMENT)
                    .commit()


        })
        savedListViewModel = ViewModelProvider(this, viewModelFactory).get(
            SavedListViewModel::class.java
        )


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SavedListNewsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @androidx.annotation.OptIn(com.google.android.material.badge.ExperimentalBadgeUtils::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)
         filterState = mainViewModel.getStateShared.value as FilterState

        savedListViewModel.loadNewsFromRepository(filterState = filterState)

        menuHost = requireActivity()
        binding.savedListNewsRecycler.adapter = newsAdapter
        binding.savedListNewsRecycler.layoutManager = LinearLayoutManager(context)
        savedListViewModel.newsList.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
            binding.savedListNewsProgressBar.visibility = View.INVISIBLE
            binding.savedListNewsRecycler.visibility = View.VISIBLE

        }

        savedListViewModel.internalError.observe(viewLifecycleOwner){
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.lottie_view_fragment_container,
                    InternalErrorFragment.newInstance(),
                    INTERNAL_ERROR_FRAGMENT
                )
                .addToBackStack(INTERNAL_ERROR_FRAGMENT)
                .commit()
        }

        val needBage: Boolean = savedListViewModel.hasBage(filterState)

        val searchItem = binding.savedListToolbar.menu.findItem(R.id.top_bar_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        if (needBage){

            val badgeDrawable = BadgeDrawable.create(requireContext()) // Create a new BadgeDrawable
            badgeDrawable.backgroundColor = Color.RED
            BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.savedListToolbar, R.id.top_bar_menu)
        }

        searchItem.setOnMenuItemClickListener {

            searchView.requestFocus()
            true
        }

        searchView.setOnCloseListener {
            newsAdapter.submitList(null)
            savedListViewModel.loadNewsFromRepository(filterState = filterState)
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


        binding.savedListToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.top_bar_menu -> {
                    parentFragmentManager.beginTransaction()

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

        newsAdapter.submitList(null)
        savedListViewModel.searchNewsFromQuery(filterState =  filterState, query = query)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(): SavedListFragment {
            return SavedListFragment()
        }
    }
}