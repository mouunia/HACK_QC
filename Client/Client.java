import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Client {

    private BufferedReader in;
    private  Socket socket;
    // pour envoyer un message vers le serveur avec le usernmae et le password
    PrintWriter output;
    Scanner reader = new Scanner(System.in);
    String JPG = ".jpg";

    @SuppressWarnings("resource")
    public void connectToServer() throws IOException {

        System.out.print("LANCEMENT DU CLIENT\n\n");
        boolean isIP = false;
        String serverAddress = "";

        // Get the server address from a dialog box.
        do{
            System.out.println("Entrez l'adresse IP du serveur:");

            serverAddress = reader.next();

            isIP = IPAddressValidator(serverAddress);

            if(!isIP)
            {
                System.out.println("Veuillez verifier l adresse ip entrée");
            }else{
                System.out.println("Adresse Ip Correcte");
            }

        }while(!isIP);


        //verification du port
        // Get the server address from a dialog box.
        int port = 0;
        boolean portBool = false;
        do{
            System.out.println("Entrez le port:");
            port = reader.nextInt();


            if(port >= 5000 && port <= 5050)
            {
                System.out.println("le port saisi est correct");
                portBool = true;


            }else{
                System.out.println("le port saisi n'est pas correct, veuillez ressayer");

            }

        }while(!portBool);

        this.socket = new Socket(serverAddress, port);

        //pour lire la reponse du serveur
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //pour envoyer un socket de string vers le serveur
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        boolean userIsChecked = false;
        do {
            System.out.println("Veuillez saisir le nom d utilisateur :");
            String username = reader.next();
            output.println(username);

            System.out.println("Veuillez saisir le mot de passe :");
            String password = reader.next();
            output.println(password);
            userIsChecked = Boolean.parseBoolean(in.readLine());


            if(userIsChecked){
                System.out.println(username + ", vous venez de vous connecter!");
            }
            else
                System.out.println("Erreur dans le mot de passe! Veuillez réentrer vos informations\n");
        }while(!userIsChecked);

    }

    //pour verifier une adresse ip selon un pattern
    public static boolean IPAddressValidator(String ip){

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        matcher = pattern.matcher(ip);

        return matcher.find();
    }

    public void sobelTreatment(){
        Scanner reader = new Scanner(System.in);

        System.out.println("Bienvenue au traitement d'images avec le filtre Sobel!\n" +
                "Vous pouvez entrer le nom de l'image sur laquelle vous voulez appliquer le filtre!\n" +
                "Donnez également le nom de l'image après le traitment\n" +
                "Mettre seulement le nom du fichier sans l'extension." +
                "\nEntrer '-1' comme nom d'image lorsque vous voulez quitter.\n\n");

        boolean isFinished = false;
        String fileName, newFileName = "";
        boolean exist = false;
        do{
            System.out.print("Veuillez entrer le nom de l'image à modifier: ");
            fileName = reader.next();
            if(fileName == "-1"){
                isFinished = true;
            }
            else {
                exist = new File(System.getProperty("user.dir") + File.separator + fileName + JPG).exists();
                if (!exist) {
                    System.out.println("L'image n'existe pas. Entrer le nom d'une image valide!\n");
                } else {
                    try {
                        displayImage(ImageIO.read(new File(System.getProperty("user.dir") + File.separator + fileName + JPG)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.print("Veuillez entrer le nom de la nouvelle modifiée: ");
                    newFileName = reader.next();
                    try {
                        output.println(fileName);
                        imageTreatment(fileName);
                        System.out.println("Le traitement de votre image est terminée!");

                        BufferedImage image = ImageIO.read(ImageIO.createImageInputStream(socket.getInputStream()));
                        File savedFile = saveFile(image, newFileName);
                        Image imageCreated = ImageIO.read(savedFile);
                        displayImage(imageCreated);
                        
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }while(!isFinished);
    }

    private void displayImage(Image image) {

    	System.out.println("Lecture de l image en cours...");
    	JFrame frame = new JFrame();
        frame.setSize(300, 300);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);
    	System.out.println("Affichage...");
    }

    private File saveFile(BufferedImage image, String fileName) {
        File file = new File(System.getProperty("user.dir") + File.separator + fileName + JPG);
        Image newImage = null;
        try {
            file.createNewFile();
            ImageIO.write(image, JPG, file);
            System.out.println("Votre image a été enregistrée à cet endroit " + file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private  void imageTreatment(String fileName) throws IOException{
        // TODO Auto-generated method stub
        File sourceimage = new File(System.getProperty("user.dir") + File.separator + fileName + JPG);
        Image image = ImageIO.read(sourceimage);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(sourceimage);
        System.out.println("Votre image " + fileName + JPG + " a été envoyée au serveur.");

    }
    /**
     * Runs the client application.
     */
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connectToServer();
        client.sobelTreatment();
        client.closeConnection();

    }



    private  void closeConnection() throws IOException {
        // TODO Auto-generated method stub
        socket.close();
        System.out.println("Vous avez terminé votre session");

    }
}