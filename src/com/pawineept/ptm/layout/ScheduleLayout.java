package com.pawineept.ptm.layout;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.pawineept.ptm.constant.PTConstant;
import com.pawineept.ptm.dao.TbTPatientDAO;
import com.pawineept.ptm.dao.TbTScheduleDAO;
import com.pawineept.ptm.model.TbTPatient;
import com.pawineept.ptm.model.TbTSchedule;
import com.pawineept.ptm.popup.ScheduleDetailDialog;
import com.pawineept.ptm.popup.ScheduleRegisterDialog;
import com.pawineept.ptm.util.DBUtil;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;

public class ScheduleLayout extends Composite {
	private Table table_schedule;
	private Date date;
	protected Shell shell;
	private DateTime dateTime;
	Label text_totalcustomer;
	Label text_totalappy;
	Label text_totalcustommernoactive;
	Label label;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ScheduleLayout(Composite parent, int style) {
		super(parent, style);
		shell = parent.getShell();
		setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		setLayout(new GridLayout(10, false));
		
		label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		label.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		label.setText("สถิติประจำวัน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		dateTime = new DateTime(this, SWT.BORDER | SWT.CALENDAR | SWT.LONG);
		dateTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
				try {
					date = sdf.parse(dateTime.getDay()+"/"+(dateTime.getMonth()+1)+"/"+dateTime.getYear());
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				changeDate();
				
			}
		});
		dateTime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 5));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setAlignment(SWT.RIGHT);
		label_1.setText("จำนวนคนไข้");
		
		text_totalcustomer = new Label(this, SWT.NONE);
		text_totalcustomer.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		text_totalcustomer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		text_totalcustomer.setText("000");
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_2.setText("คน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_3.setText("จำนวนคนไข้ที่เข้ารับการรักษา");
		
		text_totalappy = new Label(this, SWT.NONE);
		text_totalappy.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		text_totalappy.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		text_totalappy.setText("0");
		
		Label label_5 = new Label(this, SWT.NONE);
		label_5.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_5.setText("คน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_6 = new Label(this, SWT.NONE);
		label_6.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_6.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_6.setText("ยังไม่มา");
		
		text_totalcustommernoactive = new Label(this, SWT.NONE);
		text_totalcustommernoactive.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		text_totalcustommernoactive.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		text_totalcustommernoactive.setText("0");
		
		Label label_8 = new Label(this, SWT.NONE);
		label_8.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_8.setText("คน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_4 = new Label(this, SWT.NONE);
		label_4.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		label_4.setText("ตารางนัด");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ScheduleRegisterDialog regisDialog = new ScheduleRegisterDialog(shell,SWT.NONE);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
				ScheduleRegisterDialog.dateText = sdf.format(date);				
				regisDialog.open();
				changeDate();
			}
		});
		button.setText("ลงคิวจองเวลา");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		table_schedule = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table_schedule.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		table_schedule.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1));
		table_schedule.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				@SuppressWarnings("unchecked")
				java.util.List<TbTSchedule> scheduleL = (List<TbTSchedule>) e.item.getData();
				ScheduleDetailDialog.scheduleL = scheduleL;
				ScheduleDetailDialog shDialog = new ScheduleDetailDialog(shell,SWT.NONE);
				shDialog.open();
				changeDate();
			}
		});
		table_schedule.setHeaderVisible(true);
		table_schedule.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table_schedule, SWT.NONE);
		tableColumn.setResizable(false);
		tableColumn.setWidth(54);
		tableColumn.setText("เวลา");
		
		TableColumn tableColumn_1 = new TableColumn(table_schedule, SWT.NONE);
		tableColumn_1.setWidth(443);
		tableColumn_1.setText("รายละเอียด");
		date = new Date();
		changeDate();
	}

	private void setDetailDate() {
		Connection conn = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
		try{
			conn = DBUtil.connect();
			TbTScheduleDAO dao = new TbTScheduleDAO();
			int totpt = dao.countAllByDate(date,conn);
			int totactive = dao.countAllByDateAndStatus(date,2, conn);
			int totnoactive = dao.countAllByDateAndStatus(date,1, conn);
			label.setText("สถิติประจำวันที่ "+sdf.format(date));
			text_totalcustomer.setText(String.valueOf(totpt));
			text_totalappy.setText(String.valueOf(totactive));
			text_totalcustommernoactive.setText(String.valueOf(totnoactive));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
	}

	protected void changeDate() {
		table_schedule.removeAll();
		System.out.println(date);
		for(int i=0; i<PTConstant.TIMEWORK.length;i++){
			TableItem tableItem = new TableItem(table_schedule, SWT.NONE);
			HashMap<String,Object> sh = getSchedule(date,PTConstant.TIMEWORK[i]);
			tableItem.setText(new String[] {PTConstant.TIMEWORK[i],sh.get("nameList").toString()});
			tableItem.setData(sh.get("lstData"));
		}
		setDetailDate();
	}

	private HashMap<String,Object> getSchedule(Date idate, String itime) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		Connection conn = null;
		try {
			conn = DBUtil.connect();
			TbTScheduleDAO dao = new TbTScheduleDAO();
			TbTPatientDAO pdao = new TbTPatientDAO();
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHH:mm",Locale.US);
			SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyy",Locale.US);
			java.util.List<TbTSchedule> lst = dao.findByDateTime(conn,sdf.parse(sdf2.format(idate)+itime));
			map.put("lstData", lst);
			StringBuffer str = new StringBuffer();
			if(lst!=null)
				for(int i=0;i<lst.size();i++){
					TbTSchedule obj = lst.get(i);
					if(str.length()>0)
						str.append(", ");
					if(obj.getPatientId()!=null && obj.getPatientId().trim().length()>0){
						TbTPatient pobj = new TbTPatient();
						pobj.setPatientid(obj.getPatientId());
						pdao.select(conn, pobj);
						if(pobj.getNickname()!=null && pobj.getNickname().trim().length()>0)
							str.append(pobj.getNickname()+"-");
						str.append(pobj.getFirstname()+" "+pobj.getLastname()+"["+pobj.getMobilephone()+"]");
					}else{
						str.append("(ใหม่)"+obj.getName()+"["+obj.getTel()+"]");
					}
				}
			map.put("nameList", str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
		return map;
	}

	@Override
	protected void checkSubclass() {
		
	}

}
