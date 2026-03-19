package com.kheng.pos.features.category.service.impl;

import com.kheng.pos.core.dto.BaseApiResponse;
import com.kheng.pos.databases.pg.cateogry.entity.Category;
import com.kheng.pos.databases.pg.cateogry.repository.CategoryRepository;
import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.databases.pg.store.repository.StoreInfoRepository;
import com.kheng.pos.databases.pg.userinfo.repository.UserStoreRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.category.mapper.CategoryMapper;
import com.kheng.pos.features.category.payload.dto.CategoryDto;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import com.kheng.pos.features.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.kheng.pos.features.category.service.util.CategoryServiceUtil.checkAuthority;

@Service
@RequiredArgsConstructor
public class CreateCategoryService {
    private final CategoryRepository categoryRepository;
    private final StoreInfoRepository storeInfoRepository;
    private final UserInfoService userInfoService;
    private final UserStoreRepository userStoreRepository;

    public BaseApiResponse<CategoryDto> createCategory(CategoryDto categoryDto) {
        BaseApiResponse<CategoryDto> response = new BaseApiResponse<>();

        // check store
        StoreInfo storeInfo = storeInfoRepository.findById(categoryDto.getStoreId()).orElse(null);
        if (storeInfo == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }

        UserProfileResponse userInfo = userInfoService.getCurrentUserInfo().getData();

        checkAuthority(userInfo, storeInfo, userStoreRepository);

        Category category = CategoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);

        response.setData(CategoryMapper.toDto(savedCategory));
        response.isSuccess();

        return response;
    }
}
