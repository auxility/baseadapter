package dev.auxility.baseadapter.sample

import androidx.lifecycle.ViewModelProviders
import dev.auxility.baseadapter.sample.R.string

class FilterableAdapterSampleFragment : TabbedFragment() {
  override val viewModel: TabbedViewModel by lazy {
    ViewModelProviders.of(this)
        .get(FilterableAdapterSampleViewModel::class.java)
  }

  override val titleRes: Int = string.filterableadapter_sample

}