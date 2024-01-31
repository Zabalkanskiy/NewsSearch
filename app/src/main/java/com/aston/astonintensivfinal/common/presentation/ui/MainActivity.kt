package com.aston.astonintensivfinal.common.presentation.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.airbnb.lottie.LottieAnimationView
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.common.dagger.DaggerMainComponent
import com.aston.astonintensivfinal.common.presentation.viewModel.MainViewModel
import com.aston.astonintensivfinal.core.startWorker
import com.aston.astonintensivfinal.headlines.presentation.ui.HeadlinesGeneralFragment
import com.aston.astonintensivfinal.saved.presentation.ui.SavedListFragment
import com.aston.astonintensivfinal.sources.presentation.ui.SourceListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

const val MAINFRAGMENT = "MAINFRAGMENT"
const val SOURCE = "SOURCE"
const val SAVEFRAGMENT = "SAVEFRAGMENT"
const val ROOTBACKSTACK = "ROOTBACKSTACK"

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mainViewModel: MainViewModel

    lateinit var lottieView: LottieAnimationView
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var fragmentContainerView: FragmentContainerView
    lateinit var constraintLayout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()


        super.onCreate(savedInstanceState)
        setContentView(R.layout.lottie_layout)

        DaggerMainComponent.builder().build().inject(this)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)


        lottieView = findViewById(R.id.lottie_animated_view)
        fragmentContainerView = findViewById(R.id.lottie_view_fragment_container)
        bottomNavigationView = findViewById(R.id.lottie_view_bottomNavigationView)
        constraintLayout = findViewById(R.id.main_lottie_container)
        splashScreen.setOnExitAnimationListener { vp ->
            lottieView.enableMergePathsForKitKatAndAbove(true)


            val time_long = vp.iconAnimationStartMillis + vp.iconAnimationDurationMillis
            val currentTimeMillis = System.currentTimeMillis()
            val delay_time = time_long - currentTimeMillis



            lottieView.postDelayed({
                vp.view.alpha = 0f
                vp.iconView.alpha = 0f
                lottieView!!.playAnimation()
            }, delay_time)


            lottieView.addAnimatorListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {


                    show_bottomNavigation()
                }

            })
        }

        startWorker()
    }

    fun show_bottomNavigation() {
        lottieView.visibility = View.INVISIBLE
        fragmentContainerView.visibility = View.VISIBLE
        bottomNavigationView.visibility = View.VISIBLE
        constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))

        val headlineesNavTag = supportFragmentManager.findFragmentByTag(MAINFRAGMENT)
        if (headlineesNavTag == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.lottie_view_fragment_container,
                    HeadlinesGeneralFragment.newInstance(),
                    MAINFRAGMENT
                )
                .addToBackStack(ROOTBACKSTACK)
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottomNavigationView_headlines -> {
                    val headlineesTag = supportFragmentManager.findFragmentByTag(MAINFRAGMENT)
                    val sourceFragment = supportFragmentManager.findFragmentByTag(SOURCE)
                    if (headlineesTag == null) {
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.lottie_view_fragment_container,
                                HeadlinesGeneralFragment.newInstance(),
                                MAINFRAGMENT
                            )
                            .commit()
                    } else {

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.lottie_view_fragment_container,
                                headlineesTag,
                                MAINFRAGMENT

                            )
                            .commit()


                    }


                    true

                }

                R.id.bottomNavigationView_saved -> {

                    val saveFragment = supportFragmentManager.findFragmentByTag(SAVEFRAGMENT)
                    if (saveFragment == null){
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.lottie_view_fragment_container,
                            SavedListFragment.newInstance(),
                            SAVEFRAGMENT
                            )
                            .commit()

                    } else {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.lottie_view_fragment_container,
                                saveFragment,
                                SAVEFRAGMENT
                            )
                            .commit()
                    }


                    true

                }

                R.id.bottomNavigationView_sourсes -> {
                    val sourceFragment = supportFragmentManager.findFragmentByTag(SOURCE)

                    if (sourceFragment == null) {
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.lottie_view_fragment_container,
                                SourceListFragment.newInstance(),
                                SOURCE
                            )
                            .commit()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .replace( R.id.lottie_view_fragment_container, sourceFragment, SOURCE)
                            .commit()
                    }

                    true

                }

                else -> {
                    false
                }

            }
        }


    }


}