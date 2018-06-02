package com.okayama.shop.ui.auth.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginFragment extends BaseFragment implements LoginContract.View {

    @BindView(R.id.email_edit_text)
    EditText emailEditText;

    @BindView(R.id.password_edit_text)
    EditText passwordEditText;

    @BindView(R.id.login_button)
    Button loginButton;

    @BindView(R.id.cancel_button)
    Button cancelButton;

    @BindView(R.id.login_progress)
    ProgressBar loginProgress;

    private Unbinder unbinder;
    private LoginPresenter presenter;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new LoginPresenter();
        presenter.setView(this);
        presenter.subscribe();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void setPresenter(LoginPresenter presenter) {

    }

    @Override
    public void showProgress(Boolean b) {
        loginProgress.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String message) {
        if (isAdded()) {
            MaterialDialog materialDialog = new MaterialDialog.Builder(getContext())
                    .title(R.string.warning)
                    .content(message)
                    .positiveText(android.R.string.ok)
                    .show();
            materialDialog.getActionButton(DialogAction.POSITIVE).requestFocus();
        }
    }

    @Override
    public void showError(int message) {
        showError(getString(message));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean isValidData() {
        // TODO: 28.05.2018 check
        return true;
    }

    @OnClick(R.id.login_button)
    public void onOpenLoginButtonClicked() {
        if (isValidData()) {
            presenter.login(emailEditText.getText().toString(),
                    passwordEditText.getText().toString());
        }
    }

    @OnClick(R.id.cancel_button)
    public void onCancelButtonClicked() {
        if (isAdded()) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void loginSuccess() {
        MainActivity.startNewTask(getContext());
    }
}
