/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package creatures;

/**
 *
 * @author bli
 */
public interface ITerrain {
    ITerrain change(Beetle p);
    ITerrain change(Splasher p);
    ITerrain change(Greenie p);
}
