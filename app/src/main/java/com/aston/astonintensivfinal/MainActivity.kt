package com.aston.astonintensivfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aston.astonintensivfinal.databinding.ActivityMainBinding
import com.aston.astonintensivfinal.headlines.ui.Headlines_view_pager
import com.aston.astonintensivfinal.utils.Utils.HEADLINES


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)


        //need load другие представления при перевороте экрана

        val headkineesTag = supportFragmentManager.findFragmentByTag(HEADLINES)
        if (headkineesTag==null){
            supportFragmentManager.beginTransaction()
                .replace(binding.mainActivityFragmentContainer.id, Headlines_view_pager.newInstance(), HEADLINES)
                .commit()
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            item ->
            when(item.itemId){
                R.id.bottomNavigationView_headlines ->{
                    val headkineesTag = supportFragmentManager.findFragmentByTag(HEADLINES)
                    if (headkineesTag==null){
                        supportFragmentManager.beginTransaction()
                            .replace(binding.mainActivityFragmentContainer.id, Headlines_view_pager.newInstance(), HEADLINES)
                            .commit()
                    }


                    true

                }
                R.id.bottomNavigationView_saved -> {

                    true

                }
                R.id.bottomNavigationView_sourсes ->{

                    true

                }
                else ->{
                    false
                }

            }
        }
    }
}