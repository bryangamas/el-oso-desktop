package com.bgamas;

/**
 *
 * @author Bryan
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.armedbear.lisp.*;

public class ConexionLisp {
    private static org.armedbear.lisp.Package defaultPackage;
    private static Interpreter interpreter;
    private static OsoFrame osoFrame;
    private static Timer timer1 = new Timer(200, evt -> jugadaMaquina());

    private ConexionLisp() {

    }

    public static void inicializarInterprete() {
        if (interpreter == null)
            interpreter = Interpreter.createInstance();
        interpreter.eval("(load \"Codigo-oso.LISP\")");
        defaultPackage = Packages.findPackage("CL-USER");
    }

    public static void inicializar(OsoFrame osoFrame) {
        ConexionLisp.osoFrame = osoFrame;
        osoFrame.setColorBtn();
        int n = ConexionLisp.osoFrame.getN();
        try {
            Symbol voidsym = defaultPackage.findAccessibleSymbol("INICIALIZAR");
            Function voidFunction = (Function) voidsym.getSymbolFunction();

            switch (osoFrame.getTurno()) {
                case TurnoFrame.TURNO_HUMANO:
                    voidFunction.execute(Fixnum.getInstance(n), interpreter.eval("'H"));

                    break;
                case TurnoFrame.TURNO_MAQUINA:
                    voidFunction.execute(Fixnum.getInstance(n), interpreter.eval("'C"));
                    break;
                default: /* TURNO_AZAR */
                    int aux = new Random().nextInt(1);
                    if (aux == 0) {
                        JOptionPane.showMessageDialog(null, "¡Tú empiezas!");
                        voidFunction.execute(Fixnum.getInstance(n), interpreter.eval("'H"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Empieza la maquina");
                        voidFunction.execute(Fixnum.getInstance(n), interpreter.eval("'C"));
                    }
                    break;
            }

        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    static ArrayList<JButton> construirTablero(int n) {
        ArrayList<JButton> botones = new ArrayList<>();
        SimpleArray_T tableroLisp = ((SimpleArray_T) interpreter.eval("*tablero*").javaInstance());
        JPanel pnlTablero = osoFrame.getPnlTablero();
        pnlTablero.removeAll();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                LispObject letra = tableroLisp.AREF(n * i + j).STRING().CHAR(0);
                JButton btnAux = new JButton();
                if (!letra.javaInstance().equals('N')) { // Si es diferente de NIL
                    btnAux.setText("" + (Character) letra.javaInstance());
                }
                btnAux.setFont(new java.awt.Font("Tahoma", 0, 20));
                btnAux.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        desactivarBoton(btnAux);
                    }

                    private void desactivarBoton(JButton x) {
                        if (!x.isSelected() && osoFrame.getTeclaActual() != null
                                && osoFrame.getTurno() == TurnoFrame.TURNO_HUMANO) {
                            osoFrame.getBtnTecla().requestFocusInWindow();
                            x.setSelected(true);
                            x.setFocusable(false);
                            x.setText(osoFrame.getTeclaActual().toString());
                            ConexionLisp.jugadaHumano(osoFrame.getTeclaActual(), osoFrame.coordenadasBoton(btnAux));

                        }
                    }
                });
                pnlTablero.add(btnAux);
                botones.add(btnAux);
            }
        }
        pnlTablero.updateUI();
        return botones;
    }

    private static void jugadaHumano(Character teclaActual, int[] coordenadasBoton) {
        Symbol voidsym = defaultPackage.findAccessibleSymbol("" + teclaActual);
        Function function = (Function) voidsym.getSymbolFunction();
        function.execute(Fixnum.getInstance(coordenadasBoton[0]), Fixnum.getInstance(coordenadasBoton[1]));
        osoFrame.pintar(getPintar());
        siguienteJugada();
    }

    private static ArrayList<int[]> getPintar() {
        LispObject[] lista = interpreter.eval("*pintar*").copyToArray();
        ArrayList<int[]> casillasAPintar = new ArrayList<>();
        for (LispObject aux1 : lista) {
            LispObject[] par = aux1.copyToArray();
            casillasAPintar.add(new int[] { par[0].intValue(), par[1].intValue() });
        }
        return casillasAPintar;
    }

    private static void jugadaMaquina() {
        osoFrame.bloquearSO();
        LispObject[] aux = interpreter.eval("(pem)").copyToArray();
        osoFrame.jugadaMaquina((char) aux[0].STRING().CHAR(0).javaInstance(), aux[1].intValue(), aux[2].intValue());
        osoFrame.pintar(getPintar());
        timer1.stop();
        siguienteJugada();
    }

    public static void siguienteJugada() {
        actualizarTurno();
        actualizarPuntos();
        switch (osoFrame.getTurno()) {
            case TurnoFrame.TURNO_MAQUINA:
                timer1.start();
                break;
            case TurnoFrame.TURNO_HUMANO:
                osoFrame.desbloquearSO();
                break;
            default:
                int auxi = (Integer) interpreter.eval("(verificar)").intValue();
                String mensaje;
                switch (auxi) {
                    case 1:
                        mensaje = "Ud. ha ganado";
                        break;
                    case 2:
                        mensaje = "¡Te gané!";
                        break;
                    default:
                        mensaje = "Ha sido un empate...";
                        break;
                }
                JOptionPane.showMessageDialog(null, mensaje);
                new InicioFrame().setVisible(true);
                osoFrame.dispose();
        }
    }

    private static void actualizarTurno() {
        char aux = (char) interpreter.eval("*turno*").STRING().CHAR(0).javaInstance();
        switch (aux) {
            case 'C':
                osoFrame.setTurno(TurnoFrame.TURNO_MAQUINA);
                break;
            case 'H':
                osoFrame.setTurno(TurnoFrame.TURNO_HUMANO);
                break;
            default:
                osoFrame.setTurno(TurnoFrame.NO_TURNO);
        }
    }

    private static void actualizarPuntos() {
        int ph = interpreter.eval("*puntos-humano*").intValue();
        int pc = interpreter.eval("*puntos-maquina*").intValue();
        osoFrame.actualizarPuntos(ph, pc);
    }
}
