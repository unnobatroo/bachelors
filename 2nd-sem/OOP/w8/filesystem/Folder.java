/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filesystem;

/**
 *
 * @author famil
 */
import java.util.ArrayList;
import java.util.List;

public class Folder extends Registration {
    private final List<Registration> items = new ArrayList<>();

    @Override
    public int getSize() {
        int totalSize = 0;
        for (Registration item : items) {
            totalSize += item.getSize();
        }
        return totalSize;
    }

    public void add(Registration r) {
        items.add(r);
    }

    public boolean remove(Registration r) {
        return items.remove(r);
    }
}
