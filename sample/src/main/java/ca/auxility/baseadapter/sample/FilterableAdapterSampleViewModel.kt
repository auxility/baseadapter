package ca.auxility.baseadapter.sample

import ca.auxility.baseadapter.BaseItemAdapter
import ca.auxility.baseadapter.ItemAdapter
import ca.auxility.baseadapter.item.TitledItem

class FilterableAdapterSampleViewModel : TabbedViewModel() {
  override val adapter: ItemAdapter<TitledItem> =
    BaseItemAdapter(
        listOf(
            FilterableRVItem(),
            FilterablePagerItem()
        )
    )
}