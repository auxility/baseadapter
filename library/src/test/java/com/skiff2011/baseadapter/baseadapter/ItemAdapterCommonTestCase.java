package com.skiff2011.baseadapter.baseadapter;

import com.skiff2011.baseadapter.AbstractItemAdapter;
import com.skiff2011.baseadapter.BaseItemAdapter;
import com.skiff2011.baseadapter.TestItem;
import com.skiff2011.baseadapter.general.AdapterCommonTestCase;
import java.util.List;

public class ItemAdapterCommonTestCase extends AdapterCommonTestCase {
  @Override protected AbstractItemAdapter<TestItem> provideAdapter(List<TestItem> vms) {
    return new BaseItemAdapter<>(vms);
  }
}
