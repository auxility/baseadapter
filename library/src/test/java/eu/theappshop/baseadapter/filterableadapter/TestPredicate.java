package eu.theappshop.baseadapter.filterableadapter;

import android.support.annotation.NonNull;
import eu.theappshop.baseadapter.StubVM;
import eu.theappshop.baseadapter.adapterv2.SerializablePredicate;

public class TestPredicate implements SerializablePredicate<StubVM> {
  @Override public Boolean apply(@NonNull StubVM object) {
    return (object.value % 2) != 0;
  }
}
