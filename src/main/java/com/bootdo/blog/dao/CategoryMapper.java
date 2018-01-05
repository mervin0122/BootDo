package com.bootdo.blog.dao;


import com.bootdo.blog.domain.Category;
import com.bootdo.blog.domain.CategoryCustom;
import com.bootdo.blog.domain.Pager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* Created by GeneratorFx on 2017-04-11.
*/
@Mapper
public interface CategoryMapper {

    /**
     * 初始化分类信息
     *
     * @return
     */
    List<CategoryCustom> initCategoryList();

    Category getCategoryById(Integer id);

    List<Category> loadCategory(@Param("pager") Pager pager, @Param("categoryName") String categoryName);

    int checkExist(Category category);

    int saveCategory(Category category);

    int updateCategory(Category category);

    int initPage(Pager pager);

    List<Category> getCategoryList(Map<String, Object> params);

    int count(Map<String, Object> params);
    /**
     * 获取当前id的文章数量
     *
     * @param categoryId
     * @return
     */
    int ArticleCatePage(int categoryId);

    void deleteCategoryById(Integer categoryId);

    int deleteCategory(Integer id);
    int batchRemove(Long[] ids);
    int updateState(@Param("id") Integer id, @Param("status") int status);
}
