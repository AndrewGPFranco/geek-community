package com.agp.geek.dtos.user;

import java.time.Instant;

public record InfoProfileDTO(
        String username,
        String description,
        String role,
        Instant registrationDate
) {}
