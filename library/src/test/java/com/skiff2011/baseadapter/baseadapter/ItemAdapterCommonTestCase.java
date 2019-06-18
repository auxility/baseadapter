package com.skiff2011.baseadapter.baseadapter;

import com.skiff2011.baseadapter.AbstractItemAdapter;
import com.skiff2011.baseadapter.BaseItemAdapter;
import com.skiff2011.baseadapter.StubItem;
import com.skiff2011.baseadapter.general.AdapterCommonTestCase;
import java.util.List;

public class ItemAdapterCommonTestCase extends AdapterCommonTestCase {
  @Override protected AbstractItemAdapter<StubItem> provideAdapter(List<StubItem> vms) {
    return new BaseItemAdapter<>(vms);
  }
}
