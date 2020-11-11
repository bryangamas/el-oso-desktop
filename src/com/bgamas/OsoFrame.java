package com.bgamas;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.bgamas.util.FontsUtil;

/**
 *
 * @author Bryan
 */
public class OsoFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 8034023891787630674L;
    private int n;
    private int turno;
    private Character teclaActual;
    private ArrayList<JButton> tablero = new ArrayList<>(); // Lista de botones del tablero
    private Color colorBtnMaquina = new Color(255, 248, 124);
    private Color colorBtnHumano = new Color(43, 150, 226);
    private Color colorBtn;

    public OsoFrame(int n, int turno) {
        initComponents();
        this.n = n;
        this.turno = turno;
        this.setLocationRelativeTo(null);
        pnlTablero.setLayout(new GridLayout(n, n));
        ConexionLisp.inicializar(this);
        tablero = ConexionLisp.construirTablero(n);
        ConexionLisp.siguienteJugada();
    }

    public void jugadaMaquina(char letra, int x, int y) {
        JButton aux = tablero.get(x * n + y);
        aux.setText("" + letra);
        aux.setSelected(true);
        aux.setFocusable(false);
    }

    public void actualizarPuntos(int ph, int pc) {
        lblPuntosHumano.setText("" + ph);
        lblPuntosPC.setText("" + pc);
    }

    public int[] coordenadasBoton(JButton x) {
        int aux = tablero.indexOf(x);
        return new int[] { aux / n, aux % n };
    }

    public void despintar() {
        for (JButton aux : tablero) {
            aux.setBackground(colorBtn);
        }
    }

    public void pintar(List<int[]> casillasAPintar) {
        despintar();
        Color aux = (turno == TurnoFrame.TURNO_HUMANO) ? colorBtnHumano : colorBtnMaquina;
        for (int[] casilla : casillasAPintar) {
            tablero.get(casilla[0] * n + casilla[1]).setBackground(aux);
        }
    }

    public void bloquearSO() {
        btnO.setEnabled(false);
        btnS.setEnabled(false);
    }

    public void desbloquearSO() {
        btnO.setEnabled(true);
        btnS.setEnabled(true);
    }

    public int getN() {
        return n;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public JPanel getPnlTablero() {
        return pnlTablero;
    }

    public Character getTeclaActual() {
        return teclaActual;
    }

    public void setTeclaActual() {
        teclaActual = null;
    }

    public void setColorBtn() {
        colorBtn = btnS.getBackground();
    }

    public JButton getBtnTecla() {
        if (teclaActual == 'S') {
            return btnS;
        }
        return btnO;
    }

    private void initComponents() {

        JPanel pnlPrincipal;
        JLabel lblTxtTusPuntos;
        JLabel lblTxtPuntosMaquina;
        pnlTablero = new JPanel();
        pnlPrincipal = new JPanel();
        btnS = new JButton();
        btnO = new JButton();
        lblTxtTusPuntos = new JLabel();
        lblPuntosHumano = new JLabel();
        lblPuntosPC = new JLabel();
        lblTxtPuntosMaquina = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pnlTablero.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        GroupLayout pnlTableroLayout = new GroupLayout(pnlTablero);
        pnlTablero.setLayout(pnlTableroLayout);
        pnlTableroLayout.setHorizontalGroup(
                pnlTableroLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 500, Short.MAX_VALUE));
        pnlTableroLayout.setVerticalGroup(
                pnlTableroLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 500, Short.MAX_VALUE));

        pnlPrincipal.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnS.setFont(new java.awt.Font(FontsUtil.COMIC_SANS_MS, 1, 30)); // NOI18N
        btnS.setText("S");
        btnS.addActionListener(evt -> btnSActionPerformed());

        btnO.setFont(new java.awt.Font(FontsUtil.COMIC_SANS_MS, 1, 30)); // NOI18N
        btnO.setText("O");
        btnO.addActionListener(evt -> btnOActionPerformed());

        lblTxtTusPuntos.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblTxtTusPuntos.setText("Tus puntos:");

        lblPuntosHumano.setFont(new java.awt.Font(FontsUtil.COMIC_SANS_MS, 1, 36)); // NOI18N
        lblPuntosHumano.setHorizontalAlignment(SwingConstants.CENTER);
        lblPuntosHumano.setText("0");

        lblPuntosPC.setFont(new java.awt.Font(FontsUtil.COMIC_SANS_MS, 1, 36)); // NOI18N
        lblPuntosPC.setHorizontalAlignment(SwingConstants.CENTER);
        lblPuntosPC.setText("0");

        lblTxtPuntosMaquina.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblTxtPuntosMaquina.setText("Máquina:");

        GroupLayout pnlPrincipalLayout = new GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(pnlPrincipalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pnlPrincipalLayout.createSequentialGroup().addGroup(pnlPrincipalLayout
                        .createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup().addContainerGap()
                                .addGroup(pnlPrincipalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                                .addComponent(btnS, GroupLayout.PREFERRED_SIZE, 92,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 19,
                                                        Short.MAX_VALUE)
                                                .addComponent(btnO, GroupLayout.PREFERRED_SIZE, 92,
                                                        GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                                .addComponent(lblTxtTusPuntos).addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(pnlPrincipalLayout.createSequentialGroup().addGap(41, 41, 41)
                                .addGroup(pnlPrincipalLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblPuntosPC, GroupLayout.PREFERRED_SIZE, 149,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblPuntosHumano, GroupLayout.PREFERRED_SIZE, 149,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                .addGroup(pnlPrincipalLayout.createSequentialGroup().addContainerGap().addComponent(lblTxtPuntosMaquina)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pnlPrincipalLayout.setVerticalGroup(pnlPrincipalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup().addGap(18, 18, 18)
                        .addComponent(lblTxtTusPuntos).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPuntosHumano, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28).addComponent(lblTxtPuntosMaquina).addGap(18, 18, 18)
                        .addComponent(lblPuntosPC, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .addGroup(pnlPrincipalLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnS, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnO, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap()));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addComponent(pnlTablero, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(pnlPrincipal,
                                GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(pnlPrincipal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlTablero, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap()));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOActionPerformed() {// GEN-FIRST:event_btnOActionPerformed
        teclaActual = 'O';
        btnO.setBackground(new Color(120, 120, 120));
        btnO.setFocusPainted(true);
        btnS.setBackground(colorBtn);
    }// GEN-LAST:event_btnOActionPerformed

    private void btnSActionPerformed() {// GEN-FIRST:event_btnSActionPerformed
        teclaActual = 'S';
        btnS.setBackground(new Color(120, 120, 120));
        btnS.setFocusPainted(true);
        btnO.setBackground(colorBtn);

    }// GEN-LAST:event_btnSActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnO;
    private JButton btnS;
    private JLabel lblPuntosHumano;
    private JLabel lblPuntosPC;
    private JPanel pnlTablero;
    // End of variables declaration//GEN-END:variables

}
