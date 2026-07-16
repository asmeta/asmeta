package org.asmeta.visualdesigner.model;

public class FunctionSignature {

    private String name;
    private String type;
    private String domain;
    private String codomain;
    private String definition;

    public FunctionSignature(String name, String type, String domain, String codomain, String definition) {
        this.name = name;
        this.type = type;
        this.domain = domain;
        this.codomain = codomain;
        this.definition = definition;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCodomain() {
        return codomain;
    }

    public void setCodomain(String codomain) {
        this.codomain = codomain;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}