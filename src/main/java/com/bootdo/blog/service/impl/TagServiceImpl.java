package com.bootdo.blog.service.impl;


import com.bootdo.blog.dao.ArticleMapper;
import com.bootdo.blog.dao.TagMapper;
import com.bootdo.blog.domain.ArticleTag;
import com.bootdo.blog.service.TagService;
import com.bootdo.blog.domain.ArticleCustom;
import com.bootdo.blog.domain.Pager;
import com.bootdo.blog.domain.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Do
 * @package com.bootdo.blog.service.impl
 * @name TagServiceImpl
 * @date 2017/4/13
 * @time 18:56
 */
@Service
public class TagServiceImpl implements TagService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private TagMapper tagMapper;
    @Override
    public List<ArticleCustom> loadArticleByTag(Pager pager, Integer tagId) {
        return articleMapper.loadArticleByTag(pager,tagId);
    }

    @Override
    public int getTagCount() {
        return tagMapper.getTagCount();
    }

    @Override
    public Tag getTagById(Integer id) {
        return tagMapper.getTagById(id);
    }

    @Override
    public List<Tag> loadTagList(Map<String, Object> params) {
        List<Tag> tagList=  tagMapper.loadTagList(params);
        List<ArticleTag> atList=articleMapper.getTag(params);
        if (atList.size()>0){
            for (ArticleTag at : atList) {
                for (Tag tag : tagList) {
                    if (!Objects.equals(at.getTagId().toString(),tag.getId().toString())) {
                        continue;
                    }
                    tag.setRemarks("true");
                    break;
                }
            }
        }
        return tagList;
    }

    @Override
    public int saveTag(Tag tag) {
     return    tagMapper.saveTag(tag);
    }

    @Override
    public boolean checkExist(Tag tag) {
        int count = tagMapper.checkExist(tag);
        if (count>0){
            return true;
        }
        return false;
    }

    @Override
    public int updateTag(Tag tag) {
        return  tagMapper.updateTag(tag);
    }

    @Override
    public void initPage(Pager pager) {
        int count = tagMapper.initPage(pager);
        pager.setTotalCount(count);
    }

    @Override
    public List<Tag> getTagList() {
        return tagMapper.getTagList();
    }



    /**
     * 初始化tag -> article的分页
     * @param pager
     */
    @Override
    public void ArticleTagPage(Pager pager, int tagId) {
        int count =  tagMapper.articleTagPage(tagId);
        pager.setTotalCount(count);
    }
    @Override
    public int deleteTag(Integer id) {
        return tagMapper.deleteTag(id);
    }

    @Override
    public int batchRemove(Long[] ids){
        return  tagMapper.batchRemove(ids);
    }

    @Override
    public  int count(Map<String, Object> params) {
        return tagMapper.count(params);
    }

    @Override
    public int saveArtitleTag(ArticleTag at) {
        List<ArticleTag> list=new ArrayList();
      if (StringUtils.isNotEmpty(at.getTagId())){
          String[] str = at.getTagId().split(",");
          for (int i = 0; i < str.length; i++) {

              ArticleTag articleTag=new ArticleTag();
              articleTag.setTagId(str[i]);
              articleTag.setArticleId(at.getArticleId());
              articleTag.setTagName(tagMapper.getTagById(Integer.parseInt(str[i])).getTagName());
              list.add(articleTag);
          }
      }
          return tagMapper.saveArtitleTag(list);
    }
    @Override
    public int deleteArtitleTag(Integer id){
        return tagMapper.deleteArtitleTag(id);
    }
}
