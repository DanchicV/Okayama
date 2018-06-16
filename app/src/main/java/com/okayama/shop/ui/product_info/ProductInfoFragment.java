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

    private Unbinder unbinder;
    private ProductInfoPresenter presenter;

    public static ProductInfoFragment newInstance(Product product) {
        ProductInfoFragment productInfoFragment = new ProductInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PRODUCT, product);
        productInfoFragment.setArguments(bundle);
        return productInfoFragment;
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
            Product product = arguments.getParcelable(KEY_PRODUCT);
            setData(product);
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

    public void setData(Product product) {
        titleTextView.setText(product.getName());
        descriptionTextView.setText(product.getDescription());
        priceTextView.setText(String.valueOf(product.getAmount()));
        if (!TextUtils.isEmpty(product.getImage())) {
            Picasso.get().load(product.getImage()).into(productImage);
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
    }
}
