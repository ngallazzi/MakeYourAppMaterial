// Generated code from Butter Knife. Do not modify!
package com.example.xyzreader.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.xyzreader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ArticleListActivity_ViewBinding<T extends ArticleListActivity> implements Unbinder {
  protected T target;

  @UiThread
  public ArticleListActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.clArticleList = Utils.findRequiredViewAsType(source, R.id.clArticleList, "field 'clArticleList'", CoordinatorLayout.class);
    target.mSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.srlArticleList, "field 'mSwipeRefreshLayout'", SwipeRefreshLayout.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.rvArticleList, "field 'mRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.clArticleList = null;
    target.mSwipeRefreshLayout = null;
    target.mRecyclerView = null;

    this.target = null;
  }
}
