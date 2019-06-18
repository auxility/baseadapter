package com.skiff2011.baseadapter.filterableadapter;

import androidx.annotation.NonNull;
import com.skiff2011.baseadapter.StubItem;
import com.skiff2011.baseadapter.misc.function.SerializablePredicate;

public class TestPredicate implements SerializablePredicate<StubItem> {
  @Override public Boolean apply(@NonNull StubItem object) {
    return (object.value % 2) != 0;
  }
}
