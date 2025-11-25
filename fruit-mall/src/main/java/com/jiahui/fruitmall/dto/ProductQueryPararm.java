package com.jiahui.fruitmall.dto;

import com.jiahui.fruitmall.constant.ProductCategory;

public class ProductQueryPararm {

  private   ProductCategory category;
  private  String search;

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
