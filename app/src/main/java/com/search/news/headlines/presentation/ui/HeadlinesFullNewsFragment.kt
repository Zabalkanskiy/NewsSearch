package com.search.news.headlines.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
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
import androidx.core.content.ContextCompat
import androidx.core.os.BundleCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.search.news.R
import com.search.news.headlines.dagger.DaggerHeadlinesComponent
import com.search.news.headlines.presentation.presenter.presenterModel.fullNewsPresenter.FullNewsPresenterModel
import com.search.news.headlines.presentation.presenter.FullNewsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.launch
import javax.inject.Inject

const val URLTOIMAGE = "URLTOIMAGE"
const val TITLE = "TITLE"
const val CONTENT = "CONTENT"
const val PUBLISHEDAT = "PUBLISHEDAT"
const val SOURCE = "SOURCE"
const val URL = "URL"
const val DESCRIPTION = "DESCRIPTION"
const val FULLNEWSPRESENTERMODEL = "FULLNEWSPRESENTERMODEL"

class HeadlinesFullNewsFragment : Fragment() {


    lateinit var headlinesFullNewsViewModel: FullNewsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var fullNewsPresenterModel: FullNewsPresenterModel
    lateinit var fullNewsMenuItem: MenuItem
    var newsInDataBase: Boolean = false


    lateinit var myMenuProvider: MenuProvider

    lateinit var menuHost: MenuHost
    lateinit var hedlinesFullNewsToolbar: Toolbar
    lateinit var headlinesFullNewsCollapsingToolbar: CollapsingToolbarLayout
    lateinit var titleNewsTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var sourceTextView: TextView
    lateinit var describeNews: TextView
    lateinit var imageNews: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            fullNewsPresenterModel = BundleCompat.getParcelable(it,FULLNEWSPRESENTERMODEL, FullNewsPresenterModel::class.java) as FullNewsPresenterModel

        }

        DaggerHeadlinesComponent.builder().build().inject(this)
        headlinesFullNewsViewModel =
            ViewModelProvider(this, viewModelFactory).get(FullNewsViewModel::class.java)
        headlinesFullNewsViewModel.checkIfNewsExists(title = fullNewsPresenterModel.title, urlToImage = fullNewsPresenterModel.urlToImage)



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.headlines_full_news, container, false)



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
        headlinesFullNewsCollapsingToolbar.apply {
            title = fullNewsPresenterModel.title
            setExpandedTitleColor(ContextCompat.getColor(context, R.color.transparent_color))
            setCollapsedTitleTextColor(ContextCompat.getColor(context, R.color.white))
        }


        hedlinesFullNewsToolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        hedlinesFullNewsToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        Glide.with(this)
            .load(fullNewsPresenterModel.urlToImage)
            .placeholder(R.drawable.placeholder_bage)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageNews)
        titleNewsTextView.text = fullNewsPresenterModel.title
        dateTextView.text = fullNewsPresenterModel.publishedAt
        sourceTextView.text = fullNewsPresenterModel.source
       // describeNews.text = fullNewsPresenterModel.content
        val lastIndexChar = fullNewsPresenterModel.content.lastIndexOf("[")
        if (fullNewsPresenterModel.content.isNotBlank() && lastIndexChar != -1) {
            val spannableString = SpannableString(fullNewsPresenterModel.content)
            val lastIndex = fullNewsPresenterModel.content.length - 1



                val clickableSpan: ClickableSpan = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(fullNewsPresenterModel.url))
                        startActivity(browserIntent)

                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }
                }
                spannableString.setSpan(
                    clickableSpan,
                    lastIndexChar,
                    lastIndex,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                describeNews.text = spannableString
                describeNews.movementMethod = LinkMovementMethod.getInstance()

        } else {
            dateTextView.text = fullNewsPresenterModel.content
        }
        menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.full_news_menu, menu)
                fullNewsMenuItem = menu.findItem(R.id.menu_full_news_save)
                updateIcon()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_full_news_save -> {
                        if (newsInDataBase) {
                            headlinesFullNewsViewModel.deleteNews(fullNewsPresenterModel)
                        } else {
                            headlinesFullNewsViewModel.saveNews(fullNewsPresenterModel)
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


    }

    fun updateFromFlowIcon(isInDatabase: Boolean) {
        newsInDataBase = isInDatabase
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

            fullNewsPresenterModel: FullNewsPresenterModel
        ): HeadlinesFullNewsFragment {
            return HeadlinesFullNewsFragment().apply {
                arguments = Bundle().apply {

                    putParcelable(FULLNEWSPRESENTERMODEL, fullNewsPresenterModel)
                }

            }
        }
    }


}