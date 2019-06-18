package com.skiff2011.baseadapter.baseadapter;

import com.skiff2011.baseadapter.AbstractItemAdapter;
import com.skiff2011.baseadapter.BaseItemAdapter;
import com.skiff2011.baseadapter.StubItem;
import com.skiff2011.baseadapter.general.AdapterSerializationTestCase;
import java.util.List;

public class ItemAdapterSerializationTestCase extends AdapterSerializationTestCase {
  @Override protected AbstractItemAdapter<StubItem> getAdapter(List<StubItem> vms) {
    return new BaseItemAdapter<>(vms);
  }
}
