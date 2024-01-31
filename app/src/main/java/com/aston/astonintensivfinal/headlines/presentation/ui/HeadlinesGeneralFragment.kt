package com.aston.astonintensivfinal.headlines.presentation.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.common.mviState.DateRange
import com.aston.astonintensivfinal.common.mviState.FilterState
import com.aston.astonintensivfinal.common.mviState.Language
import com.aston.astonintensivfinal.common.mviState.Sort
import com.aston.astonintensivfinal.common.presentation.ui.FilterFragment
import com.aston.astonintensivfinal.core.hasNetwork
import com.aston.astonintensivfinal.core.recycler.PaginationScrollListener
import com.aston.astonintensivfinal.headlines.dagger.DaggerHeadlinesComponent
import com.aston.astonintensivfinal.headlines.presentation.moxyinterface.HeadlinesView
import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.headlinesNews.HPresenterNewsModelData
import com.aston.astonintensivfinal.headlines.presentation.presenter.Category
import com.aston.astonintensivfinal.headlines.presentation.presenter.HeadlinesPresenter
import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.fullNewsPresenter.FullNewsPresenterModel
import com.aston.astonintensivfinal.headlines.presentation.recycler.HeadlinesGeneralAdapter
import com.aston.astonintensivfinal.common.presentation.ui.InternalErrorFragment
import com.aston.astonintensivfinal.common.presentation.ui.NoInternetConnectionFragment
import com.aston.astonintensivfinal.common.presentation.viewModel.MainViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment
import javax.inject.Inject

const val FULL_NEWS_FRAGMENT = "FULL_NEWS_FRAGMENT"
const val NETWORK_ERROR_FRAGMENT = "NETWORK_ERROR_FRAGMENT"
const val INTERNAL_ERROR_FRAGMENT = "INTERNAL_ERROR_FRAGMENT"
const val FILTER_FRAGMENT = "FILTER_FRAGMENT"

class HeadlinesGeneralFragment : MvpAppCompatFragment(), HeadlinesView {

    var isLastPage: Boolean = false
    var isLoading: Boolean = false


