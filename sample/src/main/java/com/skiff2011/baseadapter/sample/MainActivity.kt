package com.skiff2011.baseadapter.sample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.skiff2011.baseadapter.FilterableVmAdapter
import com.skiff2011.baseadapter.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  lateinit var adapter: FilterableVmAdapter<TestVM>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    adapter = savedInstanceState?.getSerializable("adapter") as FilterableVmAdapter<TestVM>?
        ?: FilterableVmAdapter(List(10) {
          TestVM(it)
        }) { vm ->
          true
        }
    binding.setVariable(BR.activity, this)
  }

  fun remove() {
    adapter.remove(adapter.size - 1)
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    outState?.putSerializable("adapter", adapter)
  }
}
