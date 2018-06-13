package com.okayama.shop.ui.auth.registration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.okayama.shop.R;
import com.okayama.shop.base.BaseFragment;
import com.okayama.shop.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegistrationFragment extends BaseFragment implements RegistrationContract.View {

    @BindView(R.id.registration_progress)
    ProgressBar registrationProgress;

    @BindView(R.id.name_edit_text)
    EditText nameEditText;

    @BindView(R.id.email_edit_text)
    EditText emailEditText;

    @BindView(R.id.city_edit_text)
    EditText cityEditText;

    @BindView(R.id.organization_edit_text)
    EditText organizationEditText;

    @BindView(R.id.password_edit_text)
    EditText passwordEditText;

    @BindView(R.id.retry_password_edit_text)
    EditText retryPasswordEditText;

    @BindView(R.id.confirm_button)
    Button confirmButton;

    @BindView(R.id.cancel_button)
    Button cancelButton;

    @BindView(R.id.registration_view_group)
    LinearLayout registrationViewGroup;

    @BindView(R.id.role_user_button)
    Button roleUserButton;

    @BindView(R.id.role_dealer_button)
    Button roleDealerButton;

    @BindView(R.id.select_role_view_group)
    LinearLayout selectRoleViewGroup;

    @BindView(R.id.header_view_group)
    LinearLayout headerViewGroup;

    private Unbinder unbinder;
    private RegistrationPresenter presenter;
    private int role;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new RegistrationPresenter();
        presenter.setView(this);
        presenter.subscribe();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void setPresenter(RegistrationPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(Boolean b) {
        registrationProgress.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String message) {
        if (isAdded() && checkValid()) {
            MaterialDialog materialDialog = new MaterialDialog.Builder(getContext())
                    .title(R.string.warning)
                    .content(message)
                    .positiveText(android.R.string.ok)
                    .show();
            materialDialog.getActionButton(DialogAction.POSITIVE).requestFocus();
        }
    }

    private boolean checkValid() {
        // TODO: 28.05.2018 check
        return true;
    }

    @Override
    public void showError(int message) {
        showError(getString(message));
    }

    @Override
    public void registrationSuccess() {
        MainActivity.startNewTask(getContext());
    }

    private boolean isValidData() {
        return true;
    }

    @OnClick(R.id.confirm_button)
    public void onConfirmButtonClicked() {
        if (isValidData()) {
            presenter.registration(role,
                    nameEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    emailEditText.getText().toString(),
                    cityEditText.getText().toString(),
                    organizationEditText.getText().toString());
        }
    }

    @OnClick(R.id.cancel_button)
    public void onCancelButtonClicked() {
        selectRoleViewGroup.setVisibility(View.VISIBLE);
        registrationViewGroup.setVisibility(View.GONE);
        role = 0;
    }

    @OnClick(R.id.role_user_button)
    public void onRoleUserButtonClicked() {
        selectRoleViewGroup.setVisibility(View.GONE);
        registrationViewGroup.setVisibility(View.VISIBLE);
        cityEditText.setVisibility(View.GONE);
        organizationEditText.setVisibility(View.GONE);
        role = 1;
    }

    @OnClick(R.id.role_dealer_button)
    public void onRoleDealerButtonClicked() {
        selectRoleViewGroup.setVisibility(View.GONE);
        registrationViewGroup.setVisibility(View.VISIBLE);
        cityEditText.setVisibility(View.VISIBLE);
        organizationEditText.setVisibility(View.VISIBLE);
        role = 2;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.unsubscribe();
    }
}
