// Generated by view binder compiler. Do not edit!
package com.example.petvet.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.petvet.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentOrderBinding implements ViewBinding {
  @NonNull
  private final DrawerLayout rootView;

  @NonNull
  public final FrameLayout FrameIdentifiers;

  @NonNull
  public final ImageView backArrow;

  @NonNull
  public final RecyclerView rvClientOrders;

  @NonNull
  public final CardView tvCardHeader;

  @NonNull
  public final TextView tvHeader2;

  @NonNull
  public final TextView tvListStatus;

  private FragmentOrderBinding(@NonNull DrawerLayout rootView,
      @NonNull FrameLayout FrameIdentifiers, @NonNull ImageView backArrow,
      @NonNull RecyclerView rvClientOrders, @NonNull CardView tvCardHeader,
      @NonNull TextView tvHeader2, @NonNull TextView tvListStatus) {
    this.rootView = rootView;
    this.FrameIdentifiers = FrameIdentifiers;
    this.backArrow = backArrow;
    this.rvClientOrders = rvClientOrders;
    this.tvCardHeader = tvCardHeader;
    this.tvHeader2 = tvHeader2;
    this.tvListStatus = tvListStatus;
  }

  @Override
  @NonNull
  public DrawerLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentOrderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentOrderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_order, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentOrderBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.FrameIdentifiers;
      FrameLayout FrameIdentifiers = ViewBindings.findChildViewById(rootView, id);
      if (FrameIdentifiers == null) {
        break missingId;
      }

      id = R.id.backArrow;
      ImageView backArrow = ViewBindings.findChildViewById(rootView, id);
      if (backArrow == null) {
        break missingId;
      }

      id = R.id.rvClientOrders;
      RecyclerView rvClientOrders = ViewBindings.findChildViewById(rootView, id);
      if (rvClientOrders == null) {
        break missingId;
      }

      id = R.id.tvCardHeader;
      CardView tvCardHeader = ViewBindings.findChildViewById(rootView, id);
      if (tvCardHeader == null) {
        break missingId;
      }

      id = R.id.tvHeader2;
      TextView tvHeader2 = ViewBindings.findChildViewById(rootView, id);
      if (tvHeader2 == null) {
        break missingId;
      }

      id = R.id.tvListStatus;
      TextView tvListStatus = ViewBindings.findChildViewById(rootView, id);
      if (tvListStatus == null) {
        break missingId;
      }

      return new FragmentOrderBinding((DrawerLayout) rootView, FrameIdentifiers, backArrow,
          rvClientOrders, tvCardHeader, tvHeader2, tvListStatus);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
