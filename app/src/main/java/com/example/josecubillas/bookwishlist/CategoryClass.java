package com.example.josecubillas.bookwishlist;

import com.orm.SugarRecord;

public class CategoryClass extends SugarRecord<CategoryClass>{

    private int categoryId;
    private String name;
    private String nicename;

    public CategoryClass(){ }

    public CategoryClass(int categoryId, String name, String nicename) {
        this.categoryId = categoryId;
        this.name = name;
        this.nicename = nicename;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

}
