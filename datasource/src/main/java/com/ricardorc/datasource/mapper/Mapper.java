package com.ricardorc.datasource.mapper;

public interface Mapper<I, O> {
    O convert(I response);
}
