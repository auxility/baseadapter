package com.skiff2011.baseadapter.sample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.skiff2011.baseadapter.sample.databinding.FragmentTabbedBinding

abstract class TabbedFragment : Fragment() {

  private lateinit var binding: FragmentTabbedBinding

  protected abstract val titleRes: Int

  protected abstract val viewModel: TabbedViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tabbed, container, false)
    binding.setVariable(BR.viewModel, viewModel)
    binding.executePendingBindings()
    binding.toolbar.setNavigationOnClickListener { toolbar ->
      toolbar.findNavController()
          .navigateUp()
    }
    binding.toolbar.title = context!!.resources.getString(titleRes)
    binding.tabLayout.setupWithViewPager(binding.viewPager)
    return binding.root
  }

}