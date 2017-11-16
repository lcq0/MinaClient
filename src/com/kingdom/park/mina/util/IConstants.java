/**
 *
 */
package com.kingdom.park.mina.util;


/**
 * 协议编码常量类
 * @author YaoZhq
 *
 */
public interface IConstants {



//	public static final String SERVER_ADDRESS = "192.168.10.52:9697";
	public static boolean iswondows = false;//后台系统是否是windows服务器
//	public static final String SERVER_ADDRESS = "localhost:9697";

//	public static final String SERVER_ADDRESS = "localhost:9697";
//	public static final String SERVER_ADDRESS = "121.43.156.229:9697";

//	public static final String LICENSE_KEY="42010620001720161231235959906141";

//	public static final String LICENSE_KEY="c1e63e321ee8ed882f043d477512653b";//李雪在用
//	public static final String LICENSE_KEY="4bd87f070e20d61a11551ecc44e2f487";//伍路
//	public static String LICENSE_KEY="844aea0b72e212a17018a173560ea49b";//曲江银座停车场
	//http://192.168.10.71:81/yunpark/page/frame/frame.htm?login=true parkadmin 123qaz

	public static final String ProtocolVersion="1.4";

	/**
	 * 心跳间隔
	 */
	public static final int HeartSpace=10000;

	/**
	 * 入场流水间隔
	 */
	public static final int InlogSpace=15000;

	/**
	 * 出场流水间隔
	 */
	public static final int OutlogSpace=15000;

	/**
	 * 定时关闭与云平台的连接状态间隔时长
	 */
	public static final int termination=20000;

	/**
	 * 定时启动与云平台的连接状态间隔时长
	 */
	public static final int reconnection=20000;

	/**
	 * 分隔符
	 */
	public static final String SPLITCHAR = "&";

	/**
	 * 冒号
	 */
	public static final String COLON = ":";

	/**
	 * 逗号
	 */
	public static final String COMMA =",";

	/**
	 * FF******FF 8字节替代符号
	 */
	public static final String FF_CHAR="FFFFFFFFFFFFFF";

	/**
	 * 左斜杠
	 */
	public static final String LEFTSLASH="/";

	/**
	 * 包尾
	 */
	 public static final String CRLF = "\r\n";




	/**********************状态协议**************************/

	/**转义符**/
	public static final byte d_0x24 = 0x24;

	/**协议头**/
	public static final short s_0x2424=0x2424;

	/**转义符**/
	public static final byte d_0x0d = 0x0d;

	/**转义符**/
	public static final byte d_0x0a = 0x0a;

	/**协议尾**/
	public static final short s_0x0d0a=0x0d0a;

	/**********************指令协议**************************/

	//上行

	/**
	 * 心跳上传
	 */
	public static final byte U_0x11=0x11;

	/**
	 * 心跳应答
	 */
	public static final byte L_0x12=0x12;


	/**
	 * 车辆进场上报
	 */
	public static final byte U_0x21=0x21;

	/**
	 * 车辆进场上报应答
	 */
	public static final byte L_0x22=0x22;

	/**
	 * 车辆出场
	 */
	public static final byte U_0x23=0x23;

	/**
	 * 车辆出场应答
	 */
	public static final byte L_0x24=0x24;


	/**
	 * 提交日报表
	 */
	public static final byte U_0x25=0x25;

	/**
	 * 提交日报表应答
	 */
	public static final byte L_0x26=0x26;

	/**
	 * 查询车辆资产
	 */
	public static final byte U_0x27=0x27;

	/**
	 * 查询车辆资产应答
	 */
	public static final byte L_0x28=0x28;

	/**
	 * 上传并支付订单
	 */
	public static final byte U_0x29=0x29;

	/**
	 * 上传并支付订单应答
	 */
	public static final byte L_0x30=0x30;

	/**
	 * 更新入场图片路径
	 */
	public static final byte U_0x31=0x31;

	/**
	 * 更新入场图片路径应答
	 */
	public static final byte L_0x32=0x32;


	/**
	 * 入场查询月卡
	 */
	public static final byte U_0x33=0x33;
	/**
	 * 入场查询月卡应答
	 */
	public static final byte L_0x34=0x34;

	/**
	 * 定时查询月卡
	 */
	public static final byte U_0x35=0x35;
	/**
	 * 定时查询月卡应答
	 */
	public static final byte L_0x36=0x36;

	//下行
	/**
	 * 同步会员资产
	 */
	public static final byte L_0x51=0x51;

	/**
	 * 同步会员资产应答
	 */
	public static final byte U_0x52=0x52;

	/**
	 * 同步车牌绑定
	 */
	public static final byte L_0x53=0x53;

	/**
	 * 同步车牌绑定应答
	 */
	public static final byte U_0x54=0x54;

	/**
	 * 同步自助缴费
	 */
	public static final byte L_0x55=0x55;

	/**
	 * 同步自助缴费应答
	 */
	public static final byte U_0x56=0x56;

	/**
	 * 同步预约订单
	 */
	public static final byte L_0x57=0x57;

	/**
	 * 同步预约订单应答
	 */
	public static final byte U_0x58=0x58;


	/**
	 * 同步停车费用
	 */
	public static final byte L_0x59=0x59;


	/**
	 * 同步停车费用应答
	 */
	public static final byte U_0x60=0x60;

	/**
	 * 月卡会员变更
	 */
	public static final byte L_0x61=0x61;
	/**
	 * 月卡会员变更应答
	 */
	public static final byte U_0x62=0x62;

	/**********************指令协议**************************/


	/**********************业务处理结果**************************/
	/**成功*/
	public static final String Result_Code_Y="Y";
	/**失败*/
	public static final String Result_Code_N="N";

	/**成功*/
	public static final String Result_Code_1="1";
	/**失败*/
	public static final String Result_Code_0="0";

	/**********************业务处理结果**************************/

	/**********************异常错误代码**************************/
	public static final String Error_Code_80000000="80000000";
	public static final String Error_Msg_80000000="成功";

	public static final String Error_Code_80000001="80000001";
	public static final String Error_Msg_80000001="系统异常";

	public static final String Error_Code_80000002="80000002";
	public static final String Error_Msg_80000002="数据解析异常";

	public static final String Error_Code_80000003="80000003";
	public static final String Error_Msg_80000003="数据包体格式不正确";

	public static final String Error_Code_80000004="80000004";
	public static final String Error_Msg_80000004="请求未授权";


	//停车场流水上传
	public static final String Error_Code_80002001="80002001";
	public static final String Error_Msg_80002001="";

	/**********************异常错误代码**************************/




}
