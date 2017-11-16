/**
 * 
 */
package com.kingdom.park.mina.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.kingdom.park.mina.util.IConstants;

/**
 * @author Jonny
 * @create 2011-9-30 下午02:22:53
 */
public class ProtocolModel implements IConstants {

	private String protocol;//协议
	private String protocolName;//协议名称
	private String commandTemplate;//命令模板文字
	private String license;//授权码

	public ProtocolModel(String protocol, String protocolName,String license, String commandTemplate) {
		this.protocol = protocol;
		this.protocolName = protocolName;
		this.commandTemplate = commandTemplate;
		this.license=license;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getProtocolName() {
		return protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	public String getCommandTemplate() {
		return commandTemplate;
	}

	public void setCommandTemplate(String commandTemplate) {
		this.commandTemplate = commandTemplate;
	}
	
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	/**************************************************************/

	public static ProtocolModel[] getProtocolModels() {
		List<ProtocolModel> l = new ArrayList<ProtocolModel>();
		l.add(createModel("心跳", "11","ksfkasjdf1231", "{\"version\": \"1.4\",\"ptime\": \"20160722183000\",\"freecount\": \"99\"}"));
		l.add(createModel("入场", "21","ksfkasjdf1231", "{\"version\": \"1.4\",\"ptime\": \"20160722183000\",\"freecount\": \"99\"}"));
		return l.toArray(new ProtocolModel[l.size()]);
	}
	
	public static ProtocolModel[] getProtocolModelsFromFile() {
		List<ProtocolModel> l = new ArrayList<ProtocolModel>();
//		String dir=System.getProperty("user.dir");
//		String dir=ProtocolModel.class.getClassLoader().getResource("").getPath();
//    	File f = new File(dir+"/protocol.txt");
//    	System.out.println("protocol.txt path:" + f.getAbsolutePath());
//    	
//    	URL dt=ProtocolModel.class.getClassLoader().getResource("");
//    	System.out.println("======"+dt.getPath());
    	
    	try {
//			FileReader fr = new FileReader(f);
//			BufferedReader reader = new BufferedReader(fr);
    		InputStream is=ProtocolModel.class.getResourceAsStream("/protocol.txt");   
            BufferedReader reader=new BufferedReader(new InputStreamReader(is)); 
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				String[] s = line.split("&");
				if (s.length == 4) {
					l.add(createModel(s[0], s[1], s[2],s[3]));
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ProtocolModel[] mm=l.toArray(new ProtocolModel[l.size()]);
		return mm;
	}

	public static ProtocolModel createModel(String protocolName, String protocol,String license, String commandTemplate) {
		return new ProtocolModel(protocol, protocolName,license, commandTemplate);
	}


	/**************************************************************/
	
	@Override
	public String toString() {
		return protocolName + ": " + protocol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((protocol == null) ? 0 : protocol.hashCode());
		result = prime * result
				+ ((protocolName == null) ? 0 : protocolName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProtocolModel other = (ProtocolModel) obj;
		if (protocol == null) {
			if (other.protocol != null)
				return false;
		} else if (!protocol.equals(other.protocol))
			return false;
		if (protocolName == null) {
			if (other.protocolName != null)
				return false;
		} else if (!protocolName.equals(other.protocolName))
			return false;
		return true;
	}
	

}
