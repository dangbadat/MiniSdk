package com.minisdk.glide.load.engine.prefill;

import com.minisdk.glide.load.engine.prefill.PreFillType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class PreFillQueue {

  private final Map<com.minisdk.glide.load.engine.prefill.PreFillType, Integer> bitmapsPerType;
  private final List<com.minisdk.glide.load.engine.prefill.PreFillType> keyList;
  private int bitmapsRemaining;
  private int keyIndex;

  public PreFillQueue(Map<com.minisdk.glide.load.engine.prefill.PreFillType, Integer> bitmapsPerType) {
    this.bitmapsPerType = bitmapsPerType;
    // We don't particularly care about the initial order.
    keyList = new ArrayList<>(bitmapsPerType.keySet());

    for (Integer count : bitmapsPerType.values()) {
      bitmapsRemaining += count;
    }
  }

  public com.minisdk.glide.load.engine.prefill.PreFillType remove() {
    PreFillType result = keyList.get(keyIndex);

    Integer countForResult = bitmapsPerType.get(result);
    if (countForResult == 1) {
      bitmapsPerType.remove(result);
      keyList.remove(keyIndex);
    } else {
      bitmapsPerType.put(result, countForResult - 1);
    }
    bitmapsRemaining--;

    // Avoid divide by 0.
    keyIndex = keyList.isEmpty() ? 0 : (keyIndex + 1) % keyList.size();

    return result;
  }

  public int getSize() {
    return bitmapsRemaining;
  }

  public boolean isEmpty() {
    return bitmapsRemaining == 0;
  }
}
