package com.bootdo.blog.service;


import com.bootdo.blog.domain.ArticleCustom;
import com.bootdo.blog.domain.Category;
import com.bootdo.blog.domain.CategoryCustom;
import com.bootdo.blog.domain.Pager;
import com.bootdo.common.domain.Tree;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* Created by GeneratorFx on 2017-04-11.
*/
public interface CategoryService {



    List<ArticleCustom> loadArticleByCategory(Pager pager, Integer categoryId);

    /**
     * 初始化分类信息
     * @return
     */
    List<CategoryCustom> initCategoryList();

    Category getCategoryById(Integer id);

    List<Category> loadCategory(Pager pager, String categoryName);

    int count(Map<String, Object> params);

    boolean checkExist(Category category);

    int saveCategory(Category category);

    int updateCategory(Category category);

    void initPage(Pager pager);

    List<Category> getCategoryList(Map<String, Object> params);

    void ArticleCatePage(Pager pager, int categoryId);

    List<ArticleCustom> loadArticleByArchive(String createTime, Pager pager);

    int getArticleCountByCategoryId(Integer categoryId);

    boolean deleteCategoryById(Integer categoryId);

    int deleteCategory(Integer id);
    int batchRemove(Long[] ids);
    int updateState(@Param("id") Integer id, @Param("status") int status);
}
