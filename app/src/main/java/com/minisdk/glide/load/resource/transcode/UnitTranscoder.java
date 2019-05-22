package com.minisdk.glide.load.resource.transcode;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.minisdk.glide.load.Options;
import com.minisdk.glide.load.engine.Resource;
import com.minisdk.glide.load.resource.transcode.ResourceTranscoder;

/**
 * A simple {@link ResourceTranscoder} that simply returns the given resource.
 *
 * @param <Z> The type of the resource that will be transcoded from and to.
 */
public class UnitTranscoder<Z> implements ResourceTranscoder<Z, Z> {
  private static final UnitTranscoder<?> UNIT_TRANSCODER = new UnitTranscoder<>();

  @SuppressWarnings("unchecked")
  public static <Z> ResourceTranscoder<Z, Z> get() {
    return (ResourceTranscoder<Z, Z>) UNIT_TRANSCODER;
  }

  @Nullable
  @Override
  public Resource<Z> transcode(@NonNull Resource<Z> toTranscode, @NonNull Options options) {
    return toTranscode;
  }
}