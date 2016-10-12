package controller.ui;

import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import data.Teams;

public class DropInAssignment extends JFrame {

	private static final long serialVersionUID = 6496523067224913944L;

	private static final String WINDOW_TITLE = "GameController";
	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 450;
	private String suffix = ".properties";
	private JFileChooser jFileChooser;
	private File propertiesFile;

	private JPanel contentPanel;

	private JPanel panelTeamRed;
	private JPanel panelTeamBlue;

	private JPanel teamNumbersRed;
	private JPanel teamNumbersBlue;

	private JPanel teamNameRed;
	private JPanel teamNameBlue;

	private JPanel readyPanel;

//	private JLabel teamRedHeaderLabel;
//	private JLabel teamBlueHeaderLabel;

	private String[] numbers = { "1", "2", "3", "4", "5" };

	@SuppressWarnings("unchecked")
	private JComboBox<String>[] teamNumbers = (JComboBox<String>[]) new JComboBox[10];

	@SuppressWarnings("unchecked")
	private JComboBox<String>[] teamName = (JComboBox<String>[]) new JComboBox[10];

	private JButton readyButton;

	public boolean ready = false;

	public static Map<Integer, String> numberToTeam = new HashMap<Integer, String>();
	
	

	public DropInAssignment() {
		super(WINDOW_TITLE);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setLocation((width - WINDOW_WIDTH) / 2, (height - WINDOW_HEIGHT) / 2);
		setSize(510, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

//		initializeLabels();
		initializeNumberBoxes();
		initializeNameBoxes();

		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		panelTeamRed = new JPanel();

		teamNumbersRed = new JPanel();
		teamNumbersRed
				.setLayout(new BoxLayout(teamNumbersRed, BoxLayout.Y_AXIS));
		teamNumbersRed.add(teamNumbers[0]);
		teamNumbersRed.add(teamNumbers[1]);
		teamNumbersRed.add(teamNumbers[2]);
		teamNumbersRed.add(teamNumbers[3]);
		teamNumbersRed.add(teamNumbers[4]);

		teamNameRed = new JPanel();
		teamNameRed.setLayout(new BoxLayout(teamNameRed, BoxLayout.Y_AXIS));
		teamNameRed.add(teamName[0]);
		teamNameRed.add(teamName[1]);
		teamNameRed.add(teamName[2]);
		teamNameRed.add(teamName[3]);
		teamNameRed.add(teamName[4]);

		panelTeamBlue = new JPanel();

		teamNumbersBlue = new JPanel();
		teamNumbersBlue.setLayout(new BoxLayout(teamNumbersBlue,
				BoxLayout.Y_AXIS));
		teamNumbersBlue.add(teamNumbers[5]);
		teamNumbersBlue.add(teamNumbers[6]);
		teamNumbersBlue.add(teamNumbers[7]);
		teamNumbersBlue.add(teamNumbers[8]);
		teamNumbersBlue.add(teamNumbers[9]);

		teamNameBlue = new JPanel();
		teamNameBlue.setLayout(new BoxLayout(teamNameBlue, BoxLayout.Y_AXIS));
		teamNameBlue.add(teamName[5]);
		teamNameBlue.add(teamName[6]);
		teamNameBlue.add(teamName[7]);
		teamNameBlue.add(teamName[8]);
		teamNameBlue.add(teamName[9]);

		readyPanel = new JPanel();
		readyButton = new JButton("Ready");
		readyPanel.add(readyButton);
		readyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				for (int i = 0; i < teamNumbers.length; i++) {
					if (i < 5) {
						numberToTeam.put(
								Integer.parseInt((String) "1"
										+ teamNumbers[i].getSelectedItem()),
								(String) teamName[i].getSelectedItem());
					} else {
						numberToTeam.put(
								Integer.parseInt((String) "2"
										+ teamNumbers[i].getSelectedItem()),
								(String) teamName[i].getSelectedItem());
					}
				}
			}
		});

		JButton loadFromProperties = new JButton("Load from Properties");
		loadFromProperties.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getFileChooser();
			}
		});
		readyPanel.add(loadFromProperties);

		panelTeamRed.add(teamNumbersRed);
		panelTeamRed.add(teamNameRed);

		panelTeamBlue.add(teamNumbersBlue);
		panelTeamBlue.add(teamNameBlue);

		contentPanel.add(panelTeamRed);
		contentPanel.add(panelTeamBlue);
		contentPanel.add(readyPanel);
		add(contentPanel);

		setVisible(true);
	}

//	private void initializeLabels() {
//		teamRedHeaderLabel = new JLabel("Team Red");
//		teamBlueHeaderLabel = new JLabel("Team Blue");
//	}

	private void initializeNumberBoxes() {
		int j = 0;
		for (int i = 0; i < teamNumbers.length; i++) {
			teamNumbers[i] = new JComboBox<String>(numbers);
			if (i < 4) {
				teamNumbers[i].setSelectedIndex(i);
			}
			teamNumbers[i].setSelectedIndex(j);
			j++;
		}
	}

	private void initializeNameBoxes() {
		String[] teams = getShortTeams();
		for(int i=0; i<10; i++) {
			teamName[i] = new JComboBox<String>(teams);
		}
	}

	/**
	 * Shows a {@code FileChooser}
	 */
	private void getFileChooser() {
		jFileChooser = new JFileChooser();
		jFileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return suffix;
			}

			@Override
			public boolean accept(File f) {
				if (f == null)
					return false;

				if (f.isDirectory())
					return true;

				return f.getName().toLowerCase().endsWith(suffix);
			}
		});
		int returnVal = jFileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			propertiesFile = jFileChooser.getSelectedFile();
		}
		setTeamNamesFromProperties();
	}

	/**
	 * Reads the values from the properties file and sets them as selected
	 */
	private void setTeamNamesFromProperties() {
		if (propertiesFile != null) {
			FileReader fr = null;
			BufferedReader br = null;
			try {
				fr = new FileReader(propertiesFile);
				br = new BufferedReader(fr);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				String line;
				try {
					for (int i = 0; (line = br.readLine()) != null; i++) {
						teamName[i].setSelectedItem(line);
					}
				} catch (IOException ex) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Calculates an array that contains only the existing Teams of the SPL.
	 * 
	 * @return Short teams array with numbers
	 */
	private String[] getShortTeams() {
		String[] fullTeams = Teams.getNamesSPL(true);
		String[] out;
		int k = 0;
		for (int j = 0; j < fullTeams.length; j++) {
			if (fullTeams[j] != null) {
				k++;
			}
		}
		out = new String[k];
		k = 0;
		for (int j = 0; j < fullTeams.length; j++) {
			if (fullTeams[j] != null) {
				out[k++] = fullTeams[j];
			}
		}

		Arrays.sort(out, 1, out.length, String.CASE_INSENSITIVE_ORDER);

		return out;
	}
}
