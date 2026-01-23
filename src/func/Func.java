package func;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import classes.Cell;

public class Func {
	
    static JFrame frame = new JFrame("Game Of Life");

	// Colores
	static Color muerta = new Color(50, 50, 50);
	static Color viva = new Color(255, 255, 235);
	
	static int cellSize = 20;
	
	static int tasaDeRefrescoMiliSegundos = 100;
	
	public static Cell[][] inicializarJuego() {
		/* Configurar Pantalla */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar app al salir
        // Maximiza tanto horizontal como verticalmente
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        
        /* Panel principal para organizar el botón y la rejilla */
        //JPanel panelPrincipal = new JPanel(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        frame.setContentPane(layeredPane);
        
        /* Obtener dimensiones de la pantalla para calcular celdas */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Calculamos dimensiones para llenar el rectángulo de la pantalla
        int numFilas = screenSize.height / cellSize;
        int numCols = screenSize.width / cellSize;

        /* Configurar Layout de rejilla (filas, columnas) */
        //frame.setLayout(new GridLayout(numFilas, numCols));
        JPanel gridPanel = new JPanel(new GridLayout(numFilas, numCols));
        gridPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        
        /* Crear la matriz bidimensional rectangular */
        Cell[][] matrizCelulas = new Cell[numFilas][numCols];
        
		/* Llena la pantalla de cell */
        for (int f = 0; f < numFilas; f++) {
            for (int c = 0; c < numCols; c++) {
                Cell cell = new Cell(f, c);
                
                // Guardamos la referencia en la matriz de dos dimensiones
                matrizCelulas[f][c] = cell;
                
                // Añadimos el objeto a la pantalla
                gridPanel.add(cell);
            }
        }
        
        // Setear timer
        Timer timerJuego = new Timer(tasaDeRefrescoMiliSegundos, event -> ejecutarCiclo(matrizCelulas, numFilas, numCols));
        
        List<JButton> botones = new ArrayList<JButton>();
        
        /* Panel inferior para boton flotante */
        int btnAncho = 150;
        int btnAlto = 40; 
        // Coordenada X: Ancho total - ancho botón - margen
        int posX = screenSize.width - btnAncho - 20; 
        // Coordenada Y: (Alto total / 2) - (Mitad del botón) para centrarlo
        int posY = (screenSize.height / 2) - (btnAlto / 2);

        // --- BOTÓN INICIAR ---
        JButton btnIniciar = new JButton("Iniciar");
        botones.add(btnIniciar);
        btnIniciar.setBounds(posX, posY - 60, btnAncho, btnAlto);
                
        // Añadir listener
        btnIniciar.addActionListener(event -> {
            if (timerJuego.isRunning()) {
                timerJuego.stop();
                btnIniciar.setText("Iniciar");
            } else {
                timerJuego.start();
                btnIniciar.setText("Pausar");
            }
        });
        
        // --- BOTÓN LIMPIAR ---
        JButton btnLimpiar = new JButton("Limpiar");
        botones.add(btnLimpiar);
        // Lo posicionamos justo debajo del otro botón
        btnLimpiar.setBounds(posX, posY, btnAncho, btnAlto);
        
        btnLimpiar.addActionListener(e -> {
            timerJuego.stop(); // Detener el tiempo
            btnIniciar.setText("Iniciar");
            
            // Poner todas las celdas a muertas (Gris)
            for (int f = 0; f < numFilas; f++) {
                for (int c = 0; c < numCols; c++) {
                    matrizCelulas[f][c].setBackground(muerta);
                    matrizCelulas[f][c].setAlive(false);
                }
            }
        });
        
        // BOTÓN ALEATORIO
        JButton btnAleatorio = new JButton("Aleatorio");
        botones.add(btnAleatorio);
        // Lo posicionamos arriba de todos los botones
        btnAleatorio.setBounds(posX, posY - 120, btnAncho, btnAlto);
        
        btnAleatorio.addActionListener(e -> {
            // Poner todas las celulas vivas o muertas aleatoriamente
            Random rand = new Random();
            for (Cell[] fila : matrizCelulas) {
                for (Cell c : fila) {
                    c.setAlive(rand.nextBoolean() ? true : false);
                    c.setBackground(c.isAlive() ? viva : muerta);
                }
            }
        });
        
        for (JButton boton : botones) {
	        // Estética para resaltar sobre las celdas
	        boton.setBackground(new Color(100, 100, 100));
	        boton.setForeground(Color.WHITE);
	        boton.setFocusPainted(false);
	        boton.setBorder(BorderFactory.createEtchedBorder());
	        boton.setFont(new Font("Comfort", Font.BOLD, 14));
        }        

        // --- SLIDER TIEMPO ---
        // Rango de 0ms (rápido) a 500ms (lento)
        JSlider sliderVelocidad = new JSlider(JSlider.HORIZONTAL, 0, 500, tasaDeRefrescoMiliSegundos);
        sliderVelocidad.setBounds(posX - 5, posY + 60, 160, 50);
        sliderVelocidad.setInverted(true); // Derecha = Valor pequeño (más rápido), Izquierda = Valor grande (lento)
        sliderVelocidad.setOpaque(false);
        
        sliderVelocidad.addChangeListener(e -> {
            tasaDeRefrescoMiliSegundos = sliderVelocidad.getValue();
            timerJuego.setDelay(tasaDeRefrescoMiliSegundos); // Ajusta la velocidad del timer en tiempo real
        });
        
        // --- ETIQUIETA SLIDER ---
        JLabel lblVelocidad = new JLabel("Velocidad", SwingConstants.CENTER);
        lblVelocidad.setForeground(Color.WHITE);
        lblVelocidad.setBounds(posX - 5, posY + 55, 160, 20);
        
        /* Añadir al panel de capas especificando la profundidad */
        layeredPane.add(gridPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(btnIniciar, JLayeredPane.PALETTE_LAYER); 
        layeredPane.add(btnLimpiar, JLayeredPane.PALETTE_LAYER); 
        layeredPane.add(btnAleatorio, JLayeredPane.PALETTE_LAYER); 
        layeredPane.add(sliderVelocidad, JLayeredPane.PALETTE_LAYER);   
        layeredPane.add(lblVelocidad, JLayeredPane.PALETTE_LAYER);    
        
        /* Ultimo: Muestra el frame */
        frame.setVisible(true);
        layeredPane.revalidate();
        layeredPane.repaint();
        
        return matrizCelulas; // Devolver matriz
	}
	
	private static Object ejecutarCiclo(Cell[][] matrizCelulas, int numFilas, int numCols) {

        // Matriz temporal para guardar los cambios (la actualización debe ser simultánea)
        boolean[][] proximoEstado = new boolean[numFilas][numCols];

        for (int f = 0; f < numFilas; f++) {
            for (int c = 0; c < numCols; c++) {
                int vivasVecinas = contarVivas(matrizCelulas, f, c, numFilas, numCols);
                // se aplican estas reglas si la cell esta viva
                if (matrizCelulas[f][c].isAlive()) {
                    // Regla: Si tiene menos de 2 vivas a su alrededor, muere (o sigue muerta)
                	// Regla: Si tiene mas de 3 vivas a su alrededor, muere (o sigue muerta)
                    if (vivasVecinas < 2 || vivasVecinas > 3) {
                        proximoEstado[f][c] = false; // Muerta
                    } else {
                        // Se queda viva
                        proximoEstado[f][c] = true;
                    }
                } else { // si la cell esta muerta...
                	// Regla: Si tiene justo 3 vivas a su alrededor, revive
                    if (vivasVecinas == 3) {
                        proximoEstado[f][c] = true; // Revive
                    } else {
                        // Por ahora mantenemos su estado actual si no cumple la regla
                        proximoEstado[f][c] = matrizCelulas[f][c].isAlive();
                    }
                }
            }
        }

        // Aplicar cambios a la interfaz
        for (int f = 0; f < numFilas; f++) {
            for (int c = 0; c < numCols; c++) {
            	matrizCelulas[f][c].setBackground(proximoEstado[f][c] ? viva : muerta);
            	matrizCelulas[f][c].setAlive(proximoEstado[f][c] ? true : false);
            }
        }
    
		return null;
	}

	private static int contarVivas(Cell[][] matriz, int f, int c, int maxF, int maxC) {
        int vivas = 0;
        for (int fila = -1; fila <= 1; fila++) {
            for (int columna = -1; columna <= 1; columna++) {
                if (fila == 0 && columna == 0) continue; // Saltarse a sí misma
                int nf = f + fila;
                int nc = c + columna;
                // Verificar límites de la matriz
                if (nf >= 0 && nf < maxF && nc >= 0 && nc < maxC) {
                    if (matriz[nf][nc].isAlive()) {
                        vivas++;
                    }
                }
            }
        }
        return vivas;
    }
	
}
