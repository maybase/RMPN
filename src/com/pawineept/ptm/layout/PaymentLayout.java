package com.pawineept.ptm.layout;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import com.pawineept.ptm.dao.TbRunningnoDAO;
import com.pawineept.ptm.dao.TbTPatientDAO;
import com.pawineept.ptm.dao.TbTPaymentDAO;
import com.pawineept.ptm.dao.TbTPaymentDetailDAO;
import com.pawineept.ptm.dao.TbTScheduleDAO;
import com.pawineept.ptm.model.TbRunningno;
import com.pawineept.ptm.model.TbTPatient;
import com.pawineept.ptm.model.TbTPayment;
import com.pawineept.ptm.model.TbTPaymentDetail;
import com.pawineept.ptm.model.TbTSchedule;
import com.pawineept.ptm.popup.PaymentDetailAddDialog;
import com.pawineept.ptm.util.DBUtil;
import com.pawineept.ptm.util.ReportUtil;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class PaymentLayout extends Composite {
	private Text txtMoney;
	Text txt1;
	Text txt2;
	Combo cmbPTReady;
	GridData gd_cmbPTReady;
	
	Label vPtNo;
	private Label vAge;
	private Label vLastService;
	private Label vLastPaymentAmt;
	public static Table table;
	private static Text txtBalance;
	private Text txtChange;
	private Text txtName;
	private Text txtAddress;
	private Label vReceiptNo;
	private Label vMessage;
	private Button button_1;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PaymentLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));
		final SimpleDateFormat sdflong = new SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.US);
		Group group = new Group(this, SWT.NONE);
		group.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		group.setText("เลือกผู้รับบริการ");
		
		Label label = new Label(group, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("ผู้ที่กำลังเข้ารับบริการ");
		
		cmbPTReady = new Combo(group, SWT.NONE);
		cmbPTReady.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
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
					txtName.setText(ptObj.getFirstname()+" "+ptObj.getLastname());
					vAge.setText(String.valueOf(ptObj.getAge()==null?"":ptObj.getAge()));
					txtAddress.setText(ptObj.getAddress());
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
		button.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		button.setText("เลือก");
		
		Group group_1 = new Group(this, SWT.NONE);
		group_1.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		group_1.setLayout(new GridLayout(8, false));
		group_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		group_1.setText("รายละเอียดผู้ใช้บริการ");
		
		Label lblPtNo = new Label(group_1, SWT.NONE);
		lblPtNo.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblPtNo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPtNo.setText("PT. NO.");
		
		vPtNo = new Label(group_1, SWT.NONE);
		vPtNo.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_vPtNo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_vPtNo.widthHint = 99;
		vPtNo.setLayoutData(gd_vPtNo);
		vPtNo.setText("-");
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		
		Label label_1 = new Label(group_1, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("ชื่อ");
		
		txtName = new Text(group_1, SWT.BORDER);
		txtName.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtName = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_txtName.widthHint = 232;
		txtName.setLayoutData(gd_txtName);
		new Label(group_1, SWT.NONE);
		
		Label label_2 = new Label(group_1, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_2.setText("อายุ");
		
		vAge = new Label(group_1, SWT.NONE);
		vAge.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		vAge.setAlignment(SWT.RIGHT);
		GridData gd_vAge = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_vAge.widthHint = 22;
		vAge.setLayoutData(gd_vAge);
		vAge.setText("-");
		
		Label label_4 = new Label(group_1, SWT.NONE);
		label_4.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_4.setText("ปี");
		new Label(group_1, SWT.NONE);
		
		Label label_3 = new Label(group_1, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		label_3.setText("ที่อยู่");
		
		txtAddress = new Text(group_1, SWT.BORDER | SWT.MULTI);
		txtAddress.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtAddress = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gd_txtAddress.heightHint = 64;
		txtAddress.setLayoutData(gd_txtAddress);
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		
		Label label_6 = new Label(group_1, SWT.NONE);
		label_6.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label_6.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		label_6.setText("รายละเอียดเพิ่มเติม");
		
		Label lblNewLabel = new Label(group_1, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1);
		gd_lblNewLabel.heightHint = 47;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("-");
		
		Label label_5 = new Label(group_1, SWT.NONE);
		label_5.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_5.setText("ใช้บริการล่าสุดวันที่");
		
		vLastService = new Label(group_1, SWT.NONE);
		vLastService.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		vLastService.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		vLastService.setText("-");
		new Label(group_1, SWT.NONE);
		
		Label label_7 = new Label(group_1, SWT.NONE);
		label_7.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_7.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label_7.setText("ค่าบริการล่าสุด");
		
		vLastPaymentAmt = new Label(group_1, SWT.NONE);
		vLastPaymentAmt.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		vLastPaymentAmt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		vLastPaymentAmt.setText("-");
		
		Group group_2 = new Group(this, SWT.NONE);
		group_2.setLayout(new GridLayout(6, false));
		group_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 3, 1));
		group_2.setText("รายการค่าชำระปัจจุบัน");
		
		Label lblReceiptNo = new Label(group_2, SWT.NONE);
		lblReceiptNo.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblReceiptNo.setText("RECEIPT NO");
		
		vReceiptNo = new Label(group_2, SWT.NONE);
		vReceiptNo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		vReceiptNo.setText("-");
		new Label(group_2, SWT.NONE);
		new Label(group_2, SWT.NONE);
		
		Button btnDelPay = new Button(group_2, SWT.NONE);
		btnDelPay.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selected = table.getSelectionIndex();
				table.remove(selected);
			}
		});
		btnDelPay.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnDelPay.setText("ลบรายการ");
		
		Button btn_addPay = new Button(group_2, SWT.NONE);
		btn_addPay.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
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
		txtMoney.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Double money = new Double(txtMoney.getText());
				Double totalamount = new Double(txtBalance.getText());
				Double changeAmount = money-totalamount;
				txtChange.setText(String.valueOf(new BigDecimal(changeAmount).setScale(2)));
			}
		});
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
		editor.grabHorizontal = true;
		editor2.grabHorizontal = true;
		
		vMessage = new Label(this, SWT.RIGHT);
		GridData gd_vMessage = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_vMessage.widthHint = 430;
		vMessage.setLayoutData(gd_vMessage);
		
		button_1 = new Button(this, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Connection conn = null;
				if(vReceiptNo.getText().trim().length()>1){
					vMessage.setText("ไม่สามารถแก้ไขข้อมูลได้");
					return;
				}
				button_1.setEnabled(false);
				// insert payment
				int itemcount = table.getItemCount();
				try {
					conn = DBUtil.connect();
					TbRunningnoDAO runningnoDAO = new TbRunningnoDAO();
					TbRunningno runningno = new TbRunningno();
					runningno.setPrefix("RECEIPT_NO");
					SimpleDateFormat yearf = new SimpleDateFormat("yyyy",new Locale("th","TH"));
					runningno.setYear(yearf.format(new java.util.Date()));
					Integer receiptNoInt = runningnoDAO.selectMaxRunNo(conn, runningno);
					if(receiptNoInt==null){
						runningno.setRunNum(1L);
						runningnoDAO.insertRunNo(conn, runningno);
						receiptNoInt = 1;
					}else{
						receiptNoInt++;
						runningno.setRunNum(receiptNoInt.longValue());
						runningnoDAO.runNoplus(conn, runningno);
						
					}
					DecimalFormat receiptFormat = new DecimalFormat("0000/"+yearf.format(new java.util.Date()));
					String receiptNo = receiptFormat.format(receiptNoInt);
					System.out.println(receiptNo);
					
					SimpleDateFormat enFormat = new SimpleDateFormat("dd MMMMM yyyy",Locale.US);
					SimpleDateFormat thFormat = new SimpleDateFormat("dd MMMMM yyyy",new Locale("th","TH"));
					
					TbTPayment tbTPayment = new TbTPayment();
					tbTPayment.setReceiptNo(receiptNo);
					tbTPayment.setPatientName(txtName.getText());
					tbTPayment.setPatientId(vPtNo.getText());
					tbTPayment.setFlagPrint(0L);
					tbTPayment.setDateTextEn(enFormat.format(new java.util.Date()));
					tbTPayment.setDateTextTh(thFormat.format(new java.util.Date()));
					tbTPayment.setCreateBy("1");
					tbTPayment.setCreateDatetime(new Timestamp(new java.util.Date().getTime()));
					TbTPaymentDAO tbTPaymentDAO = new TbTPaymentDAO();
					Long paymentid = tbTPaymentDAO.insert(conn,tbTPayment);
					BigDecimal totalAmount = new BigDecimal(0);
					TbTPaymentDetailDAO paymentDetailDAO = new TbTPaymentDetailDAO();
					for(int i=0; i<itemcount; i++){
						@SuppressWarnings("unchecked")
						HashMap<String,Object> hashMap = (HashMap<String,Object>)table.getItem(i).getData();
						System.out.println("ID : "+hashMap.get("ID"));
						System.out.println("COST : "+hashMap.get("COST"));
						System.out.println("UNIT : "+hashMap.get("UNIT"));
						BigDecimal amount = new BigDecimal(hashMap.get("COST").toString());
						totalAmount.add(amount);
						TbTPaymentDetail detail = new TbTPaymentDetail();
						detail.setAmount(amount);
						detail.setCreateBy("1");
						detail.setCreateDatetime(new Timestamp(new java.util.Date().getTime()));
						detail.setPayDetailType(new Long(hashMap.get("ID").toString()));
						if(hashMap.get("UNIT")==null){
							detail.setUnit(1);
						}else{
							detail.setUnit(new Integer(hashMap.get("UNIT").toString()));
						}						
						detail.setPaymentid(paymentid);
						detail.setReceiptNo(receiptNo);
						paymentDetailDAO.insert(conn,detail);						
						
					}
					tbTPayment.setTotalAmount(totalAmount);
					tbTPaymentDAO.updateTotalAmount(conn,receiptNo,totalAmount);
					TbTScheduleDAO scheduleDAO = new TbTScheduleDAO();					
					scheduleDAO.updateStatusPayment(vPtNo.getText(), conn);
					vReceiptNo.setText(receiptNo);
					vMessage.setText("บันทึกข้อมูลเรียบร้อยแล้ว");
					
				} catch (Exception e1) {
					e1.printStackTrace();
					vMessage.setText(e1.getMessage());
				}finally{
					if(conn!=null) DBUtil.close(conn);
				}
				
			}
		});
		button_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		button_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		button_1.setText("ชำระค่าบริการ");
		
		Button button_2 = new Button(this, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(vReceiptNo.getText().trim().length()>1){
					ReportUtil report = new ReportUtil();
					try {
						report.pt201(vReceiptNo.getText());
					} catch (Exception e1) {
						e1.printStackTrace();
						vMessage.setText("ไม่สามารถสร้างเอกสารปริ้นได้ :: "+e1.getMessage());
					}
					vMessage.setText("คุณได้พิมพ์ใบเสร็จ");
				}else{
					vMessage.setText("คุณยังไม่ได้กด ชำระค่าบริการ");
				}
			}
		});
		button_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
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
			@SuppressWarnings("unchecked")
			HashMap<String,Object> hashMap = (HashMap<String, Object>) tableItem.getData();
			System.out.println(hashMap.get("COST"));
			total += Double.parseDouble(String.valueOf(hashMap.get("COST")));
		}
		System.out.println(total);
		txtBalance.setText(String.valueOf(new BigDecimal(total).setScale(2)));
	}
}
