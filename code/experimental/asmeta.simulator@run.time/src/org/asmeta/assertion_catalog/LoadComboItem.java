package org.asmeta.assertion_catalog;

public class LoadComboItem
{
    private int i;
    private String s;

    public LoadComboItem(int id, String model)
    {
        this.i = id;
        this.s = model;
    }

    @Override
    public String toString()
    {
        return "ID: "+i+" Model: "+showName();
    }
    
    private String showName() {
    	return s.substring(s.lastIndexOf('/')+1, s.length());
    }
    
    public int getInt()
    {
        return i;
    }

    public String getStr()
    {
        return s;
    }
}