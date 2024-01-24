package com.aston.astonintensivfinal.sources.presentation.ui

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.core.createMenuProvider
import com.aston.astonintensivfinal.core.hasNetwork
import com.aston.astonintensivfinal.core.networkConnection
import com.aston.astonintensivfinal.databinding.SourcesListBinding
import com.aston.astonintensivfinal.headlines.presentation.ui.INTERNAL_ERROR_FRAGMENT
import com.aston.astonintensivfinal.headlines.presentation.ui.NETWORK_ERROR_FRAGMENT
import com.aston.astonintensivfinal.presentation.InternalErrorFragment
import com.aston.astonintensivfinal.presentation.MainActivity
import com.aston.astonintensivfinal.presentation.NoInternetConnectionFragment
import com.aston.astonintensivfinal.sources.dagger.DaggerSourcesComponent
import com.aston.astonintensivfinal.sources.presentation.recycler.SourceListAdapter
import com.aston.astonintensivfinal.sources.presentation.viewmodel.SourceListViewModel
import com.aston.astonintensivfinal.sources.presentation.viewmodel.model.sourseList.SourceNewsVM
import com.aston.astonintensivfinal.utils.Utils
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerSourcesComponent.factory().create(AstonIntensivApplication.getAstonApplicationContext.appComponent).inject(this)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sourceListRecyclerView.adapter = sourcesAdapter
        binding.sourceListRecyclerView.layoutManager = LinearLayoutManager(context)


        menuHost = requireActivity()

        val isNetwork = hasNetwork(requireContext())

        sourceListViewModel.getSourcesData(
            apiKey = Utils.NEWSAPIKEY,
            page = 1,
            pageSize = 20,
            isOnline = isNetwork
        )
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
            // Perform search when menu item is clicked
            searchView.requestFocus()
            true
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private var searchQuery = ""

            override fun onQueryTextSubmit(query: String): Boolean {
                // Обрабатываем отправку поискового запроса
                // Возвращаем true, чтобы указать, что мы обработали событие
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Обрабатываем изменение текста поискового запроса
                searchQuery = newText
                uiScope.launch {
                    flow {
                        emit(searchQuery)
                    }
                        .debounce(300) // Устанавливаем задержку
                        .collect { query ->
                            // Обрабатываем поисковый запрос после задержки
                            // Этот блок кода будет выполнен только после того, как пройдет 300 мс без изменения поискового запроса пользователем
                            handleSearchQuery(query)
                        }
                }
                // Возвращаем false, чтобы SearchView выполнил действие по умолчанию
                return false
            }
        })



        /*

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.headlines_top_bar_menu, menu)
                val searchItem = menu.findItem(R.id.top_bar_search)
                val searchView = searchItem.actionView as SearchView

             //   val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
             //   editText.setTextColor(Color.WHITE)  // Устанавливаем цвет текста
             //   editText.setHintTextColor(Color.WHITE)  // Устанавливаем цвет текста подсказки

                searchItem.setOnMenuItemClickListener {
                    // Perform search when menu item is clicked
                    searchView.requestFocus()
                    true
                }

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    private var searchQuery = ""

                    override fun onQueryTextSubmit(query: String): Boolean {
                        // Обрабатываем отправку поискового запроса
                        // Возвращаем true, чтобы указать, что мы обработали событие
                        searchView.clearFocus()
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        // Обрабатываем изменение текста поискового запроса
                        searchQuery = newText
                        uiScope.launch {
                            flow {
                                emit(searchQuery)
                            }
                                .debounce(300) // Устанавливаем задержку
                                .collect { query ->
                                    // Обрабатываем поисковый запрос после задержки
                                    // Этот блок кода будет выполнен только после того, как пройдет 300 мс без изменения поискового запроса пользователем
                                    handleSearchQuery(query)
                                }
                        }
                        // Возвращаем false, чтобы SearchView выполнил действие по умолчанию
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

         */



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