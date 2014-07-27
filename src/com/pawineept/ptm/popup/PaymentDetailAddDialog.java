package com.pawineept.ptm.popup;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.pawineept.ptm.dao.TbMPaymentDetailTypeDAO;
import com.pawineept.ptm.dao.TbMPaymentTypeDAO;
import com.pawineept.ptm.layout.PaymentLayout;
import com.pawineept.ptm.model.TbMPaymentDetailType;
import com.pawineept.ptm.util.DBUtil;

public class PaymentDetailAddDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Combo cmb_gourp;
	private Table table;
	private Text txt_title;
	private Text txt_payamt_base;
	private Text txt_payamt;
	private TbMPaymentDetailType typeDetail;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public PaymentDetailAddDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(631, 372);
		shell.setText("เพิ่มรายการชำระเงิน");
		shell.setLayout(new GridLayout(5, false));
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("หมวดหมู่");
		
		cmb_gourp = new Combo(shell, SWT.NONE);
		cmb_gourp.setItems(cmbGroupItem());
		cmb_gourp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setSubGroupItem(cmb_gourp.getText());
			}
		});
		GridData gd_cmb_gourp = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_cmb_gourp.widthHint = 455;
		cmb_gourp.setLayoutData(gd_cmb_gourp);
		new Label(shell, SWT.NONE);
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				typeDetail = (TbMPaymentDetailType) e.item.getData();
				txt_title.setText(typeDetail.getPay_detail_type_name());
				txt_payamt_base.setText(String.valueOf(typeDetail.getCost()));
				txt_payamt.setText(String.valueOf(typeDetail.getCost()));
			}
		});
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, false, 5, 1);
		gd_table.heightHint = 121;
		gd_table.widthHint = 96;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(442);
		tableColumn.setText("ชื่อรายการ");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.RIGHT);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("ราคา");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("ชื่อรายการ");
		
		txt_title = new Text(shell, SWT.BORDER);
		txt_title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label label_4 = new Label(shell, SWT.NONE);
		label_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_4.setText("ราคาแนะนำ");
		
		txt_payamt_base = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		txt_payamt_base.setText("0");
		txt_payamt_base.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label label_5 = new Label(shell, SWT.NONE);
		label_5.setText("บาท");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_2.setText("ราคา");
		
		txt_payamt = new Text(shell, SWT.BORDER | SWT.RIGHT);
		txt_payamt.setText("0");
		txt_payamt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setText("บาท");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem tableItem = new TableItem(PaymentLayout.table, SWT.NONE);
				tableItem.setText(new String[]{txt_title.getText(),txt_payamt.getText()});
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("ID", typeDetail.getId());
				map.put("COST", txt_payamt.getText());
				tableItem.setData(map);
				PaymentLayout.calculate();
			}
		});
		button.setText("เพิ่มรายการ");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

	}

	protected void setSubGroupItem(String parentname) {
		Connection conn = null;
		try{
			conn = DBUtil.connect();
			table.removeAll();
			TbMPaymentDetailTypeDAO subDao = new TbMPaymentDetailTypeDAO();
			List<TbMPaymentDetailType> lst = subDao.findActiveListByParentName(conn,parentname);
			if(lst!=null){
				for (TbMPaymentDetailType tbMPaymentDetailType : lst) {
					TableItem tableItem = new TableItem(table, SWT.NONE);
					tableItem.setText(new String[]{tbMPaymentDetailType.getPay_detail_type_name(),String.valueOf(tbMPaymentDetailType.getCost())});
					tableItem.setData(tbMPaymentDetailType);
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private String[] cmbGroupItem() {
		Connection conn = null;
		String [] out = null;
		try{
			conn = DBUtil.connect();
			TbMPaymentTypeDAO dao = new TbMPaymentTypeDAO();
			List<String> lst = dao.findActiveList(conn);
			if(lst!=null){
				out = new String[lst.size()];
				lst.toArray(out);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return out;
	}

}
