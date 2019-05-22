package com.minisdk.glide.load.engine;

import com.minisdk.glide.load.Key;
import com.minisdk.glide.load.Options;
import com.minisdk.glide.load.Transformation;
import com.minisdk.glide.load.engine.EngineKey;

import java.util.Map;

class EngineKeyFactory {

  @SuppressWarnings("rawtypes")
  com.minisdk.glide.load.engine.EngineKey buildKey(Object model, Key signature, int width, int height,
                                                    Map<Class<?>, Transformation<?>> transformations, Class<?> resourceClass,
                                                    Class<?> transcodeClass, Options options) {
    return new com.minisdk.glide.load.engine.EngineKey(model, signature, width, height, transformations, resourceClass,
        transcodeClass, options);
  }
}
