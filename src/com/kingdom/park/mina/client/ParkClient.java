/**
 *
 */
package com.kingdom.park.mina.client;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.kingdom.park.mina.cache.UserWrapperCache;
import com.kingdom.park.mina.client.ParkClientHandler.Callback;
import com.kingdom.park.mina.codec.Coding;
import com.kingdom.park.mina.command.CommandBuilder;
import com.kingdom.park.mina.command.CommandImpl;
import com.kingdom.park.mina.command.ICommand;
import com.kingdom.park.mina.command.up.C_0x11_Command;
import com.kingdom.park.mina.model.MessageBag;
import com.kingdom.park.mina.model.ProtocolModel;
import com.kingdom.park.mina.model.SessionWrapper;
import com.kingdom.park.mina.model.User;
import com.kingdom.park.mina.util.Assert;
import com.kingdom.park.mina.util.IConstants;
import com.kingdom.park.mina.util.LoggerUtil;

/**
 * @author
 * @create 2011-10-10 上午09:12:39
 */
public class ParkClient extends JFrame implements Callback, UIClient {

	private static final long serialVersionUID = -1142880842394123900L;


	private User user = new User();

    private static JTable messageList;
    private ParkClientSupport client;
    private ParkClientHandler handler;
    private NioSocketConnector connector;
	private Timer timer;



	/**
	 * 实例化客户端
	 */
	public void initClient(String serverField,String LICENSE_KEY,boolean firstTime) {

        SocketAddress address = parseSocketAddress(serverField);

        connector = new NioSocketConnector();
        handler = new ParkClientHandler(ParkClient.this);
        client = new ParkClientSupport("Socket Test", handler);

        if (!client.connect(connector, address, false)) {
        	String message = "不能连接到服务器：" + address + ". \n";
        	append(message);
        	LoggerUtil.logger(ParkClientSupport.class, message);

        	reStartService();//重启注册服务（不能连接服务器时调用）
        } else {
        	String message = "连接成功！开始授权认证...... \n";
        	append(message);
        	LoggerUtil.logger(ParkClientSupport.class, message+"sessionId:"+client.session.getId());
//        	//发送 心跳，校验授权
        	try {
        		MessageBag mb=CommandBuilder.createHeartBeats(LICENSE_KEY);
    			ICommand C_0x11_Command=new C_0x11_Command();
    			//标识是否是首次登录 true:是 false：否
    			boolean flag=true;
            	C_0x11_Command.execute(client.session, mb, flag);

			} catch (Exception e) {
				e.printStackTrace();
			}

			//放入SessionWrapper缓存中
			SessionWrapper sessionWrapper=new SessionWrapper();
			sessionWrapper.setClient(client);
			sessionWrapper.setConnector(connector);
			sessionWrapper.setHandler(handler);
			sessionWrapper.setSession(client.session);
			sessionWrapper.setSessionId(client.session.getId());
			sessionWrapper.setLicenseKey(LICENSE_KEY);
			UserWrapperCache.getInstance().addSessionWrapper(sessionWrapper);
			initHeartbeat(LICENSE_KEY);
        }
	}

	/**
	 * 心跳
	 */
	private void initHeartbeat(final String LICENSE_KEY){
		Timer hearttimer = new Timer();
		TimerTask hearttask = new TimerTask() {
			@Override
			public void run() {
				MessageBag mb=CommandBuilder.createHeartBeats(LICENSE_KEY);
    			ICommand C_0x11_Command=new C_0x11_Command();
    			//标识是否是首次登录 true:是 false：否
    			boolean flag=false;
            	C_0x11_Command.execute(client.session, mb, flag);
			}
		};

		hearttimer.schedule(hearttask, IConstants.HeartSpace, IConstants.HeartSpace);
	}

	/**
	 * 重启注册服务（不能连接服务器时调用）
	 */
	private void reStartService(){
		Timer hearttimer = new Timer();
		TimerTask hearttask = new TimerTask() {
			@Override
			public void run() {
				try {
					LoggerUtil.logger(ParkClient.class,"重启同步外挂服务,原因:不能连接到服务器");
					Desktop.getDesktop().open(new File("config\\runstart.cmd"));
					cancel();
				} catch (Exception e) {
					LoggerUtil.error(ParkClient.class, e, "重启同步外挂服务出错");
					e.printStackTrace();
				}
			}
		};

		hearttimer.schedule(hearttask, 120*1000, 120*1000);//不能连接到服务器之后，2分钟重启一次
	}

