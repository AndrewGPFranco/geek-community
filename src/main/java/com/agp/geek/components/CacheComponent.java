package com.agp.geek.components;

import com.agp.geek.dtos.auth.ValidateCodeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CacheComponent {

    private final CacheManager cacheManager;

    public void savedUUIDForgotPassword(UUID uuid, String email) {
        Objects.requireNonNull(cacheManager.getCache("cacheManager")).put(uuid, email);
    }

    public String recoversCache(UUID uuid) {
        return Objects.requireNonNull(cacheManager.getCache("cacheManager")).get(uuid, String.class);
    }

    public void removeCache(String uuid) {
        Objects.requireNonNull(cacheManager.getCache("cacheManager")).evict(uuid);
    }

    public void savedUser(ValidateCodeDTO dto, UUID uuid) {
        Objects.requireNonNull(cacheManager.getCache("cacheManager")).put(uuid, dto);
    }

    public ValidateCodeDTO recoverUserCache(UUID id) {
        return Objects.requireNonNull(cacheManager.getCache("cacheManager")).get(id, ValidateCodeDTO.class);
    }

    public void removeUserCache(UUID id) {
        Objects.requireNonNull(cacheManager.getCache("cacheManager")).evict(id);
    }

}
