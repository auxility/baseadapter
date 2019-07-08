package dev.auxility.baseadapter;

import java.util.List;

public class AdapterCommonTestCase extends dev.auxility.baseadapter.general.AdapterCommonTestCase {
  @Override protected AbstractAdapter<TestItem> provideAdapter(List<TestItem> vms) {
    return new BaseAdapter<>(vms);
  }
}
