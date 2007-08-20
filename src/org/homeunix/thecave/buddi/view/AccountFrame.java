/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.Type;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.model.swing.AccountTreeTableModel;
import org.homeunix.thecave.buddi.util.InternalFormatter;
import org.homeunix.thecave.buddi.view.menu.items.EditViewTransactions;
import org.homeunix.thecave.moss.model.DocumentChangeEvent;
import org.homeunix.thecave.moss.model.DocumentChangeListener;
import org.homeunix.thecave.moss.swing.window.MossPanel;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

public class AccountFrame extends MossPanel {
	public static final long serialVersionUID = 0;

	private final JXTreeTable tree;
	private final JLabel balanceLabel;

	private final AccountTreeTableModel treeTableModel;
	
	private final MainFrame parent;
	
	public AccountFrame(MainFrame parent) {
		super(true);
//		this.model = model;
		this.parent = parent;
		this.treeTableModel = new AccountTreeTableModel((DataModel) parent.getDocument());
		
		tree = new JXTreeTable(treeTableModel);
		balanceLabel = new JLabel("Change Me");
		
		open();
	}
	
	public List<Account> getSelectedAccounts(){
		List<Account> accounts = new LinkedList<Account>();
		
		for (Integer i : tree.getSelectedRows()) {
			if (tree.getModel().getValueAt(i, -1) instanceof Account)
				accounts.add((Account) tree.getModel().getValueAt(i, -1));
		}
		
		return accounts;
	}
	
	public List<Type> getSelectedTypes(){
		List<Type> types = new LinkedList<Type>();
		
		for (Integer i : tree.getSelectedRows()) {
			if (tree.getModel().getValueAt(i, -1) instanceof Type)
				types.add((Type) tree.getModel().getValueAt(i, -1));
		}
		
		return types;
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}

	public void init() {
		super.init();
		
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setAutoResizeMode(JXTreeTable.AUTO_RESIZE_ALL_COLUMNS);
		tree.setClosedIcon(null);
		tree.setOpenIcon(null);
		tree.setLeafIcon(null);
//		tree.setColumnSelectionAllowed(false);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addHighlighter(HighlighterFactory.createAlternateStriping(Const.COLOR_EVEN_ROW, Const.COLOR_ODD_ROW));
		
		int treeColumnWidth = 30;
		tree.getColumn(0).setMaxWidth(treeColumnWidth);
		tree.getColumn(0).setMinWidth(treeColumnWidth);
		tree.getColumn(0).setPreferredWidth(treeColumnWidth);

		parent.getDocument().addDocumentChangeListener(new DocumentChangeListener(){
			public void documentChange(DocumentChangeEvent event) {
				updateContent();
			}
		});
		
		JScrollPane listScroller = new JScrollPane(tree);

		JPanel balanceLabelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		balanceLabelPanel.add(balanceLabel);

		JPanel listScrollerPanel = new JPanel(new BorderLayout());
		listScrollerPanel.add(listScroller, BorderLayout.CENTER);

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());

		mainPanel.add(listScrollerPanel, BorderLayout.CENTER);
		mainPanel.add(balanceLabelPanel, BorderLayout.SOUTH);
		
		if (OperatingSystemUtil.isMac()){
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}

		tree.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
			}
		});
		
		tree.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent arg0) {
				updateButtons();
			}
		});
		
		tree.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() >= 2)
					new EditViewTransactions(parent).doClick();
				super.mouseClicked(arg0);
			}
		});
		
		tree.addTreeExpansionListener(new TreeExpansionListener(){
			public void treeCollapsed(TreeExpansionEvent event) {
				Object o = event.getPath().getPath()[event.getPath().getPath().length - 1];
				if (o instanceof Type){
					Type t = (Type) o;
					t.setExpanded(false);
				}
			}
			public void treeExpanded(TreeExpansionEvent event) {
				Object o = event.getPath().getPath()[event.getPath().getPath().length - 1];
				if (o instanceof Type){
					Type t = (Type) o;
					t.setExpanded(true);
				}				
			}
		});
		
		updateButtons();
		
//		this.setJMenuBar(new AccountFrameMenuBar(this));
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
	}

	public void updateContent() {
//		String dataFile = getDocument().getFile() == null ? " - Unsaved " : " - " + getDocument().getFile().getAbsolutePath();
//		this.setTitle(PrefsModel.getInstance().getTranslator().get(BuddiKeys.MY_ACCOUNTS) + dataFile + " - " + PrefsModel.getInstance().getTranslator().get(BuddiKeys.BUDDI));

		//We need to set the title first. 
		super.updateContent();
		
		//Fire a change event on the table model.
		treeTableModel.fireStructureChanged();
		
		//Restore the state of the expanded / unrolled nodes.
		for (Type t : ((DataModel) parent.getDocument()).getTypes()) {
			TreePath path = new TreePath(new Object[]{treeTableModel.getRoot(), t});
			if (t.isExpanded())
				tree.expandPath(path);
			else
				tree.collapsePath(path);
		}
		
		((DataModel) parent.getDocument()).updateAllBalances();
		
		long netWorth = 0;
		for (Account a : ((DataModel) parent.getDocument()).getAccounts()) {
			netWorth += a.getBalance();
		}
		
		balanceLabel.setText(PrefsModel.getInstance().getTranslator().get(BuddiKeys.NET_WORTH) + ": " + InternalFormatter.getFormattedCurrency(netWorth));
		
	}
}