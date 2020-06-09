package priv.zhou.domain.po;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.Date;

/**
 * 访问日志 数据持久化模型
 *
 * @author zhou
 * @since 2020.06.08
 */
@Getter
@Setter
@Accessors(chain = true)
public class AccessLogPO implements Serializable{

	/**
	 *
	 */
	private Integer id;

	/**
	 * 访客id
	 */
	private Integer visitorId;

	/**
	 * 主机
	 */
	private String host;

	/**
	 * 厂商
	 */
	private String mfrs;

	/**
	 * 设备
	 */
	private String device;

	/**
	 * 操作系统
	 */
	private String os;

	/**
	 * 浏览器
	 */
	private String browser;

	/**
	 * 访问接口
	 */
	private String api;

	/**
	 * 请求参数
	 */
	private String param;

	/**
	 * 用户代理
	 */
	private String userAgent;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;


	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
