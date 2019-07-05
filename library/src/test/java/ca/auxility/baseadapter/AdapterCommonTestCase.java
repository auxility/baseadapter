package ca.auxility.baseadapter;

import java.util.List;

public class AdapterCommonTestCase extends ca.auxility.baseadapter.general.AdapterCommonTestCase {
  @Override protected AbstractAdapter<TestItem> provideAdapter(List<TestItem> vms) {
    return new BaseAdapter<>(vms);
  }
}
