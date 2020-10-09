package com.example.server.dtos;

public class CategoryDto implements CategoryProjection {
    private Long id;
    private String name;

    public CategoryDto(CategoryProjection projection){
        this.id = projection.getId();
        this.name = projection.getName();
    }

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