    private val searchJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + searchJob)



    lateinit var headlines_swipe_to_refresh: SwipeRefreshLayout
    lateinit var headlines_recycler_view: RecyclerView

    lateinit var headlinesTabLayout: TabLayout

    @Inject
    lateinit var headlinesPresenter: HeadlinesPresenter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var mainViewModel: MainViewModel

    lateinit var filterState: FilterState

    lateinit var headlinesAdapter: HeadlinesGeneralAdapter
    lateinit var headlinesProgressBar: CircularProgressIndicator
    lateinit var headlinesBottomProgressBar: CircularProgressIndicator
    lateinit var materialToolbar: MaterialToolbar

    lateinit var languageFromFilter: Language
    lateinit var sortFromFilter: Sort
     var dateFromFilter: DateRange? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        headlinesAdapter =
            HeadlinesGeneralAdapter { article: HPresenterNewsModelData, position: Int ->
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
                                idSource = article.idSource
                            )
                        ),
                        FULL_NEWS_FRAGMENT
                    )
                    .addToBackStack(FULL_NEWS_FRAGMENT)
                    .commit()


            }




    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DaggerHeadlinesComponent.builder().build().inject(this)

        headlinesPresenter.attachView(this)
        //need add layout
        return inflater.inflate(R.layout.headlines_tabs_and_recycler, container, false)
    }

    @androidx.annotation.OptIn(com.google.android.material.badge.ExperimentalBadgeUtils::class)
    override fun   onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)


        languageFromFilter = mainViewModel.getStateShared.value?.language as Language
        sortFromFilter = mainViewModel.getStateShared.value?.sort as Sort
        dateFromFilter = mainViewModel.getStateShared.value?.date


        headlinesAdapter.submitList(null)
        headlinesPresenter.clearData()





        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())


        headlines_swipe_to_refresh = view.findViewById(R.id.headlines_swipe_to_refresh_layout)
        headlinesBottomProgressBar = view.findViewById(R.id.headlines_list_progress_bar)
        headlinesProgressBar = view.findViewById(R.id.headlines_general_progress_bar)
        headlinesTabLayout = view.findViewById(R.id.headlines_tab_layout)
        headlines_recycler_view = view.findViewById(R.id.headlines_list_recycler_view)
        materialToolbar = view.findViewById(R.id.headlines_tab_toolbar)

        headlines_recycler_view.layoutManager = linearLayoutManager
        headlines_recycler_view.adapter = headlinesAdapter
        headlinesPresenter.loadNextData(isOnline = hasNetwork(requireContext()), filterState = mainViewModel.getStateShared.value as FilterState )


        val tabIcons = intArrayOf(R.drawable.world_sphere, R.drawable.coin_hand, R.drawable.atom)
        val tabTexts = arrayOf("General", "Business", "Travelling")

        for (i in tabIcons.indices) {
            val tab: TabLayout.Tab = headlinesTabLayout.newTab()
            val view: View = LayoutInflater.from(context).inflate(R.layout.custom_tabs, null)
            val icon = view.findViewById<ImageView>(R.id.headlines_custom_tab_image_view)
            val text = view.findViewById<TextView>(R.id.headlines_custom_tab_textView)
            icon.setImageResource(tabIcons[i])
            text.text = tabTexts[i]

            tab.setCustomView(view)
            headlinesTabLayout.addTab(tab)
        }

        headlinesTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val customView = tab.customView
                val textView =
                    customView?.findViewById<TextView>(R.id.headlines_custom_tab_textView)
                textView?.setTextColor(Color.parseColor("#00677C"))

                tab?.let {
                    val selectedTabPosition = it.position

                    when (selectedTabPosition) {
                        0 -> {


                            headlinesPresenter.changeCategory(
                                Category.GENERAL,
                                isOnline = hasNetwork(requireContext()), filterState = mainViewModel.getStateShared.value as FilterState
                            )
                        }

                        1 -> {

                            headlinesPresenter.changeCategory(
                                Category.BUSINESS,
                                isOnline = hasNetwork(requireContext()),
                                filterState = mainViewModel.getStateShared.value as FilterState
                            )
                        }

                        2 -> {

                            headlinesPresenter.changeCategory(
                                Category.TRAVELLING,
                                isOnline = hasNetwork(requireContext()),
                                filterState = mainViewModel.getStateShared.value as FilterState
                            )
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

                val customView = tab.customView
                val textView =
                    customView?.findViewById<TextView>(R.id.headlines_custom_tab_textView)
                textView?.setTextColor(Color.parseColor("#40484B"))
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


        headlinesTabLayout.getTabAt(0)?.select()

        val selectedTabView = headlinesTabLayout.getTabAt(0)?.customView
        val selectedTextView =
            selectedTabView?.findViewById<TextView>(R.id.headlines_custom_tab_textView)
        selectedTextView?.setTextColor(Color.parseColor("#00677C"))



        headlines_recycler_view.addOnScrollListener(object :
            PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                headlinesBottomProgressBar.visibility = View.VISIBLE
                isLoading = true
                headlinesPresenter.loadNextData(isOnline = hasNetwork(requireContext()), filterState = mainViewModel.getStateShared.value as FilterState)


            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })

        headlines_swipe_to_refresh.setOnRefreshListener {
            isLastPage = false
            headlinesPresenter.clearData()
            val isOnline = hasNetwork(requireContext())
            headlinesPresenter.loadNextData(isOnline, filterState = mainViewModel.getStateShared.value as FilterState)
            headlinesAdapter.submitList(null)
        }

        headlines_swipe_to_refresh.setColorSchemeResources(R.color.system_prymary_color)

        val needBage:Boolean = headlinesPresenter.hasBage(filterState = mainViewModel.getStateShared.value as FilterState)


        val searchItem = materialToolbar.menu.findItem(R.id.top_bar_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        if (needBage){

            val badgeDrawable = BadgeDrawable.create(requireContext()) // Create a new BadgeDrawable
            badgeDrawable.backgroundColor = Color.RED
            BadgeUtils.attachBadgeDrawable(badgeDrawable, materialToolbar, R.id.top_bar_menu)
        }
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
                        .debounce(300)
                        .collect { query ->

                            handleSearchQuery(query, filterState = mainViewModel.getStateShared.value as FilterState)
                        }
                }

                return false
            }
        })

        materialToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.top_bar_menu ->{
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

    fun handleSearchQuery(query:String, filterState: FilterState){
        isLastPage = false
        headlinesPresenter.clearData()
        headlinesPresenter.loadNextData(hasNetwork(requireContext()), query = query, filterState = filterState)
        headlinesAdapter.submitList(null)

    }


    override fun showData(totalResults: Int?, data: List<HPresenterNewsModelData>) {
        isLoading = false
        headlinesBottomProgressBar.visibility = View.INVISIBLE
        if (data.size == totalResults) {
            isLastPage = true
        }

        headlinesProgressBar.visibility = View.INVISIBLE


        headlinesAdapter.submitList(data)
        headlines_recycler_view.visibility = View.VISIBLE
        headlines_swipe_to_refresh.visibility = View.VISIBLE
        headlines_swipe_to_refresh.isRefreshing = false
    }





    override fun changeTab() {
        headlinesBottomProgressBar.visibility = View.INVISIBLE
        headlinesProgressBar.visibility = View.VISIBLE
        headlines_recycler_view.visibility = View.INVISIBLE
        headlines_swipe_to_refresh.visibility = View.INVISIBLE
        headlinesAdapter.submitList(null)
        headlines_recycler_view.scrollToPosition(0)
    }

    override fun networkError() {
        parentFragmentManager.beginTransaction()
            .replace( R.id.lottie_view_fragment_container, NoInternetConnectionFragment.newInstance(), NETWORK_ERROR_FRAGMENT)
            .addToBackStack(NETWORK_ERROR_FRAGMENT)
            .commit()
    }

    override fun internalError() {
        parentFragmentManager.beginTransaction()
            .replace( R.id.lottie_view_fragment_container, InternalErrorFragment.newInstance(), INTERNAL_ERROR_FRAGMENT)
            .addToBackStack(INTERNAL_ERROR_FRAGMENT)
            .commit()
    }

    companion object {
        fun newInstance(): HeadlinesGeneralFragment {
            return HeadlinesGeneralFragment()
        }
    }
}