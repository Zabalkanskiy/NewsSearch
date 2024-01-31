package com.aston.astonintensivfinal.sources.presentation.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.common.mviState.DateRange
import com.aston.astonintensivfinal.common.mviState.FilterState
import com.aston.astonintensivfinal.common.mviState.Language
import com.aston.astonintensivfinal.common.mviState.Sort
import com.aston.astonintensivfinal.common.presentation.ui.FilterFragment
import com.aston.astonintensivfinal.core.hasNetwork
import com.aston.astonintensivfinal.core.recycler.NewsAdapter
import com.aston.astonintensivfinal.core.recycler.PaginationScrollListener
import com.aston.astonintensivfinal.core.recycler.modelRecycler.NewsInterface
import com.aston.astonintensivfinal.databinding.OneSourceListLayoutBinding
import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.fullNewsPresenter.FullNewsPresenterModel
import com.aston.astonintensivfinal.headlines.presentation.ui.FULL_NEWS_FRAGMENT
import com.aston.astonintensivfinal.headlines.presentation.ui.HeadlinesFullNewsFragment
import com.aston.astonintensivfinal.headlines.presentation.ui.INTERNAL_ERROR_FRAGMENT
import com.aston.astonintensivfinal.headlines.presentation.ui.NETWORK_ERROR_FRAGMENT
import com.aston.astonintensivfinal.common.presentation.ui.InternalErrorFragment
import com.aston.astonintensivfinal.common.presentation.ui.NoInternetConnectionFragment
import com.aston.astonintensivfinal.common.presentation.viewModel.MainViewModel
import com.aston.astonintensivfinal.headlines.presentation.ui.FILTER_FRAGMENT
import com.aston.astonintensivfinal.sources.dagger.DaggerSourcesComponent
import com.aston.astonintensivfinal.sources.presentation.viewmodel.OneSourceListViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val NAMESOURCE = "NAMESOURCE"
const val IDSOURCE = "IDSOURCE"

class OneSourceLIstFragment : Fragment() {

    private var _binding: OneSourceListLayoutBinding? = null
    private val binding get() = _binding!!


    var isLastPage: Boolean = false
    var isLoading: Boolean = false


