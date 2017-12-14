package com.bootdo.wx;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 微信回调处理
 *
 * @author : zscat
 * @version : 1.0
 * @created on  : 2017/6/28  下午5:21
 */
@Controller
@RequestMapping(Constant.WX_CALLBACK_URI)
public class WxCallbackController extends BaseController {

}
