package com.kheng.pos.features.store.service.util;

import com.kheng.pos.databases.pg.store.entity.StoreContact;
import com.kheng.pos.databases.pg.store.repository.StoreContactRepository;
import com.kheng.pos.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class StoreServiceUtil {
    public static StoreContact checkStoreContact(Long storeId, StoreContactRepository storeContactRepository) {
        StoreContact storeContact = storeContactRepository.findByStoreId(storeId);
        if (storeContact == null) {
            throw new AppException("Store not found",
                    HttpStatus.NOT_FOUND, "STORE_NOT_FOUND");
        }
        return storeContact;
    }
}
