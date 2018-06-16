package com.okayama.shop.ui.categories;

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
import com.okayama.shop.data.models.Category;
import com.okayama.shop.ui.product.ProductFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CategoriesFragment extends BaseFragment implements CategoriesContract.View {

    @BindView(R.id.login_progress)
    ProgressBar loginProgress;

    @BindView(R.id.categories_recycler_view)
    RecyclerView categoriesRecyclerView;

    @BindView(R.id.basket_fab)
    ImageView basketFab;

    private Unbinder unbinder;
    private CategoriesPresenter presenter;
    private CategoriesAdapter adapter;

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
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
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new CategoriesPresenter();
        presenter.setView(this);
        presenter.subscribe();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        categoriesRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CategoriesAdapter(new ItemClickListener() {
            @Override
            public void onClick(long id) {
                if (CategoriesFragment.this.isAdded()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .add(android.R.id.content, ProductFragment.newInstance(id), ProductFragment.class.getSimpleName())
                            .addToBackStack(ProductFragment.class.getSimpleName())
                            .commit();
                }
            }
        });
        categoriesRecyclerView.setAdapter(adapter);

        presenter.loadCategories();
    }

    @Override
    public void setPresenter(CategoriesPresenter presenter) {
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
    public void setData(List<Category> categories) {
        adapter.setCategories(categories);
        adapter.notifyDataSetChanged();
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
}
