package node;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author admin
 */
import models.Registering;

public class RegisteringNode {

    public Registering info;
    public RegisteringNode next;

    public RegisteringNode(Registering info) {
        this.info = info;
        this.next = null;
    }
}
