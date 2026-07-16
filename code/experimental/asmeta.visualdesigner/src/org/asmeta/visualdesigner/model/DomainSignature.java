package org.asmeta.visualdesigner.model;

public class DomainSignature {

    private String name;
    private String type;
    private boolean dynamic;
    private String values;

    public DomainSignature(String name, String type, boolean dynamic, String values) {
        this.name = name;
        this.type = type;
        this.dynamic = dynamic;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getType() {
		return type;
	}

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}