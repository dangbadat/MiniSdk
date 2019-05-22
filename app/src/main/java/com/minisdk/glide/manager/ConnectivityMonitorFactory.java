package com.minisdk.glide.manager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.minisdk.glide.manager.ConnectivityMonitor;

/**
 * A factory class that produces a functional
 * {@link com.minisdk.glide.manager.ConnectivityMonitor}.
 */
public interface ConnectivityMonitorFactory {

  @NonNull
  com.minisdk.glide.manager.ConnectivityMonitor build(
          @NonNull Context context,
          @NonNull ConnectivityMonitor.ConnectivityListener listener);
}
