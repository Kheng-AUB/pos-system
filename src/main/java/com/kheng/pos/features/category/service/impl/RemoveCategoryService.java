package com.kheng.pos.features.category.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.cateogry.entity.Category;
import com.kheng.pos.databases.pg.cateogry.repository.CategoryRepository;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.databases.pg.store.repository.StoreInfoRepository;
import com.kheng.pos.databases.pg.userinfo.repository.UserStoreRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.kheng.pos.features.category.service.util.CategoryServiceUtil.checkAuthority;

@Service
@RequiredArgsConstructor
public class RemoveCategoryService {
    private final CategoryRepository categoryRepository;
    private final UserInfoService userInfoService;
    private final UserStoreRepository userStoreRepository;
    private final StoreInfoRepository storeInfoRepository;

    public BaseApiResponse<Void> deleteCategory(Long categoryId) {
        BaseApiResponse<Void> response = new BaseApiResponse<>();

        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new AppException("Category not found",
                    HttpStatus.NOT_FOUND, "CATEGORY_NOT_FOUND");
        }

        UserProfileResponse userInfo = userInfoService.getCurrentUserInfo().getData();
        StoreInfo storeInfo = storeInfoRepository.findById(category.getStoreId()).orElse(null);
        if (storeInfo == null) {
            throw new AppException("Store info not found",
                    HttpStatus.NOT_FOUND, "STORE_INFO_NOT_FOUND");
        }

        checkAuthority(userInfo, storeInfo, userStoreRepository);

        categoryRepository.deleteById(categoryId);

        response.isSuccess();
        return response;
    }
}
