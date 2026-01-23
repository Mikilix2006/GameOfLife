package classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

//La clase Cell es tanto el objeto lógico como el componente gráfico
public class Cell extends JPanel {

	public boolean alive = false; // muerta por defecto
	public int fila, columna;
	
	// Colores
	Color muerta = new Color(50, 50, 50);
	Color viva = new Color(255, 255, 235);
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}


	// componente grafico
	public Cell(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
		

		// Propiedades de la celula
		this.setBackground(muerta);
		this.setBorder(BorderFactory.createLineBorder(new Color(25, 25, 25))); // Borde para que se distinga

		/* Añadir el evento de clic */
		this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Obtenemos el color actual y lo comparamos
            	setBackground(isAlive() ? muerta : viva); // invertir su color
                setAlive(isAlive() ? false : true); // invertir su estado se vida
                // Forzar actualización visual (opcional en la mayoría de casos)
                repaint();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
            	if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
                    // Obtenemos el color actual y lo comparamos
                	setBackground(isAlive() ? muerta : viva); // invertir su color
                    setAlive(isAlive() ? false : true); // invertir su estado se vida
                    // Forzar actualización visual (opcional en la mayoría de casos)
                    repaint();
                }
            }
        });
		
	}
	
	public void cambiarEstadoDeCell() {
        // Al arrastrar, pintamos la celda de blanco (viva)
        setBackground(isAlive() ? muerta : viva); // invertir su color
        setAlive(isAlive() ? false : true); // invertir su estado se vida
        // Forzar actualización visual (opcional en la mayoría de casos)
        repaint();
	}
}
