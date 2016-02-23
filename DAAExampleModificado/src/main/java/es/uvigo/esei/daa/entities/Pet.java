package es.uvigo.esei.daa.entities;

import static java.util.Objects.requireNonNull;

public class Pet {
	private int id;
	private String name;
	private String tipe;

	public Pet(){}

	public Pet(int i, String n, String tip){
		id = i;
		this.setName(n);
		this.setTipe(tip);
	}
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = requireNonNull(name, "Name can't be null");
	}

	public String getTipe() {
		return tipe;
	}

	public void setTipe(String tipe) {
		this.name = requireNonNull(tipe, "Name can't be null");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Pet other = (Pet) obj;
		if (id != other.id)
			return false;
		return true;
	}
}