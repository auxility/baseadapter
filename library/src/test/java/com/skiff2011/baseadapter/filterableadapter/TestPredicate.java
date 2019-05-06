package com.skiff2011.baseadapter.filterableadapter;

import android.support.annotation.NonNull;
import com.skiff2011.baseadapter.StubVM;
import com.skiff2011.baseadapter.misc.function.SerializablePredicate;

public class TestPredicate implements SerializablePredicate<StubVM> {
  @Override public Boolean apply(@NonNull StubVM object) {
    return (object.value % 2) != 0;
  }
}
