package proj;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;


public class Main {

    public static boolean keepAlive;
    private static Scanner in;

    public static void main(String[] args) throws LinhaIncorretaException, IOException {
        //Iniciar objetos
        Parser p = new Parser();
        in = new Scanner(System.in);


        keepAlive = true;
        while (keepAlive == true) {
            System.out.println("\nEnter one of the following options: " + "\n1. Start game" + "\n2. Display Saved Games" + "\n3. Manage Team" + "\n4. Create Team" + "\n5. Create Player" + "\n6. Credits\n7. Exit\n");
            // string name é a capturada
            String stdName = in.nextLine();
            switch (stdName) {
                case "1":
                    System.out.println("Start");
                    //Parser.parse();
                    Jogo j = Parser.startGame();
                    save(j);
                    //keepAlive = false;

                    break;
                case "2":
                    // load save
                    System.out.println("Saved Games: ");
                    Parser.loadSave();
                    break;
                case "3":
                    associatePlayer();
                    break;
                case "4":
                    createTeam();
                    keepAlive = false;
                    break;
                case "5":
                    createPlayer();
                    break;
                case "6":
                    // Créditos
                    System.out.println("Feito por: Cristiano Ronaldo");
                    keepAlive = false;
                    break;
                case "7":
                    // Leave Game
                    keepAlive = false;
                    break;
                default:
                    // it will end the program
                    System.out.println("Invalid option");
                    keepAlive = false;
                    in.close();

            }
        }
    }
    //3. Manage team
    private static void associatePlayer() throws IOException {
        System.out.println("Nome do jogador a transferir ?");
        String nome = in.nextLine();
        System.out.println("Equipa de destino ?");
        String destino = in.nextLine();

        String fullLine = getFullData(nome);
        System.out.println(fullLine);

        updateDatabase(fullLine,"");
        updateDatabase("Equipa:"+destino, "Equipa:"+destino+"\n"+fullLine);
    }
    //4. Create team
    private static void createTeam() {
        System.out.println("Nome da equipa:");
        String name = in.nextLine();
        try {
            String content = "\nEquipa:" + name;
            Files.write(Paths.get("src/proj/Jogadores.txt"), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    //5. Create Player
    private static void createPlayer() throws IOException {
        //Recebe estatísticas
        System.out.println("Nome do jogador:");
        String name = in.nextLine();
        System.out.println("Nº Jogador: ");int number = in.nextInt();
        System.out.println("Velocidade: ");int velocidade = in.nextInt();
        System.out.println("Resistência: ");int resistencia = in.nextInt();
        System.out.println("Destreza: ");int destreza = in.nextInt();
        System.out.println("Impulsao: ");int impulsao = in.nextInt();
        System.out.println("Cabeça: ");int cabeca = in.nextInt();
        System.out.println("Remate: ");int remate = in.nextInt();
        System.out.println("Passe: ");int passe = in.nextInt();


        //Recebe posição
        System.out.println("Posição: \n1. Avançado\n2. Defesa\n3. Medio\n4. Lateral\n5. Guarda-Redes \n");
        int posicao = in.nextInt();
        int recuperacao = 0, cruzamento = 0, elasticidade = 0;
        String posicaoString = null;

            //Dependendo da posição escrever "Posição: Nome,vel,res,destr, etc..
        switch (posicao) {
            case 1:
                posicaoString = "Avancado";
                break;
            case 2:
                posicaoString = "Defesa";
                break;
            case 3:
                posicaoString = "Medio";
                System.out.println("Recuperação: ");
                recuperacao = in.nextInt();
                break;
            case 4:
                posicaoString = "Lateral";
                System.out.println("Cruzamento: ");
                cruzamento = in.nextInt();
                break;
            case 5:
                posicaoString = "Guarda-Redes";
                System.out.println("Elasticidade: ");
                elasticidade = in.nextInt();
                break;
            default:
                System.out.println("Posição inexistente");
                break;
        }

        //nao há perigo de posicaoString nao estar incializada, pois caso contrário não muda a database
        if (posicao >= 1 && posicao <= 5) {
            if (posicao == 1 || posicao == 2) { updateDatabase("Equipa:Temp", "Equipa:Temp\n"+posicaoString+":"+name+","+number+","+velocidade+","+resistencia+","+destreza+","+impulsao+","+cabeca+","+ remate +","+passe);}
            else if (posicao == 3) {updateDatabase("Equipa:Temp", "Equipa:Temp\n"+posicaoString+":"+name+","+number+","+velocidade+","+resistencia+","+destreza+","+impulsao+","+cabeca+","+ remate +","+passe+","+recuperacao);}
            else if (posicao == 4) {updateDatabase("Equipa:Temp", "Equipa:Temp\n"+posicaoString+":"+name+","+number+","+velocidade+","+resistencia+","+destreza+","+impulsao+","+cabeca+","+ remate +","+passe+","+cruzamento);}
            else {updateDatabase("Equipa:Temp", "Equipa:Temp\n"+posicaoString+":"+name+","+velocidade+","+number+","+resistencia+","+destreza+","+impulsao+","+cabeca+","+ remate +","+passe+","+elasticidade);}
        }
    }

    //Se não existir equipa não realiza alterações mas não retorna erro
    public static void updateDatabase(String regex, String replacement) throws IOException {
        StringBuffer buffer = new StringBuffer();
        Scanner fileinput = new Scanner(new File("src/proj/Jogadores.txt"));
        //Ler linhas e adicionar ao StringBuffer
        while (fileinput.hasNextLine()){
            buffer.append(fileinput.nextLine() + System.lineSeparator());
        }
        String fileContent = buffer.toString();
        fileinput.close();

        fileContent = fileContent.replaceAll(regex,replacement);

        FileWriter writer = new FileWriter("src/proj/Jogadores.txt");
        writer.append(fileContent);
        writer.flush();
    }

    //obter linha inteira onde ocorre o nome do jogador
    public static String getFullData(String palavra) throws IOException, NullPointerException {
        Scanner fileInput = new Scanner(new File("src/proj/Jogadores.txt"));
        while (fileInput.hasNextLine()){
            String line = fileInput.nextLine();
            if (line.contains(palavra)){ return line; }
        }
        return "nada";
    }

    public static void save(Jogo j){
        System.out.println("Pretende gravar o jogo ? (1 se sim)");
        int grav = in.nextInt();

        if (grav == 1){
            try {
                String content = j.toString()+"\n";
                Files.write(Paths.get("src/proj/Jogadores.txt"), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
