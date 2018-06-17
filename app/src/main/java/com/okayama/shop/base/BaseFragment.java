package com.okayama.shop.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.okayama.shop.OkayamaApplication;
import com.okayama.shop.data.dao.ProductDao;
import com.okayama.shop.ui.basket.BasketFragment;

public abstract class BaseFragment extends Fragment {

    protected ProductDao productDao;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productDao = OkayamaApplication.getComponent()
                .getDatabase()
                .getProductDao();
    }

    protected void onBasketClicked() {
        if (BaseFragment.this.isAdded()) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, BasketFragment.newInstance(), BasketFragment.class.getSimpleName())
                    .addToBackStack(BasketFragment.class.getSimpleName())
                    .commit();
        }
    }
}
