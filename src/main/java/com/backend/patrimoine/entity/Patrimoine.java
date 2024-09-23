package com.backend.patrimoine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patrimoine {
    private String possesseur;
    private LocalDateTime derniereModification;
}
