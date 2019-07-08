package dev.auxility.baseadapter.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dev.auxility.baseadapter.sample.R.layout
import dev.auxility.baseadapter.sample.databinding.FragmentTabbedBinding

abstract class TabbedFragment : androidx.fragment.app.Fragment() {

  private lateinit var binding: FragmentTabbedBinding

  protected abstract val titleRes: Int

  protected abstract val viewModel: TabbedViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(
        inflater,
        layout.fragment_tabbed, container, false
    )
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