    public void append(boolean send, Object message) {
    	MessageBag b =new MessageBag();
    	String msg = message.toString();
    	if (message instanceof MessageBag) {
    		b = (MessageBag) message;
    		msg = ((MessageBag)message).getBagCode() + IConstants.SPLITCHAR +((MessageBag)message).getLicenseKey()+IConstants.SPLITCHAR + ((MessageBag)message).getTextContent() + "\n";
    		b = new MessageBag();
    	}

    	if (send) {
    		b.setStrData("发：");
    	} else {
    		b.setStrData("收：");
//    		msg = ParkCache.GBKtoUTF(msg);
    	}
    	String string = b.getStrData() + msg;
    	if (!Assert.isNull(b.getBagCode())) {
    		if (!b.getBagCode().equals(CommandImpl.getCodeString(IConstants.L_0x12))&&!b.getBagCode().equals(CommandImpl.getCodeString(IConstants.U_0x11))) {
    		}
		}
        b.setStrData(b.getStrData() + msg);
//        ((DefaultTableModel)messageList.getModel()).addRow(new Object[]{b.getStrData()});
        LoggerUtil.logger(this.getClass(), b.getStrData());
    }

    void append(String text) {
        append(true, text);
    }

    private void notifyError(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private SocketAddress parseSocketAddress(String s) {
        s = s.trim();
        int colonIndex = s.indexOf(":");
        if (colonIndex > 0) {
            String host = s.substring(0, colonIndex);
            int port = parsePort(s.substring(colonIndex + 1));
            return new InetSocketAddress(host, port);
        } else {
            int port = parsePort(s.substring(colonIndex + 1));
            return new InetSocketAddress(port);
        }
    }

    private int parsePort(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Illegal port number: " + s);
        }
    }

    public void connected() {
        //client.login();
    }

    public void disconnected() {
        append("连接关闭.\n");
//        setLoggedOut();
    }

    public void error(String message) {
        notifyError(message + "\n");
    }

    public void loggedIn() {
//        setLoggedIn();
        append("连接成功.\n");
    }

    public void loggedOut() {
        try {
            client.quit(user.getLoginName());
            append("连接断开.\n");
//            setLoggedOut();
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(this,
                    "Session could not be closed.");
        }
    }

    public void quit() {
    	 if (client != null) {
             client.quit(user.getLoginName());
         }
    	 dispose();
    }

    public void messageReceived(IoSession session,Object message) {
        append(false, message);
        //响应下行指令
//        SendResponse(session,message);
    }


    /**
     *
     * @param session
     * @param message
     */
    private void SendResponse(IoSession session,Object message){
    	try {
    		MessageBag bag=(MessageBag)message;
    		String bagcode=bag.getBagCode();
    		//判断是否是平台下行的指令
    		if(bagcode.equals("51")||bagcode.equals("53")
    				||bagcode.equals("55")||bagcode.equals("57")||bagcode.equals("59")){
    			MessageBag mb=new MessageBag();
    			String data="{\"result\": \"Y\",\"error_code\": \"80000000\",\"error_msg\": \"成功\",\"data\":\"\"}";
    			if(bagcode.equals("51")){
    				mb.setBagCode("52");
    			}else if(bagcode.equals("53")){
    				mb.setBagCode("54");
    			}else if(bagcode.equals("55")){
    				mb.setBagCode("56");
    				data="{\"result\": \"Y\",\"error_code\": \"80000000\",\"error_msg\": \"成功\",\"data\":[{\"fundbal\":\"15.00\",\"feetime\":\"20160722175900\"}]}";
    			}else if(bagcode.equals("57")){
    				mb.setBagCode("58");
    			}else if(bagcode.equals("59")){
    				mb.setBagCode("60");
    				data="{\"result\": \"Y\",\"error_code\": \"80000000\",\"error_msg\": \"成功\",\"data\":[{\"fundbal\":\"15.00\",\"feetime\":\"20160722175900\"}]}";
    			}else
    				return ;

    			mb.setLicenseKey(bag.getLicenseKey());
    			mb.setTextContent(data);
    			mb.setBagLength(data.getBytes().length+47);
    			mb.setTextLength(data.getBytes().length);

    			CommandHelper.sendCommand(session, mb.getBagCode(),bag.getLicenseKey(), data);
				append(true, mb);
    		}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }

    public void afterBroadcast(ProtocolModel pm, String message) {
    	if (String.valueOf(IConstants.U_0x11).equals(pm.getProtocol())) {
    		String[] dat = message.split(IConstants.SPLITCHAR);
    		user.setLoginName(dat[0]);
    	}
    	CommandCache.addCommand(pm, message);
    	CommandCache.resetCommandIndex();
    }

	public void sendCommand(String protocol,String license, String command) {
		client.sendCommand(protocol, license,command);
	}

	public void cleanUI() {
//        inputText.setText("");
//        inputText.setFocusable(true);
	}

	public void copyMessage() {
		Object obj = messageList.getModel().getValueAt(messageList.getSelectedRow(), messageList.getSelectedColumn());
		if (obj == null) {
			return;
		}
		Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection s = new StringSelection(obj.toString());
		sysc.setContents(s, s);

	}

	public void clearMessage() {
		 ((DefaultTableModel)messageList.getModel()).getDataVector().clear();
		 messageList.updateUI();
	}

	@Override
	public void dispose() {
		super.dispose();
		if (messageList != null) {
			((DefaultTableModel)messageList.getModel()).getDataVector().clear();
		}
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public static JTable getMessageList() {
		return messageList;
	}


	public static void setMessageList(JTable messageList) {
		ParkClient.messageList = messageList;
	}
}
