package com.search.news.common.presentation.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.search.news.R
import com.search.news.common.dagger.DaggerFilterComponent
import com.search.news.common.mviState.FilterState
import com.search.news.common.mviState.Language
import com.search.news.common.mviState.Sort
import com.search.news.common.presentation.viewModel.FilterViewModel
import com.search.news.common.presentation.viewModel.MainViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import org.orbitmvi.orbit.viewmodel.observe
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class FilterFragment: Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var filterViewModel: FilterViewModel
    lateinit var mainViewModel: MainViewModel

    lateinit var materialToolbar: MaterialToolbar

    lateinit var buttonFirst : MaterialButton
    lateinit var buttonSecond: MaterialButton
    lateinit var buttonThird: MaterialButton
    lateinit var russianButton: Button
    lateinit var englishButton: Button
    lateinit var deutschButton: Button
    lateinit var todayImageButton: ImageButton
    lateinit var rangeTextView: TextView
    lateinit var datePickerRange: MaterialDatePicker<androidx.core.util.Pair<Long, Long>>


     var defaultColor: Int = 0
    var selectedColor: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        DaggerFilterComponent.builder().filterState(mainViewModel.getStateShared.value as FilterState) .build().inject(this)

        filterViewModel = ViewModelProvider(this, viewModelFactory).get(FilterViewModel::class.java)


        defaultColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
        selectedColor = Color.parseColor("#CEE7F0")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       return inflater.inflate(R.layout.filter_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         buttonFirst  = view.findViewById(R.id.filter_screen_button_first)
        buttonSecond = view.findViewById(R.id.filter_screen_button_second)
        buttonThird = view.findViewById(R.id.filter_screen_button_third)
        russianButton = view.findViewById(R.id.filter_screen_russian_button)
        englishButton = view.findViewById(R.id.filter_screen_english_button)
        deutschButton = view.findViewById(R.id.filter_screen_deutsch_button)
        materialToolbar = view.findViewById(R.id.filter_screen_toolbar)

        todayImageButton = view.findViewById(R.id.filter_screen_tooday_image_button)
        rangeTextView = view.findViewById(R.id.filter_screen_choose_date_text_view)


        val constraintsBuilder = CalendarConstraints.Builder().apply {
            setValidator(DateValidatorPointBackward.before(System.currentTimeMillis()))
        }

         datePickerRange = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select date")
            .setCalendarConstraints(constraintsBuilder.build())
             .setTheme(R.style.ThemeOverlay_App_DatePicker)
            .build()

        datePickerRange.addOnPositiveButtonClickListener {
            val dateRange = it
            filterViewModel.changeDate(dateRange)
            filterViewModel.closeDatePicker()


        }
        datePickerRange.addOnCancelListener{
            filterViewModel.closeDatePicker()

        }
        datePickerRange.addOnNegativeButtonClickListener {
          filterViewModel.closeDatePicker()
        }


            todayImageButton.setOnClickListener {
               filterViewModel.openDatePicker()
            }


        materialToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.filter_menu_check ->{
                    parentFragmentManager.popBackStack()

                    true
                }

                else -> false
            }
        }

        filterViewModel.observe(lifecycleOwner = this, state = ::render, sideEffect = ::handleSideEffect)



        buttonFirst.setOnClickListener{

            filterViewModel.changeSort(newSort = Sort.Popular)

        }

        buttonSecond.setOnClickListener {
            filterViewModel.changeSort(newSort = Sort.New)

        }

        buttonThird.setOnClickListener {
            filterViewModel.changeSort(newSort = Sort.Relevant)

        }

        russianButton.setOnClickListener {
            filterViewModel.changeLanguage(language = Language.Russian)
        }

        englishButton.setOnClickListener {
            filterViewModel.changeLanguage(language = Language.English)
        }

        deutschButton.setOnClickListener {
            filterViewModel.changeLanguage(language = Language.Deutsch)

        }
    }

    private fun render(state: FilterState){
        mainViewModel.changeSharedState(state)

        when(state.language){
            Language.Russian -> {
                russianButton.setBackgroundResource(R.drawable.filter_button_background)
                englishButton.setBackgroundResource(R.drawable.filter_button_shape)
                deutschButton.setBackgroundResource(R.drawable.filter_button_shape)
            }
            Language.English -> {
                russianButton.setBackgroundResource(R.drawable.filter_button_shape)
                englishButton.setBackgroundResource(R.drawable.filter_button_background)
                deutschButton.setBackgroundResource(R.drawable.filter_button_shape)
            }

            Language.Deutsch -> {
                russianButton.setBackgroundResource(R.drawable.filter_button_shape)
                englishButton.setBackgroundResource(R.drawable.filter_button_shape)
                deutschButton.setBackgroundResource(R.drawable.filter_button_background)
            }
        }

        when(state.sort){
            Sort.Popular ->{
                buttonFirst.backgroundTintList = ColorStateList.valueOf(selectedColor)
                buttonSecond.backgroundTintList = ColorStateList.valueOf(defaultColor)
                buttonThird.backgroundTintList = ColorStateList.valueOf(defaultColor)

                buttonFirst.setIconResource(R.drawable.black_check_24)
                buttonSecond.icon = null
                buttonThird.icon = null

            }
            Sort.New ->{
                buttonFirst.backgroundTintList = ColorStateList.valueOf(defaultColor)
                buttonSecond.backgroundTintList = ColorStateList.valueOf(selectedColor)
                buttonThird.backgroundTintList = ColorStateList.valueOf(defaultColor)

                buttonFirst.icon = null
                buttonSecond.setIconResource(R.drawable.black_check_24)
                buttonThird.icon = null}

            Sort.Relevant ->{
                buttonFirst.backgroundTintList = ColorStateList.valueOf(defaultColor)
                buttonSecond.backgroundTintList = ColorStateList.valueOf(defaultColor)
                buttonThird.backgroundTintList = ColorStateList.valueOf(selectedColor)

                buttonFirst.icon = null
                buttonSecond.icon = null
                buttonThird.setIconResource(R.drawable.black_check_24)
            }
        }

        if (state.date != null){

            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val outputFormat = SimpleDateFormat("MMM dd", Locale.US)
            val yearFormat = SimpleDateFormat("yyyy", Locale.US)

            val dateRange = state.date
            val startDate = inputFormat.parse(dateRange.start)
            val endDate = inputFormat.parse(dateRange.end)

            val startString = outputFormat.format(startDate)
            val endString = outputFormat.format(endDate)
            val yearString = yearFormat.format(endDate)

            val result = "$startString-$endString, $yearString"
            todayImageButton.setColorFilter(ContextCompat.getColor(requireContext(), R.color.proggress_color))
            rangeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.proggress_color))
            rangeTextView.text = result

        }else{
            todayImageButton.clearColorFilter()
            rangeTextView.text = getText(R.string.choose_date)
            rangeTextView.setTextColor(ContextCompat.getColor(requireContext(),R.color.refresh_text))
        }

        if (state.openDateRangePicker){
            datePickerRange.show(childFragmentManager, "date_picker")

        }

    }

    private fun handleSideEffect(sideEffect: Nothing){}
    
    companion object {
        fun newInstance(): FilterFragment {

            return FilterFragment()
        }
    }
}