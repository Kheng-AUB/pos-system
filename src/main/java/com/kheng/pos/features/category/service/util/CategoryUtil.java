package com.kheng.pos.features.category.service.util;

import com.kheng.pos.databases.pg.store.entity.StoreInfo;
import com.kheng.pos.databases.pg.userinfo.entity.UserStore;
import com.kheng.pos.databases.pg.userinfo.repository.UserStoreRepository;
import com.kheng.pos.exception.AppException;
import com.kheng.pos.features.user.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryUtil {
    public static void checkAuthority(UserProfileResponse userInfo, StoreInfo storeInfo, UserStoreRepository userStoreRepository) throws AppException {
        boolean isAdmin = userInfo.getRoleType().equals("ROLE_ADMIN");
        boolean isManager = userInfo.getRoleType().equals("ROLE_STORE_MANAGER");

        UserStore userStore = userStoreRepository.findByStoreId(storeInfo.getId());
        boolean isSameStore = userInfo.getUserId().equals(userStore.getUserId());

        if (!(isAdmin && isSameStore) && !isManager) {
            throw new AppException("You don't have permission to perform this action",
                    null, "FORBIDDEN");
        }
    }
}
