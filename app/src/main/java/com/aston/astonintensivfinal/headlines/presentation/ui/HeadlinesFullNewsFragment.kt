package com.aston.astonintensivfinal.headlines.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.headlines.dagger.DaggerHeadlinesComponent
import com.aston.astonintensivfinal.headlines.presentation.moxyinterface.HeadlinesFullNewsView
import com.aston.astonintensivfinal.headlines.presentation.moxyinterface.model.fullNewsPresenter.FullNewsPresenterModel
import com.aston.astonintensivfinal.headlines.presentation.presenter.FullNewsViewModel
import com.aston.astonintensivfinal.headlines.presentation.presenter.HeadlinesFullNewsPresenter
import com.aston.astonintensivfinal.sources.presentation.viewmodel.SourceListViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment
import javax.inject.Inject

const val URLTOIMAGE = "URLTOIMAGE"
const val TITLE = "TITLE"
const val CONTENT = "CONTENT"
const val PUBLISHEDAT = "PUBLISHEDAT"
const val SOURCE = "SOURCE"

class HeadlinesFullNewsFragment : Fragment() /*, HeadlinesFullNewsView */ {
  /*  @Inject
    lateinit var headlinesFullNewsPresenter: HeadlinesFullNewsPresenter

   */

    lateinit var headlinesFullNewsViewModel: FullNewsViewModel

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var fullNewsPresenterModel: FullNewsPresenterModel
    lateinit var fullNewsMenuItem: MenuItem
    var newsInDataBase: Boolean = false
   // lateinit var resourceIcon


    lateinit var myMenuProvider: MenuProvider

    lateinit var menuHost: MenuHost
    lateinit var hedlinesFullNewsToolbar: Toolbar
    lateinit var headlinesFullNewsCollapsingToolbar: CollapsingToolbarLayout
    lateinit var titleNewsTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var sourceTextView: TextView
    lateinit var describeNews: TextView
    lateinit var imageNews: ImageView


    lateinit var urlToImage: String
    lateinit var titleNews: String
    lateinit var content: String
    lateinit var publishedAt: String
    lateinit var source: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            urlToImage = it.getString(URLTOIMAGE, "")
            titleNews = it.getString(TITLE, "")
            content = it.getString(CONTENT, "")
            publishedAt = it.getString(PUBLISHEDAT, "")
            source = it.getString(SOURCE, "")
        }

        DaggerHeadlinesComponent.builder().build().inject(this)
        headlinesFullNewsViewModel = ViewModelProvider(this, viewModelFactory).get(FullNewsViewModel::class.java)
        headlinesFullNewsViewModel.checkIfNewsExists(title = titleNews, urlToImage = urlToImage)


        fullNewsPresenterModel = FullNewsPresenterModel(urlToImage = urlToImage, titleNews = titleNews, content = content, publishedAt = publishedAt, source = source )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.headlines_full_news, container, false)
      //  headlinesFullNewsPresenter.attachView(this)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hedlinesFullNewsToolbar = view.findViewById(R.id.toolbar)
        headlinesFullNewsCollapsingToolbar = view.findViewById(R.id.toolbar_layout)
        titleNewsTextView = view.findViewById(R.id.full_news_text_view_title)
        dateTextView = view.findViewById(R.id.full_news_text_view_date)
        sourceTextView = view.findViewById(R.id.full_news_text_view_source)
        describeNews = view.findViewById(R.id.full_news_all_text_view)
        imageNews = view.findViewById(R.id.imageView)
        (activity as AppCompatActivity).setSupportActionBar(hedlinesFullNewsToolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(true)
      //  headlinesFullNewsPresenter.checkIfNewsExists(title = titleNews, urlToImage = urlToImage)
        headlinesFullNewsCollapsingToolbar.apply {
            title = titleNews
            setExpandedTitleColor(ContextCompat.getColor(context, R.color.transparent_color))
            setCollapsedTitleTextColor(ContextCompat.getColor(context, R.color.white))
        }

       // (activity as? AppCompatActivity)?.setSupportActionBar(hedlinesFullNewsToolbar)
        hedlinesFullNewsToolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        hedlinesFullNewsToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        //hedlinesFullNewsToolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.system_prymary_color))
        Glide.with(this)
            .load(urlToImage)
            .placeholder(R.drawable.placeholder_bage)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageNews)
        titleNewsTextView.text = titleNews
        dateTextView.text = publishedAt
        sourceTextView.text = source
        describeNews.text = content

         menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
               menuInflater.inflate(R.menu.full_news_menu, menu)
                fullNewsMenuItem = menu.findItem(R.id.menu_full_news_save)
                updateIcon()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.menu_full_news_save-> {
                        if (newsInDataBase){
                           // menuItem.setIcon(R.drawable.bookmark_border_24px)
                            headlinesFullNewsViewModel.deleteNews(fullNewsPresenterModel)
                          //  headlinesFullNewsPresenter.deleteNews(fullNewsPresenterModel)
                        } else{
                         //   menuItem.setIcon(R.drawable.bookmark_24px)
                            headlinesFullNewsViewModel.saveNews(fullNewsPresenterModel)
                          //  headlinesFullNewsPresenter.saveNews(fullNewsPresenterModel)
                        }

                        true
                    }
                    else -> false
                }
            }


        }, viewLifecycleOwner)




        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                headlinesFullNewsViewModel.newsInDataBase.collect {
                   updateFromFlowIcon(it)
                }
            }
        }

        /*
         myMenuProvider = object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.full_news_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.menu_full_news_save-> {
                        if (newsInDataBase){
                            menuItem.setIcon(R.drawable.bookmark_border_24px)
                            headlinesFullNewsViewModel.deleteNews(fullNewsPresenterModel)
                            //  headlinesFullNewsPresenter.deleteNews(fullNewsPresenterModel)
                        } else{
                            menuItem.setIcon(R.drawable.bookmark_24px)
                            headlinesFullNewsViewModel.saveNews(fullNewsPresenterModel)
                            //  headlinesFullNewsPresenter.saveNews(fullNewsPresenterModel)
                        }

                        true
                    }
                    else -> false
                }
            }

        }

         */



    }

    fun updateFromFlowIcon(isInDatabase: Boolean){
        newsInDataBase = isInDatabase
   //     menuHost.addMenuProvider(myMenuProvider, viewLifecycleOwner)
        requireActivity().invalidateOptionsMenu()



    }


    private fun updateIcon() {
        if (newsInDataBase) {
            fullNewsMenuItem.setIcon(R.drawable.bookmark_24px)
        } else {
            fullNewsMenuItem.setIcon(R.drawable.bookmark_border_24px)
        }
    }








    companion object {
        fun newInstance(
            urlToImage: String,
            title: String,
            content: String,
            publishedAt: String,
            source: String
        ): HeadlinesFullNewsFragment {
            return HeadlinesFullNewsFragment().apply {
                arguments = Bundle().apply {
                    putString(URLTOIMAGE, urlToImage)
                    putString(TITLE, title)
                    putString(CONTENT, content)
                    putString(PUBLISHEDAT, publishedAt)
                    putString(SOURCE, source)
                }

            }
        }
    }


}