package IA.Red;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class UIMain extends JFrame {
	// Variables locals pel problema
	Estat estat;
	RedCercador cerca;
	RedSuccessorFunction1 operadors;
	RedHeuristicFunction1 heuristic;
	// Variables per a escriurte el fitxer resultat
	int info_einicial, info_operadors, info_heuristic, info_k, info_iter, info_passos_iter;
	String info_algoritme;
	double info_lambda;

	/** Creates new form UIMain */
	public UIMain() {
		initComponents();

		// Insertar etiqueta d'error i arreglar les posicions
		errorLabel = new javax.swing.JLabel();
		getContentPane().add(errorLabel, BorderLayout.PAGE_START);
		/*
		 * getContentPane().remove(jTabbedPane1); getContentPane().add(jTabbedPane1,
		 * BorderLayout.CENTER);
		 */
		dibuixNetworkHC.setVisible(false);
		dibuixNetworkSA.setVisible(false);

		error(null);
		valorsPerDefecte();
		actualitzarOperadors();
		actualitzarHeuristic();

		estat.createNetwork(Integer.parseInt(nText.getText()), Integer.parseInt(mText.getText()),
				Integer.parseInt(nSensorsText.getText()), Integer.parseInt(seedSensorsText.getText()),
				Integer.parseInt(nCentresText.getText()), Integer.parseInt(seedCentresText.getText()));
		dibuixNetwork1.novaNetwork(estat);
		errorInicialLabel.setText("Coste Inicial: " + estat.getCoste());

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		panelInici = new javax.swing.JPanel();
		panelIniciParams = new javax.swing.JPanel();
		jPanel5 = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		nText = new javax.swing.JTextField();
		mText = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		nSensorsText = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		nCentresText = new javax.swing.JTextField();
		jLabel10 = new javax.swing.JLabel();
		seedSensorsText = new javax.swing.JTextField();
		jLabel11 = new javax.swing.JLabel();
		seedCentresText = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		generarNetworkButton = new javax.swing.JButton();
		jPanel6 = new javax.swing.JPanel();
		jLabel9 = new javax.swing.JLabel();
		estatInicialCombo = new javax.swing.JComboBox();
		estatInicialButton = new javax.swing.JButton();
		errorInicialLabel = new javax.swing.JLabel();
		jPanel12 = new javax.swing.JPanel();
		jLabel26 = new javax.swing.JLabel();
		jLabel27 = new javax.swing.JLabel();
		jLabel28 = new javax.swing.JLabel();
		jLabel29 = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		perDefecteParamsButton = new javax.swing.JButton();
		panelIniciNetwork = new javax.swing.JPanel();
		int width = 200;
		int height = 200;
		estat = new Estat();
		dibuixNetwork1 = new DibuixNetwork(width, height, estat.getM(), estat.getN(), estat.getSensores(), estat.getCentros(), estat.getConnexions());
		panelParams = new javax.swing.JPanel();
		jPanel4 = new javax.swing.JPanel();
		jLabel6 = new javax.swing.JLabel();
		operadorsCombo = new javax.swing.JComboBox();
		jScrollPane3 = new javax.swing.JScrollPane();
		operadorsInfo = new javax.swing.JTextPane();
		jPanel13 = new javax.swing.JPanel();
		jLabel8 = new javax.swing.JLabel();
		heuristicCombo = new javax.swing.JComboBox();
		jScrollPane1 = new javax.swing.JScrollPane();
		heuristicInfo = new javax.swing.JTextPane();
		jPanel14 = new javax.swing.JPanel();
		hillClimbingButton = new javax.swing.JButton();
		jPanel15 = new javax.swing.JPanel();
		jLabel18 = new javax.swing.JLabel();
		kText = new javax.swing.JTextField();
		iteracionsText = new javax.swing.JTextField();
		jLabel19 = new javax.swing.JLabel();
		jLabel20 = new javax.swing.JLabel();
		jLabel21 = new javax.swing.JLabel();
		passosText = new javax.swing.JTextField();
		lambdaText = new javax.swing.JTextField();
		simulatedAnButton = new javax.swing.JButton();
		panelHillClimbing = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		hillClimbingButton1 = new javax.swing.JButton();
		errorInicialLabel1 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		infoHC = new javax.swing.JTextPane();
		panelIniciNetwork1 = new javax.swing.JPanel();
		dibuixNetworkHC = new IA.Red.DibuixNetwork();
		panelSimulatedAnnealing = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		simulatedAnButton1 = new javax.swing.JButton();
		errorInicialLabel2 = new javax.swing.JLabel();
		jScrollPane4 = new javax.swing.JScrollPane();
		infoSA = new javax.swing.JTextPane();
		panelIniciNetwork3 = new javax.swing.JPanel();
		dibuixNetworkSA = new IA.Red.DibuixNetwork();
		jMenuBar2 = new javax.swing.JMenuBar();
		jMenu2 = new javax.swing.JMenu();
		Salir = new javax.swing.JMenuItem();

		jMenu1.setText("Menu");
		jMenuBar1.add(jMenu1);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Red de Sensors");
		setResizable(false);

		jTabbedPane1.setMaximumSize(new java.awt.Dimension(1025, 758));
		jTabbedPane1.setMinimumSize(new java.awt.Dimension(1025, 758));
		jTabbedPane1.setPreferredSize(new java.awt.Dimension(1025, 758));
		jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jTabbedPane1StateChanged(evt);
			}
		});

		panelInici.setLayout(new java.awt.GridBagLayout());

		panelIniciParams.setMaximumSize(new java.awt.Dimension(320, 700));
		panelIniciParams.setMinimumSize(new java.awt.Dimension(320, 700));
		panelIniciParams.setPreferredSize(new java.awt.Dimension(320, 700));
		panelIniciParams.setLayout(new javax.swing.BoxLayout(panelIniciParams, javax.swing.BoxLayout.Y_AXIS));

		jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Par�metres de la Network"));
		jPanel5.setLayout(new java.awt.GridBagLayout());

		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel2.setText("N:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		jPanel5.add(jLabel2, gridBagConstraints);

		nText.setColumns(3);
		nText.setToolTipText("Amplada de la Network");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		jPanel5.add(nText, gridBagConstraints);

		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel1.setText("M:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		jPanel5.add(jLabel1, gridBagConstraints);

		mText.setColumns(3);
		mText.setToolTipText("Al�ada de la Network");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		jPanel5.add(mText, gridBagConstraints);

		jLabel3.setText("num.sensors:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 5);
		jPanel5.add(jLabel3, gridBagConstraints);

		nSensorsText.setColumns(3);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
		jPanel5.add(nSensorsText, gridBagConstraints);

		jLabel4.setText("num.centres:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 5);
		jPanel5.add(jLabel4, gridBagConstraints);

		nCentresText.setColumns(3);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
		jPanel5.add(nCentresText, gridBagConstraints);

		jLabel10.setText("Seed S:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 5);
		jPanel5.add(jLabel10, gridBagConstraints);

		seedSensorsText.setColumns(4);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
		jPanel5.add(seedSensorsText, gridBagConstraints);

		jLabel11.setText("Seed C:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 5);
		jPanel5.add(jLabel11, gridBagConstraints);

		seedCentresText.setColumns(4);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
		jPanel5.add(seedCentresText, gridBagConstraints);

		generarNetworkButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		generarNetworkButton.setText("Generar Network");
		generarNetworkButton.setMaximumSize(new java.awt.Dimension(170, 35));
		generarNetworkButton.setMinimumSize(new java.awt.Dimension(170, 35));
		generarNetworkButton.setPreferredSize(new java.awt.Dimension(170, 35));
		generarNetworkButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				generarNetworkButtonActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
		jPanel5.add(generarNetworkButton, gridBagConstraints);

		panelIniciParams.add(jPanel5);

		jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Estat inicial"));
		jPanel6.setLayout(new java.awt.GridBagLayout());

		jLabel9.setText("Tipus d'estat inicial:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel6.add(jLabel9, gridBagConstraints);

		estatInicialCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aleatori (tal com s'han creat)",
				"Connex. directes a C + Dist. m�nima entre S i C + Sensors restants backwards",
				"Connex. directes a C + Dist. m�nima entre S i C + Sensors restants" }));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		jPanel6.add(estatInicialCombo, gridBagConstraints);

		estatInicialButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		estatInicialButton.setText("Generar Estat Inicial");
		estatInicialButton.setMaximumSize(new java.awt.Dimension(190, 35));
		estatInicialButton.setMinimumSize(new java.awt.Dimension(190, 35));
		estatInicialButton.setPreferredSize(new java.awt.Dimension(190, 35));
		estatInicialButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				estatInicialButtonActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
		jPanel6.add(estatInicialButton, gridBagConstraints);

		errorInicialLabel.setText("Coste Inicial: 0");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel6.add(errorInicialLabel, gridBagConstraints);

		panelIniciParams.add(jPanel6);

		jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		perDefecteParamsButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
		perDefecteParamsButton.setText("valors per defecte");
		perDefecteParamsButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				perDefecteParamsActionPerformed(evt);
			}
		});
		jPanel1.add(perDefecteParamsButton);

		panelIniciParams.add(jPanel1);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		panelInici.add(panelIniciParams, gridBagConstraints);

		panelIniciNetwork.setBorder(javax.swing.BorderFactory.createTitledBorder("Network"));
		panelIniciNetwork.setMaximumSize(new java.awt.Dimension(1200, 1200));
		panelIniciNetwork.setMinimumSize(new java.awt.Dimension(700, 700));
		panelIniciNetwork.setPreferredSize(new java.awt.Dimension(700, 700));
		panelIniciNetwork.setLayout(new java.awt.BorderLayout());
		panelIniciNetwork.add(dibuixNetwork1, java.awt.BorderLayout.CENTER);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		panelInici.add(panelIniciNetwork, gridBagConstraints);

		jTabbedPane1.addTab("Opcions Inicials", panelInici);

		panelParams.setLayout(new javax.swing.BoxLayout(panelParams, javax.swing.BoxLayout.Y_AXIS));

		jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Operadors"));
		jPanel4.setLayout(new java.awt.GridBagLayout());

		jLabel6.setText("Conjunt d'operadors:");
		jPanel4.add(jLabel6, new java.awt.GridBagConstraints());

		operadorsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"1: Modificar Sensor Output Connexion", "2: -", "3: -", "4: -", "5: -", "6: -", "7: -" }));
		operadorsCombo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				operadorsComboActionPerformed(evt);
			}
		});
		jPanel4.add(operadorsCombo, new java.awt.GridBagConstraints());

		operadorsInfo.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
		operadorsInfo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
		operadorsInfo.setEditable(false);
		operadorsInfo.setMinimumSize(new java.awt.Dimension(2, 64));
		operadorsInfo.setPreferredSize(new java.awt.Dimension(2, 64));
		jScrollPane3.setViewportView(operadorsInfo);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
		jPanel4.add(jScrollPane3, gridBagConstraints);

		panelParams.add(jPanel4);

		jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Heur�stic"));
		jPanel13.setLayout(new java.awt.GridBagLayout());

		jLabel8.setText("Heur�stic utilitzat:");
		jPanel13.add(jLabel8, new java.awt.GridBagConstraints());

		heuristicCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cost Total",
				"Cost Total + num. de connexions a Centres", "Cost Total * num. de connexions a Centres", "-", "-" }));
		heuristicCombo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				heuristicComboActionPerformed(evt);
			}
		});
		jPanel13.add(heuristicCombo, new java.awt.GridBagConstraints());

		heuristicInfo.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
		heuristicInfo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
		heuristicInfo.setEditable(false);
		heuristicInfo.setMinimumSize(new java.awt.Dimension(2, 64));
		heuristicInfo.setPreferredSize(new java.awt.Dimension(2, 64));
		jScrollPane1.setViewportView(heuristicInfo);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
		jPanel13.add(jScrollPane1, gridBagConstraints);

		panelParams.add(jPanel13);

		jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Hill Climbing"));
		jPanel14.setLayout(new java.awt.GridBagLayout());

		hillClimbingButton.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
		hillClimbingButton.setText("Iniciar Hill Climbing");
		hillClimbingButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				hillClimbingButtonActionPerformed(evt);
			}
		});
		jPanel14.add(hillClimbingButton, new java.awt.GridBagConstraints());

		panelParams.add(jPanel14);

		jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Simulated Annealing"));
		jPanel15.setLayout(new java.awt.GridBagLayout());

		jLabel18.setText("K:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		jPanel15.add(jLabel18, gridBagConstraints);

		kText.setColumns(3);
		kText.setText("5");
		kText.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				kTextActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		jPanel15.add(kText, gridBagConstraints);

		iteracionsText.setColumns(4);
		iteracionsText.setText("2000");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		jPanel15.add(iteracionsText, gridBagConstraints);

		jLabel19.setText("iteracions:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 5);
		jPanel15.add(jLabel19, gridBagConstraints);

		jLabel20.setText("passos per iteraci�:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 5);
		jPanel15.add(jLabel20, gridBagConstraints);

		jLabel21.setText("lambda:");
		jLabel21.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 5);
		jPanel15.add(jLabel21, gridBagConstraints);

		passosText.setColumns(3);
		passosText.setText("5");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		jPanel15.add(passosText, gridBagConstraints);

		lambdaText.setColumns(4);
		lambdaText.setText("0.001");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 7;
		jPanel15.add(lambdaText, gridBagConstraints);

		simulatedAnButton.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
		simulatedAnButton.setText("Iniciar Simulated Annealing");
		simulatedAnButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				simulatedAnButtonActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 7;
		gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
		jPanel15.add(simulatedAnButton, gridBagConstraints);

		panelParams.add(jPanel15);

		jTabbedPane1.addTab("Par�metres", panelParams);

		panelHillClimbing.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				panelHillClimbingFocusGained(evt);
			}
		});
		panelHillClimbing.setLayout(new java.awt.GridBagLayout());

		jPanel2.setMaximumSize(new java.awt.Dimension(320, 500));
		jPanel2.setMinimumSize(new java.awt.Dimension(320, 500));
		jPanel2.setPreferredSize(new java.awt.Dimension(320, 500));
		jPanel2.setLayout(new java.awt.GridBagLayout());

		hillClimbingButton1.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
		hillClimbingButton1.setText("Iniciar Hill Climbing");
		hillClimbingButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				hillClimbingButtonActionPerformed(evt);
			}
		});
		jPanel2.add(hillClimbingButton1, new java.awt.GridBagConstraints());

		errorInicialLabel1.setText("Coste Inicial: 0");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		jPanel2.add(errorInicialLabel1, gridBagConstraints);

		jScrollPane2.setPreferredSize(new java.awt.Dimension(320, 400));

		infoHC.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
		infoHC.setEditable(false);
		jScrollPane2.setViewportView(infoHC);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		jPanel2.add(jScrollPane2, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		panelHillClimbing.add(jPanel2, gridBagConstraints);

		panelIniciNetwork1.setBorder(javax.swing.BorderFactory.createTitledBorder("Network"));
		panelIniciNetwork1.setMaximumSize(new java.awt.Dimension(1200, 1200));
		panelIniciNetwork1.setMinimumSize(new java.awt.Dimension(700, 700));
		panelIniciNetwork1.setPreferredSize(new java.awt.Dimension(700, 700));
		panelIniciNetwork1.setLayout(new java.awt.BorderLayout());
		panelIniciNetwork1.add(dibuixNetworkHC, java.awt.BorderLayout.CENTER);

		panelHillClimbing.add(panelIniciNetwork1, new java.awt.GridBagConstraints());

		jTabbedPane1.addTab("Hill Climbing", panelHillClimbing);

		panelSimulatedAnnealing.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				panelSimulatedAnnealingFocusGained(evt);
			}
		});
		panelSimulatedAnnealing.setLayout(new java.awt.GridBagLayout());

		jPanel3.setMaximumSize(new java.awt.Dimension(320, 500));
		jPanel3.setMinimumSize(new java.awt.Dimension(320, 500));
		jPanel3.setPreferredSize(new java.awt.Dimension(320, 500));
		jPanel3.setLayout(new java.awt.GridBagLayout());

		simulatedAnButton1.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
		simulatedAnButton1.setText("Iniciar Simulated Annealing");
		simulatedAnButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				simulatedAnButtonActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		jPanel3.add(simulatedAnButton1, gridBagConstraints);

		errorInicialLabel2.setText("Coste Inicial: 0");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		jPanel3.add(errorInicialLabel2, gridBagConstraints);

		jScrollPane4.setPreferredSize(new java.awt.Dimension(320, 400));

		infoSA.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
		infoSA.setEditable(false);
		jScrollPane4.setViewportView(infoSA);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		jPanel3.add(jScrollPane4, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		panelSimulatedAnnealing.add(jPanel3, gridBagConstraints);

		panelIniciNetwork3.setBorder(javax.swing.BorderFactory.createTitledBorder("Network"));
		panelIniciNetwork3.setMaximumSize(new java.awt.Dimension(1200, 1200));
		panelIniciNetwork3.setMinimumSize(new java.awt.Dimension(700, 700));
		panelIniciNetwork3.setPreferredSize(new java.awt.Dimension(700, 700));
		panelIniciNetwork3.setLayout(new java.awt.BorderLayout());
		panelIniciNetwork3.add(dibuixNetworkSA, java.awt.BorderLayout.CENTER);

		panelSimulatedAnnealing.add(panelIniciNetwork3, new java.awt.GridBagConstraints());

		jTabbedPane1.addTab("Simulated Annealing", panelSimulatedAnnealing);

		getContentPane().add(jTabbedPane1);

		jMenu2.setText("Menu");

		Salir.setText("Salir");
		Salir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SalirActionPerformed(evt);
			}
		});
		jMenu2.add(Salir);

		jMenuBar2.add(jMenu2);

		setJMenuBar(jMenuBar2);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void SalirActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SalirActionPerformed
