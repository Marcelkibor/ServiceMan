// Generated by view binder compiler. Do not edit!
package com.example.petvet.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.petvet.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AdminLoggActivityBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final MaterialButton btLogin;

  @NonNull
  public final EditText edEmail;

  @NonNull
  public final EditText edPassword;

  @NonNull
  public final TextView tvHeader;

  private AdminLoggActivityBinding(@NonNull CardView rootView, @NonNull MaterialButton btLogin,
      @NonNull EditText edEmail, @NonNull EditText edPassword, @NonNull TextView tvHeader) {
    this.rootView = rootView;
    this.btLogin = btLogin;
    this.edEmail = edEmail;
    this.edPassword = edPassword;
    this.tvHeader = tvHeader;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static AdminLoggActivityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AdminLoggActivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.admin_logg_activity, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AdminLoggActivityBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btLogin;
      MaterialButton btLogin = ViewBindings.findChildViewById(rootView, id);
      if (btLogin == null) {
        break missingId;
      }

      id = R.id.edEmail;
      EditText edEmail = ViewBindings.findChildViewById(rootView, id);
      if (edEmail == null) {
        break missingId;
      }

      id = R.id.edPassword;
      EditText edPassword = ViewBindings.findChildViewById(rootView, id);
      if (edPassword == null) {
        break missingId;
      }

      id = R.id.tvHeader;
      TextView tvHeader = ViewBindings.findChildViewById(rootView, id);
      if (tvHeader == null) {
        break missingId;
      }

      return new AdminLoggActivityBinding((CardView) rootView, btLogin, edEmail, edPassword,
          tvHeader);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
