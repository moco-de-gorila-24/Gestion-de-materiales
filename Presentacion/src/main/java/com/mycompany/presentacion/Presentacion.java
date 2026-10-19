/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.presentacion;

/**
 *
 * @author luis
 */
public class Presentacion {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new VerificarDisponibilidad().setVisible(true);
        });
    }
}
