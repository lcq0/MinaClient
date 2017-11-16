/**
 * 
 */
package com.kingdom.park.mina.client;

import java.util.ArrayList;
import java.util.List;

import com.kingdom.park.mina.model.ProtocolModel;

/**
 * @author Jonny
 * @create 2011-10-13 下午05:29:33
 */
public class CommandCache {
	private static List<CommandEntry> commands = new ArrayList<CommandEntry>();
	private static int COMMAND_INDEX = 0;

	public static void addCommand(ProtocolModel protocol, String cmd) {
		CommandEntry ce = new CommandEntry(cmd, protocol);
		//if (!commands.contains(ce)) {
		commands.add(ce);
		//}
	}
	
	public static void removeCommand(ProtocolModel protocol, String cmd) {
		commands.remove(new CommandEntry(cmd, protocol));
	}
	
	public static List<CommandEntry> getCommands() {
		return commands;
	}
	
	public static CommandEntry getCommand(int index) {
		if (index >= 0 && index < commands.size()) {
			CommandEntry ce = commands.get(index);
			return ce;
		}
		return null;
	}
	
	public static CommandEntry getCommand() {
		System.out.println("Index:" + COMMAND_INDEX);
		return getCommand(COMMAND_INDEX);
	}
	
	public static void indexMoveUp() {
		COMMAND_INDEX ++;
		if (COMMAND_INDEX >= commands.size() - 1) {
			COMMAND_INDEX = 0;
		}
	}
	
	public static void indexMoveDown() {
		COMMAND_INDEX --;
		if (COMMAND_INDEX < 0) {
			COMMAND_INDEX = commands.size() - 1;
		}
	}
	
	public static void resetCommandIndex() {
		COMMAND_INDEX = commands.size() - 1;
	}
	
	public static class CommandEntry {
		private String command;
		private ProtocolModel protocol;

		public CommandEntry(String command, ProtocolModel protocol) {
			super();
			this.command = command;
			this.protocol = protocol;
		}
		public String getCommand() {
			return command;
		}
		public void setCommand(String command) {
			this.command = command;
		}
		public ProtocolModel getProtocol() {
			return protocol;
		}
		public void setProtocol(ProtocolModel protocol) {
			this.protocol = protocol;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((command == null) ? 0 : command.hashCode());
			result = prime * result
					+ ((protocol == null) ? 0 : protocol.hashCode());
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
			CommandEntry other = (CommandEntry) obj;
			if (command == null) {
				if (other.command != null)
					return false;
			} else if (!command.equals(other.command))
				return false;
			if (protocol == null) {
				if (other.protocol != null)
					return false;
			} else if (!protocol.equals(other.protocol))
				return false;
			return true;
		}
		
		
	}
}
