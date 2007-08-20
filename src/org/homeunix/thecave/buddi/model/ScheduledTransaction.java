/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.beans.ScheduledTransactionBean;

/**
 * @author wyatt
 *
 */
public class ScheduledTransaction extends Transaction {
	
	ScheduledTransaction(DataModel model, ScheduledTransactionBean scheduledTransaction) {
		super(model, scheduledTransaction);
	}
	
	/**
	 * Creates a new scheduled transaction with no fields set.  You need to add fields 
	 * beore you can put it into the data model. 
	 * @param model
	 */
	public ScheduledTransaction(DataModel model) {
		this(model, new ScheduledTransactionBean());
	}
	
	public Date getEndDate() {
		return getScheduledTransaction().getEndDate();
	}
	public void setEndDate(Date endDate) {
		getScheduledTransaction().setEndDate(endDate);
		getModel().setChanged();
	}
	public String getFrequencyType() {
		return getScheduledTransaction().getFrequencyType();
	}
	public void setFrequencyType(String frequencyType) {
		getScheduledTransaction().setFrequencyType(frequencyType);
		getModel().setChanged();
	}
	public Date getLastDayCreated() {
		return getScheduledTransaction().getLastDayCreated();
	}
	public void setLastDayCreated(Date lastDayCreated) {
		getScheduledTransaction().setLastDayCreated(lastDayCreated);
		getModel().setChanged();
	}
	public String getMessage() {
		return getScheduledTransaction().getMessage();
	}
	public void setMessage(String message) {
		getScheduledTransaction().setMessage(message);
		getModel().setChanged();
	}
	public int getScheduleDay() {
		return getScheduledTransaction().getScheduleDay();
	}
	public void setScheduleDay(int scheduleDay) {
		getScheduledTransaction().setScheduleDay(scheduleDay);
		getModel().setChanged();
	}
	public int getScheduleMonth() {
		return getScheduledTransaction().getScheduleMonth();
	}
	public void setScheduleMonth(int scheduleMonth) {
		getScheduledTransaction().setScheduleMonth(scheduleMonth);
		getModel().setChanged();
	}
	public String getScheduleName() {
		return getScheduledTransaction().getScheduleName();
	}
	public void setScheduleName(String scheduleName) {
		getScheduledTransaction().setScheduleName(scheduleName);
		getModel().setChanged();
	}
	public int getScheduleWeek() {
		return getScheduledTransaction().getScheduleWeek();
	}
	public void setScheduleWeek(int scheduleWeek) {
		getScheduledTransaction().setScheduleWeek(scheduleWeek);
		getModel().setChanged();
	}
	public Date getStartDate() {
		return getScheduledTransaction().getStartDate();
	}
	public void setStartDate(Date startDate) {
		getScheduledTransaction().setStartDate(startDate);
		getModel().setChanged();
	}
	ScheduledTransactionBean getScheduledTranasactionBean(){
		return getScheduledTransaction();
	}
	private ScheduledTransactionBean getScheduledTransaction(){
		return (ScheduledTransactionBean) getTransactionBean();
	}
}