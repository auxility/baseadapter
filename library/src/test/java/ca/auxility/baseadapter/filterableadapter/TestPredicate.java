package ca.auxility.baseadapter.filterableadapter;

import androidx.annotation.NonNull;
import ca.auxility.baseadapter.TestItem;
import ca.auxility.baseadapter.misc.function.SerializablePredicate;

public class TestPredicate implements SerializablePredicate<TestItem> {
  @Override public Boolean apply(@NonNull TestItem object) {
    return (object.value % 2) != 0;
  }
}
