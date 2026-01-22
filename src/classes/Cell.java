package classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

//La clase Cell es tanto el objeto lógico como el componente gráfico
public class Cell extends JPanel {
	public boolean alive = true;
	public int fila, columna;

	// componente grafico
	public Cell(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
		
		// Colores
		Color muerta = new Color(50, 50, 50);
		Color viva = new Color(255, 255, 235);

		// Propiedades de la celula
		this.setBackground(muerta);
		this.setBorder(BorderFactory.createLineBorder(new Color(25, 25, 25))); // Borde para que se distinga

		/* Añadir el evento de clic */
		this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Obtenemos el color actual y lo comparamos
            	setBackground(getBackground().equals(viva) ? muerta : viva);
                
                // Forzar actualización visual (opcional en la mayoría de casos)
                repaint();
            }
        });
	}
}
