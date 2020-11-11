package com.bgamas;

import com.bgamas.util.FontsUtil;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 *
 * @author Bryan
 */
public class InicioFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InicioFrame() {
        ConexionLisp.inicializarInterprete();
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public void iniciar(int n) {
        new TurnoFrame(n).setVisible(true);
        dispose();
    }

    private void initComponents() {

        JButton btn10x10;
        JButton btn8x8;
        JButton btn6x6;
        JLabel jLabel1;
        JLabel jLabel2;
        JPanel jPanel1;

        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        btn6x6 = new JButton();
        btn8x8 = new JButton();
        btn10x10 = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font(FontsUtil.COOPER_BLACK, 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("EL OSO");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(170, 50, 148, 42);

        jLabel2.setFont(new java.awt.Font(FontsUtil.COOPER_BLACK, 0, 24)); // NOI18N
        jLabel2.setText("Elige la dimensión del tablero");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(70, 120, 370, 28);

        btn6x6.setFont(new java.awt.Font(FontsUtil.COMIC_SANS_MS, 1, 30)); // NOI18N
        btn6x6.setText("6x6");
        btn6x6.addActionListener(evt -> btn6x6ActionPerformed());
        jPanel1.add(btn6x6);
        btn6x6.setBounds(180, 180, 123, 51);

        btn8x8.setFont(new java.awt.Font(FontsUtil.COMIC_SANS_MS, 1, 30)); // NOI18N
        btn8x8.setText("8x8");
        btn8x8.addActionListener(evt -> btn8x8ActionPerformed());
        jPanel1.add(btn8x8);
        btn8x8.setBounds(180, 260, 123, 51);

        btn10x10.setFont(new java.awt.Font(FontsUtil.COMIC_SANS_MS, 1, 30)); // NOI18N
        btn10x10.setText("10x10");
        btn10x10.addActionListener(evt -> btn10x10ActionPerformed());
        jPanel1.add(btn10x10);
        btn10x10.setBounds(180, 340, 123, 51);

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

    private void btn6x6ActionPerformed() {// GEN-FIRST:event_btn6x6ActionPerformed
        iniciar(6);
    }// GEN-LAST:event_btn6x6ActionPerformed

    private void btn8x8ActionPerformed() {// GEN-FIRST:event_btn8x8ActionPerformed
        iniciar(8);
    }// GEN-LAST:event_btn8x8ActionPerformed

    private void btn10x10ActionPerformed() {// GEN-FIRST:event_btn10x10ActionPerformed
        iniciar(10);
    }// GEN-LAST:event_btn10x10ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InicioFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        java.awt.EventQueue.invokeLater(() -> new InicioFrame().setVisible(true));
    }

}
