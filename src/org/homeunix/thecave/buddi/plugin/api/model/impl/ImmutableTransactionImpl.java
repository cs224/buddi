/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableTransaction;

public class ImmutableTransactionImpl extends ImmutableModelObjectImpl implements ImmutableTransaction {
	
	public ImmutableTransactionImpl(Transaction transaction) {
		super(transaction);
	}
	
	public Transaction getTransaction(){
		return (Transaction) getRaw();
	}
	
	public Boolean isCleared() {
		return getTransaction().isCleared();
	}
	public Date getDate() {
		return getTransaction().getDate();
	}
	public String getDescription() {
		return getTransaction().getDescription();
	}
	public String getMemo() {
		return getTransaction().getMemo();
	}
	public String getNumber() {
		return getTransaction().getNumber();
	}
	public Boolean isReconciled() {
		return getTransaction().isReconciled();
	}
	public boolean isScheduled() {
		return getTransaction().isScheduled();
	}
	public ImmutableSourceImpl getFrom(){
		if (getTransaction().getFrom() instanceof Account)
			return new ImmutableAccountImpl((Account) getTransaction().getFrom());
		if (getTransaction().getFrom() instanceof BudgetCategory)
			return new ImmutableBudgetCategoryImpl((BudgetCategory) getTransaction().getFrom());
		return null;
	}
	public ImmutableSourceImpl getTo(){
		if (getTransaction().getTo() instanceof Account)
			return new ImmutableAccountImpl((Account) getTransaction().getTo());
		if (getTransaction().getTo() instanceof BudgetCategory)
			return new ImmutableBudgetCategoryImpl((BudgetCategory) getTransaction().getTo());
		return null;
	}
	public long getBalanceFrom() {
		return getTransaction().getBalanceFrom();
	}
	public long getBalanceTo() {
		return getTransaction().getBalanceTo();
	}
	public boolean isInflow(){
		return getTransaction().isInflow();
	}
	public long getAmount(){
		return getTransaction().getAmount();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ImmutableTransaction)
			return getUid().equals(((ImmutableTransaction) obj).getUid());
		return false;
	}
}