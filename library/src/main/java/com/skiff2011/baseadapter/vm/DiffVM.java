package com.skiff2011.baseadapter.vm;

import android.support.annotation.NonNull;

public interface DiffVM extends VM {

  boolean isEqualItem(@NonNull VM vm);

  boolean isEqualContent(@NonNull VM vm);

}
