package com.skiff2011.baseadapter.sample

import androidx.lifecycle.ViewModelProviders

class BaseAdapterSampleFragment : TabbedFragment() {

  override val viewModel: TabbedViewModel by lazy {
    ViewModelProviders.of(this)
        .get(BaseAdapterSampleViewModel::class.java)
  }

  override val titleRes: Int = R.string.baseadapter_sample

}