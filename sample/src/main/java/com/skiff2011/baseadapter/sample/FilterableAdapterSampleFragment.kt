package com.skiff2011.baseadapter.sample

import android.arch.lifecycle.ViewModelProviders

class FilterableAdapterSampleFragment : TabbedFragment() {
  override val viewModel: TabbedViewModel by lazy {
    ViewModelProviders.of(this)
        .get(FilterableAdapterSampleViewModel::class.java)
  }

  override val titleRes: Int = R.string.filterableadapter_sample

}