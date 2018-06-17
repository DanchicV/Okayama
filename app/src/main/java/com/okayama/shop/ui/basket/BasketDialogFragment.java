package com.okayama.shop.ui.basket;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.okayama.shop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BasketDialogFragment extends DialogFragment {

    private static final String KEY_PHONE = "PHONE";

    @BindView(R.id.phone_edit_text)
    EditText phoneEditText;

    private PhoneConfirmListener phoneConfirmListener;
    private Unbinder unbinder;

    public static BasketDialogFragment newInstance(PhoneConfirmListener phoneConfirmListener, String phone) {
        BasketDialogFragment fragment = new BasketDialogFragment().setPhoneConfirmListener(phoneConfirmListener);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PHONE, phone);
        fragment.setArguments(bundle);
        return fragment;
    }

    public BasketDialogFragment setPhoneConfirmListener(PhoneConfirmListener phoneConfirmListener) {
        this.phoneConfirmListener = phoneConfirmListener;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_phone, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            phoneEditText.setText(bundle.getString(KEY_PHONE, ""));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.confirm_button)
    public void onViewClicked() {
        if (phoneConfirmListener != null) {
            dismiss();
            phoneConfirmListener.phoneConfirm(phoneEditText.getText().toString());
        }
    }

    public interface PhoneConfirmListener {

        void phoneConfirm(String phone);
    }
}
