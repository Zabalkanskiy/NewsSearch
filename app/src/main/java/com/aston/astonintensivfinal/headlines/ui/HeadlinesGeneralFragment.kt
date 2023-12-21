package com.aston.astonintensivfinal.headlines.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.data.Article
import com.aston.astonintensivfinal.headlines.moxyinterface.HeadlinesView
import com.aston.astonintensivfinal.headlines.presenter.HeadlinesPresenter
import com.aston.astonintensivfinal.headlines.recycler.HeadlinesGeneralAdapter
import moxy.MvpAppCompatFragment

const val FULL_NEWS_FRAGMENT = "FULL_NEWS_FRAGMENT"
class HeadlinesGeneralFragment : MvpAppCompatFragment(), HeadlinesView{
    lateinit var headlinesPresenter: HeadlinesPresenter
    lateinit var headlines_recycler_view: RecyclerView
    lateinit var headlinesAdapter: HeadlinesGeneralAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        headlinesAdapter = HeadlinesGeneralAdapter{ article: Article, position: Int ->
            childFragmentManager.beginTransaction()
                //need add news data to fragment
                .replace(R.id.headlines_general_fragment, HeadlinesFullNewsFragment.newInstance())
                .addToBackStack(FULL_NEWS_FRAGMENT)
                .commit()




        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        headlinesPresenter = HeadlinesPresenter()
        headlinesPresenter.attachView(this)
        //need add layout
        return inflater.inflate(R.layout.headlines_general_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        headlines_recycler_view = view.findViewById(R.id.headlines_general_recycler_view)
        headlines_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        headlines_recycler_view.adapter = headlinesAdapter
        headlinesPresenter.loadData()


    }



    override fun showData(data: List<Article>) {
        headlinesAdapter.submitList(data)
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance(): HeadlinesGeneralFragment{
            return HeadlinesGeneralFragment()
        }
    }
}