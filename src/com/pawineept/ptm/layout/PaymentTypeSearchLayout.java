package com.pawineept.ptm.layout;

import java.sql.Connection;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbMPaymentTypeDAO;
import com.pawineept.ptm.model.TbMPaymentType;
import com.pawineept.ptm.util.DBUtil;

public class PaymentTypeSearchLayout extends Composite {
	private Text txtSearch;
	private Table table;
	private Button btnAdd;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PaymentTypeSearchLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("ค้นหาข้อมูลประเภทการชำระเงิน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("ค้นหา ประเภทการชำระเงิน");
		
		txtSearch = new Text(this, SWT.BORDER);
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(txtSearch.getText().trim().length()>0)
					searchPaymentTypeRecord();
				else
					table.removeAll();
			}
		});
		GridData gd_txtSearch = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSearch.widthHint = 342;
		txtSearch.setLayoutData(gd_txtSearch);
		txtSearch.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		new Label(this, SWT.NONE);
		
		btnAdd = new Button(this, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplicationMain.closeShell();
				PaymentTypeLayout pt = new PaymentTypeLayout(ApplicationMain.shell,SWT.NONE);
				ApplicationMain.composite = pt;
				ApplicationMain.openShell();
				PaymentTypeLayout.editMode = false;
			}
		});
		btnAdd.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnAdd.setText("เพิ่มประเภทชำระเงิน");
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchAllPaymentTypeRecord();
			}
		});
		button.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		button.setText("แสดงประเภทการชำระเงินทั้งหมด");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String paymenttypeid = e.item.getData().toString();
				System.out.println("edit paymenttypeid:"+paymenttypeid);
				ApplicationMain.closeShell();
				PaymentTypeLayout.editMode = true;
				PaymentTypeLayout.paymenttypeid = Integer.parseInt(paymenttypeid);
				ApplicationMain.composite = new PaymentTypeLayout(ApplicationMain.shell, SWT.NULL);
				ApplicationMain.openShell();
			}
		});
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(219);
		tableColumn_3.setText("กลุ่มเวชระเบียน");
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(285);
		tableColumn.setText("ประเภทการชำระเงิน");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(149);
		tableColumn_1.setText("ผู้เป็นเจ้าของ");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(129);
		tableColumn_2.setText("สถานะประเภทชำระเงิน");

	}
	
	protected void searchPaymentTypeRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbMPaymentTypeDAO dao = new TbMPaymentTypeDAO();
			String name[] = txtSearch.getText().split(" ");
			List<TbMPaymentType> lst = dao.findAllName(conn,name);
			table.removeAll();
			for(int i=0;i<lst.size();i++){
				TbMPaymentType obj = lst.get(i);
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {obj.getMedical_group_name(), obj.getPay_type_name(), obj.getOwner(), obj.getStatus(), ""});
				tableItem.setData(obj.getId().toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
		
	}
	
	protected void searchAllPaymentTypeRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbMPaymentTypeDAO dao = new TbMPaymentTypeDAO();
//			String name[] = txtSearch.getText().split(" ");
			List<TbMPaymentType> lst = dao.findAllList(conn);
			table.removeAll();
			for(int i=0;i<lst.size();i++){
				TbMPaymentType obj = lst.get(i);
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {obj.getMedical_group_name(), obj.getPay_type_name(), obj.getOwner(), obj.getStatus(), ""});
				tableItem.setData(obj.getId().toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
		
	}


	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
