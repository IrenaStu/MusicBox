package com.example.musicBox.model.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@MappedSuperclass
@Getter
public abstract class TableDates {

    @CreationTimestamp
    protected LocalDate createdAt;

    @UpdateTimestamp
    public LocalDate updatedAt;

}