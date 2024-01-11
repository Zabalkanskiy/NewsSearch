package com.aston.astonintensivfinal.presentation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentContainerView
import com.airbnb.lottie.LottieAnimationView
import com.aston.astonintensivfinal.R
import com.aston.astonintensivfinal.headlines.presentation.ui.HeadlinesGeneralFragment
import com.aston.astonintensivfinal.sources.presentation.ui.SourceListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

const val MAINFRAGMENT = "MAINFRAGMENT"
const val SOURCE ="SOURCE"
class MainActivity : AppCompatActivity() {
    lateinit var lottieView: LottieAnimationView
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var fragmentContainerView: FragmentContainerView
    lateinit var constraintLayout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()


        super.onCreate(savedInstanceState)
        setContentView(R.layout.lottie_layout)
        lottieView = findViewById(R.id.lottie_animated_view)
        fragmentContainerView = findViewById(R.id.lottie_view_fragment_container)
        bottomNavigationView = findViewById(R.id.lottie_view_bottomNavigationView)
        constraintLayout = findViewById(R.id.main_lottie_container)
        splashScreen.setOnExitAnimationListener{vp ->
           // val lottieView = findViewById<LottieAnimationView>(R.id.lottie_animated_view)
            lottieView.enableMergePathsForKitKatAndAbove(true)
          //  val splashScreenAnimationEndTime =
           //     Instant.ofEpochMilli(vp.iconAnimationStartMillis + vp.iconAnimationDurationMillis)

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
    }

    fun show_bottomNavigation(){
        lottieView.visibility = View.INVISIBLE
        fragmentContainerView.visibility = View.VISIBLE
        bottomNavigationView.visibility = View.VISIBLE
        constraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))

        val headkineesTag = supportFragmentManager.findFragmentByTag(MAINFRAGMENT)
        if (headkineesTag==null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.lottie_view_fragment_container, HeadlinesGeneralFragment.newInstance(), MAINFRAGMENT)
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener {
                item ->
            when(item.itemId){
                R.id.bottomNavigationView_headlines ->{
                    val headlineesTag = supportFragmentManager.findFragmentByTag(MAINFRAGMENT)
                    if (headlineesTag==null){
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.lottie_view_fragment_container, HeadlinesGeneralFragment.newInstance(), MAINFRAGMENT)
                            .commit()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .show(headlineesTag)
                            .commit()
                    }


                    true

                }
                R.id.bottomNavigationView_saved -> {

                    true

                }
                R.id.bottomNavigationView_sourсes ->{
                    val sourceFragment = supportFragmentManager.findFragmentByTag(SOURCE)
                    if (sourceFragment == null){
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.lottie_view_fragment_container, SourceListFragment.newInstance(), SOURCE)
                            .commit()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .show(sourceFragment)
                            .commit()
                    }

                    true

                }
                else ->{
                    false
                }

            }
        }


    }
}