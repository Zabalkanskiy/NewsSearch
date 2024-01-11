package com.aston.astonintensivfinal.headlines.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.headlines.dagger.DaggerHeadlinesComponent
import com.aston.astonintensivfinal.headlines.presentation.moxyinterface.HeadlinesView
import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.headlinesNews.HPresenterNewsModelData
import com.aston.astonintensivfinal.headlines.presentation.presenter.Category
import com.aston.astonintensivfinal.headlines.presentation.presenter.HeadlinesPresenter
import com.aston.astonintensivfinal.headlines.presentation.recycler.HeadlinesGeneralAdapter
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.tabs.TabLayout
import moxy.MvpAppCompatFragment
import javax.inject.Inject

const val FULL_NEWS_FRAGMENT = "FULL_NEWS_FRAGMENT"

class HeadlinesGeneralFragment : MvpAppCompatFragment(), HeadlinesView {
    //paginating recycler
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    //end paginating


    lateinit var headlines_swipe_to_refresh: SwipeRefreshLayout
    lateinit var headlines_recycler_view: RecyclerView
    lateinit var searchImageButton: ImageButton
    lateinit var filterImageButton: ImageButton
    lateinit var headlinesTabLayout: TabLayout

    @Inject
    lateinit var headlinesPresenter: HeadlinesPresenter

    lateinit var headlinesAdapter: HeadlinesGeneralAdapter
    lateinit var headlinesProgressBar: CircularProgressIndicator
    lateinit var headlinesBottomProgressBar: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        headlinesAdapter = HeadlinesGeneralAdapter { article: HPresenterNewsModelData, position: Int ->
            parentFragmentManager.beginTransaction()
                //need add news data to fragment
                .replace(R.id.lottie_view_fragment_container,
                    HeadlinesFullNewsFragment.newInstance(
                        article.urlToImage ?: "",
                        article.title ?: "",
                        article.content ?: "",
                        article.publishedAt ?: "",
                        article.source?.name ?: ""
                    )
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())


        headlines_swipe_to_refresh = view.findViewById(R.id.headlines_swipe_to_refresh_layout)
        headlinesBottomProgressBar = view.findViewById(R.id.headlines_list_progress_bar)
        headlinesProgressBar = view.findViewById(R.id.headlines_general_progress_bar)
        headlinesTabLayout = view.findViewById(R.id.headlines_tab_layout)
        headlines_recycler_view = view.findViewById(R.id.headlines_list_recycler_view)
        headlines_recycler_view.layoutManager = linearLayoutManager
        headlines_recycler_view.adapter = headlinesAdapter
        headlinesPresenter.loadNextData()


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
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Обработка события выбора вкладки
                tab?.let {
                    val selectedTabPosition = it.position
                    // Действия при выборе определенной вкладки
                    when (selectedTabPosition) {
                        0 -> {
                            // Действия для первой вкладки
                           headlinesPresenter.changeCategory(Category.GENERAL)
                        }
                        1 -> {
                            // Действия для второй вкладки
                            headlinesPresenter.changeCategory(Category.BUSINESS)
                        }
                        2 -> {
                            // Действия для третьей вкладки
                            headlinesPresenter.changeCategory(Category.TRAVELLING)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Обработка события, когда вкладка перестает быть выбранной
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Обработка события, когда выбранная вкладка повторно выбрана
            }
        })

      /*  headlines_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener(){



            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val total = headlinesAdapter.itemCount
                val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

                if (lastVisibleItem == (total -1) ){

                    //load new data
                    headlinesPresenter.loadNextData(category = "general", language = "en", country = "us")
                    //show progress bar
                }

                super.onScrolled(recyclerView, dx, dy)

            }



        })

       */
        headlines_recycler_view.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager){
            override fun loadMoreItems() {
                headlinesBottomProgressBar.visibility = View.VISIBLE
                isLoading = true
                headlinesPresenter.loadNextData()


            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })

        headlines_swipe_to_refresh.setOnRefreshListener {
            headlinesPresenter.clearData()
            headlinesPresenter.loadNextData()
            headlinesAdapter.submitList(null)
        }

        headlines_swipe_to_refresh.setColorSchemeResources(R.color.system_prymary_color)


    }


    override fun showData(totalResults: Int?, data: List<HPresenterNewsModelData>) {
        isLoading = false
        headlinesBottomProgressBar.visibility = View.INVISIBLE
        if (data.size == totalResults){
            isLastPage = true
        }

        headlinesProgressBar.visibility = View.INVISIBLE


        headlinesAdapter.submitList(data)
        headlines_recycler_view.visibility = View.VISIBLE
        headlines_swipe_to_refresh.visibility = View.VISIBLE
        headlines_swipe_to_refresh.isRefreshing = false
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun changeTab() {
        headlinesBottomProgressBar.visibility = View.INVISIBLE
        headlinesProgressBar.visibility = View.VISIBLE
        headlines_recycler_view.visibility = View.INVISIBLE
        headlines_swipe_to_refresh.visibility = View.INVISIBLE
        headlinesAdapter.submitList(null)
        headlines_recycler_view.scrollToPosition(0)
    }
    companion object {
        fun newInstance(): HeadlinesGeneralFragment {
            return HeadlinesGeneralFragment()
        }
    }
}