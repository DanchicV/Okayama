package com.okayama.shop.ui.basket;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.okayama.shop.R;
import com.okayama.shop.base.BaseFragment;
import com.okayama.shop.base.ItemClickListener;
import com.okayama.shop.ui.product.ProductFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BasketFragment extends BaseFragment implements BasketContract.View {

    @BindView(R.id.progress)
    ProgressBar progress;

    @BindView(R.id.basket_recycler_view)
    RecyclerView basketRecyclerView;

    @BindView(R.id.buy_button)
    Button buyButton;

    @BindView(R.id.back_button)
    ImageView backButton;

    private Unbinder unbinder;
    private BasketPresenter presenter;
    private BasketAdapter adapter;

    public static BasketFragment newInstance() {
        return new BasketFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new BasketPresenter();
        presenter.setView(this);
        presenter.subscribe();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        basketRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new BasketAdapter(new ItemClickListener() {
            @Override
            public void onClick(long id) {
                if (BasketFragment.this.isAdded()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .add(android.R.id.content, ProductFragment.newInstance(id), ProductFragment.class.getSimpleName())
                            .addToBackStack(ProductFragment.class.getSimpleName())
                            .commit();
                }
            }
        });

        adapter.setProducts(presenter.getBasket());
        basketRecyclerView.setAdapter(adapter);
    }


    @Override
    public void setPresenter(BasketPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(Boolean b) {
        progress.setVisibility(b ? View.VISIBLE : View.GONE);
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
        presenter.unsubscribe();
    }

    @OnClick(R.id.back_button)
    public void onBackButtonClicked() {
        if (isAdded()) {
            getActivity().onBackPressed();
        }
    }

    @OnClick(R.id.buy_button)
    public void onBuyClicked() {
        BasketDialogFragment.newInstance(new BasketDialogFragment.PhoneConfirmListener() {
            @Override
            public void phoneConfirm(String phone) {
                if (!TextUtils.isEmpty(phone)) {
                    presenter.sendBasket(phone);
                }
            }
        }, presenter.getPhone()).show(getFragmentManager(), BasketDialogFragment.class.getSimpleName());
    }
}
