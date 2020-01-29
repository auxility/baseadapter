package dev.auxility.baseadapter.sample

import androidx.lifecycle.ViewModelProviders

class EndlessAdapterSampleFragment : TabbedFragment() {
    override val titleRes: Int = R.string.endless_adapter_sample

    override val viewModel: TabbedViewModel by lazy {
        ViewModelProviders.of(this)
                .get(EndlessAdapterViewModel::class.java)
    }

}