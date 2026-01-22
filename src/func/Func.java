package func;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import classes.Cell;

public class Func {
	
    static JFrame frame = new JFrame("Game Of Life");

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
        int cellSize = 20;
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
        
        /* Panel inferior para boton flotante */
        JButton btnIniciar = new JButton("Iniciar");
        int btnAncho = 150;
        int btnAlto = 40; 
        // Coordenada X: Ancho total - ancho botón - margen
        int posX = screenSize.width - btnAncho - 20; 
        // Coordenada Y: (Alto total / 2) - (Mitad del botón) para centrarlo
        int posY = (screenSize.height / 2) - (btnAlto / 2);

        btnIniciar.setBounds(posX, posY, btnAncho, btnAlto);
        
        // Estética para resaltar sobre las celdas
        btnIniciar.setBackground(new Color(100, 100, 100));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setBorder(BorderFactory.createEtchedBorder());
        btnIniciar.setFont(new Font("Comfort", Font.BOLD, 14));
        
        // Añadir listener
        btnIniciar.addActionListener(event -> {
            System.out.println("Acción iniciada sobre la matriz...");
            // Aquí irá la lógica futura
        });
        
        /* Añadir al panel de capas especificando la profundidad */
        layeredPane.add(gridPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(btnIniciar, JLayeredPane.PALETTE_LAYER);        
        
        /* Ultimo: Muestra el frame */
        frame.setVisible(true);
        layeredPane.revalidate();
        layeredPane.repaint();
        
        return matrizCelulas; // Devolver matriz
	}
	
}
