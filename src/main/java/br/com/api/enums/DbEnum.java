package br.com.api.enums;

public enum DbEnum {

	DRIVER("org.postgresql.Driver"), URL("jdbc:postgresql://localhost/biblioteca"), USER("postgres"),
	PASSWORD("Jmfobpatd456");

	private final String value;

	DbEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