// TODO add your handling code here:
		dispose();
	}// GEN-LAST:event_SalirActionPerformed

	private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jTabbedPane1StateChanged
		switch (jTabbedPane1.getSelectedIndex()) {
		case 2:
			dibuixNetworkHC.setVisible(true);
			break;
		case 3:
			dibuixNetworkSA.setVisible(true);
			break;
		}
	}// GEN-LAST:event_jTabbedPane1StateChanged

	private void panelHillClimbingFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_panelHillClimbingFocusGained
		dibuixNetworkHC.setVisible(true);
	}// GEN-LAST:event_panelHillClimbingFocusGained

	private void panelSimulatedAnnealingFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_panelSimulatedAnnealingFocusGained
		dibuixNetworkSA.setVisible(true);
	}// GEN-LAST:event_panelSimulatedAnnealingFocusGained

	private void simulatedAnButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_simulatedAnButtonActionPerformed
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		if (comprovarParamsSimulatedAnnealing())
			return;
		int k, it, pit;
		double lbd;
		k = Integer.parseInt(kText.getText());
		it = Integer.parseInt(iteracionsText.getText());
		pit = Integer.parseInt(passosText.getText());
		lbd = Float.parseFloat(lambdaText.getText());
		try {
			info_algoritme = "SA";
			info_iter = it;
			info_k = k;
			info_passos_iter = pit;
			info_lambda = lbd;

			cerca = new RedCercador(estat, operadors, heuristic, it, pit, k, lbd);
			cerca.executarCerca();
			infoSA.setText("");
			infoSA.setText(cerca.getEstatFinal().connexionesToString() + "\n " + "Coste Final: " + cerca.getEstatFinal().getCoste() + "\n" + cerca.getPropietats()
					+ cerca.getAccions() + "\n ");
			cerca.fitxerResultats(estat, info_einicial, info_algoritme, info_operadors, info_heuristic, info_k,
					info_iter, info_passos_iter, info_lambda);
			dibuixNetworkSA.novaNetwork(cerca.getEstatFinal());
			cerca.getEstatFinal().finalUI();
			dibuixNetworkSA.actualitzar();
		} catch (Exception e) {
			error(e.toString());
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}// GEN-LAST:event_simulatedAnButtonActionPerformed

	private void hillClimbingButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_hillClimbingButtonActionPerformed
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try {
			info_algoritme = "HC";
			info_iter = -1;
			info_k = -1;
			info_passos_iter = -1;
			info_lambda = -1;

			cerca = new RedCercador(estat, operadors, heuristic);
			cerca.executarCerca();
			infoHC.setText("");
			infoHC.setText(cerca.getEstatFinal().connexionesToString() + "\n " + "Coste Final: " + cerca.getEstatFinal().getCoste() + "\n" + cerca.getPropietats()
					+ cerca.getAccions() + "\n ");
			cerca.fitxerResultats(estat, info_einicial, info_algoritme, info_operadors, info_heuristic, info_k,
					info_iter, info_passos_iter, info_lambda);
			dibuixNetworkHC.novaNetwork(cerca.getEstatFinal());
			cerca.getEstatFinal().finalUI();
			dibuixNetworkHC.actualitzar();
		} catch (Exception e) {
			error(e.toString());
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}// GEN-LAST:event_hillClimbingButtonActionPerformed

	private void heuristicComboActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_heuristicComboActionPerformed
		actualitzarHeuristic();
	}// GEN-LAST:event_heuristicComboActionPerformed

	private void operadorsComboActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_operadorsComboActionPerformed
		actualitzarOperadors();
	}// GEN-LAST:event_operadorsComboActionPerformed

	private void actualitzarOperadors() {
		int op = operadorsCombo.getSelectedIndex();
		info_operadors = op + 1;
		switch (op) {
		case 0:
			operadors = new RedSuccessorFunction1();
			operadorsInfo.setText("Modificar Sensor Output Connexion");
			break;
		default:
			operadorsInfo.setText("operador no definit");

		}
	}

	private void actualitzarHeuristic() {
		int heu = heuristicCombo.getSelectedIndex();
		info_heuristic = heu + 1;
		switch (heu) {
		case 0:
			heuristic = new RedHeuristicFunction1();
			heuristicInfo.setText("Minimitza tan sols el cost total del graf.");
			break;
		default:
			heuristicInfo.setText("heuristic no definit");

		}
	}

	private void kTextActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_kTextActionPerformed
