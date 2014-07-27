package com.pawineept.ptm.layout;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;

import com.ibm.icu.math.BigDecimal;
import com.pawineept.ptm.dao.TbTPatientDAO;
import com.pawineept.ptm.dao.TbTScheduleDAO;
import com.pawineept.ptm.model.TbTPatient;
import com.pawineept.ptm.model.TbTSchedule;
import com.pawineept.ptm.popup.PaymentDetailAddDialog;
import com.pawineept.ptm.util.DBUtil;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class PaymentLayout extends Composite {
	private Text txtMoney;
	Text txt1;
	Text txt2;
	Combo cmbPTReady;
	GridData gd_cmbPTReady;
	
	Label vPtNo;
	Label vFullName;
	private Label vAge;
	private Label vAddress;
	private Label vLastService;
	private Label vLastPaymentAmt;
	public static Table table;
	private static Text txtBalance;
	private Text txtChange;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PaymentLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		final SimpleDateFormat sdflong = new SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.US);
		Group group = new Group(this, SWT.NONE);
		group.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		group.setText("เลือกผู้รับบริการ");
		
		Label label = new Label(group, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("ผู้ที่กำลังเข้ารับบริการ");
		
		cmbPTReady = new Combo(group, SWT.NONE);
		cmbPTReady.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("text:"+cmbPTReady.getText());
				String ptid = cmbPTReady.getText().substring(1,cmbPTReady.getText().indexOf("]"));
				Connection conn = null;
				try{
					conn = DBUtil.connect();
					TbTPatientDAO dao = new TbTPatientDAO();
					TbTPatient ptObj =  dao.findById(ptid,conn);
					vPtNo.setText(ptObj.getPatientid());
					vFullName.setText(ptObj.getFirstname()+" "+ptObj.getLastname());
					vAge.setText(String.valueOf(ptObj.getAge()==null?"":ptObj.getAge()));
					vAddress.setText(ptObj.getAddress());
					vLastService.setText(ptObj.getLastservice()==null?"บริการครั้งแรก":sdflong.format(ptObj.getLastservice()));
					vLastPaymentAmt.setText("0");
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}finally{
					DBUtil.close(conn);
				}
				
			}
		});
		cmbPTReady.setItems(new String[] {"PT-100 fff", "PT-221 ssff", "PT-500 fgs"});
		gd_cmbPTReady = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_cmbPTReady.widthHint = 308;
		cmbPTReady.setLayoutData(gd_cmbPTReady);
		
		Button button = new Button(group, SWT.NONE);
		button.setText("เลือก");
		
		Group group_1 = new Group(this, SWT.NONE);
		group_1.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		group_1.setLayout(new GridLayout(7, false));
		group_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		group_1.setText("รายละเอียดผู้ใช้บริการ");
		
		Label lblPtNo = new Label(group_1, SWT.NONE);
		lblPtNo.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblPtNo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPtNo.setText("PT. NO.");
		
		vPtNo = new Label(group_1, SWT.NONE);
		vPtNo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		vPtNo.setText("-");
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		
		Label label_1 = new Label(group_1, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("ชื่อ");
		
		vFullName = new Label(group_1, SWT.NONE);
		vFullName.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		GridData gd_vFullName = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_vFullName.widthHint = 271;
		vFullName.setLayoutData(gd_vFullName);
		vFullName.setText("-");
		new Label(group_1, SWT.NONE);
		
		Label label_2 = new Label(group_1, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_2.setText("อายุ");
		
		vAge = new Label(group_1, SWT.NONE);
		vAge.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		vAge.setAlignment(SWT.RIGHT);
		GridData gd_vAge = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_vAge.widthHint = 22;
		vAge.setLayoutData(gd_vAge);
		vAge.setText("-");
		
		Label label_4 = new Label(group_1, SWT.NONE);
		label_4.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		label_4.setText("ปี");
		new Label(group_1, SWT.NONE);
		
		Label label_3 = new Label(group_1, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		label_3.setText("ที่อยู่");
		
		vAddress = new Label(group_1, SWT.NONE);
		vAddress.setText("-");
		vAddress.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		GridData gd_vAddress = new GridData(SWT.FILL, SWT.TOP, false, false, 6, 1);
		gd_vAddress.heightHint = 41;
		vAddress.setLayoutData(gd_vAddress);
		
		Label label_5 = new Label(group_1, SWT.NONE);
		label_5.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		label_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_5.setText("ใช้บริการล่าสุดวันที่");
		
		vLastService = new Label(group_1, SWT.NONE);
		vLastService.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		vLastService.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		vLastService.setText("-");
		
		Label label_7 = new Label(group_1, SWT.NONE);
		label_7.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		label_7.setText("ค่าบริการล่าสุด");
		
		vLastPaymentAmt = new Label(group_1, SWT.NONE);
		vLastPaymentAmt.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		vLastPaymentAmt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		vLastPaymentAmt.setText("-");
		
		Group group_2 = new Group(this, SWT.NONE);
		group_2.setLayout(new GridLayout(6, false));
		group_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		group_2.setText("รายการค่าชำระปัจจุบัน");
		new Label(group_2, SWT.NONE);
		new Label(group_2, SWT.NONE);
		new Label(group_2, SWT.NONE);
		new Label(group_2, SWT.NONE);
		
		Button btnDelPay = new Button(group_2, SWT.NONE);
		btnDelPay.setText("ลบรายการ");
		
		Button btn_addPay = new Button(group_2, SWT.NONE);
		btn_addPay.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PaymentDetailAddDialog dialog = new PaymentDetailAddDialog(getShell(), SWT.NONE);
				dialog.open();
			}
		});
		btn_addPay.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		btn_addPay.setText("เพิ่มรายการ");
		
		table = new Table(group_2, SWT.BORDER | SWT.FULL_SELECTION);
		table.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.LEFT);
		tableColumn.setWidth(506);
		tableColumn.setText("รายการ");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.RIGHT);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("ราคา");
		
		TableEditor editor = new TableEditor(table);
		txt1 = new Text(table,SWT.RIGHT);
		txt1.setText("0.00");
		txt1.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		txt1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Text txt1 = (Text) e.getSource();
				txt1.selectAll();
			}
		});
		txt1.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent arg0) {
				switch (arg0.keyCode) {  
		            case SWT.BS:           // Backspace  
		            case SWT.DEL:          // Delete  
		            case SWT.HOME:         // Home  
		            case SWT.END:          // End  
		            case SWT.ARROW_LEFT:   // Left arrow  
		            case SWT.ARROW_RIGHT:  // Right arrow  
		                return;  
		        }  
		  
		        if (!Character.isDigit(arg0.character)) {  
		        	arg0.doit = false;  // disallow the action  
		        }
			}
		});
		TableEditor editor2 = new TableEditor(table);
		txt2 = new Text(table,SWT.RIGHT);
		txt2.setText("0.00");
		txt2.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		txt2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Text txt2 = (Text) e.getSource();
				txt2.selectAll();
			}
		});
		txt2.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent arg0) {
				switch (arg0.keyCode) {  
		            case SWT.BS:           // Backspace  
		            case SWT.DEL:          // Delete  
		            case SWT.HOME:         // Home  
		            case SWT.END:          // End  
		            case SWT.ARROW_LEFT:   // Left arrow  
		            case SWT.ARROW_RIGHT:  // Right arrow  
		                return;  
		        }  
		  
		        if (!Character.isDigit(arg0.character)) {  
		        	arg0.doit = false;  // disallow the action  
		        }
			}
		});
		editor.grabHorizontal = true;
		editor2.grabHorizontal = true;
		new Label(group_2, SWT.NONE);
		
		//new Label(group_2, SWT.NONE);
		
		
		
		
		Label label_9 = new Label(group_2, SWT.NONE);
		label_9.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		label_9.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label_9.setText("รวม");
		
		txtBalance = new Text(group_2, SWT.READ_ONLY | SWT.RIGHT);
		txtBalance.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		txtBalance.setText("0.00");
		GridData gd_txtBalance = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtBalance.widthHint = 79;
		txtBalance.setLayoutData(gd_txtBalance);
		
		Label label_11 = new Label(group_2, SWT.NONE);
		label_11.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label_11.setText("บาท");
		new Label(group_2, SWT.NONE);
		new Label(group_2, SWT.NONE);
		new Label(group_2, SWT.NONE);
		
		Label label_12 = new Label(group_2, SWT.NONE);
		label_12.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label_12.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_12.setText("รับเงินจำนวน");
		
		txtMoney = new Text(group_2, SWT.RIGHT);	
		txtMoney.setText("0.00");
		txtMoney.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		txtMoney.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		txtMoney.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent arg0) {
				switch (arg0.keyCode) {  
		            case SWT.BS:           // Backspace  
		            case SWT.DEL:          // Delete  
		            case SWT.HOME:         // Home  
		            case SWT.END:          // End  
		            case SWT.ARROW_LEFT:   // Left arrow  
		            case SWT.ARROW_RIGHT:  // Right arrow
		            case 16777262:	// dot
		            case 44:	// comma
		                return;  
		        }
				System.out.println(arg0.keyCode);
		  
		        if (!Character.isDigit(arg0.character)) {  
		        	arg0.doit = false;  // disallow the action  
		        }
			}
		});
		txtMoney.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				txtMoney.selectAll();
			}
		});
		txtMoney.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				DecimalFormat myFormatter = new DecimalFormat("###,###.00");
				System.out.println("myFormatter:"+myFormatter.format(new Double(txtMoney.getText())));
			}
		});
		
		Label label_13 = new Label(group_2, SWT.NONE);
		label_13.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label_13.setText("บาท");
		new Label(group_2, SWT.NONE);
		new Label(group_2, SWT.NONE);
		new Label(group_2, SWT.NONE);
		
		Label label_14 = new Label(group_2, SWT.NONE);
		label_14.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_14.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label_14.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_14.setText("เงินทอน");
		
		txtChange = new Text(group_2, SWT.RIGHT);
		txtChange.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		txtChange.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		txtChange.setText("0.00");
		txtChange.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label label_15 = new Label(group_2, SWT.NONE);
		label_15.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_15.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label_15.setText("บาท");
		new Label(group_2, SWT.NONE);
		new Label(group_2, SWT.NONE);
		
		Button button_1 = new Button(this, SWT.NONE);
		button_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		button_1.setText("ชำระค่าบริการ");
		
		Button button_2 = new Button(this, SWT.NONE);
		button_2.setText("พิมพ์ใบเสร็จรับเงิน");
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void findPTUsed(){
		Connection conn = null;
		try{
			conn = DBUtil.connect();
			TbTScheduleDAO dao = new TbTScheduleDAO();
			List<TbTSchedule> listused = dao.findUsed(conn);
			cmbPTReady.removeAll();
			for (TbTSchedule tbTSchedule : listused) {
				cmbPTReady.add("["+tbTSchedule.getPatientId()+"]"+tbTSchedule.getName());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
	}

	public static void calculate() {
		TableItem[] items =  table.getItems();
		double total = 0.0;
		for (TableItem tableItem : items) {
			HashMap<String,Object> hashMap = (HashMap<String, Object>) tableItem.getData();
			System.out.println(hashMap.get("COST"));
			total += Double.parseDouble(String.valueOf(hashMap.get("COST")));
		}
		System.out.println(total);
		txtBalance.setText(String.valueOf(new BigDecimal(total).setScale(2)));
	}
}
