package dev.auxility.baseadapter.sample

import dev.auxility.baseadapter.Adapter
import dev.auxility.baseadapter.BaseAdapter
import dev.auxility.baseadapter.item.TitledItem

class FilterableAdapterSampleViewModel : TabbedViewModel() {
  override val adapter: Adapter<TitledItem> =
    BaseAdapter(
        listOf(
            FilterableRVItem(),
            FilterablePagerItem()
        )
    )
}