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

public final class ClientRegisterActivityBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final MaterialButton btRegister;

  @NonNull
  public final EditText edClientEmail;

  @NonNull
  public final EditText edClientFirstName;

  @NonNull
  public final EditText edClientLastName;

  @NonNull
  public final EditText edClientPassword;

  @NonNull
  public final EditText edClientPhone;

  @NonNull
  public final TextView tvAccount;

  @NonNull
  public final TextView tvHeader;

  private ClientRegisterActivityBinding(@NonNull CardView rootView,
      @NonNull MaterialButton btRegister, @NonNull EditText edClientEmail,
      @NonNull EditText edClientFirstName, @NonNull EditText edClientLastName,
      @NonNull EditText edClientPassword, @NonNull EditText edClientPhone,
      @NonNull TextView tvAccount, @NonNull TextView tvHeader) {
    this.rootView = rootView;
    this.btRegister = btRegister;
    this.edClientEmail = edClientEmail;
    this.edClientFirstName = edClientFirstName;
    this.edClientLastName = edClientLastName;
    this.edClientPassword = edClientPassword;
    this.edClientPhone = edClientPhone;
    this.tvAccount = tvAccount;
    this.tvHeader = tvHeader;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ClientRegisterActivityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ClientRegisterActivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.client_register_activity, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ClientRegisterActivityBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btRegister;
      MaterialButton btRegister = ViewBindings.findChildViewById(rootView, id);
      if (btRegister == null) {
        break missingId;
      }

      id = R.id.edClientEmail;
      EditText edClientEmail = ViewBindings.findChildViewById(rootView, id);
      if (edClientEmail == null) {
        break missingId;
      }

      id = R.id.edClientFirstName;
      EditText edClientFirstName = ViewBindings.findChildViewById(rootView, id);
      if (edClientFirstName == null) {
        break missingId;
      }

      id = R.id.edClientLastName;
      EditText edClientLastName = ViewBindings.findChildViewById(rootView, id);
      if (edClientLastName == null) {
        break missingId;
      }

      id = R.id.edClientPassword;
      EditText edClientPassword = ViewBindings.findChildViewById(rootView, id);
      if (edClientPassword == null) {
        break missingId;
      }

      id = R.id.edClientPhone;
      EditText edClientPhone = ViewBindings.findChildViewById(rootView, id);
      if (edClientPhone == null) {
        break missingId;
      }

      id = R.id.tvAccount;
      TextView tvAccount = ViewBindings.findChildViewById(rootView, id);
      if (tvAccount == null) {
        break missingId;
      }

      id = R.id.tvHeader;
      TextView tvHeader = ViewBindings.findChildViewById(rootView, id);
      if (tvHeader == null) {
        break missingId;
      }

      return new ClientRegisterActivityBinding((CardView) rootView, btRegister, edClientEmail,
          edClientFirstName, edClientLastName, edClientPassword, edClientPhone, tvAccount,
          tvHeader);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
