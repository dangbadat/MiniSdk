package com.minisdk.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import com.minisdk.glide.load.EncodeStrategy;
import com.minisdk.glide.load.Options;
import com.minisdk.glide.load.ResourceEncoder;
import com.minisdk.glide.load.engine.Resource;
import com.minisdk.glide.load.engine.bitmap_recycle.BitmapPool;
import com.minisdk.glide.load.resource.bitmap.BitmapResource;

import java.io.File;

/**
 * Encodes {@link BitmapDrawable}s.
 */
public class BitmapDrawableEncoder implements ResourceEncoder<BitmapDrawable> {

  private final BitmapPool bitmapPool;
  private final ResourceEncoder<Bitmap> encoder;

  public BitmapDrawableEncoder(BitmapPool bitmapPool, ResourceEncoder<Bitmap> encoder) {
    this.bitmapPool = bitmapPool;
    this.encoder = encoder;
  }

  @Override
  public boolean encode(@NonNull Resource<BitmapDrawable> data, @NonNull File file,
      @NonNull Options options) {
    return encoder.encode(new BitmapResource(data.get().getBitmap(), bitmapPool), file, options);
  }

  @NonNull
  @Override
  public EncodeStrategy getEncodeStrategy(@NonNull Options options) {
    return encoder.getEncodeStrategy(options);
  }
}