    private val searchJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + searchJob)

    lateinit var menuHost: MenuHost
    lateinit var nameSource: String
    lateinit var idSource: String
    lateinit var newsAdapter: NewsAdapter
    lateinit var oneSourceListViewModel: OneSourceListViewModel

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var mainViewModel: MainViewModel



    lateinit var languageFromFilter: Language
    lateinit var sortFromFilter: Sort
    var dateFromFilter: DateRange? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerSourcesComponent.factory()
            .create(AstonIntensivApplication.getAstonApplicationContext.appComponent).inject(this)
        arguments?.let {
            nameSource = it.getString(NAMESOURCE, "")
            idSource = it.getString(IDSOURCE, "")
        }
        oneSourceListViewModel =
            ViewModelProvider(this, modelFactory).get(OneSourceListViewModel::class.java)
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
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OneSourceListLayoutBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @androidx.annotation.OptIn(com.google.android.material.badge.ExperimentalBadgeUtils::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity(), modelFactory).get(MainViewModel::class.java)
        languageFromFilter = mainViewModel.getStateShared.value?.language as Language
        sortFromFilter = mainViewModel.getStateShared.value?.sort as Sort
        dateFromFilter = mainViewModel.getStateShared.value?.date

        newsAdapter.submitList(null)
        oneSourceListViewModel.clearData()
        oneSourceListViewModel.clearMediator()

        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())

        val isHasNetwork = hasNetwork(requireContext())

        oneSourceListViewModel.loadNextData(idSource, isNetwork = isHasNetwork,  language = languageFromFilter,
            fromDate = dateFromFilter?.start ?: "",
            toDate = dateFromFilter?.end ?: "",
            sortBy = sortFromFilter,)

        (activity as AppCompatActivity).setSupportActionBar(binding.oneSourceListToolbar)

        val needBage:Boolean = oneSourceListViewModel.hasBage(filterState = mainViewModel.getStateShared.value as FilterState)


        binding.oneSourceListToolbar.title = nameSource
        binding.oneSourceListToolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        binding.oneSourceListToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.oneSourceListToolbar.setOnMenuItemClickListener { menuItem ->
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


        if (needBage){
            val badgeDrawable = BadgeDrawable.create(requireContext()) // Create a new BadgeDrawable
            badgeDrawable.backgroundColor = Color.RED
            BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.oneSourceListToolbar, R.id.top_bar_menu)

        }


        menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.headlines_top_bar_menu, menu)
                val searchItem = menu.findItem(R.id.top_bar_search)
                val searchView = searchItem.actionView as SearchView

                val editText =
                    searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
                editText.setTextColor(Color.WHITE)  // Устанавливаем цвет текста
                editText.setHintTextColor(Color.WHITE)  // Устанавливаем цвет текста подсказки




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

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {

                    else -> false
                }
            }


        }, viewLifecycleOwner)


        binding.oneSourceListRecyclerView.adapter = newsAdapter
        binding.oneSourceListRecyclerView.layoutManager = linearLayoutManager

        binding.oneSourceListRecyclerView.addOnScrollListener(object :
            PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                binding.oneSourceListRecyclerProgressBar.visibility = View.VISIBLE
                isLoading = true
                val isOnline = hasNetwork(requireContext())
                oneSourceListViewModel.loadNextData(source = idSource, isNetwork = isOnline,  language = languageFromFilter,
                    fromDate = dateFromFilter?.start ?: "",
                    toDate = dateFromFilter?.end ?: "",
                    sortBy = sortFromFilter,)


            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })

        binding.oneSourceListSwipeToRefreshLayout.setOnRefreshListener {
            oneSourceListViewModel.clearData()
            newsAdapter.submitList(null)
            val isOnline = hasNetwork(requireContext())
            oneSourceListViewModel.loadNextData(source = idSource, isNetwork = isOnline,  language = languageFromFilter,
                fromDate = dateFromFilter?.start ?: "",
                toDate = dateFromFilter?.end ?: "",
                sortBy = sortFromFilter,)
            isLastPage = false

        }

        binding.oneSourceListSwipeToRefreshLayout.setColorSchemeResources(R.color.system_prymary_color)





        oneSourceListViewModel.getListNews.observe(viewLifecycleOwner) {

            isLoading = false
            binding.oneSourceListProgressBar.visibility = View.INVISIBLE
            binding.oneSourceListRecyclerProgressBar.visibility = View.INVISIBLE

            if (it.totalResults == it.articles.size) {
                isLastPage = true
            }
            newsAdapter.submitList(it.articles)
            binding.oneSourceListSwipeToRefreshLayout.visibility = View.VISIBLE
            binding.oneSourceListRecyclerView.visibility = View.VISIBLE
            binding.oneSourceListSwipeToRefreshLayout.isRefreshing = false

        }

        oneSourceListViewModel.getNetworkNewsError.observe(viewLifecycleOwner) {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.lottie_view_fragment_container,
                    NoInternetConnectionFragment.newInstance(),
                    NETWORK_ERROR_FRAGMENT
                )
                .addToBackStack(NETWORK_ERROR_FRAGMENT)
                .commit()
        }

        oneSourceListViewModel.getInterlalNewsError.observe(viewLifecycleOwner) {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.lottie_view_fragment_container,
                    InternalErrorFragment.newInstance(),
                    INTERNAL_ERROR_FRAGMENT
                )
                .addToBackStack(INTERNAL_ERROR_FRAGMENT)
                .commit()
        }


    }

    private fun handleSearchQuery(query: String) {
        // Обрабатываем поисковый запрос здесь
        newsAdapter.submitList(null)
        val isOnline = hasNetwork(requireContext())
        oneSourceListViewModel.searchOnString(
            source = idSource,
            searchString = query,
            isNetwork = isOnline,
            language = languageFromFilter,
            fromDate = dateFromFilter?.start ?: "",
            toDate = dateFromFilter?.end ?: "",
            sortBy = sortFromFilter,
        )
        isLastPage = false

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(nameSource: String, idSource: String): OneSourceLIstFragment {
            val args = Bundle().apply {
                putString(NAMESOURCE, nameSource)
                putString(IDSOURCE, idSource)
            }

            val fragment = OneSourceLIstFragment()
            fragment.arguments = args
            return fragment
        }

    }
}