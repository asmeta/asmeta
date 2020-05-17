/*******************************************************************************
 * Copyright (c) 2005, 2006 ASMETA group (http://asmeta.sourceforge.net)
 * License Information: http://asmeta.sourceforge.net/licensing/
 * 
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License version 2 as
 *   published by the Free Software Foundation.
 * 
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 * 
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301
 *   USA
 * 
 *   http://www.gnu.org/licenses/gpl.txt
 * 
 *   
 *******************************************************************************/

/**
 * XmlToTextLayout.java
 *
 * Created on 31/lug/06, 17:03:57
 * 
 */
package org.asmeta.simulator.util;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * An XML to text layout.
 * The XML-like log messages are converted in indented strings.
 * Only two kind of message are managed:
 * <br>1) &lt a tag &gt
 * <br>2) &lt open tag &gt a string &lt close tag &gt
 *
 */
public class XmlToTextLayout extends Layout {
	
	/**
	 * To this string, is added a tab for each open tag encountered and is
	 * removed for each close tag.
	 * 
	 */
	private String tabs;
	
	/**
	 * A tab-width string.
	 * 
	 */
	private String TAB = "    ";
	
	/**
	 * Constructor.
	 * 
	 */
	public XmlToTextLayout() {
		tabs = "";
	}

    @Override
	public void activateOptions() {
    }
    
    @Override
	public String format(LoggingEvent event) {
        String message = event.getRenderedMessage();
        if( message.indexOf('<') >= 0){
        	if (message.charAt(message.indexOf('<') + 1) != '/' && 
        			message.charAt(message.lastIndexOf('<') + 1) == '/') {
        		message = tabs + message;
        	} else if (message.charAt(message.indexOf('<') + 1) == '/') {
        		tabs = tabs.substring(TAB.length());
        		message = tabs + message;
        	} else {
        		message = tabs + message; 
        		tabs = tabs.concat(TAB);        	
        	}
        } else {
        	// NO XML TAGS
        	message = tabs + message;
        }
        return message + LINE_SEP;
    }
        
    @Override
	public boolean ignoresThrowable() {
        return true;
    }

}
