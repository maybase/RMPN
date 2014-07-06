package com.pawineept.ptm.layout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.pawineept.ptm.app.ApplicationMain;

public class LoginLayout extends Composite {
	private Text user;
	private Text pwd;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LoginLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));
		new Label(this, SWT.NONE);
		
		Canvas canvas_2 = new Canvas(this, SWT.NONE);
		canvas_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		new Label(this, SWT.NONE);
		
		Canvas canvas = new Canvas(this, SWT.NONE);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
		
		Label lblLoginSystem = new Label(composite, SWT.NONE);
		lblLoginSystem.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblLoginSystem.setBounds(82, 0, 124, 25);
		lblLoginSystem.setText("Login System");
		
		Label lblUserName = new Label(composite, SWT.NONE);
		lblUserName.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblUserName.setBounds(0, 31, 76, 21);
		lblUserName.setText("User name");
		
		user = new Text(composite, SWT.BORDER);
		user.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		user.setBounds(82, 28, 164, 24);
		
		Label lblPassword = new Label(composite, SWT.NONE);
		lblPassword.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblPassword.setBounds(0, 61, 76, 18);
		lblPassword.setText("Password");
		
		pwd = new Text(composite, SWT.BORDER);
		pwd.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		pwd.setBounds(82, 58, 164, 25);
		
		Button btnLogin = new Button(composite, SWT.NONE);
		btnLogin.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		btnLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplicationMain.showMenu();
				ApplicationMain.menu.setVisible(true);
				ApplicationMain.menu.setEnabled(true);
				ApplicationMain.closeShell();
				ApplicationMain.composite = new ScheduleLayout(ApplicationMain.shell, SWT.NULL);
				ApplicationMain.openShell();
				
			}
		});
		btnLogin.setBounds(82, 85, 98, 25);
		btnLogin.setText("Login");
		
		Canvas canvas_1 = new Canvas(this, SWT.NONE);
		canvas_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Canvas canvas_4 = new Canvas(this, SWT.NONE);
		canvas_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));
		new Label(this, SWT.NONE);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
