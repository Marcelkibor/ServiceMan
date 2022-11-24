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
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.petvet.R;
import com.google.android.material.navigation.NavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AuthenticatedClientBinding implements ViewBinding {
  @NonNull
  private final DrawerLayout rootView;

  @NonNull
  public final DrawerLayout ClientDrawerLayout;

  @NonNull
  public final CardView accountCardView;

  @NonNull
  public final CardView cardCompletedOrders;

  @NonNull
  public final CardView cardSearch;

  @NonNull
  public final CardView cardWallet;

  @NonNull
  public final TextView clientDisplayName;

  @NonNull
  public final NavigationView clientNavBar;

  @NonNull
  public final ImageView drawerHome;

  @NonNull
  public final ImageView drawerIcon;

  @NonNull
  public final TextView greetingText;

  @NonNull
  public final FrameLayout ltService;

  @NonNull
  public final CardView relativeLayout2;

  @NonNull
  public final TextView tvMyServices;

  @NonNull
  public final FrameLayout vetFrameLayout;

  private AuthenticatedClientBinding(@NonNull DrawerLayout rootView,
      @NonNull DrawerLayout ClientDrawerLayout, @NonNull CardView accountCardView,
      @NonNull CardView cardCompletedOrders, @NonNull CardView cardSearch,
      @NonNull CardView cardWallet, @NonNull TextView clientDisplayName,
      @NonNull NavigationView clientNavBar, @NonNull ImageView drawerHome,
      @NonNull ImageView drawerIcon, @NonNull TextView greetingText, @NonNull FrameLayout ltService,
      @NonNull CardView relativeLayout2, @NonNull TextView tvMyServices,
      @NonNull FrameLayout vetFrameLayout) {
    this.rootView = rootView;
    this.ClientDrawerLayout = ClientDrawerLayout;
    this.accountCardView = accountCardView;
    this.cardCompletedOrders = cardCompletedOrders;
    this.cardSearch = cardSearch;
    this.cardWallet = cardWallet;
    this.clientDisplayName = clientDisplayName;
    this.clientNavBar = clientNavBar;
    this.drawerHome = drawerHome;
    this.drawerIcon = drawerIcon;
    this.greetingText = greetingText;
    this.ltService = ltService;
    this.relativeLayout2 = relativeLayout2;
    this.tvMyServices = tvMyServices;
    this.vetFrameLayout = vetFrameLayout;
  }

  @Override
  @NonNull
  public DrawerLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AuthenticatedClientBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AuthenticatedClientBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.authenticated_client, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AuthenticatedClientBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      DrawerLayout ClientDrawerLayout = (DrawerLayout) rootView;

      id = R.id.accountCardView;
      CardView accountCardView = ViewBindings.findChildViewById(rootView, id);
      if (accountCardView == null) {
        break missingId;
      }

      id = R.id.cardCompletedOrders;
      CardView cardCompletedOrders = ViewBindings.findChildViewById(rootView, id);
      if (cardCompletedOrders == null) {
        break missingId;
      }

      id = R.id.cardSearch;
      CardView cardSearch = ViewBindings.findChildViewById(rootView, id);
      if (cardSearch == null) {
        break missingId;
      }

      id = R.id.cardWallet;
      CardView cardWallet = ViewBindings.findChildViewById(rootView, id);
      if (cardWallet == null) {
        break missingId;
      }

      id = R.id.clientDisplayName;
      TextView clientDisplayName = ViewBindings.findChildViewById(rootView, id);
      if (clientDisplayName == null) {
        break missingId;
      }

      id = R.id.clientNavBar;
      NavigationView clientNavBar = ViewBindings.findChildViewById(rootView, id);
      if (clientNavBar == null) {
        break missingId;
      }

      id = R.id.drawer_home;
      ImageView drawerHome = ViewBindings.findChildViewById(rootView, id);
      if (drawerHome == null) {
        break missingId;
      }

      id = R.id.drawer_icon;
      ImageView drawerIcon = ViewBindings.findChildViewById(rootView, id);
      if (drawerIcon == null) {
        break missingId;
      }

      id = R.id.greetingText;
      TextView greetingText = ViewBindings.findChildViewById(rootView, id);
      if (greetingText == null) {
        break missingId;
      }

      id = R.id.ltService;
      FrameLayout ltService = ViewBindings.findChildViewById(rootView, id);
      if (ltService == null) {
        break missingId;
      }

      id = R.id.relativeLayout2;
      CardView relativeLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (relativeLayout2 == null) {
        break missingId;
      }

      id = R.id.tvMyServices;
      TextView tvMyServices = ViewBindings.findChildViewById(rootView, id);
      if (tvMyServices == null) {
        break missingId;
      }

      id = R.id.vet_frame_layout;
      FrameLayout vetFrameLayout = ViewBindings.findChildViewById(rootView, id);
      if (vetFrameLayout == null) {
        break missingId;
      }

      return new AuthenticatedClientBinding((DrawerLayout) rootView, ClientDrawerLayout,
          accountCardView, cardCompletedOrders, cardSearch, cardWallet, clientDisplayName,
          clientNavBar, drawerHome, drawerIcon, greetingText, ltService, relativeLayout2,
          tvMyServices, vetFrameLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
