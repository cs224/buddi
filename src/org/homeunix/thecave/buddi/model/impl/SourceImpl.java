/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;

/**
 * Default implementation of a Source.  You should not create this object directly; 
 * instead, please use the ModelFactory to create it, as this will ensure that all
 * required fields are correctly set.
 * @author wyatt
 *
 */
public abstract class SourceImpl extends ModelObjectImpl implements Source {
	//Source Attributes
	private String name;
	private boolean deleted;
	private String notes;
	
	public String getName() {
		return TextFormatter.getTranslation(name);
	}
	public String getNotes() {
		return notes;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
		setChanged();
	}
	public void setName(String name) {
		this.name = name;
		setChanged();
	}
	public void setNotes(String notes) {
		this.notes = notes;
		setChanged();
	}
	@Override
	public String toString() {
		return getName() + " (" + getUid() + ")";
	}
}
