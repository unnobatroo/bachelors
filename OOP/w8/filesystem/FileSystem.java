/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package filesystem;

/**
 *
 * @author famil
 */
public class FileSystem {
    public static void main(String[] args) {
        File file1 = new File(10);
        File file2 = new File(20);
        
        Folder folder = new Folder();
        folder.add(file1);
        folder.add(file2);
        
        Folder folder2 = new Folder();
        folder.add(folder2);
        folder2.add(new File(4));
        folder2.add(new File(8));
        
        System.out.println("Total size of folder: " + folder.getSize());
    }
}
