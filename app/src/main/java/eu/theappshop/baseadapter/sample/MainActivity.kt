package eu.theappshop.baseadapter.sample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import eu.theappshop.baseadapter.adapterv2.FilterableVmAdapter
import eu.theappshop.baseadapter.adapterv2.SerializablePredicate
import eu.theappshop.baseadapterproject.BR
import eu.theappshop.baseadapterproject.R
import eu.theappshop.baseadapterproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  lateinit var adapter: FilterableVmAdapter<TestVM>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    adapter = savedInstanceState?.getSerializable("adapter") as FilterableVmAdapter<TestVM>?
        ?: FilterableVmAdapter(object : SerializablePredicate<TestVM> {
          override fun apply(o: TestVM): Boolean {
            return true
          }

        }, List(10) {
          TestVM(it)
        })
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
