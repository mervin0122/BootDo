package com.bootdo.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bootdo.system.vo.UserVO;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.stereotype.Service;

import com.bootdo.common.domain.Tree;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.UserDO;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
	UserDO get(Long id);

	List<UserDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(UserDO user);
	int saveOnly(UserDO user);

	int update(UserDO user);
	int updateOnly(UserDO user);
	int remove(Long userId);

	int batchremove(Long[] userIds);

	boolean exit(Map<String, Object> params);

	Set<String> listRoles(Long userId);

	/*int resetPwd(UserDO user);*/
	int resetPwd(UserVO userVO, UserDO userDO) throws Exception;
	int adminResetPwd(UserVO userVO) throws Exception;
	Tree<DeptDO> getTree();
	/**
	 * 合并微信用户信息
	 *
	 * @param wxOauthCode
	 * @return
	 */
	UserDO mergeUserInfo(String wxOauthCode);

	/**
	 * 获取微信用户
	 *
	 * @param wxOauthCode
	 * @return
	 */
	UserDO getByWxOauthCode(String wxOauthCode) throws WxErrorException;

	/**
	 * 根据 微信openid查询
	 * @param openid
	 * @return
	 */
	UserDO getByWxOpenId(String openid);
	/**
	 * 更新个人信息
	 * @param userDO
	 * @return
	 */
	int updatePersonal(UserDO userDO);
	/**
	 * 更新个人图片
	 * @param file 图片
	 * @param avatar_data 裁剪信息
	 * @param userId 用户ID
	 * @throws Exception
	 */
	Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;
}