// TODO add your handling code here:
	}// GEN-LAST:event_kTextActionPerformed

	private void perDefecteParamsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_perDefecteParamsActionPerformed
		valorsPerDefecte();
		errorInicialLabel.setText("Coste Inicial: " + estat.getCoste());
	}// GEN-LAST:event_perDefecteParamsActionPerformed

	private void estatInicialButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_estatInicialButtonActionPerformed
		estat.generarEstatInicial(estatInicialCombo.getSelectedIndex());
		info_einicial = estatInicialCombo.getSelectedIndex() + 1;
		try {
			dibuixNetwork1.actualitzar();
		} catch (Exception e) {
			error(e.toString());
		}
		textErrorInicial();
	}// GEN-LAST:event_estatInicialButtonActionPerformed

	private void generarNetworkButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_generarNetworkButtonActionPerformed
		if (comprovarParams())
			return;
		estat.createNetwork(Integer.parseInt(nText.getText()), Integer.parseInt(mText.getText()),
				Integer.parseInt(nSensorsText.getText()), Integer.parseInt(seedSensorsText.getText()),
				Integer.parseInt(nCentresText.getText()), Integer.parseInt(seedCentresText.getText()));
		dibuixNetwork1.novaNetwork(estat);
		textErrorInicial();
	}// GEN-LAST:event_generarNetworkButtonActionPerformed

	public static void modificaNetworkRePaint(Estat estat) {
		dibuixNetwork1.novaNetwork(estat);
	}
	
	private void textErrorInicial() {
		errorInicialLabel.setText("Coste Inicial: " + estat.getCoste());
		errorInicialLabel1.setText("Coste Inicial: " + estat.getCoste());
		errorInicialLabel2.setText("Coste Inicial: " + estat.getCoste());
	}

	/**
	 * Comprova que no hi hagi incoherencies en les entrades, retorna cert si hi ha
	 * algun error
	 */
	private boolean comprovarParams() {
		boolean err = false;
		int m = 0, n = 0, ns = 0, nc = 0, ss = 0, sc = 0, alfa = 0, beta = 0, gamma = 0, maxCent = 0;
		try {
			m = Integer.parseInt(mText.getText());
			n = Integer.parseInt(nText.getText());
			ns = Integer.parseInt(nSensorsText.getText());
			nc = Integer.parseInt(nCentresText.getText());
			ss = Integer.parseInt(seedSensorsText.getText());
			sc = Integer.parseInt(seedCentresText.getText());
			if (m < 0 || n < 0 || ns < 0 || nc < 0)
				throw new Exception();
		} catch (Exception e) {
			error("S'han d'introduir nombres naturals.");
			return true;
		}
		if (ns + nc > m * n) {
			error("Numero de sensors i centres massa gran.");
			err = true;
		} else if (ns < 1) {
			error("Numero de sensors massa petit.");
			err = true;
		} else if (nc < 1) {
			error("Numero de centres massa petit.");
			err = true;
		}
		if (!err)
			error(null);
		return err;
	}

	private boolean comprovarParamsSimulatedAnnealing() {
		boolean err = false;
		int k = 0, it = 0, pit = 0;
		double lbd = 0.000;
		try {
			k = Integer.parseInt(kText.getText());
			it = Integer.parseInt(iteracionsText.getText());
			pit = Integer.parseInt(passosText.getText());
			lbd = Float.parseFloat(lambdaText.getText());
			if (k < 0 || it < 0 || pit < 0 || lbd < 0)
				throw new Exception();
		} catch (Exception e) {
			error("S'han d'introduir nombres majors que zero.");
			return true;
		}
		if (lbd > 1.0) {
			error("Lambda ha de ser menor que 1");
			err = true;
		}
		return err;
	}

	private void error(String err) {
		if (err != null) {
			errorLabel.setForeground(Color.red);
			errorLabel.setText(" " + err);
		} else {
			errorLabel.setForeground(Color.black);
			errorLabel
					.setText(" Pr�ctica de Cerca Local d'IA. Albert Pedraza, Miguel Angel i Sergio Preciado, FIB 2021");
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new UIMain().setVisible(true);
			}
		});
	}

	private void valorsPerDefecte() {
		nText.setText("100");
		mText.setText("100");
		nSensorsText.setText("100");
		seedSensorsText.setText("1234");
		nCentresText.setText("4");
		seedCentresText.setText("4321");
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JMenuItem Salir;
	private static IA.Red.DibuixNetwork dibuixNetwork1;
	private IA.Red.DibuixNetwork dibuixNetworkHC;
	private IA.Red.DibuixNetwork dibuixNetworkSA;
	private javax.swing.JLabel errorInicialLabel;
	private javax.swing.JLabel errorInicialLabel1;
	private javax.swing.JLabel errorInicialLabel2;
	private javax.swing.JButton estatInicialButton;
	private javax.swing.JComboBox estatInicialCombo;
	private javax.swing.JButton generarNetworkButton;
	private javax.swing.JComboBox heuristicCombo;
	private javax.swing.JTextPane heuristicInfo;
	private javax.swing.JButton hillClimbingButton;
	private javax.swing.JButton hillClimbingButton1;
	private javax.swing.JTextPane infoHC;
	private javax.swing.JTextPane infoSA;
	private javax.swing.JTextField iteracionsText;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel19;
	private javax.swing.JLabel jLabel20;
	private javax.swing.JLabel jLabel21;
	private javax.swing.JLabel jLabel26;
	private javax.swing.JLabel jLabel27;
	private javax.swing.JLabel jLabel28;
	private javax.swing.JLabel jLabel29;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuBar jMenuBar2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel12;
	private javax.swing.JPanel jPanel13;
	private javax.swing.JPanel jPanel14;
	private javax.swing.JPanel jPanel15;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTextField kText;
	private javax.swing.JTextField lambdaText;
	private javax.swing.JTextField mText;
	private javax.swing.JTextField nSensorsText;
	private javax.swing.JTextField nCentresText;
	private javax.swing.JTextField seedSensorsText;
	private javax.swing.JTextField seedCentresText;
	private javax.swing.JTextField nText;
	private javax.swing.JComboBox operadorsCombo;
	private javax.swing.JTextPane operadorsInfo;
	private javax.swing.JPanel panelHillClimbing;
	private javax.swing.JPanel panelInici;
	private javax.swing.JPanel panelIniciNetwork;
	private javax.swing.JPanel panelIniciNetwork1;
	private javax.swing.JPanel panelIniciNetwork3;
	private javax.swing.JPanel panelIniciParams;
	private javax.swing.JPanel panelParams;
	private javax.swing.JPanel panelSimulatedAnnealing;
	private javax.swing.JTextField passosText;
	private javax.swing.JButton perDefecteParamsButton;
	private javax.swing.JButton simulatedAnButton;
	private javax.swing.JButton simulatedAnButton1;
	// End of variables declaration//GEN-END:variables
	private javax.swing.JLabel errorLabel;
}