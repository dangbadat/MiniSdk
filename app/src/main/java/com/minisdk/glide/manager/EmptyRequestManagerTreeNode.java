package com.minisdk.glide.manager;

import android.support.annotation.NonNull;
import com.minisdk.glide.RequestManager;
import com.minisdk.glide.manager.RequestManagerTreeNode;

import java.util.Collections;
import java.util.Set;

/**
 * A {@link com.minisdk.glide.manager.RequestManagerTreeNode} that returns no relatives.
 */
final class EmptyRequestManagerTreeNode implements RequestManagerTreeNode {
    @NonNull
    @Override
    public Set<RequestManager> getDescendants() {
        return Collections.emptySet();
    }
}
