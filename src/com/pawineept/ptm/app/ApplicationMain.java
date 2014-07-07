package com.pawineept.ptm.app;

import java.util.Locale;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.pawineept.ptm.layout.BranchLayout;
import com.pawineept.ptm.layout.ChooseDBLayout;
import com.pawineept.ptm.layout.MedicalRecordGroupLayout;
import com.pawineept.ptm.layout.MedicalRecordLayout;
import com.pawineept.ptm.layout.MedicalRecordSearchLayout;
import com.pawineept.ptm.layout.PaymentLayout;
import com.pawineept.ptm.layout.ScheduleLayout;
import com.pawineept.ptm.layout.UserManageLayout;
import com.pawineept.ptm.layout.UserManageSearchLayout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class ApplicationMain {
	public static Composite composite;
	public static Menu menu;
	public static Shell shell;
	public static String dbpath;
	public static String USERNAME = "admin";

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Locale.setDefault(new Locale("th", "TH"));
			open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public static void open() {
		Display display = Display.getDefault();
		createShell();
		
		composite = new ChooseDBLayout(shell,SWT.NONE);

		openShell();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public static void openShell() {
		shell.open();
		shell.layout();
	}

	public static void createShell() {
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(ApplicationMain.class, "/com/pawineept/ptm/image/logo64.png"));
		shell.setSize(800, 400);
		shell.setText("Pawinee Physical Therapy Management Version 0.0.1");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		
		
		menu = new Menu(shell, SWT.BAR);
		menu.setEnabled(false);
		shell.setMenuBar(menu);
		
		MenuItem menu_file = new MenuItem(menu, SWT.CASCADE);
		menu_file.setText("ไฟล์");
		
		Menu menu_1 = new Menu(menu_file);
		menu_file.setMenu(menu_1);
		
		MenuItem menuItem_exit = new MenuItem(menu_1, SWT.NONE);
		menuItem_exit.setImage(SWTResourceManager.getImage(ApplicationMain.class, "/com/pawineept/ptm/image/icon/door_out.png"));
		menuItem_exit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		menuItem_exit.setText("ออกจากโปรแกรม");
		
		MenuItem menuItem = new MenuItem(menu, SWT.CASCADE);
		menuItem.setText("เวชระเบียน");
		
		Menu menu_2 = new Menu(menuItem);
		menuItem.setMenu(menu_2);
		
		MenuItem menuItem_2 = new MenuItem(menu_2, SWT.NONE);
		menuItem_2.setImage(SWTResourceManager.getImage(ApplicationMain.class, "/com/pawineept/ptm/image/icon/group.png"));
		menuItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				closeShell();
				composite = new MedicalRecordSearchLayout(shell,SWT.NONE);
				openShell();
			}
		});
		menuItem_2.setText("ค้นหา / แก้ไขเวชระเบียน");
		
		MenuItem menuItem_1 = new MenuItem(menu_2, SWT.NONE);
		menuItem_1.setImage(SWTResourceManager.getImage(ApplicationMain.class, "/com/pawineept/ptm/image/icon/group_add.png"));
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MedicalRecordLayout.modeedit = false;
				MedicalRecordLayout.patientId = null;
				closeShell();
				composite = new MedicalRecordLayout(shell,SWT.NONE);
				openShell();
				
			}
		});
		menuItem_1.setText("สร้างเวชระเบียน");
		
		MenuItem menuItem_6 = new MenuItem(menu, SWT.CASCADE);
		menuItem_6.setText("ปฏิทิน");
		
		Menu menu_4 = new Menu(menuItem_6);
		menuItem_6.setMenu(menu_4);
		
		MenuItem menuItem_7 = new MenuItem(menu_4, SWT.NONE);
		menuItem_7.setImage(SWTResourceManager.getImage(ApplicationMain.class, "/com/pawineept/ptm/image/icon/calendar_view_month.png"));
		menuItem_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				closeShell();
				composite = new ScheduleLayout(shell,SWT.NONE);
				openShell();
			}
		});
		menuItem_7.setText("ปฏิทินการรักษา");
		
		MenuItem menuItem_3 = new MenuItem(menu, SWT.CASCADE);
		menuItem_3.setText("ชำระค่าบริการ");
		
		Menu menu_3 = new Menu(menuItem_3);
		menuItem_3.setMenu(menu_3);
		
		MenuItem menuItem_4 = new MenuItem(menu_3, SWT.NONE);
		menuItem_4.setImage(SWTResourceManager.getImage(ApplicationMain.class, "/com/pawineept/ptm/image/icon/cart.png"));
		menuItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				closeShell();
				PaymentLayout paymentLayout = new PaymentLayout(shell,SWT.NONE);
				composite = paymentLayout;
				paymentLayout.findPTUsed();
				openShell();
			}
		});
		menuItem_4.setText("ชำระค่าบริการ");
		
		MenuItem menuItem_5 = new MenuItem(menu_3, SWT.NONE);
		menuItem_5.setImage(SWTResourceManager.getImage(ApplicationMain.class, "/com/pawineept/ptm/image/icon/page_green.png"));
		menuItem_5.setText("พิมพ์ใบเสร็จใหม่");
		
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("จัดการข้อมูล");
		
		Menu menu_5 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_5);
		
		MenuItem menuItem_8 = new MenuItem(menu_5, SWT.NONE);
		menuItem_8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				closeShell();
				BranchLayout brl = new BranchLayout(shell, SWT.NONE);
//				MedicalRecordGroupLayout mrg = new MedicalRecordGroupLayout(shell, SWT.NONE);
				composite = brl;
				openShell();
			}
		});
		menuItem_8.setText("ข้อมูลสาขา");
		
		MenuItem menuItem_11 = new MenuItem(menu_5, SWT.NONE);
		menuItem_11.setText("ข้อมูลนักกายภาพ");
		
		MenuItem mntmNewItem = new MenuItem(menu_5, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				closeShell();
				UserManageSearchLayout umls = new UserManageSearchLayout(shell, SWT.NONE);
				composite = umls;
				openShell();
			}
		});
		mntmNewItem.setText("ข้อมูลผู้ใช้งาน");
		
		MenuItem menuItem_12 = new MenuItem(menu_5, SWT.NONE);
		menuItem_12.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				closeShell();
				UserManageLayout uml = new UserManageLayout(shell, SWT.NONE);
				composite = uml;
				openShell();
			}
		});
		menuItem_12.setText("สร้างข้อมูลผู้ใช้งาน");
		
		new MenuItem(menu_5, SWT.SEPARATOR);
		
		MenuItem menuItem_9 = new MenuItem(menu_5, SWT.NONE);
		menuItem_9.setText("รายการใบเสร็จ");
		
		MenuItem menuItem_10 = new MenuItem(menu_5, SWT.NONE);
		menuItem_10.setText("ปฺฏิทินวันหยุด");
		
		//shell.setMenuBar(null);

	}
	
	public static void showMenu(){
		shell.setMenuBar(menu);
	}

	public static void closeShell() {
		composite.dispose();
	}
}
