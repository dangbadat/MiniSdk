package com.minisdk.glide.manager;

import com.minisdk.glide.manager.ConnectivityMonitor;

/**
 * A no-op {@link com.minisdk.glide.manager.ConnectivityMonitor}.
 */
class NullConnectivityMonitor implements ConnectivityMonitor {

  @Override
  public void onStart() {
    // Do nothing.
  }

  @Override
  public void onStop() {
    // Do nothing.
  }

  @Override
  public void onDestroy() {
    // Do nothing.
  }
}
