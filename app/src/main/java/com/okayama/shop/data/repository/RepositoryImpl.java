package com.okayama.shop.data.repository;

import com.okayama.shop.OkayamaApplication;
import com.okayama.shop.base.BaseRepository;
import com.okayama.shop.data.api.ApiService;
import com.okayama.shop.data.dao.ProductDao;
import com.okayama.shop.data.models.Category;
import com.okayama.shop.data.models.OrderElement;
import com.okayama.shop.data.models.Product;
import com.okayama.shop.util.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Callback;

public class RepositoryImpl implements BaseRepository {

    private ApiService apiService;
    private ProductDao productDao;
    private PreferenceHelper preferenceHelper;

    public RepositoryImpl() {
        OkayamaApplication.AppComponent appComponent = OkayamaApplication.getComponent();
        this.apiService = appComponent.getApiService();
        productDao = appComponent.getDatabase().getProductDao();
        preferenceHelper = appComponent.getPreferenceHelper();
    }

    @Override
    public void registration(int role,
                             String name,
                             String password,
                             String email,
                             String city,
                             String organization,
                             Callback<Void> callback) {
        apiService.registration(
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(role)),
                RequestBody.create(MediaType.parse("text/plain"), name),
                RequestBody.create(MediaType.parse("text/plain"), email),
                RequestBody.create(MediaType.parse("text/plain"), password),
                RequestBody.create(MediaType.parse("text/plain"), city),
                RequestBody.create(MediaType.parse("text/plain"), organization)
        ).enqueue(callback);
    }

    @Override
    public void login(String email, String password,
                      Callback<Void> callback) {
        apiService.authorization(
                RequestBody.create(MediaType.parse("text/plain"), email),
                RequestBody.create(MediaType.parse("text/plain"), password)
        ).enqueue(callback);
    }

    @Override
    public void getCategories(Callback<List<Category>> callback) {
        apiService.getCategories().enqueue(callback);
    }

    @Override
    public void getProducts(long id, Callback<List<Product>> callback) {
        apiService.getProducts(id).enqueue(callback);
    }

    @Override
    public void createOrder(Callback<Void> callback) {
        String email = preferenceHelper.getEmail();
        String phone = preferenceHelper.getPhone();
        List<Product> products = productDao.getAllProduct();
        List<OrderElement> elements = new ArrayList<>();
        for (Product product : products) {
            elements.add(new OrderElement(product.getId(), product.getCount()));
        }
        apiService.createOrder(
                RequestBody.create(MediaType.parse("text/plain"), email),
                RequestBody.create(MediaType.parse("text/plain"), phone),
                elements
        ).enqueue(callback);
    }

    @Override
    public List<Product> getBasket() {
        return productDao.getAllProduct();
    }
}
