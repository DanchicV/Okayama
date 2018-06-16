package com.okayama.shop.ui.product_info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.okayama.shop.R;
import com.okayama.shop.base.BaseFragment;
import com.okayama.shop.data.models.Product;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProductInfoFragment extends BaseFragment implements ProductInfoContract.View {

    private static final String KEY_PRODUCT = "PRODUCT";

    @BindView(R.id.login_progress)
    ProgressBar loginProgress;

    @BindView(R.id.product_image)
    ImageView productImage;

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.price_text_view)
    TextView priceTextView;

    @BindView(R.id.description_text_view)
    TextView descriptionTextView;

    @BindView(R.id.count_edit_text)
    EditText countEditText;

    @BindView(R.id.basket_fab)
    ImageView basketFab;

    private Unbinder unbinder;
    private ProductInfoPresenter presenter;
    private Product product;

    public static ProductInfoFragment newInstance(Product product) {
        ProductInfoFragment productInfoFragment = new ProductInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PRODUCT, product);
        productInfoFragment.setArguments(bundle);
        return productInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new ProductInfoPresenter();
        presenter.setView(this);
        presenter.subscribe();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            product = arguments.getParcelable(KEY_PRODUCT);
            setData();
        }
    }

    @Override
    public void setPresenter(ProductInfoPresenter presenter) {
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

    public void setData() {
        titleTextView.setText(product.getName());
        descriptionTextView.setText(product.getDescription());
        priceTextView.setText(String.valueOf(product.getAmount()));
        if (!TextUtils.isEmpty(product.getImage())) {
            Picasso.get().load(product.getImage()).into(productImage);
        }
        updateBasketFab(productDao.getAllProduct().isEmpty());
    }

    private void updateBasketFab(boolean isEmpty) {
        if (isEmpty) {
            basketFab.setImageResource(R.drawable.ic_basket_empty);
        } else {
            basketFab.setImageResource(R.drawable.ic_basket_not_empty);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.unsubscribe();
    }

    @OnClick(R.id.basket_fab)
    public void onViewClicked() {
    }

    @OnClick(R.id.basket_fab)
    public void onBasketFabClicked() {
    }

    @OnClick(R.id.back_button)
    public void onBackButtonClicked() {
        if (isAdded()) {
            getActivity().onBackPressed();
        }
    }

    @OnClick(R.id.add_button)
    public void onAddButtonClicked() {
        String count = countEditText.getText().toString();
        if (!TextUtils.isEmpty(count)) {
            product.setCount(Integer.parseInt(count));
            productDao.insert(product);
            Toast.makeText(getContext(), "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
            updateBasketFab(false);
        }
    }
}
