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

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbMPaymentDetailTypeDAO;
import com.pawineept.ptm.model.TbMPaymentDetailType;
import com.pawineept.ptm.util.DBUtil;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class PaymentDetailTypeSearchLayout extends Composite {
	private Text txtSearch;
	private Table table;
	private Button btnAdd;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PaymentDetailTypeSearchLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("ค้นหาข้อมูลรายละเอียดประเภทการชำระเงิน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_1.setText("ค้นหา รายละเอียดประเภทการชำระเงิน");
		
		txtSearch = new Text(this, SWT.BORDER);
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(txtSearch.getText().trim().length()>0)
					searchPaymentDetailTypeRecord();
				else
					table.removeAll();
			}
		});
		txtSearch.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		btnAdd = new Button(this, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplicationMain.closeShell();
				PaymentDetailTypeLayout pt = new PaymentDetailTypeLayout(ApplicationMain.shell, SWT.NONE);
				ApplicationMain.composite = pt;
				ApplicationMain.openShell();
				PaymentDetailTypeLayout.editMode = false;
				PaymentDetailTypeLayout.validReqFieldMode = false;
			}
		});
		btnAdd.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnAdd.setText("เพิ่มรายละเอียดประเภทชำระเงิน");
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchAllPaymentDetailTypeRecord();
			}
		});
		button.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		button.setText("แสดงรายละเอียดชำระเงินทั้งหมด");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String payDetailTypeid = e.item.getData().toString();
				System.out.println("edit payDetailTypeid:"+payDetailTypeid);
				ApplicationMain.closeShell();
				PaymentDetailTypeLayout.editMode = true;
				PaymentDetailTypeLayout.payDetailTypeid = Integer.parseInt(payDetailTypeid);
				ApplicationMain.composite = new PaymentDetailTypeLayout(ApplicationMain.shell, SWT.NULL);
				ApplicationMain.openShell();
			}
		});
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(229);
		tableColumn.setText("ประเภทหลักการชำระเงิน");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(217);
		tableColumn_1.setText("ประเภทย่อยการชำระเงิน");
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(121);
		tblclmnNewColumn.setText("จำนวนครั้ง หรือ ชิ้น");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(115);
		tableColumn_2.setText("ราคา");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("สถานะการใช้งาน");

	}
	
	protected void searchPaymentDetailTypeRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbMPaymentDetailTypeDAO dao = new TbMPaymentDetailTypeDAO();
			String name[] = txtSearch.getText().split(" ");
			List<TbMPaymentDetailType> lst = dao.findAllName(conn,name);
			table.removeAll();
			for(int i=0;i<lst.size();i++){
				TbMPaymentDetailType obj = lst.get(i);
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {obj.getPay_type_name(), obj.getPay_detail_type_name(), obj.getTotal_num().toString(), obj.getCost().toString(), obj.getStatus(), ""});
				tableItem.setData(obj.getId().toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
		
	}
	
	protected void searchAllPaymentDetailTypeRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbMPaymentDetailTypeDAO dao = new TbMPaymentDetailTypeDAO();
			List<TbMPaymentDetailType> lst = dao.findAllList(conn);
			table.removeAll();
			for(int i=0;i<lst.size();i++){
				TbMPaymentDetailType obj = lst.get(i);
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {obj.getPay_type_name(), obj.getPay_detail_type_name(), obj.getTotal_num().toString(), obj.getCost().toString(), obj.getStatus(), ""});
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
