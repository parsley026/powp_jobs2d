package edu.kis.powp.jobs2d;

import java.awt.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.adapter.DrawerAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;
import edu.kis.powp.jobs2d.events.SelectChangeVisibleOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;

public class TestJobs2dPatterns {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static LineDrawerAdapter lineDrawerAdapter; // Added reference for customization

	private static void setupPresetTests(Application application) {
		SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager());

		application.addTest("Figure Joe 1", selectTestFigureOptionListener);
		application.addTest("Figure Joe 2", selectTestFigureOptionListener);
		application.addTest("Figure Jane 1", selectTestFigureOptionListener);
	}

	private static void setupDrivers(Application application) {
		Job2dDriver loggerDriver = new LoggerDriver();
		DriverFeature.addDriver("Logger Driver", loggerDriver);
		DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);

		Job2dDriver testDriver = new DrawerAdapter(DrawerFeature.getDrawerController());
		DriverFeature.addDriver("Drawer Adapter", testDriver);

		lineDrawerAdapter = new LineDrawerAdapter(DrawerFeature.getDrawerController());
		DriverFeature.addDriver("Line Drawer Adapter", lineDrawerAdapter);

		DriverFeature.updateDriverInfo();
	}

	private static void setupLineCustomizationMenu(Application application) {
		application.addComponentMenu(LineDrawerAdapter.class, "Line Drawer Customization", 0);

		// basic line type
		application.addComponentMenuElement(LineDrawerAdapter.class, "Basic Line",
				(ActionEvent e) -> lineDrawerAdapter.useBasicLine());

		application.addComponentMenuElement(LineDrawerAdapter.class, "Dotted Line",
				(ActionEvent e) -> lineDrawerAdapter.useDottedLine());

		application.addComponentMenuElement(LineDrawerAdapter.class, "Special Line",
				(ActionEvent e) -> lineDrawerAdapter.useSpecialLine());

		// custom line attributes
		application.addComponentMenuElement(LineDrawerAdapter.class, "Set Color (Red)",
				(ActionEvent e) -> lineDrawerAdapter.setColor(Color.RED));

		application.addComponentMenuElement(LineDrawerAdapter.class, "Set Color (Green)",
				(ActionEvent e) -> lineDrawerAdapter.setColor(Color.GREEN));

		application.addComponentMenuElement(LineDrawerAdapter.class, "Set Color (Blue)",
				(ActionEvent e) -> lineDrawerAdapter.setColor(Color.BLUE));

		application.addComponentMenuElement(LineDrawerAdapter.class, "Set Thickness 2.5",
				(ActionEvent e) -> lineDrawerAdapter.setThickness(2.5f));

		application.addComponentMenuElement(LineDrawerAdapter.class, "Set Thickness 5",
				(ActionEvent e) -> lineDrawerAdapter.setThickness(5f));

		application.addComponentMenuElement(LineDrawerAdapter.class, "Set Thickness 7.5",
				(ActionEvent e) -> lineDrawerAdapter.setThickness(7.5f));

		application.addComponentMenuElementWithCheckBox(LineDrawerAdapter.class, "Dotted Line",
				(ActionEvent e) -> lineDrawerAdapter.setDotted(true), false);
	}

	private static void setupDefaultDrawerVisibilityManagement(Application application) {
		DefaultDrawerFrame defaultDrawerWindow = DefaultDrawerFrame.getDefaultDrawerFrame();
		application.addComponentMenuElementWithCheckBox(DrawPanelController.class, "Default Drawer Visibility",
				new SelectChangeVisibleOptionListener(defaultDrawerWindow), true);
		defaultDrawerWindow.setVisible(true);
	}

	private static void setupLogger(Application application) {
		application.addComponentMenu(Logger.class, "Logger", 0);
		application.addComponentMenuElement(Logger.class, "Clear log",
				(ActionEvent e) -> application.flushLoggerOutput());
		application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
		application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
		application.addComponentMenuElement(Logger.class, "Warning level",
				(ActionEvent e) -> logger.setLevel(Level.WARNING));
		application.addComponentMenuElement(Logger.class, "Severe level",
				(ActionEvent e) -> logger.setLevel(Level.SEVERE));
		application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Application app = new Application("2d jobs Visio");
				DrawerFeature.setupDrawerPlugin(app);

				DriverFeature.setupDriverPlugin(app);
				setupDrivers(app);
				setupLineCustomizationMenu(app);
				setupPresetTests(app);
				setupLogger(app);

				app.setVisibility(true);
			}
		});
	}
}
