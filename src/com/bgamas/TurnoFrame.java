package com.bgamas;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.bgamas.util.FontsUtil;

/**
 *
 * @author Bryan
 */
public class TurnoFrame extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static final int NO_TURNO = 0;
    static final int TURNO_HUMANO = 1;
    static final int TURNO_MAQUINA = 2;
    static final int TURNO_AZAR = 3;
    private final int n;

    /**
     * Creates new form InicioFrame
     * 
     * @param n
     */
    public TurnoFrame(int n) {
        initComponents();
        this.n = n;
        this.setLocationRelativeTo(null);
    }

    public void iniciar(int n, int turno) {
        new OsoFrame(n, turno).setVisible(true);
        dispose();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JButton btnAzar;
        JButton btnHumano;
        JButton btnMaquina;
        JLabel jLabel1;
        JLabel jLabel2;
        JPanel jPanel1;

        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        btnMaquina = new JButton();
        btnAzar = new JButton();
        btnHumano = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font(FontsUtil.COOPER_BLACK, 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("EL OSO");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(170, 50, 148, 42);

        jLabel2.setFont(new java.awt.Font(FontsUtil.COOPER_BLACK, 0, 24)); // NOI18N
        jLabel2.setText("¿Quién empieza?");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(140, 110, 210, 28);

        btnMaquina.setFont(new java.awt.Font(FontsUtil.COMIC_SANS_MS, 1, 30)); // NOI18N
        btnMaquina.setText("La máquina");
        btnMaquina.addActionListener(evt -> iniciar(n, TURNO_MAQUINA));
        jPanel1.add(btnMaquina);
        btnMaquina.setBounds(120, 260, 240, 51);

        btnAzar.setFont(new java.awt.Font(FontsUtil.COMIC_SANS_MS, 1, 30)); // NOI18N
        btnAzar.setText("Al azar");
        btnAzar.addActionListener(evt -> iniciar(n, TURNO_AZAR));
        jPanel1.add(btnAzar);
        btnAzar.setBounds(120, 340, 240, 51);

        btnHumano.setFont(new java.awt.Font(FontsUtil.COMIC_SANS_MS, 1, 30)); // NOI18N
        btnHumano.setText("¡Yo empiezo!");
        btnHumano.addActionListener(evt -> iniciar(n, TURNO_HUMANO));
        jPanel1.add(btnHumano);
        btnHumano.setBounds(120, 180, 240, 51);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE).addContainerGap()));

        pack();
    }// </editor-fold>//GEN-END:initComponents

}
