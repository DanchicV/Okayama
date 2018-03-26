package com.okayama.shop.data.repository;

import com.okayama.shop.data.api.ApiService;
import com.okayama.shop.data.repository.base.AuthRepository;

import javax.inject.Inject;

public class AuthRepositoryImpl implements AuthRepository {

    @Inject
    ApiService apiService;


}
