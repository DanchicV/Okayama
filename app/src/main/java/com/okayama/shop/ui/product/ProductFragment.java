package com.okayama.shop.ui.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.okayama.shop.R;
import com.okayama.shop.base.BaseFragment;
import com.okayama.shop.base.ItemClickListener;
import com.okayama.shop.data.models.Product;
import com.okayama.shop.ui.product_info.ProductInfoFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProductFragment extends BaseFragment implements ProductContract.View {

    private static final String KEY_ID = "ID";

    @BindView(R.id.login_progress)
    ProgressBar loginProgress;

    @BindView(R.id.categories_recycler_view)
    RecyclerView categoriesRecyclerView;

    @BindView(R.id.basket_fab)
    ImageView basketFab;

    @BindView(R.id.back_button)
    ImageView backButton;

    private Unbinder unbinder;
    private ProductPresenter presenter;
    private ProductAdapter adapter;
    private List<Product> products;

    public static ProductFragment newInstance(long id) {
        ProductFragment productFragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(KEY_ID, id);
        productFragment.setArguments(bundle);
        return productFragment;
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
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new ProductPresenter();
        presenter.setView(this);
        presenter.subscribe();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateBasketFab(productDao.getAllProduct().isEmpty());
        backButton.setVisibility(View.VISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        categoriesRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ProductAdapter(new ItemClickListener() {
            @Override
            public void onClick(long id) {
                if (ProductFragment.this.isAdded()) {
                    for (Product product : products) {
                        if (product.getId() == id) {
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(android.R.id.content, ProductInfoFragment.newInstance(product), ProductInfoFragment.class.getSimpleName())
                                    .addToBackStack(ProductInfoFragment.class.getSimpleName())
                                    .commit();
                            break;
                        }
                    }
                }
            }
        });
        categoriesRecyclerView.setAdapter(adapter);

        Bundle arguments = getArguments();
        if (arguments != null) {
            long id = arguments.getLong(KEY_ID);
            presenter.loadProducts(id);
        }
    }

    private void updateBasketFab(boolean isEmpty) {
        if (isEmpty) {
            basketFab.setImageResource(R.drawable.ic_basket_empty);
        } else {
            basketFab.setImageResource(R.drawable.ic_basket_not_empty);
        }
    }

    @Override
    public void setPresenter(ProductPresenter presenter) {
        this.presenter = presenter;
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
    public void setData(List<Product> products) {
        this.products = products;
        adapter.setProducts(products);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.unsubscribe();
    }

    @OnClick(R.id.basket_fab)
    protected void onBasketClicked() {
        super.onBasketClicked();
    }

    @OnClick(R.id.back_button)
    public void onBackButtonClicked() {
        if (isAdded()) {
            getActivity().onBackPressed();
        }
    }
}
