/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package tgtlib.util;
/*
 pemette il debug (nel caso che nel costruttore di metta out = true
 */
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

import org.apache.log4j.Logger;

public class MyStreamTokenizer extends StreamTokenizer{
    
	static final Logger log = Logger.getLogger(MyStreamTokenizer.class);

	
    private final boolean stdOutput;
    
    public MyStreamTokenizer(Reader r, boolean out){
        super(r);
        stdOutput = out;
    }
    
    @Override
	public int nextToken() {
        int charRead = 0;
        try{
            charRead = super.nextToken();
            
            if (stdOutput){
                if (ttype == TT_WORD)
                    log.debug(sval+" ");
                else if (ttype == TT_NUMBER)
                	log.debug(nval+" ");
                else if (ttype == TT_EOL)
                	log.debug("");
                else if (ttype == TT_EOF)
                	log.debug("");
                else log.debug((char)charRead+" ");
            }
        } catch (IOException t){
            log.error("error");
            log.error(t);
            log.error(toString());
        }
        return charRead;
    }
    
/*    public String readLine(){
        super.
    }*/
    
}
