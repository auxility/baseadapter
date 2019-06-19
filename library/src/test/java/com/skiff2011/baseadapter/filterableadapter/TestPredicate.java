package com.skiff2011.baseadapter.filterableadapter;

import androidx.annotation.NonNull;
import com.skiff2011.baseadapter.TestItem;
import com.skiff2011.baseadapter.misc.function.SerializablePredicate;

public class TestPredicate implements SerializablePredicate<TestItem> {
  @Override public Boolean apply(@NonNull TestItem object) {
    return (object.value % 2) != 0;
  }
}
