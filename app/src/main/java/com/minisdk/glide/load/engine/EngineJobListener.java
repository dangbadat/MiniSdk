package com.minisdk.glide.load.engine;

import com.minisdk.glide.load.Key;
import com.minisdk.glide.load.engine.EngineJob;
import com.minisdk.glide.load.engine.EngineResource;

interface EngineJobListener {

  void onEngineJobComplete(com.minisdk.glide.load.engine.EngineJob<?> engineJob, Key key, com.minisdk.glide.load.engine.EngineResource<?> resource);

  void onEngineJobCancelled(com.minisdk.glide.load.engine.EngineJob<?> engineJob, Key key);
}
