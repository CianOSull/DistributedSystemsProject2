package ClientAndServer;

import Factory.flyHeroFactory;
import PowerPeople.flyHero;
import PowerPeople.flyVillain;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class client implements Runnable {
    flyHeroFactory factory = new flyHeroFactory();

    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socketTest6();
    }

    private void socketTest6(){
        try {
            Socket socket = new Socket("localhost", 8001);

            // get the ouput stream from the socket
            OutputStream outputStream = socket.getOutputStream();
            // create an objecto uput stream
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            boolean checkForVillain = true;
            System.out.println("Checking for a villain");
            objectOutputStream.writeObject("1");

            // get the input stream from the connected socket
            InputStream inputStream = socket.getInputStream();
            // create a DataInputStream so we can read data from it.
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            if((boolean) objectInputStream.readObject()){
                System.out.println("The villain Exists");

                flyVillain vil = (flyVillain) objectInputStream.readObject();
                System.out.println(vil.getName());

                flyHero flyHer = factory.getHero();
                ArrayList<Object> powerPeopleList = new ArrayList<>();
                powerPeopleList.add(vil);
                powerPeopleList.add(flyHer);

                System.out.println("Sending Arraylist to the ServerSocket");
                objectOutputStream.writeObject(powerPeopleList);

            }
            else {
                System.out.println("The vilain doesn't exist");
            }

        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}