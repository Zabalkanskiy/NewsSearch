package com.aston.astonintensivfinal.saved.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.core.recycler.NewsAdapter
import com.aston.astonintensivfinal.core.recycler.modelRecycler.NewsInterface
import com.aston.astonintensivfinal.databinding.SavedListNewsBinding
import com.aston.astonintensivfinal.headlines.presentation.presenter.presenterModel.fullNewsPresenter.FullNewsPresenterModel
import com.aston.astonintensivfinal.headlines.presentation.ui.FULL_NEWS_FRAGMENT
import com.aston.astonintensivfinal.headlines.presentation.ui.HeadlinesFullNewsFragment
import com.aston.astonintensivfinal.saved.dagger.DaggerSavedListNewsComponent
import com.aston.astonintensivfinal.saved.presentation.repository.SavedListViewModel
import com.aston.astonintensivfinal.sources.dagger.DaggerSourcesComponent
import com.aston.astonintensivfinal.sources.presentation.viewmodel.SourceListViewModel
import javax.inject.Inject

class SavedListFragment : Fragment() {
    private var _binding: SavedListNewsBinding? = null
    private val binding get() = _binding!!

    lateinit var newsAdapter: NewsAdapter


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var savedListViewModel: SavedListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerSavedListNewsComponent.builder().build().inject(this)
        // DaggerSourcesComponent.builder().build().inject(this)

        newsAdapter = NewsAdapter(onClickAction = { article: NewsInterface, position: Int ->

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.lottie_view_fragment_container,
                        HeadlinesFullNewsFragment.newInstance(
                            /*FullNewsPresenterModel(
                            urlToImage = article.urlToImage ?: "",
                            title = article.title ?: "",
                            content = article.content ?: "",
                            publishedAt = article.publishedAt ?: "",
                            source = article.source ?: "",
                            description = article.description ?: "",
                            url = article.url ?: ""

                         */
                            FullNewsPresenterModel(
                                urlToImage = article.urlToImage ?: "",
                                title = article.title ?: "",
                                content = article.content ?: "",
                                publishedAt = article.publishedAt ?: "",
                                source = article.source ?: "",
                                description = article.description ?: "",
                                url = article.url ?: ""
                            )
                        )
                    )

                    .addToBackStack(FULL_NEWS_FRAGMENT)
                    .commit()


        })
        savedListViewModel = ViewModelProvider(this, viewModelFactory).get(
            SavedListViewModel::class.java
        )

        savedListViewModel.loadNewsFromRepository()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.savedListNewsRecycler.adapter = newsAdapter
        binding.savedListNewsRecycler.layoutManager = LinearLayoutManager(context)
        savedListViewModel.newsList.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
            binding.savedListNewsProgressBar.visibility = View.INVISIBLE
            binding.savedListNewsRecycler.visibility = View.VISIBLE

        }


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